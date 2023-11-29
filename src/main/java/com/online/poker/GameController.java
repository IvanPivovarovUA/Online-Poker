package com.online.poker;

import org.springframework.messaging.simp.SimpMessagingTemplate;

import org.springframework.context.event.EventListener;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import java.security.Principal;
import org.springframework.messaging.simp.annotation.SendToUser;

import com.online.poker.repository.GameState;
import com.online.poker.repository.PlayerInput;
import com.online.poker.repository.Card;
import com.online.poker.repository.User;
import java.util.ArrayList;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.util.HtmlUtils;



@Controller
public class GameController {
    GameState gameState = new GameState();
    private SimpMessagingTemplate messagingTemplate;
    
    public GameController(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }
    
    @EventListener
    public void handleWebSocketDisconnectListener(SessionDisconnectEvent event) {
        String name = event.getUser().getName();
        // System.out.println("\n\n\n\n");
        // System.out.println(name);
        // System.out.println(principal.getName());
        // System.out.println("\n\n\n\n");

        for (int i = 0; i < gameState.PlayersOnTable.size(); i++) {
            if (gameState.PlayersOnTable.get(i).Name.equals(name)) {
                gameState.PlayersOnTable.remove(i);
                // System.out.println("\n\n\n\n111111111\n\n\n\n");
                break;
            }
        }

        for (int i = 0; i < gameState.PlayersOnHall.size(); i++) {
            if (gameState.PlayersOnHall.get(i).Name.equals(name)) {
                gameState.PlayersOnHall.remove(i);
                // System.out.println("\n\n\n\n111111111\n\n\n\n");
                break;
            }
        }

        messagingTemplate.convertAndSend("/topic/greetings", gameState);
        // testto();
    }




    @GetMapping("/")
    public String nothing() {
        return "redirect:table";
    }

    @GetMapping("/table")
    public String table(Principal principal) {
        // greeting(principal, new playerInput);

        boolean one_user_test = true;
        String name = principal.getName();

        for (int i = 0; i < gameState.PlayersOnTable.size(); i++) {
            if (gameState.PlayersOnTable.get(i).Name.equals(name)) {
                one_user_test = false;
                break;
            }
        }
        for (int i = 0; i < gameState.PlayersOnHall.size(); i++) {
            if (gameState.PlayersOnHall.get(i).Name.equals(name)) {
                one_user_test = false;
                break;
            }
        }
        if (one_user_test) {

            return "table";
        }else {
            return "redirect:error";
        }

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
        System.out.print("\n\n\n\n\nTTTTTTTTTTT\n\n\n\n\n");
        return gameState;
    }

    @MessageMapping("/getinfo")
    @SendTo("/queue/getuser")
    public User getUserInfo(Principal principal) {
        String name = principal.getName();

        User player = new User();
        player.Name = name;
        player.Balance = 9999;



        boolean one_user_test = true;
        for (int i = 0; i < gameState.PlayersOnTable.size(); i++) {
            if (gameState.PlayersOnTable.get(i).Name.equals(name)) {
                one_user_test = false;
                break;
            }
        }
        for (int i = 0; i < gameState.PlayersOnHall.size(); i++) {
            if (gameState.PlayersOnHall.get(i).Name.equals(name)) {
                one_user_test = false;
                break;
            }
        }
        if (one_user_test) {
            gameState.PlayersOnHall.add(player);
        }
        
        return player;
    }

}