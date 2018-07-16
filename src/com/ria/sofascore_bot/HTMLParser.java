package com.ria.sofascore_bot;

import java.io.StringWriter;
import java.util.ArrayList;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import com.ria.sofascore_bot.models.Match;
import org.w3c.dom.Document;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Worker;
import javafx.scene.Scene;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import javafx.util.Duration;

public class HTMLParser extends Application {

	
    public void start(Stage primaryStage) {

        WebView webview = new WebView();
        final WebEngine webengine = webview.getEngine();
        primaryStage.setOpacity(0);
        webengine.getLoadWorker().stateProperty().addListener(
        
                new ChangeListener<Worker.State>() {

                    public void changed(ObservableValue ov, Worker.State oldState, Worker.State newState) {
                        if (newState == Worker.State.SUCCEEDED) {
                            Document doc = webengine.getDocument();
                            try {
                                Transformer transformer = TransformerFactory.newInstance().newTransformer();
                                transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "no");
                                transformer.setOutputProperty(OutputKeys.METHOD, "xml");
                                transformer.setOutputProperty(OutputKeys.INDENT, "yes");
                                transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
                                transformer.setOutputProperty("{https://www.sofascore.com/ru/tennis/livescore}", "4");

                                StringWriter writer = new StringWriter();
                                StreamResult result = new StreamResult(writer);
                                transformer.transform(new DOMSource(doc),result);
                                String strResult = writer.toString();
                                //System.out.println(strResult);
                                MatchBuilder matchBuilder = MatchBuilder.getInstance();
                                ArrayList<Match> matches=matchBuilder.buildMatches(strResult);
                                showMatches(matches);
                            } catch (Exception ex) {
                                ex.printStackTrace();
                            }
                        }
                    }
                });
        webengine.load("https://www.sofascore.com/ru/tennis/livescore");
        Scene view = new Scene(webview,800,600);
        primaryStage.setScene(view);
        primaryStage.setTitle("sofascore_parser");
        primaryStage.show();
        KeyFrame keyFrame = new KeyFrame(Duration.millis(5000),e->{
            webengine.reload();
        });
        Timeline timeline = new Timeline(keyFrame);
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    private void showMatches(ArrayList<Match> matches) {
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

    public static void main(String[] args) {
		launch(args);
	}

}
