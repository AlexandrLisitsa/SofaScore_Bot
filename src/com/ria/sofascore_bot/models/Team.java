package com.ria.sofascore_bot.models;

import java.util.ArrayList;

public class Team {
	
	private ArrayList<Player> players = new ArrayList<>();

	public ArrayList<Player> getPlayers() {
		return players;
	}

	public void setPlayers(ArrayList<Player> players) {
		this.players = players;
	}

}
