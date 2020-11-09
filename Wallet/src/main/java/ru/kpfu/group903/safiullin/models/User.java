package ru.kpfu.group903.safiullin.models;


import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class User {
    private long id;
    private String name;
    private String email;
    private String password;
    private List<Bank> banks;
}
