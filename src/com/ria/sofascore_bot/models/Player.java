package com.ria.sofascore_bot.models;

import java.util.ArrayList;

public class Player {
	
	private boolean isPitch;
	private int score;
	private Set sets = new Set();

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
	public Set getSets() {
		return sets;
	}
	public void setSets(Set sets) {
		this.sets = sets;
	}

}
