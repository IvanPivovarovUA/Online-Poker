package com.online.poker.repository;

public class PlayerInput {
    
    private String act;
    private String bet;

    private PlayerInput() {

    }

    private PlayerInput(String act) {
        this.act = act;
    }

    private PlayerInput(String act, String bet) {
        this.act = act;
        this.bet = bet;
    }
}