package handler;

import org.eclipse.jetty.websocket.api.Session;
import websocket.messages.ServerMessage;

import java.io.IOException;
import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

public class ConnectionManager {
    public final HashMap<Integer, ConcurrentHashMap<Session, Session>> connections = new HashMap<>();

    public void add(Session session, int gameID) {
        if (connections.get(gameID) == null) {
            ConcurrentHashMap<Session, Session> connect = new ConcurrentHashMap<>();
            connect.put(session, session);
            connections.put(gameID, connect);
        } else {
            connections.get(gameID).put(session, session);
        }
    }

    public void remove(Session session, int gameID) {
        connections.get(gameID).remove(session);
    }

    public void broadcast(Session excludeSession, ServerMessage notification, int gameID) throws IOException {
        String msg = notification.toString();
        if (notification.getServerMessageType().equals(ServerMessage.ServerMessageType.NOTIFICATION)) {
            ConcurrentHashMap<Session, Session> connection = connections.get(gameID);
            for (Session c : connection.values()) {
                if (c.isOpen()) {
                    if (!c.equals(excludeSession)) {
                        c.getRemote().sendString(msg);
                    }
                }
            }
        } else if (notification.getServerMessageType().equals(ServerMessage.ServerMessageType.LOAD_GAME)) {
            if (excludeSession.isOpen()) {
                excludeSession.getRemote().sendString(msg);
            }
        } else if (notification.getServerMessageType().equals(ServerMessage.ServerMessageType.ERROR)) {
            if (excludeSession.isOpen()) {
                excludeSession.getRemote().sendString(msg);
            }
        }
    }

    public void broadcastAll(ServerMessage board, int gameID) throws IOException {
        ConcurrentHashMap<Session, Session> connection = connections.get(gameID);
        for (Session c : connection.values()) {
            if (c.isOpen()) {
                c.getRemote().sendString(board.toString());
            }
        }
    }

    public void sendNotification(Session session, ServerMessage notice) throws IOException {
        session.getRemote().sendString(notice.toString());
    }
}
