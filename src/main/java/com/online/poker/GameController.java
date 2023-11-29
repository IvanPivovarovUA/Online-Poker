package com.online.poker;

import java.security.Principal;
import org.springframework.messaging.simp.annotation.SendToUser;

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

    GameState gameState = new GameState();

    @GetMapping("/")
    public String nothing() {
        return "redirect:table";
    }

    @GetMapping("/table")
    public String table(Principal principal) {
        Player player = new Player();
        player.Name = principal.getName();
        player.Balance = 9999;

        gameState.PlayersOnTable.add(player);

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
        // System.out.println("\n\n" + principal.getName() +"\n\n");

        // GameState test = new GameState();
        // test.PlayersOnTable = new ArrayList<Player>();
        // test.PlayersOnTable.add(new Player());

        // test.PlayersOnHall = new ArrayList<Player>();
        // test.PlayersOnHall.add(new Player());

        // test.CardsOnTable = new ArrayList<Card>();
        // test.CardsOnTable.add(new Card());
        
        // test.BiggestBet = 999;
        // test.PlayersBet = new ArrayList(54);
        
        // test.DilerId =1;
        // test.StepId =1;
        
        // test.Bank = 998;
        // return test;
        // return new GameState();
        return gameState;
    }

    @MessageMapping("/getinfo")
    @SendTo("/queue/getuser")
    public Player getUserInfo(Principal principal) {
        Player player = new Player();
        player.Name = principal.getName();
        player.Balance = 9999;
        System.out.println("\n\n" + player.Name  +"\n\n");
        return player;
    }

}