package ru.kpfu.group903.safiullin.controllers;

import ru.kpfu.group903.safiullin.exceptions.LoginException;
import ru.kpfu.group903.safiullin.exceptions.NoSuchUserException;
import ru.kpfu.group903.safiullin.models.User;
import ru.kpfu.group903.safiullin.services.UserService;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/login")
public class LoginController extends HttpServlet {
    private UserService userService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        ServletContext servletContext = config.getServletContext();
        userService = (UserService) servletContext.getAttribute("userService");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("WEB-INF/views/login.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String email = req.getParameter("email");
        String password = req.getParameter("password");
        User user = User.builder()
                .email(email)
                .password(password)
                .build();
        try {
            user = userService.auth(user, req);
            resp.addCookie(new Cookie("userName", user.getName()));
            resp.sendRedirect(getServletContext().getContextPath() + "/wallet");
            req.getSession().setAttribute("user", user);
        } catch (NoSuchUserException | LoginException e) {
            req.setAttribute("loginErr", "Неверный логин или пароль!");
            req.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(req, resp);
        }
    }
}
