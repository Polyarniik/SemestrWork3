package ru.kpfu.group903.safiullin.services;

import ru.kpfu.group903.safiullin.exceptions.LoginException;
import ru.kpfu.group903.safiullin.exceptions.NoSuchUserException;
import ru.kpfu.group903.safiullin.exceptions.RegisterException;
import ru.kpfu.group903.safiullin.models.Bank;
import ru.kpfu.group903.safiullin.models.User;
import ru.kpfu.group903.safiullin.repositories.UserRepository;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;

public class UserService {
    UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User auth(User user, HttpServletRequest req) throws NoSuchUserException, LoginException {
        User userByEmail = userRepository.findByEmail(user.getEmail());
        if (userByEmail == null) throw new NoSuchUserException();
        if (userByEmail.getPassword().equals(user.getPassword())) {
            req.getSession().setAttribute("user", user);
            return userByEmail;
        } else throw new LoginException();
    }

    public void register(User user, HttpServletRequest req) throws ParseException, RegisterException {
        if (userRepository.findByEmail(user.getEmail()) == null) {
            userRepository.save(user);
            req.getSession().setAttribute("user", user);
        } else throw new RegisterException();
    }

    public boolean isSigned(HttpServletRequest req) {
        return req.getSession().getAttribute("email") != null;
    }

    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public void addBank(User user, Bank bank) {

    }

}
