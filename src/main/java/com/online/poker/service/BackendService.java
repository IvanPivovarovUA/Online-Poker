package com.online.poker.service;

import org.springframework.stereotype.Service;

import com.online.poker.repository.GameState;
import com.online.poker.repository.PlayerInput;
import com.online.poker.repository.Card;
import com.online.poker.repository.User;
import java.util.ArrayList;


@Service
public class BackendService {
    //Set Data
    public GameState gameState = new GameState();
    private ArrayList<User> ALL_USERS = new ArrayList<User>();

    public BackendService() {
        ALL_USERS.add(new User(1,"Ivan"  ,1000));
        ALL_USERS.add(new User(2,"Nikita",1000));
        ALL_USERS.add(new User(3,"Gleb"  ,1000));
        ALL_USERS.add(new User(4,"Maria" ,1000));
    }
    
    
    //Base operation for find
    public User find_by_name(String name) {
        User user = new User();
        for (User u: ALL_USERS) {
            if (name.equals(u.getName())) {
                user = u;
            }
        }
        return user;
    }
    public int check_onhall(String name) {
        int index = -1;
        for (int i=0; i < gameState.PlayersOnHall.size(); i++) {
            if (name.equals(gameState.PlayersOnHall.get(i).getName())) {
                index = i;
            }
        }
        return index;
    }
    public int check_ontable(String name) {
        int index = -1;
        for (int i=0; i < gameState.PlayersOnTable.size(); i++) {
            if (name.equals(gameState.PlayersOnTable.get(i).getName())) {
                index = i;
            }
        }
        return index;
    }

    //Add or Remove from GameSate
    public void set_user_in_gamestate(String name, String hall_or_table) {
        if (hall_or_table == "hall") {
            gameState.PlayersOnHall.add(find_by_name(name));
        }
        if (hall_or_table== "table") {
            gameState.PlayersOnTable.add(find_by_name(name));
        }
    }
    public void remove_user_from_gamestate(int index, String hall_or_table) {
        if (hall_or_table == "hall") {
            gameState.PlayersOnHall.remove(index);
        }
        if (hall_or_table== "table") {
            gameState.PlayersOnTable.remove(index);
        }
    }

    //Step queue
    public boolean permitToStep(String name) {
        User user = find_by_name(name);
        if (user.getId() == gameState.StepId) {
            return true;
        }else {
            return false;
        }
    }
}