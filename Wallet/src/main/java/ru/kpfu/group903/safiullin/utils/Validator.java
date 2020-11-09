package ru.kpfu.group903.safiullin.utils;

import java.util.regex.Pattern;

public class Validator {
    private final String name;
    private final String email;
    private final String password;
    private final String passwordRepeat;
    private final String EMAIL_REGEX = "[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9]" +
            "(?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?";
    private final String NAME_REGEX = "^[a-zA-Z]+(([',. -][a-zA-Z ])?[a-zA-Z]*)*$";
    private final String PASSWORD_REGEX = ".{8,}$";

    public Validator(String name, String email, String password, String passwordRepeat) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.passwordRepeat = passwordRepeat;
    }

    public boolean isValid() {
        return checkName() && checkEmail() && checkPassword() && password.equals(passwordRepeat) ;
    }

    private boolean checkName() {
        return Pattern.compile(NAME_REGEX).matcher(name).matches();
    }

    private boolean checkEmail() {
        return Pattern.compile(EMAIL_REGEX).matcher(email).matches();
    }

    private boolean checkPassword() {
        return Pattern.compile(PASSWORD_REGEX).matcher(password).matches();
    }
}


