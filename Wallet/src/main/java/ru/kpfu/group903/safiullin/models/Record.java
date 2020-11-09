package ru.kpfu.group903.safiullin.models;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Record {
    private long id;
    private Bank bank;
    private Category category;
    private User user;
    private float sum;
    private String description;
    private String date;
}
