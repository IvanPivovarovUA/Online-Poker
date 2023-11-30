package com.online.poker;

//Our classes
import com.online.poker.repository.GameState;
import com.online.poker.repository.PlayerInput;
import com.online.poker.repository.Card;
import com.online.poker.repository.User;
import com.online.poker.repository.OutputUserInfo;
import com.online.poker.service.BackendService;

import java.util.ArrayList;

//Spring
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.util.HtmlUtils;

import java.security.Principal;
import org.springframework.messaging.simp.annotation.SendToUser;

import org.springframework.context.event.EventListener;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import org.springframework.messaging.simp.SimpMessagingTemplate;


@Controller
public class GameController {
    //Set Options Service and Data
    private SimpMessagingTemplate messagingTemplate;
    private BackendService backendService = new BackendService();
    // private GameState gameState = new GameState();
    // private ArrayList<User> ALL_USERS = new ArrayList<User>();
    
    public GameController(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
        
        // ALL_USERS.add(new User(1,"Ivan"  ,1000));
        // ALL_USERS.add(new User(2,"Nikita",1000));
        // ALL_USERS.add(new User(3,"Gleb"  ,1000));
        // ALL_USERS.add(new User(4,"Maria" ,1000));
    }
    
    //Get user disconnect
    @EventListener
    public void handleWebSocketDisconnectListener(SessionDisconnectEvent event) {
        String name = event.getUser().getName();

        int index_hall = backendService.check_onhall(name);
        int index_table = backendService.check_ontable(name);
        if (index_hall != -1) {
            backendService.remove_user_from_gamestate(index_hall, "hall");
        }
        if (index_table != -1) {
            backendService.remove_user_from_gamestate(index_table, "table");
        }



        messagingTemplate.convertAndSend("/topic/greetings", backendService.gameState);
    }

    //HTTP links
    @GetMapping("/")
    public String nothing() {
        return "redirect:table";
    }
    @GetMapping("/table")
    public String table(Principal principal) {
        String name = principal.getName();


        if (backendService.check_onhall(name)  == -1 &&
            backendService.check_ontable(name) == -1) 
        {
            return "table";
        }else {
            return "redirect:error";
        }
    }

    //WebSockets links
    @MessageMapping("/hello")
    @SendTo("/topic/greetings")
    public GameState greeting(Principal principal, PlayerInput playerInput) throws Exception {
        // Thread.sleep(1000); // simulated delay
 
        //Set on GameState
        String name = principal.getName();
        if (backendService.check_ontable(name) == -1) {
            if (backendService.check_onhall(name) == -1) {
                backendService.set_user_in_gamestate(name,"hall");
            }
        }

        return backendService.gameState;
    }

    @MessageMapping("/getinfo")
    @SendToUser("/queue/get_user_interface")
    public OutputUserInfo getUserInfo(Principal principal) {
        //Get player info
        String name = principal.getName();
        OutputUserInfo output = new OutputUserInfo();
        output.Player = backendService.find_by_name(name);
        output.Status = "only bet";
        return output;
    }

}