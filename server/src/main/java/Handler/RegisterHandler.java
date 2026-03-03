package Handler;

import com.google.gson.Gson;
import dataaccess.DataAccessException;
import service.RegisterRequest;
import service.RegisterResult;
import service.UserService;
import io.javalin.http.Context;

public class RegisterHandler {
    public Context ctx;
    public final UserService userService;

    public RegisterHandler(Context ctx, UserService userService) {
        this.ctx = ctx;
        this.userService = userService;
    }

    public String getRegistered() throws DataAccessException {
        RegisterRequest request = new Gson().fromJson(ctx.body(), RegisterRequest.class);

        //UserService service = new UserService();
        RegisterResult result = userService.register(request);


        return new Gson().toJson(result);
    }
}
