package com.webtech.controller.command.common;

import com.webtech.controller.command.Command;
import com.webtech.controller.command.CommandResult;
import com.webtech.entity.User;
import com.webtech.exception.ServiceException;
import com.webtech.service.UserService;
import com.webtech.util.Validation;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;


public class SignUpCommand implements Command {

    private static final String START_PAGE = "controller?command=startLogin";
    private static final String LOGIN_PAGE = "/WEB-INF/pages/login.jsp";
    private static final String SIGN_UP_ERROR = "signUpError";
    private static final String USERNAME = "username";
    private static final String PASSWORD = "password";

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) throws ServiceException {
        String login = request.getParameter(USERNAME);
        String password = request.getParameter(PASSWORD);

        Map<String, String> signUpData = new HashMap<>();
        signUpData.put(USERNAME, login);
        Validation validation = new Validation();
        if (!validation.isValid(signUpData)) {
            String errorName = validation.getInvalidData();
            request.setAttribute(SIGN_UP_ERROR, errorName);
            return CommandResult.forward(LOGIN_PAGE);
        }

        UserService userService = new UserService();
        Optional<User> optionalUser = userService.findByUsername(login);
        if (optionalUser.isPresent()) {
            request.setAttribute(SIGN_UP_ERROR, USERNAME);
            return CommandResult.forward(LOGIN_PAGE);
        }
        userService.signUpUser(null, login, password);
        return CommandResult.redirect(START_PAGE);
    }
}
