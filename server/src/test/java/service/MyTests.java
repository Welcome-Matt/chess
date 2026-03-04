package service;

import dataaccess.*;
import model.UserData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class MyTests {
    UserDAO userDAO = new MemoryUserDAO();
    AuthDAO authDAO = new MemoryAuthDAO();

    final UserService userService = new UserService(userDAO, authDAO);

    @BeforeEach
    void clear() throws DataAccessException {
        userService.clear();
    }

    @Test
    void register() throws DataAccessException {
        UserRequest request = new UserRequest("MrMan", "coolPassword", "email@me.com");
        userService.register(request);
        assertEquals(new UserData("MrMan", "coolPassword",
                "email@me.com"), userDAO.getUser("MrMan"));
    }

    @Test
    void badRegister() {
        assertThrows(DataAccessException.class, () ->
                userService.register(new UserRequest("MrMan", null, "email@me.com")));
    }


}
