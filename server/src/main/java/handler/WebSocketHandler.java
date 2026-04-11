package handler;

import chess.InvalidMoveException;
import dataaccess.DataAccessException;
import model.GameData;
import server.Server;
import chess.ChessGame;
import com.google.gson.Gson;
import io.javalin.websocket.WsCloseContext;
import io.javalin.websocket.WsCloseHandler;
import io.javalin.websocket.WsConnectContext;
import io.javalin.websocket.WsConnectHandler;
import io.javalin.websocket.WsMessageContext;
import io.javalin.websocket.WsMessageHandler;
import org.eclipse.jetty.websocket.api.Session;
import websocket.commands.UserGameCommand;
import websocket.messages.ServerMessage;

import java.io.IOException;

public class WebSocketHandler implements WsConnectHandler, WsMessageHandler, WsCloseHandler {

    private final ConnectionManager connections = new ConnectionManager();
    private Server server;

    public WebSocketHandler(Server server) {
        this.server = server;
    }

    @Override
    public void handleConnect(WsConnectContext ctx) {
        System.out.println("Websocket connected");
        ctx.enableAutomaticPings();
    }

    @Override
    public void handleMessage(WsMessageContext ctx) {
        try {
            UserGameCommand command = new Gson().fromJson(ctx.message(), UserGameCommand.class);
            switch (command.getCommandType()) {
                case CONNECT -> enter(ctx.session, command);
                case MAKE_MOVE -> makeMove(ctx.session, command);
                case RESIGN -> resign(ctx.session, command);
                case LEAVE -> exit(ctx.session, command);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void handleClose(WsCloseContext ctx) {
        System.out.println("Websocket closed");
    }

    private void enter(Session session, UserGameCommand command) throws IOException {
        try {
            server.authenticate(command.getAuthToken());
            connections.add(session, command.getGameID());
            var board = new ServerMessage(ServerMessage.ServerMessageType.LOAD_GAME);
            try {
                ChessGame game = server.getGame(command.getGameID()).game();
                board.setGame(game);
            } catch (Exception ex) {
                throw new DataAccessException("Error: Invalid game");
            }

            connections.broadcast(session, board, command.getGameID());
            var notification = new ServerMessage(ServerMessage.ServerMessageType.NOTIFICATION);
            notification.setMessage(command.getUsername() + " has entered!");
            connections.broadcast(session, notification, command.getGameID());
        } catch (DataAccessException ex) {
            var error = new ServerMessage(ServerMessage.ServerMessageType.ERROR);
            error.setErrorMessage(ex.getMessage());
            connections.broadcast(session, error, command.getGameID());
        }
    }

    private void makeMove(Session session, UserGameCommand command) throws IOException {
        try {
            server.authenticate(command.getAuthToken());
            GameData data = server.getGame(command.getGameID());
            ChessGame game = data.game();
            game.makeMove(command.getMove());
            server.updateGame(command.getGameID(), game);

            var board = new ServerMessage(ServerMessage.ServerMessageType.LOAD_GAME);
            ChessGame chessGame = server.getGame(command.getGameID()).game();
            board.setGame(chessGame);
            connections.broadcastAll(board, command.getGameID());

            var notification = new ServerMessage(ServerMessage.ServerMessageType.NOTIFICATION);
            notification.setMessage(command.getUsername() + " made move ");
            connections.broadcast(session, notification, command.getGameID());
        } catch (Exception ex) {
            var error = new ServerMessage(ServerMessage.ServerMessageType.ERROR);
            error.setErrorMessage(ex.getMessage());
            connections.broadcast(session, error, command.getGameID());
        }
    }

    private void resign(Session session, UserGameCommand command) throws IOException {
        try {
            GameData data = server.getGame(command.getGameID());
            if (data.game().getTeamTurn().equals(ChessGame.TeamColor.NONE)) {
                var notification = new ServerMessage(ServerMessage.ServerMessageType.ERROR);
                notification.setErrorMessage("The game has ended.");
                connections.sendNotification(session, notification);
            } else {
                String username = server.getAuth(command.getAuthToken()).username();
                if (!username.equals(data.whiteUsername()) &&
                        !username.equals(data.blackUsername())) {
                    var error = new ServerMessage(ServerMessage.ServerMessageType.ERROR);
                    error.setErrorMessage("Error: Invalid command");
                    connections.broadcast(session, error, command.getGameID());
                }

                ChessGame game = data.game();
                game.setTeamTurn(ChessGame.TeamColor.NONE);
                server.updateGame(command.getGameID(), game);
                var notification = new ServerMessage(ServerMessage.ServerMessageType.NOTIFICATION);
                notification.setMessage(command.getUsername() + " has resigned, GAME OVER!");
                connections.broadcastAll(notification, command.getGameID());
            }
        } catch (DataAccessException ex) {
            var error = new ServerMessage(ServerMessage.ServerMessageType.ERROR);
            error.setErrorMessage(ex.getMessage());
            connections.broadcast(session, error, command.getGameID());
        }
    }

    private void exit(Session session, UserGameCommand command) throws IOException {
        var notification = new ServerMessage(ServerMessage.ServerMessageType.NOTIFICATION);
        notification.setMessage(command.getUsername() + " has left!");
        connections.broadcast(session, notification, command.getGameID());
        connections.remove(session, command.getGameID());
    }
}
