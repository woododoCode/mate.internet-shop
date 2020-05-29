package dev.internetshop.security;

import dev.internetshop.exceptions.AuthenticationException;
import dev.internetshop.model.User;

public interface AuthenticationService {
    User login(String login, String password) throws AuthenticationException;
}
