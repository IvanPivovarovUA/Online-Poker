package com.online.poker.repository;
import java.util.ArrayList;


public class GameState {

	public ArrayList<Player> PlayersOnTable;
	public ArrayList<Player> PlayersOnHall;
	public ArrayList<Card> CardsOnTable;
	
	public int BiggestBet;
	public ArrayList<Integer> PlayersBet;
	
	public int DilerId;
	public int StepId;
	
	public int Bank;

}
