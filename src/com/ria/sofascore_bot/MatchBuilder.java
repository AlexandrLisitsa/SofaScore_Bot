package com.ria.sofascore_bot;

import com.ria.sofascore_bot.models.*;
import org.jsoup.Jsoup;
import org.jsoup.nodes.*;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

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
                    ArrayList<Player> tmpPlayer = getPlayer(elements.get(i));
                    ArrayList<Set> tmpSet = getSet(elements.get(i));
                    matches.add(tmpMatch);
                    tmpSet.forEach(s->{
                        s.getSetScore().forEach(ss->{
                            System.out.print(ss+" ");
                        });
                        System.out.println();
                    });
                    tmpPlayer.forEach(p->{
                        System.out.println(p.getScore()+" "+p.isPitch());
                    });
                   // return matches;
                    System.out.println("_______");
                }
            }
        }
        return matches;
	}

    private ArrayList<Player> getPlayer(Element element) {
	    ArrayList<Player> players = new ArrayList<>();
        Elements aClass = element.getElementsByAttributeValue("class", "cell__section event-rounds__in-progress");
        Elements allElements = aClass.get(0).getAllElements();
        allElements.forEach(e->{
            if(e.hasClass("cell__content event-rounds__tennis-live ")||e.hasClass("cell__content event-rounds__tennis-live highlight")){
                Player player = new Player();
                if(e.text()!=""&&!e.text().isEmpty()) {
                    player.setScore(Integer.parseInt(e.text()));
                }
                if(e.getAllElements().hasClass("soficons-tennis-ball"))player.setPitch(true);
                players.add(player);
            }
        });
        getPlayersName(element,players);
        return players;
    }

    private void getPlayersName(Element element, ArrayList<Player> players) {
        Elements aClass = element.getElementsByAttributeValue("class", "cell__content event-team  ");
        for (int i = 0; i < aClass.size(); i++) {
            players.get(i).setName(aClass.get(i).text());
        }
    }

    private Team getTeam(Element element) {
	    return new Team();
    }

    private ArrayList<Set> getSet(Element element) {
	    Set set = new Set();
        Elements elementsByAttributeValue = element.getElementsByAttributeValue("class", "cell__section event-rounds event-rounds__live u-text-lighter");
        for (Element x : elementsByAttributeValue) {
            List<Node> cell_content = x.childNodes();
            for (Node f : cell_content) {
                List<Node> nodes = f.childNodes();
                for (Node ch : nodes) {
                    List<Node> nodes1 = ch.childNodes();
                    nodes1.forEach(g -> {
                        if(!g.toString().contains("class")&&!g.toString().trim().isEmpty())set.getSetScore().add(Integer.valueOf(g.toString().trim()));
                    });
                }
            }
        }
        Set set1 = new Set();
        Set set2 = new Set();
        for (int i = 0; i < set.getSetScore().size(); i++) {
            if(i<(set.getSetScore().size()+1)/2){
                set1.getSetScore().add(set.getSetScore().get(i));
            }else{
                set2.getSetScore().add(set.getSetScore().get(i));
            }
        }
        ArrayList<Set> returnedList = new ArrayList<>();
        returnedList.add(set1);
        returnedList.add(set2);
        return returnedList;
    }

    private Game getGame(Element element) {
	    return new Game();
    }

    private Match getMatches(Element element) {
	    Match tmp = new Match();
	    tmp.setTitle(element.getElementsByAttributeValue("class","tournament__name").text());
	    return tmp;
    }

    public static void main(String[] args) {
        MatchBuilder matchBuilder = MatchBuilder.getInstance();
        Scanner scanner=null;
        StringBuilder stringBuilder = new StringBuilder();
        try {
             //scanner = new Scanner(new File("C:\\Users\\WyPik\\Desktop\\Новый текстовый документ.txt"));
            scanner = new Scanner(new File("C:\\Users\\WyPik\\Desktop\\error_step.txt"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        while (scanner.hasNext()){
            stringBuilder.append(scanner.nextLine());
        }
        matchBuilder.buildMatches(stringBuilder.toString());
    }

}
