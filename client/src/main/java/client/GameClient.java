package client;

public class GameClient {

    String currUser;
    private final WebSocketFacade ws;

    public GameClient(WebSocketFacade ws, String currUser) {
        this.ws = ws;
        this.currUser = currUser;
    }

    public static void run() {

    }
}
