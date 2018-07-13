package com.ria.sofascore_bot;

import com.ria.sofascore_bot.models.*;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.select.Elements;

import java.util.ArrayList;

public class MatchBuilder {
	private static MatchBuilder instance;
	private MatchBuilder() {};
	public static MatchBuilder getInstance() {
		if(instance==null) {
			instance= new MatchBuilder();
		}
		return instance;
	}
	
	public ArrayList<Match> buildMatches(String document) {
	    ArrayList<Match> matches = new ArrayList<>();
        Document document1 = Jsoup.parse(document);
        Elements elements = document1.getElementsByAttributeValue("class", "js-event-list-tournament tournament");
        if(elements.size() > 0) {
            for (int i = 0; i < elements.size(); i++) {
                if(elements.get(i).getElementsByAttributeValue("class", "tournament__category").text().equals("ITF Мужчины")){
                    Match tmpMatch = getMatches(elements.get(i));
                    Game tmpGame = getGame(elements.get(i));
                    Team tmpTeam = getTeam(elements.get(i));
                    Player tmpPlayer = getPlayer(elements.get(i));
                    Set tmpSet = getSet(elements.get(i));
                }
            }
        }
        return matches;
	}

    private Player getPlayer(Element element) {
	    return null;
    }

    private Team getTeam(Element element) {
	    return null;
    }

    private Set getSet(Element element) {
	    return null;
    }

    private Game getGame(Element element) {
	    return null;
    }

    private Match getMatches(Element element) {
	    Match tmp = new Match();
	    tmp.setTitle(element.getElementsByAttributeValue("class","tournament__name").text());
	    return tmp;
    }


}
