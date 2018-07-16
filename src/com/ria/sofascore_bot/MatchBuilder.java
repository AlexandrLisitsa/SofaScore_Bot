package com.ria.sofascore_bot;

import com.ria.sofascore_bot.models.*;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
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
                    Match tmpMatch = getMatch(elements.get(i));
                    matches.add(tmpMatch);
                    //return matches;
                }
            }
        }
        return matches;
    }

    private Match getMatch(Element element) {
        Match tmpMatch = new Match();
        tmpMatch.setTitle(getMatchTitle(element));
        tmpMatch.setGames(getMatchGames(element));
        return tmpMatch;
    }

    private ArrayList<Game> getMatchGames(Element element) {
        ArrayList<Game> games = new ArrayList<>();
        Elements aClass1 = element.getElementsByAttributeValueContaining("class", "cell cell--event-list  pointer");
        aClass1.forEach(x->{
            Game game = new Game();
            game.setTeams(getTeam(x));
            games.add(game);
        });

        return games;
    }

    private ArrayList<Team> getTeam(Element x) {
        ArrayList<Team>teams = new ArrayList<>();
        Elements elementsByAttributeValue = x.getElementsByAttributeValue("class", "cell__content");
        elementsByAttributeValue.forEach(e->{
            if(e.children().hasClass("event-rounds__tennis   ")){
                Team team = new Team();
                team.setPlayers(getPlayers(x));
                if(teams.isEmpty()){
                    teams.add(team);
                }else {
                    teams.forEach(t -> {
                        t.getPlayers().forEach(p -> {
                            if (!p.getName().equals(team.getPlayers().get(0).getName()) &&
                                    !p.getName().equals(team.getPlayers().get(1).getName())) {
                                teams.add(team);
                            }
                        });
                    });
                }
            }
        });
        return teams;
    }

    private ArrayList<Player> getPlayers(Element x) {
        ArrayList<Player> players = new ArrayList<>();
        /*====name of player init*/
        Elements aClass = x.getElementsByAttributeValue("class", "cell__content event-team  ");
        aClass.forEach(e->{
            Player tmp = new Player();
            tmp.setName(e.text());
            players.add(tmp);
        });
        /*=====set0 of player0 init*/
        Set set0 = new Set();
        Elements aClass1 = x.getElementsByAttributeValue("class", "cell__content");
        aClass1.get(0).children().forEach(e->{
            if(!e.text().isEmpty())set0.getSetScore().add(Integer.valueOf(e.text().substring(0,1)));
        });
        players.get(0).setSets(set0);
        /*====set1 of player1 init*/
        Set set1 = new Set();
        Elements aClass2 = x.getElementsByAttributeValue("class", "cell__content");
        aClass2.get(1).children().forEach(e->{
            if(!e.text().isEmpty())set1.getSetScore().add(Integer.valueOf(e.text().substring(0,1)));
        });
        players.get(1).setSets(set1);
        /*=======Player live Scores*/
        Elements liveScores = x.getElementsByAttributeValueContaining("class", "cell__content event-rounds__tennis-live ");
        if(liveScores.get(0).children().hasClass("soficons-tennis-ball"))players.get(0).setPitch(true);
        players.get(0).setScore(Integer.parseInt(liveScores.get(0).text()));
        if(liveScores.get(1).children().hasClass("soficons-tennis-ball"))players.get(1).setPitch(true);
        players.get(1).setScore(Integer.parseInt(liveScores.get(1).text()));

        return players;
    }

    private String getMatchTitle(Element element) {
        return element.getElementsByAttributeValue("class","tournament__name").text();
    }


    public static void main(String[] args) {
        MatchBuilder matchBuilder = MatchBuilder.getInstance();
        Scanner scanner=null;
        StringBuilder stringBuilder = new StringBuilder();
        try {
            //scanner = new Scanner(new File("C:\\Users\\wypik\\Desktop\\error_total.txt"));
            scanner = new Scanner(new File("C:\\Users\\wypik\\Desktop\\WORK.txt"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        while (scanner.hasNext()){
            stringBuilder.append(scanner.nextLine());
        }
        ArrayList<Match> matches = matchBuilder.buildMatches(stringBuilder.toString());
        matches.forEach(m->{
            System.out.println(m.getTitle());
            m.getGames().forEach(e->{
                e.getTeams().forEach(x->{
                    x.getPlayers().forEach(p->{
                        System.out.print(p.getName()+"      ");
                        p.getSets().getSetScore().forEach(s->{
                            System.out.print(s+" ");
                        });
                        System.out.println("    Scores  "+p.getScore()+"           is pitch        "+p.isPitch());
                    });
                });
            });
            System.out.println();
        });

    }

}
