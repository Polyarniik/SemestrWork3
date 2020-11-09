package ru.kpfu.group903.safiullin.models;


import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class Bank {
    private long id;
    private String name;
    private String color;
    private List<User> users;
    private float amount;
}
