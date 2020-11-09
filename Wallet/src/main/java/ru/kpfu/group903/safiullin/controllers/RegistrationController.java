package ru.kpfu.group903.safiullin.controllers;

import ru.kpfu.group903.safiullin.exceptions.RegisterException;
import ru.kpfu.group903.safiullin.models.User;
import ru.kpfu.group903.safiullin.services.BankService;
import ru.kpfu.group903.safiullin.services.UserService;
import ru.kpfu.group903.safiullin.utils.Validator;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.ParseException;

@WebServlet("/registration")
public class RegistrationController extends HttpServlet {
    private UserService userService;
    private BankService bankService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        ServletContext servletContext = config.getServletContext();
        userService = (UserService) servletContext.getAttribute("userService");
        bankService = (BankService) servletContext.getAttribute("bankService");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("WEB-INF/views/registration.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = req.getParameter("name");
        String email = req.getParameter("email");
        String password = req.getParameter("password");
        Validator validator = new Validator(name, email, password, (String) req.getParameter("passwordRepeat"));
        if (!validator.isValid()) {
            req.setAttribute("regErr", "Проверьте введённые данные.");
            req.getRequestDispatcher("WEB-INF/views/registration.jsp").forward(req, resp);
        }

        User user = User.builder()
                .name(name)
                .email(email)
                .password(password).build();

        try {
            userService.register(user, req);
            bankService.addBankForUser(userService.getUserByEmail(email).getId(), 0L, 0);
            resp.addCookie(new Cookie("userName", user.getName()));
            resp.sendRedirect(getServletContext().getContextPath() + "/wallet");
        } catch (RegisterException | ParseException e) {
            req.setAttribute("regErr", "Такой пользователь уже существует!");
            req.getRequestDispatcher("WEB-INF/views/registration.jsp").forward(req, resp);
        }
    }
}
