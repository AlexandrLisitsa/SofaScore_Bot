package com.ria.sofascore_bot;

public class MatchBuilder {
	private static MatchBuilder instance;
	private MatchBuilder() {};
	public static MatchBuilder getInstance() {
		if(instance==null) {
			instance= new MatchBuilder();
		}
		return instance;
	}
	
	public void buildMatches() {
		
	}
}
