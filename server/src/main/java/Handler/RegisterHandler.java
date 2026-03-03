package Handler;

import com.google.gson.Gson;
import dataaccess.DataAccessException;
import dataaccess.MemoryUserDAO;
import service.RegisterRequest;
import service.RegisterResult;
import service.UserService;
import io.javalin.http.Context;

public class RegisterHandler {
    public Context ctx;

    public RegisterHandler(Context ctx) throws DataAccessException {
        this.ctx = ctx;
    }

    public String getRegistered() throws DataAccessException {
        RegisterRequest request = new Gson().fromJson(ctx.body(), RegisterRequest.class);

        UserService service = new UserService(new MemoryUserDAO());
        RegisterResult result = service.register(request);

        return new Gson().toJson(result);
    }
}
