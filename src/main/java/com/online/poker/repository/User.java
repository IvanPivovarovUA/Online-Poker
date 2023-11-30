package com.online.poker.repository;
import java.util.ArrayList;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class User {
    private int Id;
    private String Name;
    private int Balance;

    // private ArrayList<Card> Cards;
    public User() {

    }
    public User(int id, String name, int balance) {
        this.Id = id;
        this.Name = name;
        this.Balance = balance;
    }
}


