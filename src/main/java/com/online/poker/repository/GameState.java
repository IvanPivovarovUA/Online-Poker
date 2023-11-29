package com.online.poker.repository;
import java.util.ArrayList;


public class GameState {

	public ArrayList<Player> PlayersOnTable = new ArrayList<Player>();
	public ArrayList<Player> PlayersOnHall = new ArrayList<Player>();
	public ArrayList<Card> CardsOnTable = new ArrayList<Card>();
	
	public int BiggestBet;
	public ArrayList<Integer> PlayersBet = new ArrayList<Integer>();
	
	public int DilerId;
	public int StepId;
	
	public int Bank;

}
