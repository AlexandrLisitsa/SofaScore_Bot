package com.ria.sofascore_bot.models;

import java.util.ArrayList;

public class Match {

	private String title;
	private ArrayList<Game> games = new ArrayList<>(); 

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public ArrayList<Game> getGames() {
		return games;
	}

	public void setGames(ArrayList<Game> games) {
		this.games = games;
	}
	
	
}
