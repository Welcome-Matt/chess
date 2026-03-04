package Handler;

import com.google.gson.Gson;
import dataaccess.DataAccessException;
import service.UserRequest;
import service.UserResult;
import service.UserService;
import io.javalin.http.Context;

public class UserHandler {
    public Context ctx;
    public final UserService userService;

    public UserHandler(Context ctx, UserService userService) {
        this.ctx = ctx;
        this.userService = userService;
    }

    public String getRegistered() throws DataAccessException {
        UserRequest request = new Gson().fromJson(ctx.body(), UserRequest.class);
        UserResult result = userService.register(request);
        return new Gson().toJson(result);
    }

    public String getLoggedIn() throws DataAccessException {
        UserRequest request = new Gson().fromJson(ctx.body(), UserRequest.class);
        UserResult result = userService.login(request);
        return new Gson().toJson(result);
    }
}
