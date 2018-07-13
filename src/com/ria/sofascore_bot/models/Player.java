package com.ria.sofascore_bot.models;

import java.util.ArrayList;

public class Player {
	
	private boolean isPitch;
	private int score;
	private ArrayList<Set> sets = new ArrayList<>();
	public int getScore() {
		return score;
	}
	public void setScore(int score) {
		this.score = score;
	}
	public boolean isPitch() {
		return isPitch;
	}
	public void setPitch(boolean isPitch) {
		this.isPitch = isPitch;
	}
	public ArrayList<Set> getSets() {
		return sets;
	}
	public void setSets(ArrayList<Set> sets) {
		this.sets = sets;
	}
	
}
