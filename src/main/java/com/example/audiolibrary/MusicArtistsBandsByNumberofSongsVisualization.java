package com.example.audiolibrary;

import javafx.fxml.*;
import javafx.geometry.*;
import javafx.stage.*;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.scene.input.*;
import javafx.scene.transform.*;
import javafx.scene.shape.*;
import javafx.scene.chart.*;
import javafx.scene.text.*;
import javafx.scene.Node;
import javafx.event.Event;
import javafx.collections.*;
import javafx.beans.binding.*;
import java.util.*;

public class MusicArtistsBandsByNumberofSongsVisualization
{
    @FXML
    AnchorPane AnchorPane1;
    @FXML
    AnchorPane AnchorPane2;
    @FXML
    ScrollPane ScrollPane1;
    @FXML
    VBox VBox1;
    @FXML
    VBox VBox2;
    @FXML
    TextField textfield1musicartistbandincategorybynumberofsongs;
    @FXML
    StackedBarChart<String, Number> bc;
    @FXML
    CategoryAxis x;
    @FXML
    NumberAxis y;

    Label labeltick;
    Line linecharttick;

    public void musicartistsbandsbynumberofsongsvisualization() throws Exception
    {
        String[][] noartsbandsincatbynosongsarr = AudioLibraryDB.visualization(1, 1, "", "", 0);
        String[][] noartsbandsbynosongslinarr = AudioLibraryDB.visualization(1, 2, "", "", 0);
        String[][] noartsbandsbynosongslabarr = AudioLibraryDB.visualization(1, 3, "", "", 0);
        String[][] noartsbandsarr = new String[20][2];
        String[][] colarrsort = new String[20][2];
        String[] colarr = {"#4B0000", "#520707", "#590E0E", "#601515", "#671C1C", "#6E2323", "#752A2A", "#7C3131", "#833838", "#8A3F3F", "#914646", "#984D4D", "#9F5454", "#A65B5B", "#AD6262", "#B46969", "#BB7070", "#C27777", "#C97E7E", "#D08585"};
        ObservableList<XYChart.Series<String, Number>> bcobslist = FXCollections.observableArrayList();

        textfield1musicartistbandincategorybynumberofsongs.addEventFilter(MouseEvent.ANY, Event::consume);
        x.setTickLabelGap(0);
        y.setTickLabelsVisible(false);

        XYChart.Series<String, Number> xyc1 = new XYChart.Series<>();
        XYChart.Series<String, Number> xyc2 = new XYChart.Series<>();
        XYChart.Series<String, Number> xyc3 = new XYChart.Series<>();
        XYChart.Series<String, Number> xyc4 = new XYChart.Series<>();
        XYChart.Series<String, Number> xyc5 = new XYChart.Series<>();
        XYChart.Series<String, Number> xyc6 = new XYChart.Series<>();
        XYChart.Series<String, Number> xyc7 = new XYChart.Series<>();
        XYChart.Series<String, Number> xyc8 = new XYChart.Series<>();
        XYChart.Series<String, Number> xyc9 = new XYChart.Series<>();
        XYChart.Series<String, Number> xyc10 = new XYChart.Series<>();
        XYChart.Series<String, Number> xyc11 = new XYChart.Series<>();
        XYChart.Series<String, Number> xyc12 = new XYChart.Series<>();
        XYChart.Series<String, Number> xyc13 = new XYChart.Series<>();
        XYChart.Series<String, Number> xyc14 = new XYChart.Series<>();
        XYChart.Series<String, Number> xyc15 = new XYChart.Series<>();
        XYChart.Series<String, Number> xyc16 = new XYChart.Series<>();
        XYChart.Series<String, Number> xyc17 = new XYChart.Series<>();
        XYChart.Series<String, Number> xyc18 = new XYChart.Series<>();
        XYChart.Series<String, Number> xyc19 = new XYChart.Series<>();
        XYChart.Series<String, Number> xyc20 = new XYChart.Series<>();
        String nosongs = noartsbandsincatbynosongsarr[0][0];
        for (String[] genres: noartsbandsincatbynosongsarr)
        {
            if (!nosongs.equals(genres[0]))
            {
                xyc1.setName("Авторская песня, Шансон");
                xyc1.getData().add(new XYChart.Data<>(nosongs, 0));
                xyc2.setName("Альтернатива, Инди");
                xyc2.getData().add(new XYChart.Data<>(nosongs, 0));
                xyc3.setName("Блюз");
                xyc3.getData().add(new XYChart.Data<>(nosongs, 0));
                xyc4.setName("ВИА");
                xyc4.getData().add(new XYChart.Data<>(nosongs, 0));
                xyc5.setName("Вокальная музыка");
                xyc5.getData().add(new XYChart.Data<>(nosongs, 0));
                xyc6.setName("Джаз");
                xyc6.getData().add(new XYChart.Data<>(nosongs, 0));
                xyc7.setName("Кантри");
                xyc7.getData().add(new XYChart.Data<>(nosongs, 0));
                xyc8.setName("Легкая, Инструментальная музыка");
                xyc8.getData().add(new XYChart.Data<>(nosongs, 0));
                xyc9.setName("Метал, Ню-метал, Металкор");
                xyc9.getData().add(new XYChart.Data<>(nosongs, 0));
                xyc10.setName("Панк, Эмо, Постхардкор");
                xyc10.getData().add(new XYChart.Data<>(nosongs, 0));
                xyc11.setName("Поп");
                xyc11.getData().add(new XYChart.Data<>(nosongs, 0));
                xyc12.setName("Поп-рок");
                xyc12.getData().add(new XYChart.Data<>(nosongs, 0));
                xyc13.setName("Регги, Реггетон");
                xyc13.getData().add(new XYChart.Data<>(nosongs, 0));
                xyc14.setName("Рок");
                xyc14.getData().add(new XYChart.Data<>(nosongs, 0));
                xyc15.setName("Соул, Фанк, Диско");
                xyc15.getData().add(new XYChart.Data<>(nosongs, 0));
                xyc16.setName("Хип-хоп");
                xyc16.getData().add(new XYChart.Data<>(nosongs, 0));
                xyc17.setName("Электронная музыка");
                xyc17.getData().add(new XYChart.Data<>(nosongs, 0));
                xyc18.setName("Композиторы");
                xyc18.getData().add(new XYChart.Data<>(nosongs, 0));
                xyc19.setName("Блогеры");
                xyc19.getData().add(new XYChart.Data<>(nosongs, 0));
                xyc20.setName("Каверы");
                xyc20.getData().add(new XYChart.Data<>(nosongs, 0));

                nosongs = genres[0];
            }
            switch (genres[1])
            {
                case "Авторская песня, Шансон":
                    xyc1.getData().add(new XYChart.Data<>(nosongs, Integer.parseInt(genres[2])));
                    break;
                case "Альтернатива, Инди":
                    xyc2.getData().add(new XYChart.Data<>(nosongs, Integer.parseInt(genres[2])));
                    break;
                case "Блюз":
                    xyc3.getData().add(new XYChart.Data<>(nosongs, Integer.parseInt(genres[2])));
                    break;
                case "ВИА":
                    xyc4.getData().add(new XYChart.Data<>(nosongs, Integer.parseInt(genres[2])));
                    break;
                case "Вокальная музыка":
                    xyc5.getData().add(new XYChart.Data<>(nosongs, Integer.parseInt(genres[2])));
                    break;
                case "Джаз":
                    xyc6.getData().add(new XYChart.Data<>(nosongs, Integer.parseInt(genres[2])));
                    break;
                case "Кантри":
                    xyc7.getData().add(new XYChart.Data<>(nosongs, Integer.parseInt(genres[2])));
                    break;
                case "Легкая, Инструментальная музыка":
                    xyc8.getData().add(new XYChart.Data<>(nosongs, Integer.parseInt(genres[2])));
                    break;
                case "Метал, Ню-метал, Металкор":
                    xyc9.getData().add(new XYChart.Data<>(nosongs, Integer.parseInt(genres[2])));
                    break;
                case "Панк, Эмо, Постхардкор":
                    xyc10.getData().add(new XYChart.Data<>(nosongs, Integer.parseInt(genres[2])));
                    break;
                case "Поп":
                    xyc11.getData().add(new XYChart.Data<>(nosongs, Integer.parseInt(genres[2])));
                    break;
                case "Поп-рок":
                    xyc12.getData().add(new XYChart.Data<>(nosongs, Integer.parseInt(genres[2])));
                    break;
                case "Регги, Реггетон":
                    xyc13.getData().add(new XYChart.Data<>(nosongs, Integer.parseInt(genres[2])));
                    break;
                case "Рок":
                    xyc14.getData().add(new XYChart.Data<>(nosongs, Integer.parseInt(genres[2])));
                    break;
                case "Соул, Фанк, Диско":
                    xyc15.getData().add(new XYChart.Data<>(nosongs, Integer.parseInt(genres[2])));
                    break;
                case "Хип-хоп":
                    xyc16.getData().add(new XYChart.Data<>(nosongs, Integer.parseInt(genres[2])));
                    break;
                case "Электронная музыка":
                    xyc17.getData().add(new XYChart.Data<>(nosongs, Integer.parseInt(genres[2])));
                    break;
                case "Композиторы":
                    xyc18.getData().add(new XYChart.Data<>(nosongs, Integer.parseInt(genres[2])));
                    break;
                case "Блогеры":
                    xyc19.getData().add(new XYChart.Data<>(nosongs, Integer.parseInt(genres[2])));
                    break;
                case "Каверы":
                    xyc20.getData().add(new XYChart.Data<>(nosongs, Integer.parseInt(genres[2])));
                    break;
            }
        }
        bcobslist.addAll(xyc1, xyc2, xyc3, xyc4, xyc5, xyc6, xyc7, xyc8, xyc9, xyc10, xyc11, xyc12, xyc13, xyc14, xyc15, xyc16, xyc17, xyc18, xyc19, xyc20);
        bc.getData().addAll(bcobslist);

        bc.applyCss();
        for (String[] noartsbandsbynosongs: noartsbandsbynosongslinarr)
        {
            linecharttick = new Line();
            linecharttick.getStrokeDashArray().addAll(3.0, 3.0);
            linecharttick.setStrokeWidth(0.5);
            linecharttick.setStyle("-fx-stroke: #4B0000");
            linecharttick.setEndX(1249);
            AnchorPane1.getChildren().add(linecharttick);
            linecharttick.translateYProperty().bind(Bindings.createDoubleBinding(() -> y.getDisplayPosition(Integer.parseInt(noartsbandsbynosongs[0])), y.lowerBoundProperty(), y.upperBoundProperty()));
        }
        double lx = 1240.0/noartsbandsbynosongslabarr.length;
        for (String[] noartsbandsbynosongs: noartsbandsbynosongslabarr)
        {
            labeltick = new Label(String.valueOf(noartsbandsbynosongs[0]));
            labeltick.setPrefWidth(18);
            labeltick.setStyle("-fx-alignment: CENTER; -fx-font-family: Times New Roman; -fx-font-size: 10; -fx-text-fill: #000000");
            labeltick.setLayoutX(lx+44.25);
            AnchorPane2.getChildren().add(labeltick);
            labeltick.translateYProperty().bind(Bindings.createDoubleBinding(() -> y.getDisplayPosition(Integer.parseInt(noartsbandsbynosongs[0])+6), y.lowerBoundProperty(), y.upperBoundProperty()));
            lx+=1240.0/noartsbandsbynosongslabarr.length;
        }
        for (int i = 0; i < 17; i++)
            noartsbandsarr[i] = new String[]{String.valueOf(i+1), Arrays.asList(AudioLibraryDB.select(2)[i]).get(1)};
        noartsbandsarr[17] = new String[]{"18", Arrays.asList(AudioLibraryDB.select(1)[1]).get(1)};
        noartsbandsarr[18] = new String[]{"19", Arrays.asList(AudioLibraryDB.select(1)[2]).get(1)};
        noartsbandsarr[19] = new String[]{"20", Arrays.asList(AudioLibraryDB.select(1)[3]).get(1)};
        Arrays.sort(noartsbandsarr, Comparator.comparingInt((String[] a) -> Integer.parseInt(a[1])).reversed());
        int col = 0;
        for (int i = 0; i < 20; i++)
        {
            try
            {
                if (!noartsbandsarr[i][1].equals(noartsbandsarr[i+1][1]))
                    colarrsort[i] = new String[]{noartsbandsarr[i][0], colarr[col]};
                else
                {
                    colarrsort[i] = new String[]{noartsbandsarr[i][0], colarr[col]};
                    col-=1;
                }
            }
            catch (Exception e)
            {
                colarrsort[i] = new String[]{noartsbandsarr[i][0], colarr[col]};
            }
            col+=1;
        }
        col = 0;
        Arrays.sort(colarrsort, Comparator.comparingInt(a -> Integer.parseInt(a[0])));
        List<Node> clilist = new ArrayList<>(bc.lookupAll(".chart-legend-item"));
        for (Node leg: clilist)
        {
            Node clis = leg.lookup(".chart-legend-item-symbol");
            clis.setStyle("-fx-background-color: " + colarrsort[col][1] + ";");
            col+=1;
        }
        bc.lookup(".chart-legend").setStyle("-fx-background-color: #FFFFFF; -fx-border-width: 1 0 0 0; -fx-border-color: #4B0000; -fx-font-family: Times New Roman; -fx-font-style: italic; -fx-font-size: 12; -fx-text-fill: #000000");
        VBox1.getChildren().add(bc.lookup(".chart-legend"));
        for (int i = 0; i < 20; i++)
            for (XYChart.Data<String, Number> xycdat: bc.getData().get(i).getData())
            {
                String stackgenr = bc.getData().get(i).getName();
                xycdat.getNode().setStyle("-fx-border-width: 0.5 0 0 0; -fx-border-color: #FF0000; -fx-bar-fill: " + colarrsort[i][1]);
                xycdat.getNode().setOnMouseEntered(e ->
                {
                    xycdat.getNode().setUserData(xycdat.getNode().getStyle());
                    xycdat.getNode().setStyle("-fx-border-width: 0.5 0 0 0; -fx-border-color: #FF0000; -fx-bar-fill: derive(" + xycdat.getNode().getStyle().substring(xycdat.getNode().getStyle().lastIndexOf(":")+2) + ", 20%)");
                });
                xycdat.getNode().setOnMouseExited(e -> xycdat.getNode().setStyle(String.valueOf(xycdat.getNode().getUserData())));
                xycdat.getNode().setOnMouseClicked(e ->
                {
                    ScrollPane1.setVvalue(0);
                    if (!xycdat.getXValue().equals("1"))
                        if (xycdat.getYValue().intValue() != 1)
                            textfield1musicartistbandincategorybynumberofsongs.setText(xycdat.getYValue().intValue() + " artists/bands with " + xycdat.getXValue() + " songs in " + stackgenr);
                        else
                            textfield1musicartistbandincategorybynumberofsongs.setText(xycdat.getYValue().intValue() + " artist/band with " + xycdat.getXValue() + " songs in " + stackgenr);
                    else
                        if (xycdat.getYValue().intValue() != 1)
                            textfield1musicartistbandincategorybynumberofsongs.setText(xycdat.getYValue().intValue() + " artists/bands with " + xycdat.getXValue() + " song in " + stackgenr);
                        else
                            textfield1musicartistbandincategorybynumberofsongs.setText(xycdat.getYValue().intValue() + " artist/band with " + xycdat.getXValue() + " song in " + stackgenr);
                    try
                    {
                        VBox2.getChildren().clear();
                        Text artsbands = new Text(AudioLibraryDB.visualization(1, 4, stackgenr, "", Integer.parseInt(xycdat.getXValue()))[0][0]);
                        artsbands.setWrappingWidth(395);
                        artsbands.setStyle("-fx-text-alignment: CENTER; -fx-font-family: Times New Roman; -fx-font-style: italic; -fx-font-size: 12; -fx-text-fill: #000000");
                        VBox2.getChildren().add(artsbands);
                    }
                    catch (Exception e1)
                    {
                        throw new RuntimeException(e1);
                    }
                });
                Tooltip.install(xycdat.getNode(), new Tooltip(stackgenr + ": " + xycdat.getYValue().intValue()));
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