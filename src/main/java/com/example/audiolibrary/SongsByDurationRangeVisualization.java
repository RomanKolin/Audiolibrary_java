package com.example.audiolibrary;

import javafx.fxml.*;
import javafx.geometry.*;
import javafx.stage.*;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.scene.input.*;
import javafx.scene.transform.*;
import javafx.scene.chart.*;
import javafx.scene.text.*;
import javafx.scene.Node;
import javafx.util.*;
import javafx.event.Event;
import javafx.animation.PauseTransition;
import javafx.collections.*;
import java.util.*;
import java.util.concurrent.atomic.*;

public class SongsByDurationRangeVisualization
{
    @FXML
    ScrollPane ScrollPane1;
    @FXML
    VBox VBox1;
    @FXML
    VBox VBox2;
    @FXML
    TextField textfield1songsbydurationrange;
    @FXML
    TextField textfield2search;
    @FXML
    PieChart pc;

    String[] colarr;
    String[][] nosongsarr, colarrsort, songsbydurrangarr;
    List<Node> clitotlist, cliscrlist = new ArrayList<>(), leglist = new ArrayList<>();

    public void songsbydurationrangevisualization() throws Exception
    {
        AtomicInteger scount = new AtomicInteger(), nosongs = new AtomicInteger();
        String[][] nosongsbydurrangarr = AudioLibraryDB.visualization(2, 1, "", "", 0);
        nosongsarr = AudioLibraryDB.visualization(2, 2, "", "", 0);
        Arrays.sort(nosongsarr, Comparator.comparingInt((String[] a) -> Integer.parseInt(a[1])).reversed());
        colarrsort = new String[nosongsarr.length][2];
        Integer[] nosongsdistarr = Arrays.stream(nosongsarr).map(r -> Integer.parseInt(r[1])).distinct().toArray(Integer[]::new);
        songsbydurrangarr = AudioLibraryDB.visualization(2, 5, "", "", 0);
        colarr = new String[]{"#4B0000", "#520707", "#590E0E", "#601515", "#671C1C", "#6E2323", "#752A2A", "#7C3131", "#833838", "#8A3F3F", "#914646", "#984D4D", "#9F5454", "#A65B5B", "#AD6262", "#B46969", "#BB7070", "#C27777", "#C97E7E", "#D08585", "#D78C8C", "#DE9393", "#E59A9A", "#ECA1A1", "#F3A8A8", "#FAAFAF", "#FFB6B6", "#FFBDBD", "#FFC4C4", "#FFCBCC", "#FFD2D2", "#FFD9D9", "#FFE0E0", "#FFE7E7", "#FFEEEE"};
        ObservableList<PieChart.Data> pcobslist = FXCollections.observableArrayList();

        textfield1songsbydurationrange.addEventFilter(MouseEvent.ANY, Event::consume);
        pc.setLabelsVisible(false);
        pc.setStartAngle(90);
        for (String[] pcdat: nosongsbydurrangarr)
            if (Integer.parseInt(pcdat[0].substring(0, pcdat[0].indexOf(':')))<60)
                pcobslist.add(new PieChart.Data(pcdat[0], Integer.parseInt(pcdat[1])));
            else
                pcobslist.add(new PieChart.Data(pcdat[0].replace(pcdat[0].substring(0, pcdat[0].indexOf(':')), Integer.parseInt(pcdat[0].substring(0, pcdat[0].indexOf(':')))/60+":"+Integer.parseInt(pcdat[0].substring(0, pcdat[0].indexOf(':')))%60), Integer.parseInt(pcdat[1])));
        pc.setData(pcobslist);
        leglist.add(pc.lookup(".chart-legend"));
        leglist.get(0).setStyle("-fx-background-color: #FFFFFF; -fx-border-width: 1 0 0 0; -fx-border-color: #4B0000; -fx-font-family: Times New Roman; -fx-font-style: italic; -fx-font-size: 12; -fx-text-fill: #000000");
        piechartvisualization();
        songsbydurationrange(nosongs.intValue());

        PauseTransition pt = new PauseTransition(Duration.millis(1));
        pc.setOnScroll(se ->
        {
            pt.stop();
            pt.setOnFinished(ae ->
            {
                AudioLibrary.flowpane1.requestFocus();
                if (se.getDeltaY() > 0 && scount.intValue() < nosongsdistarr.length)
                {
                    scount.incrementAndGet();
                    nosongs.incrementAndGet();
                    if (scount.intValue() == 1)
                    {
                        textfield2search.clear();
                        textfield2search.setEditable(false);
                    }
                }
                if (se.getDeltaY() < 0 && scount.intValue() >= 0)
                {
                    scount.decrementAndGet();
                    nosongs.decrementAndGet();
                    if (scount.intValue() == 0)
                        textfield2search.setEditable(true);
                }
                if (scount.intValue() < 0 || scount.intValue() >= nosongsdistarr.length)
                {
                    if (scount.intValue() < 0)
                    {
                        scount.incrementAndGet();
                        nosongs.incrementAndGet();
                    }
                    if (scount.intValue() >= nosongsdistarr.length)
                    {
                        scount.decrementAndGet();
                        nosongs.decrementAndGet();
                    }
                }
                else
                {
                    try
                    {
                        if (scount.intValue() >= 0)
                        {
                            pcobslist.clear();
                            for (String[] pcdat: AudioLibraryDB.visualization(2, 4, "", "", nosongsdistarr[scount.intValue()]))
                                if (Integer.parseInt(pcdat[0].substring(0, pcdat[0].indexOf(':')))<60)
                                    pcobslist.add(new PieChart.Data(pcdat[0], Integer.parseInt(pcdat[1])));
                                else
                                    pcobslist.add(new PieChart.Data(pcdat[0].replace(pcdat[0].substring(0, pcdat[0].indexOf(':')), Integer.parseInt(pcdat[0].substring(0, pcdat[0].indexOf(':')))/60+":"+Integer.parseInt(pcdat[0].substring(0, pcdat[0].indexOf(':')))%60), Integer.parseInt(pcdat[1])));
                            pcobslist.sort(Comparator.comparingInt(a -> Integer.parseInt(a.getName().substring(0, a.getName().indexOf(":")))));
                        }
                    }
                    catch (Exception e)
                    {
                        throw new RuntimeException(e);
                    }
                    cliscrlist.clear();
                    for (int i = 0; i < clitotlist.size() - scount.intValue(); i++)
                        cliscrlist.add(clitotlist.get(i));
                    piechartvisualization();
                    songsbydurationrange(nosongs.intValue());
                }
            });
            pt.play();
        });

        textfield2search.textProperty().addListener((o, ov, nv) ->
        {
            try
            {
                VBox2.getChildren().clear();
                Text songssearch = new Text(AudioLibraryDB.visualization(2, 6, "", "%" + textfield2search.getText() + "%", 0)[0][0]);
                songssearch.setWrappingWidth(395);
                songssearch.setStyle("-fx-text-alignment: CENTER; -fx-font-family: Times New Roman; -fx-font-style: italic; -fx-font-size: 12; -fx-text-fill: #000000");
                VBox2.getChildren().add(songssearch);
                textfield1songsbydurationrange.setText(AudioLibraryDB.visualization(2, 7, "", "%" + textfield2search.getText() + "%", 0)[0][0]);
                if (textfield1songsbydurationrange.getText() != null && textfield1songsbydurationrange.getText().contains("1 songs"))
                    textfield1songsbydurationrange.setText(textfield1songsbydurationrange.getText().replace("songs", "song"));
            }
            catch (Exception ex)
            {
                throw new RuntimeException(ex);
            }
        });
    }

