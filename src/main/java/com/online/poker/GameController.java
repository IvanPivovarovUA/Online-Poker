package com.online.poker;

import java.security.Principal;

import com.online.poker.repository.GameState;
import com.online.poker.repository.PlayerInput;
import com.online.poker.repository.Card;
import com.online.poker.repository.Player;
import java.util.ArrayList;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.util.HtmlUtils;

@Controller
public class GameController {

    @GetMapping("/table")
    public String Table() {
        return "table";
        // return "~ Poker Table ~";
    }

    // @MessageMapping("/hello")
    // @SendTo("/topic/greetings")
    // public Greeting greeting(HelloMessage message) throws Exception {
    //     Thread.sleep(1000); // simulated delay
    //     return new Greeting("Hello, " + HtmlUtils.htmlEscape(message.getName()) + "!");
    // }
    
    @MessageMapping("/hello")
    @SendTo("/topic/greetings")
    public GameState greeting(Principal principal, PlayerInput playerInput) throws Exception {
        Thread.sleep(1000); // simulated delay
        System.out.println("\n\n" + principal.getName() +"\n\n");

        GameState test = new GameState();
        test.PlayersOnTable = new ArrayList<Player>();
        test.PlayersOnTable.add(new Player());

        test.PlayersOnHall = new ArrayList<Player>();
        test.PlayersOnHall.add(new Player());

        test.CardsOnTable = new ArrayList<Card>();
        test.CardsOnTable.add(new Card());
        
        test.BiggestBet = 999;
        test.PlayersBet = new ArrayList(54);
        
        test.DilerId =1;
        test.StepId =1;
        
        test.Bank = 998;
        return test;
        // return new GameState();
    }

}