    public void piechartvisualization()
    {
        pc.applyCss();
        cliscrlist.clear();
        clitotlist = new ArrayList<>(pc.lookupAll(".chart-legend-item"));
        cliscrlist.addAll(clitotlist);
        colarrsort = new String[clitotlist.size()][2];
        int col = 0;
        int inosongs = nosongsarr.length-clitotlist.size();
        for (int i = 0; i < clitotlist.size(); i++)
        {
            try
            {
                if (!nosongsarr[i+inosongs][1].equals(nosongsarr[i+inosongs+1][1]))
                    colarrsort[i] = new String[]{nosongsarr[i+inosongs][0], colarr[col]};
                else
                {
                    colarrsort[i] = new String[]{nosongsarr[i+inosongs][0], colarr[col]};
                    col-=1;
                }
            }
            catch (Exception e)
            {
                colarrsort[i] = new String[]{nosongsarr[i+inosongs][0], colarr[col]};
            }
            col+=1;
        }
        col = 0;
        Arrays.sort(colarrsort, Comparator.comparingInt(a -> Integer.parseInt(a[0])));
        for (Node leg: cliscrlist)
        {
            Node clis = leg.lookup(".chart-legend-item-symbol");
            clis.setStyle("-fx-background-color: " + colarrsort[col][1] + ";");
            col+=1;
        }
        col = 0;
        VBox1.getChildren().clear();
        VBox1.getChildren().add(leglist.get(0));
        for (PieChart.Data pcdat: pc.getData())
        {
            pcdat.getNode().setStyle("-fx-border-width: 0.5 0 0 0; -fx-border-color: #FF0000; -fx-pie-color: " + colarrsort[col][1]);
            pcdat.getNode().setOnMouseEntered(e ->
            {
                pcdat.getNode().setUserData(pcdat.getNode().getStyle());
                pcdat.getNode().setStyle("-fx-border-width: 0.5 0 0 0; -fx-border-color: #FF0000; -fx-pie-color: derive(" + pcdat.getNode().getStyle().substring(pcdat.getNode().getStyle().lastIndexOf(":")+2) + ", 20%)");
            });
            pcdat.getNode().setOnMouseExited(e -> pcdat.getNode().setStyle(String.valueOf(pcdat.getNode().getUserData())));
            pcdat.getNode().setOnMouseClicked(e ->
            {
                ScrollPane1.setVvalue(0);
                textfield2search.setText("");
                textfield2search.setEditable(false);
                if (!String.valueOf((int)pcdat.getPieValue()).equals("1"))
                    textfield1songsbydurationrange.setText((int)pcdat.getPieValue() + " songs in range " + pcdat.getName());
                else
                    textfield1songsbydurationrange.setText((int)pcdat.getPieValue() + " song in range " + pcdat.getName());
                try
                {
                    VBox2.getChildren().clear();
                    Text songs = new Text(AudioLibraryDB.visualization(2, 3, pcdat.getName().substring(0, pcdat.getName().indexOf(":")), "", 0)[0][0]);
                    songs.setWrappingWidth(395);
                    songs.setStyle("-fx-text-alignment: CENTER; -fx-font-family: Times New Roman; -fx-font-style: italic; -fx-font-size: 12; -fx-text-fill: #000000");
                    VBox2.getChildren().add(songs);
                }
                catch (Exception e1)
                {
                    throw new RuntimeException(e1);
                }
            });
            Tooltip.install(pcdat.getNode(), new Tooltip(pcdat.getName() + ": " + (int)pcdat.getPieValue()));
            col+=1;
        }
    }

    public void songsbydurationrange(Integer nosongs)
    {
        try
        {
            VBox2.getChildren().clear();
            Text songssearch = new Text(songsbydurrangarr[nosongs][1]);
            songssearch.setWrappingWidth(395);
            songssearch.setStyle("-fx-text-alignment: CENTER; -fx-font-family: Times New Roman; -fx-font-style: italic; -fx-font-size: 12; -fx-text-fill: #000000");
            VBox2.getChildren().add(songssearch);
            textfield1songsbydurationrange.setText(songsbydurrangarr[nosongs][0]);
            if (textfield1songsbydurationrange.getText() != null && textfield1songsbydurationrange.getText().contains("1 songs"))
                textfield1songsbydurationrange.setText(textfield1songsbydurationrange.getText().replace("songs", "song"));
        }
        catch (Exception ex)
        {
            throw new RuntimeException(ex);
        }
    }

    public void button17exit() throws Exception
    {
        Runnable buttonrun = AudioLibrary.buttonrun;
        AudioLibrary.flowpane1.getChildren().clear();
        FXMLLoader fxmlloader = new FXMLLoader(AudioLibrary.class.getResource("AudioLibrary.fxml"));
        if (Screen.getPrimary().getBounds().getWidth() >= 1920)
            AudioLibrary.flowpane1.getTransforms().add(new Scale(Screen.getPrimary().getBounds().getWidth()/(Screen.getPrimary().getBounds().getWidth()*(Math.round((Screen.getPrimary().getBounds().getWidth()/1280.0)*100.0)/100.0)), Screen.getPrimary().getBounds().getHeight()/(Screen.getPrimary().getBounds().getHeight()*(Math.round((Screen.getPrimary().getBounds().getHeight()/1024.0)*100.0)/100.0))));
        AudioLibrary.flowpane1.setPadding(new Insets(0));
        AudioLibrary.flowpane1.getChildren().add(fxmlloader.load());
        buttonrun.run();
    }
}