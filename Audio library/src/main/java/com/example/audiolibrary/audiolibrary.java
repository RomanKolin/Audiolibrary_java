package com.example.audiolibrary;

import javafx.application.*;
import javafx.fxml.*;
import javafx.util.*;
import javafx.stage.*;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.scene.input.*;
import javafx.scene.transform.*;
import javafx.scene.image.*;
import javafx.scene.text.Text;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.collections.*;
import javafx.beans.property.*;
import javafx.beans.value.*;
import java.io.*;
import java.util.*;
import java.time.*;
import java.time.format.*;
import java.time.Duration;
import java.sql.*;

public class audiolibrary extends Application
{
    @FXML
    Scene scene;
    @FXML
    FlowPane FlowPane1;
    @FXML
    StackPane StackPane1;
    @FXML
    HBox HBox1;
    @FXML
    Label label1change;
    @FXML
    Label label2change;
    @FXML
    Label label3change;
    @FXML
    Label label4change;
    @FXML
    Label label5change;
    @FXML
    Label label6change;
    @FXML
    Label label7change;
    @FXML
    Label label8change;
    @FXML
    Label label9change;
    @FXML
    Label label10change;
    @FXML
    Label label11change;
    @FXML
    Label label12change;
    @FXML
    Label label13change;
    @FXML
    Label label14change;
    @FXML
    Label label15change;
    @FXML
    Label label16change;
    @FXML
    Label label17change;
    @FXML
    Label label18change;
    @FXML
    Label label19change;
    @FXML
    Label label20combobox;
    @FXML
    Label label21combobox;
    @FXML
    Label label22combobox;
    @FXML
    Label label23combobox;
    @FXML
    Label label24numberofartistssongscountandduration;
    @FXML
    Label label25numberofrelatedartistssongscountandduration;
    @FXML
    Label label26songscountandduration;
    @FXML
    TextField textfield1change;
    @FXML
    TextArea textfield2change;
    @FXML
    TextArea textfield3change;
    @FXML
    TextArea textfield4change;
    @FXML
    TextField textfield5change;
    @FXML
    TextField textfield6change;
    @FXML
    TextField textfield7change;
    @FXML
    TextField textfield8change;
    @FXML
    TextField textfield9change;
    @FXML
    TextField textfield10change;
    @FXML
    TextArea textfield11change;
    @FXML
    TextField textfield12change;
    @FXML
    TextField textfield13change;
    @FXML
    TextField textfield14change;
    @FXML
    TextField textfield15change;
    @FXML
    TextField textfield16change;
    @FXML
    TextField textfield17search;
    @FXML
    TextField textfield18search;
    @FXML
    Button button1audiolibrarygenre;
    @FXML
    Button button2musicartistband;
    @FXML
    Button button3composers;
    @FXML
    Button button4bloggers;
    @FXML
    Button button5covers;
    @FXML
    Button button6soundtracks;
    @FXML
    Button button7favourites;
    @FXML
    Button button8add;
    @FXML
    TableView<String[]> tableview1audiolibrary;
    @FXML
    TableView<String[]> tableview2genrerelatedartistsbands;
    @FXML
    TableView<String[]> tableview3songs;
    @FXML
    ComboBox combobox1filtering;
    @FXML
    ComboBox combobox2filtering;
    @FXML
    ComboBox combobox3filtering;
    @FXML
    ComboBox combobox4filtering;

    Label label3;
    Label label4;
    Label label5;
    Label label6;
    Label label7;
    Label label8;
    Label label9;
    Label label10;
    Label label12;
    Label label13;
    Label label14;
    Label label15;
    Label label17;
    Label label18;
    Label label19;
    TextArea textfield2;
    TextField textfield9;
    ContextMenu cm;
    static Label label24;
    static Label label25;
    static Label label26;
    static TextField textfield1;
    static TextArea textfield3;
    static TextArea textfield4;
    static TextField textfield5;
    static TextField textfield6;
    static TextField textfield7;
    static TextField textfield8;
    static TextField textfield10;
    static TextArea textfield11;
    static TextField textfield12;
    static TextField textfield13;
    static TextField textfield14;
    static TextField textfield15;
    static TextField textfield16;
    static TextField textfield17;
    static TextField textfield18;
    static Button button2;
    static Button button3;
    static Button button4;
    static Button button5;
    static Button button6;
    static Button button8;
    static TableView<String[]> tableview1;
    static TableView<String[]> tableview2;
    static TableView<String[]> tableview3;
    static ComboBox combobox1;
    static ComboBox combobox2;
    static ComboBox combobox3;
    static ComboBox combobox4;

    @Override
    public void start(Stage stage) throws IOException
    {
        FXMLLoader fxmlLoader = new FXMLLoader(audiolibrary.class.getResource("audiolibrary.fxml"));
        System.setProperty("prism.lcdtext", "false");
        scene = new Scene(fxmlLoader.load());
        scene.getStylesheets().add("/contextmenu.css");
        scene.getStylesheets().add("/tooltip.css");
        stage.setTitle("Audio library");
        stage.setScene(scene);
        stage.getIcons().add(new Image("/audiolibraryicon.png"));
        stage.setWidth(Screen.getPrimary().getBounds().getWidth());
        stage.setHeight(Screen.getPrimary().getBounds().getHeight());
        stage.setResizable(false);
        stage.show();
    }

    EventHandler<MouseEvent> me = Event::consume;
    int subtabl, siz10, rfnum, rsnum, rtnum, nodat, songsartband;
    static int tabl, siz9, siz11, siz12, siz13, siz14, siz15, artbandid, relartbandid, songid, coverid, soundtrackid, backup;
    static String nam9, nam10, nam11, nam12, nam13, nam14, nam15;
    static String[] coverartbandarr;
    static LocalTime songdur;
    static ObservableList<String[]> obslist = FXCollections.observableArrayList();
    static ObservableList<String[]> sub1obslist = FXCollections.observableArrayList();
    static ObservableList<String[]> sub2obslist = FXCollections.observableArrayList();
    static ObservableList<String[]> sub2sub1obslist = FXCollections.observableArrayList();

    public void initialize()
    {
        if (Screen.getPrimary().getBounds().getWidth() >= 1920)
            FlowPane1.getTransforms().add(new Scale(Screen.getPrimary().getBounds().getWidth()/1280, Screen.getPrimary().getBounds().getHeight()/1024));

        label24 = label24numberofartistssongscountandduration;
        label25 = label25numberofrelatedartistssongscountandduration;
        label26 = label26songscountandduration;
        textfield1 = textfield1change;
        textfield3 = textfield3change;
        textfield4 = textfield4change;
        textfield5 = textfield5change;
        textfield6 = textfield6change;
        textfield7 = textfield7change;
        textfield8 = textfield8change;
        textfield10 = textfield10change;
        textfield11 = textfield11change;
        textfield12 = textfield12change;
        textfield13 = textfield13change;
        textfield14 = textfield14change;
        textfield15 = textfield15change;
        textfield16 = textfield16change;
        textfield17 = textfield17search;
        textfield18 = textfield18search;
        button2 = button2musicartistband;
        button3 = button3composers;
        button4 = button4bloggers;
        button5 = button5covers;
        button6 = button6soundtracks;
        button8 = button8add;
        tableview1 = tableview1audiolibrary;
        tableview2 = tableview2genrerelatedartistsbands;
        tableview3 = tableview3songs;
        combobox1 = combobox1filtering;
        combobox2 = combobox2filtering;
        combobox3 = combobox3filtering;
        combobox4 = combobox4filtering;

        ContextMenu();
        button1audiolibrarygenre.fire();

        tableview1.setRowFactory(rf ->
        {
            TableRow row = new TableRow();
            row.setOnMouseClicked(e ->
            {
                if (e.getButton() == MouseButton.PRIMARY)
                {
                    textfield18.setText("");
                    if (tabl == 1 || tabl == 3 || tabl == 4 || tabl == 5 || tabl == 8)
                    {
                        if (tabl == 1)
                        {
                            if (textfield1.getText().equals("Жанр"))
                                tableview2.removeEventFilter(MouseEvent.ANY, me);
                            else
                            {
                                tableview2.getSelectionModel().clearSelection();
                                textfield10.setText("");
                                textfield11.setText("");
                                textfield12.setText("");
                                textfield13.setText("");
                                tableview2.addEventFilter(MouseEvent.ANY, me);
                            }
                            tableview2.getSelectionModel().selectedItemProperty().addListener((sub1s, sub1os, sub1ns) ->
                            {
                                if (sub1ns != null)
                                {
                                    textfield10.setText(Arrays.asList(tableview2.getSelectionModel().getSelectedItem()).get(0));
                                    textfield11.setText(Arrays.asList(tableview2.getSelectionModel().getSelectedItem()).get(1));
                                    textfield12.setText(Arrays.asList(tableview2.getSelectionModel().getSelectedItem()).get(2));
                                    textfield13.setText(Arrays.asList(tableview2.getSelectionModel().getSelectedItem()).get(3));
                                }
                            });
                        }
                        if (tabl == 3 || tabl == 4 || tabl == 5 || tabl == 8)
                        {
                            tableview2.getItems().clear();
                            tableview3.getItems().clear();
                            try
                            {
                                sub1obslist.clear();
                                if (tabl == 3)
                                    sub1obslist.addAll(Arrays.asList(audiolibrarydb.SUB1SELECT(Integer.parseInt(Arrays.asList(tableview1.getSelectionModel().getSelectedItem()).get(8)))));
                                else if (tabl == 8)
                                    sub1obslist.addAll(Arrays.asList(audiolibrarydb.SUB1SELECT(audiolibrarydb.MusicArtistBandID(Arrays.asList(tableview1.getSelectionModel().getSelectedItem()).get(0)))));
                            }
                            catch (Exception re)
                            {
                                throw new RuntimeException(re);
                            }
                            tableview2.getColumns().clear();
                            TableColumn sub1first = new TableColumn<>(nam9);
                            TableColumn sub1second = new TableColumn<>(nam10);
                            TableColumn<String[], String> sub1third = new TableColumn<>(nam11);
                            TableColumn<String[], String> sub1fourth = new TableColumn<>(nam12);
                            TableColumn sub1fifth = new TableColumn<>("");
                            if (tabl == 3 || tabl == 8)
                            {
                                sub1third.setComparator(Comparator.comparingInt(Integer::parseInt));
                                sub1fourth.setComparator(Comparator.comparing(s ->
                                {
                                    if (s.split(":").length == 2)
                                        return Duration.ofMinutes(Integer.parseInt(s.split(":")[0])).plusSeconds(Integer.parseInt(s.split(":")[1]));
                                    else
                                        return Duration.ofHours(Integer.parseInt(s.split(":")[0])).plusMinutes(Integer.parseInt(s.split(":")[1])).plusSeconds(Integer.parseInt(s.split(":")[2]));
                                }));
                            }
                            tableview2.getColumns().add(sub1first);
                            tableview2.getColumns().add(sub1second);
                            tableview2.getColumns().add(sub1third);
                            tableview2.getColumns().add(sub1fourth);
                            tableview2.getColumns().add(sub1fifth);
                            sub1first.setCellValueFactory((Callback<TableColumn.CellDataFeatures<String[], String>, ObservableValue<String>>) v -> new SimpleStringProperty(v.getValue()[0]));
                            sub1second.setCellValueFactory((Callback<TableColumn.CellDataFeatures<String[], String>, ObservableValue<String>>) v -> new SimpleStringProperty(v.getValue()[1]));
                            sub1third.setCellValueFactory(v -> new SimpleStringProperty(v.getValue()[2]));
                            sub1fourth.setCellValueFactory(v -> new SimpleStringProperty(v.getValue()[3]));
                            sub1fifth.setCellValueFactory((Callback<TableColumn.CellDataFeatures<String[], String>, ObservableValue<String>>) v -> new SimpleStringProperty(v.getValue()[4]));
                            sub1first.setPrefWidth(siz9);
                            sub1second.setVisible(false);
                            sub1third.setPrefWidth(siz11);
                            sub1fourth.setPrefWidth(siz12);
                            sub1fifth.setVisible(false);
                            ColumnProperties(sub1first, sub1second, sub1third, sub1fourth, sub1fifth, null, null, null, null);
                            Tip(sub1first);
                            tableview2.setItems(sub1obslist);
                            NumberofArtistsSongsCountandDuration(tableview2, label25, 2);
                            textfield10.setText("");
                            textfield11.setText("");
                            textfield12.setText("");
                            textfield13.setText("");
                            sub2obslist.clear();
                            if (tabl == 3 || tabl == 8)
                            {
                                try
                                {
                                    if (tabl == 3)
                                        sub2obslist.addAll(Arrays.asList(audiolibrarydb.SUB2SELECT(Integer.parseInt(Arrays.asList(tableview1.getSelectionModel().getSelectedItem()).get(8)), 0)));
                                    else
                                        sub2obslist.addAll(Arrays.asList(audiolibrarydb.SUB2SELECT(audiolibrarydb.MusicArtistBandID(Arrays.asList(tableview1.getSelectionModel().getSelectedItem()).get(0)), 0)));
                                }
                                catch (Exception re)
                                {
                                    throw new RuntimeException(re);
                                }
                                songsartband = Integer.parseInt(Arrays.asList(tableview1.getSelectionModel().getSelectedItem()).get(8));
                            }
                            else if (tabl == 4 || tabl == 5)
                            {
                                try
                                {
                                    sub2obslist.addAll(Arrays.asList(audiolibrarydb.SUB2SELECT(Integer.parseInt(Arrays.asList(tableview1.getSelectionModel().getSelectedItem()).get(4)), 0)));
                                }
                                catch (Exception re)
                                {
                                    throw new RuntimeException(re);
                                }
                            }
                            tableview3.getColumns().clear();
                            TableColumn sub2first = new TableColumn<>(nam13);
                            TableColumn<String[], String> sub2second = new TableColumn<>(nam14);
                            TableColumn sub2third = new TableColumn<>(nam15);
                            TableColumn sub2fourth = new TableColumn<>("");
                            if (tabl == 3 || tabl == 4 || tabl == 5 || tabl == 8)
                            {
                                sub2second.setComparator(Comparator.comparing(s ->
                                {
                                    if (s.split(":").length == 2)
                                        return Duration.ofMinutes(Integer.parseInt(s.split(":")[0])).plusSeconds(Integer.parseInt(s.split(":")[1]));
                                    else
                                        return Duration.ofHours(Integer.parseInt(s.split(":")[0])).plusMinutes(Integer.parseInt(s.split(":")[1])).plusSeconds(Integer.parseInt(s.split(":")[2]));
                                }));
                            }
                            tableview3.getColumns().add(sub2first);
                            tableview3.getColumns().add(sub2second);
                            tableview3.getColumns().add(sub2third);
                            tableview3.getColumns().add(sub2fourth);
                            sub2first.setCellValueFactory((Callback<TableColumn.CellDataFeatures<String[], String>, ObservableValue<String>>) v -> new SimpleStringProperty(v.getValue()[0]));
                            sub2second.setCellValueFactory(v -> new SimpleStringProperty(v.getValue()[1]));
                            sub2third.setCellValueFactory((Callback<TableColumn.CellDataFeatures<String[], String>, ObservableValue<String>>) v -> new SimpleStringProperty(v.getValue()[2]));
                            sub2fourth.setCellValueFactory((Callback<TableColumn.CellDataFeatures<String[], String>, ObservableValue<String>>) v -> new SimpleStringProperty(v.getValue()[3]));
                            sub2first.setPrefWidth(siz13);
                            sub2second.setPrefWidth(siz14);
                            sub2third.setPrefWidth(siz15);
                            sub2fourth.setVisible(false);
                            ColumnProperties(sub2first, sub2second, sub2third, sub2fourth, null, null, null, null, null);
                            Tip(sub2first);
                            Tip(sub2third);
                            tableview3.setItems(sub2obslist);
                            NumberofArtistsSongsCountandDuration(tableview3, label26, 1);
                            tableview2.getSelectionModel().selectedItemProperty().addListener((sub1s, sub1os, sub1ns) ->
                            {
                                if (sub1ns != null)
                                {
                                    textfield18.setText("");
                                    textfield10.setText(Arrays.asList(tableview2.getSelectionModel().getSelectedItem()).get(0));
                                    textfield11.setText(Arrays.asList(tableview2.getSelectionModel().getSelectedItem()).get(1));
                                    textfield12.setText(Arrays.asList(tableview2.getSelectionModel().getSelectedItem()).get(2));
                                    textfield13.setText(Arrays.asList(tableview2.getSelectionModel().getSelectedItem()).get(3));
                                    if (tabl == 3 || tabl == 8)
                                        relartbandid = Integer.parseInt(Arrays.asList(tableview2.getSelectionModel().getSelectedItem()).get(4));
                                    sub2sub1obslist.clear();
                                    tableview3.getColumns().clear();
                                    if (tabl == 3 || tabl == 8 && !tableview2.getItems().isEmpty())
                                    {
                                        try
                                        {
                                            sub2sub1obslist.addAll(Arrays.asList(audiolibrarydb.SUB2SELECT(Integer.parseInt(Arrays.asList(tableview2.getSelectionModel().getSelectedItem()).get(4)), 1)));
                                        }
                                        catch (Exception re)
                                        {
                                            throw new RuntimeException(re);
                                        }
                                        songsartband = Integer.parseInt(Arrays.asList(tableview2.getSelectionModel().getSelectedItem()).get(4));
                                    }
                                    TableColumn sub2sub1first = new TableColumn<>(nam13);
                                    TableColumn<String[], String> sub2sub1second = new TableColumn<>(nam14);
                                    TableColumn sub2sub1third = new TableColumn<>(nam15);
                                    TableColumn sub2sub1fourth = new TableColumn<>("");
                                    if (tabl == 3 || tabl == 8)
                                    {
                                        sub2sub1second.setComparator(Comparator.comparing(s ->
                                        {
                                            if (s.split(":").length == 2)
                                                return Duration.ofMinutes(Integer.parseInt(s.split(":")[0])).plusSeconds(Integer.parseInt(s.split(":")[1]));
                                            else
                                                return Duration.ofHours(Integer.parseInt(s.split(":")[0])).plusMinutes(Integer.parseInt(s.split(":")[1])).plusSeconds(Integer.parseInt(s.split(":")[2]));
                                        }));
                                    }
                                    tableview3.getColumns().add(sub2sub1first);
                                    tableview3.getColumns().add(sub2sub1second);
                                    tableview3.getColumns().add(sub2sub1third);
                                    tableview3.getColumns().add(sub2sub1fourth);
                                    sub2sub1first.setCellValueFactory((Callback<TableColumn.CellDataFeatures<String[], String>, ObservableValue<String>>) v -> new SimpleStringProperty(v.getValue()[0]));
                                    sub2sub1second.setCellValueFactory(v -> new SimpleStringProperty(v.getValue()[1]));
                                    sub2sub1third.setCellValueFactory((Callback<TableColumn.CellDataFeatures<String[], String>, ObservableValue<String>>) v -> new SimpleStringProperty(v.getValue()[2]));
                                    sub2sub1fourth.setCellValueFactory((Callback<TableColumn.CellDataFeatures<String[], String>, ObservableValue<String>>) v -> new SimpleStringProperty(v.getValue()[3]));
                                    sub2sub1first.setPrefWidth(siz13);
                                    sub2sub1second.setPrefWidth(siz14);
                                    sub2sub1third.setPrefWidth(siz15);
                                    sub2sub1fourth.setVisible(false);
                                    ColumnProperties(sub2sub1first, sub2sub1second, sub2sub1third, sub2sub1fourth, null, null, null, null, null);
                                    Tip(sub2sub1first);
                                    Tip(sub2sub1third);
                                    tableview3.setItems(sub2sub1obslist);
                                    NumberofArtistsSongsCountandDuration(tableview3, label26, 1);
                                }
                            });
                            tableview3.getSelectionModel().selectedItemProperty().addListener((sub2s, sub2os, sub2ns) ->
                            {
                                if (sub2ns != null)
                                {
                                    textfield14.setText(Arrays.asList(tableview3.getSelectionModel().getSelectedItem()).get(0));
                                    textfield15.setText(Arrays.asList(tableview3.getSelectionModel().getSelectedItem()).get(1));
                                    textfield16.setText(Arrays.asList(tableview3.getSelectionModel().getSelectedItem()).get(2));
                                    songid = Integer.parseInt(Arrays.asList(tableview3.getSelectionModel().getSelectedItem()).get(3));
                                    if ((String.valueOf(Arrays.asList(tableview3.getSelectionModel().getSelectedItem()).get(1)).length()>=3 && String.valueOf(Arrays.asList(tableview3.getSelectionModel().getSelectedItem()).get(1)).length()<=5) && Arrays.asList(tableview3.getSelectionModel().getSelectedItem()).get(1).lastIndexOf(":") != 3)
                                        songdur = LocalTime.parse("00:" + Arrays.asList(tableview3.getSelectionModel().getSelectedItem()).get(1), DateTimeFormatter.ofPattern("H:m:s"));
                                    else
                                        songdur = LocalTime.parse(Arrays.asList(tableview3.getSelectionModel().getSelectedItem()).get(1), DateTimeFormatter.ofPattern("H:m:s"));
                                }
                            });
                        }
                    }
                }
                if (e.getButton() == MouseButton.SECONDARY)
                {
                    cm.show(row, e.getScreenX(), e.getScreenY());
                    cm.setOnAction(ecm ->
                    {
                        try
                        {
                            if (tabl == 3 || tabl == 4 || tabl == 5 || tabl == 8)
                                Runtime.getRuntime().exec(new String[] {"firefox", "https://www.last.fm/music/" + Arrays.asList(tableview1.getSelectionModel().getSelectedItem()).get(0).replace("#", "%23")});
                            else if (tabl == 6)
                            {
                                Runtime.getRuntime().exec(new String[] {"firefox", "https://www.last.fm/music/" + Arrays.asList(tableview1.getSelectionModel().getSelectedItem()).get(1).replace("#", "%23") + "/_/" + Arrays.asList(tableview1.getSelectionModel().getSelectedItem()).get(0).replace("#", "%23")});
                                Runtime.getRuntime().exec(new String[] {"firefox", "https://www.last.fm/music/" + Arrays.asList(tableview1.getSelectionModel().getSelectedItem()).get(4).replace("#", "%23") + "/_/" + Arrays.asList(tableview1.getSelectionModel().getSelectedItem()).get(0).replace("#", "%23")});
                            }
                        }
                        catch (IOException ioee)
                        {
                            throw new RuntimeException();
                        }
                    });
                }
            });
            return row;
        });
        tableview1.setOnKeyPressed(e ->
        {
            if (e.getCode().equals(KeyCode.UP) || e.getCode().equals(KeyCode.DOWN))
                RowClick(tableview1, tableview1.getSelectionModel().getSelectedIndex());
        });
        tableview2.setRowFactory(rf ->
        {
            TableRow row = new TableRow();
            row.setOnMouseClicked(emouse ->
            {
                if (emouse.getButton() == MouseButton.SECONDARY)
                {
                    cm.show(row, emouse.getScreenX(), emouse.getScreenY());
                    cm.setOnAction(ecm ->
                    {
                        try
                        {
                            Runtime.getRuntime().exec(new String[] {"firefox", "https://www.last.fm/music/" + Arrays.asList(tableview2.getSelectionModel().getSelectedItem()).get(0).replace("#", "%23")});
                        }
                        catch (IOException e)
                        {
                            throw new RuntimeException();
                        }
                    });
                }
            });
            return row;
        });
        tableview3.setRowFactory(rf ->
        {
            TableRow row = new TableRow();
            row.setOnMouseClicked(e ->
            {
                if (e.getButton() == MouseButton.SECONDARY)
                {
                    cm.show(row, e.getScreenX(), e.getScreenY());
                    cm.setOnAction(ecm ->
                    {
                        try
                        {
                            try
                            {
                                Runtime.getRuntime().exec(new String[] {"firefox", "https://www.last.fm/music/" + Arrays.asList(tableview2.getSelectionModel().getSelectedItem()).get(0).replace("#", "%23") + "/_/" + Arrays.asList(tableview3songs.getSelectionModel().getSelectedItem()).get(0).replace("#", "%23")});
                            }
                            catch (RuntimeException re)
                            {
                                Runtime.getRuntime().exec(new String[] {"firefox", "https://www.last.fm/music/" + Arrays.asList(tableview1.getSelectionModel().getSelectedItem()).get(0).replace("#", "%23") + "/_/" + Arrays.asList(tableview3songs.getSelectionModel().getSelectedItem()).get(0).replace("#", "%23")});
                            }
                        }
                        catch (IOException ioee)
                        {
                            throw new RuntimeException();
                        }
                    });
                }
            });
            return row;
        });
    }

    public void ContextMenu() throws RuntimeException
    {
        if (tabl == 3 || tabl == 4 || tabl == 5 || tabl == 6 || tabl == 8)
        {
            MenuItem mi;
            cm = new ContextMenu();
            mi = new MenuItem("Open on Last.fm");
            mi.setStyle("-fx-text-fill: #000000");
            cm.getItems().add(mi);
        }
    }

    public void Tip(TableColumn col)
    {
        col.setCellFactory(cf -> new TableCell<String, String>()
        {
            final Tooltip tooltip = new Tooltip();

            @Override
            protected void updateItem(String item, boolean empty)
            {
                super.updateItem(item, empty);

                setText(item);
                Platform.runLater(() ->
                {
                    Text itemtext = new Text(item);
                    itemtext.setFont(getFont());
                    if (itemtext.getLayoutBounds().getWidth() > (getWidth() - getPadding().getLeft() - getPadding().getRight()) && getWidth() > 0)
                    {
                        tooltip.setText(item);
                        setTooltip(tooltip);
                    }
                    else
                        setTooltip(null);
                });
            }
        });
    }

    public void Clear()
    {
        textfield1.setText("");
        textfield2.setText("");
        textfield3.setText("");
        textfield4.setText("");
        textfield5.setText("");
        textfield6.setText("");
        textfield7.setText("");
        textfield8.setText("");
        if (tabl == 8)
            textfield9.setText("");
        textfield10.setText("");
        textfield11.setText("");
        textfield12.setText("");
        textfield13.setText("");
        textfield14.setText("");
        textfield15.setText("");
        textfield16.setText("");
        textfield18.setText("");
        if (tabl != 1)
        {
            tableview2.getItems().clear();
            label25.setText("Number of artists; songs count/duration: 0; 0/0:0");
        }
        tableview3.getItems().clear();
        label26.setText("Songs count/duration: 0/0:0");
    }

    public void NumberofArtistsSongsCountandDuration(TableView<String[]> tableviewstring, Label label, int rsong)
    {
        int noart = 0, songscount = 0, day;
        String dur;
        StringBuilder coverartband = new StringBuilder();
        LocalDateTime songsdur = LocalDateTime.of(0, 1, 1, 0, 0, 0);

        label.setText("");
        if (tabl == 1)
        {
            for (int i = 0; i < tableviewstring.getItems().size(); i++)
            {
                tableviewstring.getSelectionModel().select(i);
                noart += Integer.parseInt(Arrays.asList(tableviewstring.getSelectionModel().getSelectedItem()).get(rsong-1));
                songscount += Integer.parseInt(Arrays.asList(tableviewstring.getSelectionModel().getSelectedItem()).get(rsong));
                if (!Arrays.asList(tableviewstring.getSelectionModel().getSelectedItem()).get(3).contains("d"))
                {
                    day = 0;
                    dur = Arrays.asList(tableviewstring.getSelectionModel().getSelectedItem()).get(3).trim();
                }
                else
                {
                    day = Integer.parseInt(Arrays.asList(tableviewstring.getSelectionModel().getSelectedItem()).get(3).substring(0, 2).replace("d", "").trim());
                    dur = Arrays.asList(tableviewstring.getSelectionModel().getSelectedItem()).get(3).substring(3).trim();
                }
                songsdur = songsdur.plusDays(day).plusHours(Time.valueOf(dur).getHours()).plusMinutes(Time.valueOf(dur).getMinutes()).plusSeconds(Time.valueOf(dur).getSeconds());
            }
            label.setText("Number of artists; songs count/duration: " + noart + "; " + songscount + "/" + (songsdur.getDayOfYear()-1) + "d " + songsdur.getHour() + ":" + songsdur.getMinute() + ":" + songsdur.getSecond());
        }
        else if (tabl == 6)
        {
            for (int i = 0; i < tableviewstring.getItems().size(); i++)
            {
                tableviewstring.getSelectionModel().select(i);
                if ((Arrays.asList(tableviewstring.getSelectionModel().getSelectedItem()).get(3).length()>=3 && Arrays.asList(tableviewstring.getSelectionModel().getSelectedItem()).get(3).length()<=5) && Arrays.asList(tableviewstring.getSelectionModel().getSelectedItem()).get(3).lastIndexOf(":") != 3)
                    dur = "00:" + Arrays.asList(tableviewstring.getSelectionModel().getSelectedItem()).get(3);
                else
                    dur = Arrays.asList(tableviewstring.getSelectionModel().getSelectedItem()).get(3);
                songsdur = songsdur.plusHours(Time.valueOf(dur).getHours()).plusMinutes(Time.valueOf(dur).getMinutes()).plusSeconds(Time.valueOf(dur).getSeconds());
                for (String artband:coverartbandarr)
                    if (Arrays.asList(tableviewstring.getSelectionModel().getSelectedItem()).get(1).equals(artband) && !String.valueOf(coverartband).contains(artband))
                    {
                        noart+=1;
                        coverartband.append(artband);
                    }
            }
            label.setText("Number of artists; songs count/duration: " + noart + "; " + tableviewstring.getItems().size() + "/" + (songsdur.getDayOfYear()-1) + "d " + songsdur.getHour() + ":" + songsdur.getMinute() + ":" + songsdur.getSecond());
        }
        else if (tabl == 7)
        {
            for (int i = 0; i < tableviewstring.getItems().size(); i++)
            {
                tableviewstring.getSelectionModel().select(i);
                songscount += Integer.parseInt(Arrays.asList(tableviewstring.getSelectionModel().getSelectedItem()).get(rsong));
                if ((Arrays.asList(tableviewstring.getSelectionModel().getSelectedItem()).get(4).length()>=3 && Arrays.asList(tableviewstring.getSelectionModel().getSelectedItem()).get(4).length()<=5) && Arrays.asList(tableviewstring.getSelectionModel().getSelectedItem()).get(4).lastIndexOf(":") != 3)
                    dur = "00:" + Arrays.asList(tableviewstring.getSelectionModel().getSelectedItem()).get(4);
                else
                    dur = Arrays.asList(tableviewstring.getSelectionModel().getSelectedItem()).get(4);
                songsdur = songsdur.plusHours(Time.valueOf(dur).getHours()).plusMinutes(Time.valueOf(dur).getMinutes()).plusSeconds(Time.valueOf(dur).getSeconds());
            }
            label.setText("Number of artists; songs count/duration: " + 0 + "; " + songscount + "/" + (songsdur.getDayOfYear()-1) + "d " + songsdur.getHour() + ":" + songsdur.getMinute() + ":" + songsdur.getSecond());
        }
        else
        {
            if (tableviewstring == tableview1 || tableviewstring == tableview2)
            {
                for (int i = 0; i < tableviewstring.getItems().size(); i++)
                {
                    tableviewstring.getSelectionModel().select(i);
                    songscount += Integer.parseInt(Arrays.asList(tableviewstring.getSelectionModel().getSelectedItem()).get(rsong));
                    try
                    {
                        if((Arrays.asList(tableviewstring.getSelectionModel().getSelectedItem()).get(rsong+1).length()>=3 && Arrays.asList(tableviewstring.getSelectionModel().getSelectedItem()).get(rsong+1).length()<=5) && Arrays.asList(tableviewstring.getSelectionModel().getSelectedItem()).get(rsong+1).lastIndexOf(":") != 3)
                            dur = "00:" + Arrays.asList(tableviewstring.getSelectionModel().getSelectedItem()).get(rsong+1);
                        else
                            dur = Arrays.asList(tableviewstring.getSelectionModel().getSelectedItem()).get(rsong+1);
                    }
                    catch (Exception e)
                    {
                        dur = "0";
                    }
                    try
                    {
                        songsdur = songsdur.plusHours(Time.valueOf(dur).getHours()).plusMinutes(Time.valueOf(dur).getMinutes()).plusSeconds(Time.valueOf(dur).getSeconds());
                    }
                    catch (Exception e)
                    {
                        songsdur = songsdur.plusHours(0).plusMinutes(0).plusSeconds(0);
                    }
                }
                label.setText("Number of artists; songs count/duration: " + tableviewstring.getItems().size() + "; " + songscount + "/" + (songsdur.getDayOfYear()-1) + "d " + songsdur.getHour() + ":" + songsdur.getMinute() + ":" + songsdur.getSecond());
            }
            else
            {
                for (int i = 0; i < tableviewstring.getItems().size(); i++)
                {
                    tableviewstring.getSelectionModel().select(i);
                    if((Arrays.asList(tableviewstring.getSelectionModel().getSelectedItem()).get(rsong).length()>=3 && Arrays.asList(tableviewstring.getSelectionModel().getSelectedItem()).get(rsong).length()<=5) && Arrays.asList(tableviewstring.getSelectionModel().getSelectedItem()).get(rsong).lastIndexOf(":") != 3)
                        dur = "00:" + Arrays.asList(tableviewstring.getSelectionModel().getSelectedItem()).get(rsong);
                    else if(Arrays.asList(tableviewstring.getSelectionModel().getSelectedItem()).get(rsong).length()<=2)
                        dur = "00:00:" + Arrays.asList(tableviewstring.getSelectionModel().getSelectedItem()).get(rsong);
                    else
                        dur = Arrays.asList(tableviewstring.getSelectionModel().getSelectedItem()).get(rsong);
                    songsdur = songsdur.plusHours(Time.valueOf(dur).getHours()).plusMinutes(Time.valueOf(dur).getMinutes()).plusSeconds(Time.valueOf(dur).getSeconds());
                }
                label.setText("Songs count/duration: " + tableviewstring.getItems().size() + "/" + songsdur.getHour() + ":" + songsdur.getMinute() + ":" + songsdur.getSecond());
            }
        }
        if (tabl != 1)
        {
            label.setText(label.getText().replace("/0d ", "/"));
            if (songsdur.getHour()==0 && songsdur.getDayOfYear()-1 == 0)
                label.setText(label.getText().replaceFirst("0:", ""));
        }
        tableviewstring.getSelectionModel().clearSelection();

        if (tabl == 1 || tabl == 3 || tabl == 4 || tabl == 5 || tabl == 8)
        {
            if (tabl == 1 || tabl == 3 || tabl == 8)
            {
                if (tableview2.getItems().isEmpty())
                {
                    textfield10.setText("");
                    textfield11.setText("");
                    textfield12.setText("");
                    textfield13.setText("");
                }
            }
            if (tabl == 3 || tabl == 4 || tabl == 5 || tabl == 8)
            {
                textfield14.setText("");
                textfield15.setText("");
                textfield16.setText("");
            }
        }
    }

    public void Filtering()
    {
        textfield17.setEditable(false);
        textfield17.setText("");
        textfield18.setText("");

        try
        {
            obslist.clear();
            obslist.addAll(Arrays.asList(audiolibrarydb.SELECTFILTERING(tabl)));
            tableview1.setItems(obslist);
        }
        catch (Exception e)
        {
            throw new RuntimeException(e);
        }
        if (tabl == 3)
            NumberofArtistsSongsCountandDuration(tableview1, label24, 4);
        if (tabl == 4 || tabl == 5)
            NumberofArtistsSongsCountandDuration(tableview1, label24, 2);
        if (tabl == 6)
            NumberofArtistsSongsCountandDuration(tableview1, label24, 0);
        if (tabl == 7)
            NumberofArtistsSongsCountandDuration(tableview1, label24, 3);
        tableview1.getSelectionModel().select(null);
        tableview1.scrollTo(0);
        Clear();
    }

    public void Searching(TextField textfield, ObservableList<String[]> observablelist)
    {
        try
        {
            observablelist.clear();
            if (observablelist == obslist)
            {
                observablelist.addAll(Arrays.asList(audiolibrarydb.SELECTSEARCHING(tabl, 0, "%" + textfield.getText() + "%")));
                tableview1.setItems(observablelist);
                tableview2.getItems().clear();
                tableview3.getItems().clear();
                if (tabl == 3 || tabl == 8)
                    NumberofArtistsSongsCountandDuration(tableview1, label24, 4);
                if (tabl == 4 || tabl == 5)
                    NumberofArtistsSongsCountandDuration(tableview1, label24, 2);
                if (tabl == 6)
                    NumberofArtistsSongsCountandDuration(tableview1, label24, 3);
                if (tabl == 7)
                    NumberofArtistsSongsCountandDuration(tableview1, label24, 3);
                label25.setText("Number of artists; songs count/duration: 0; 0/0:0");
                label26.setText("Songs count/duration: 0/0:0");
            }
            else if (textfield10.getText().equals(""))
            {
                observablelist.addAll(Arrays.asList(audiolibrarydb.SELECTSEARCHING(tabl, 1, "%" + textfield.getText() + "%")));
                tableview3.setItems(observablelist);
                NumberofArtistsSongsCountandDuration(tableview3, label26, 1);
            }
            else
            {
                observablelist.addAll(Arrays.asList(audiolibrarydb.SELECTSEARCHING(tabl, 2, "%" + textfield.getText() + "%")));
                tableview3.setItems(observablelist);
                NumberofArtistsSongsCountandDuration(tableview3, label26, 1);
            }
        }
        catch (Exception e)
        {
            throw new RuntimeException();
        }
    }

    public static void ColumnProperties(TableColumn col1, TableColumn col2, TableColumn col3, TableColumn col4, TableColumn col5, TableColumn col6, TableColumn col7, TableColumn col8, TableColumn col9)
    {
        TableColumn col = col1;
        col.setResizable(false);
        col.setReorderable(false);
        if (col2 != null)
        {
            col1 = col2;
            ColumnProperties(col1, null, col3, col4, col5, col6, col7, col8, col9);
        }
        if (col3 != null)
        {
            col1 = col3;
            ColumnProperties(col1, null, null, col4, col5, col6, col7, col8, col9);
        }
        if (col4 != null)
        {
            col1 = col4;
            ColumnProperties(col1, null, null, null, col5, col6, col7, col8, col9);
        }
        if (col5 != null)
        {
            col1 = col5;
            ColumnProperties(col1, null, null, null, null, col6, col7, col8, col9);
        }
        if (col6 != null)
        {
            col1 = col6;
            ColumnProperties(col1, null, null, null, null, null, col7, col8, col9);
        }
        if (col7 != null)
        {
            col1 = col7;
            ColumnProperties(col1, null, null, null, null, null, null, col8, col9);
        }
        if (col8 != null)
        {
            col1 = col8;
            ColumnProperties(col1, null, null, null, null, null, null, null, col9);
        }
        if (col9 != null)
        {
            col1 = col9;
            ColumnProperties(col1, null, null, null, null, null, null, null, null);
        }
    }

    public void Table(int siz1, int siz2, int siz3, int siz4, int siz5, int siz6, int siz7, int siz8, int siz9, int siz11, int siz12, int siz13, int siz14, int siz15) throws Exception
    {
        textfield1.setContextMenu(new ContextMenu());
        textfield2.setContextMenu(new ContextMenu());
        textfield3.setContextMenu(new ContextMenu());
        textfield4.setContextMenu(new ContextMenu());
        textfield5.setContextMenu(new ContextMenu());
        textfield6.setContextMenu(new ContextMenu());
        textfield7.setContextMenu(new ContextMenu());
        textfield8.setContextMenu(new ContextMenu());
        textfield10.setContextMenu(new ContextMenu());
        textfield11.setContextMenu(new ContextMenu());
        textfield12.setContextMenu(new ContextMenu());
        textfield13.setContextMenu(new ContextMenu());
        textfield14.setContextMenu(new ContextMenu());
        textfield15.setContextMenu(new ContextMenu());
        textfield16.setContextMenu(new ContextMenu());
        textfield17.setContextMenu(new ContextMenu());
        textfield18.setContextMenu(new ContextMenu());
        if (tabl == 1 || tabl == 7)
        {
            cm = new ContextMenu();
            cm.getItems().clear();
        }
        else
            ContextMenu();

        label20combobox.setText("");
        label21combobox.setText("");
        label22combobox.setText("");
        label23combobox.setText("");
        label25.setText("Number of artists; songs count/duration: 0; 0/0:0");
        label26.setText("Songs count/duration: 0/0:0");
        textfield17.setVisible(true);
        textfield17.setEditable(true);
        textfield17.setPrefWidth(150);
        textfield17.setText("");
        textfield18.setVisible(false);
        textfield18.setText("");
        tableview2.setVisible(false);
        tableview3.setVisible(false);
        tableview1.setPrefWidth(935);
        tableview2.setPrefWidth(330);
        tableview2.setPrefHeight(149);
        combobox1.setVisible(false);
        combobox2.setVisible(false);
        combobox3.setVisible(false);
        combobox4.setVisible(false);
        combobox1.setValue(null);
        combobox2.setValue(null);
        combobox3.setValue(null);
        combobox4.setValue(null);
        if (tabl == 3 || tabl == 8)
        {
            HBox1.setPrefWidth(615);
            if (tabl == 3)
                textfield17.setEditable(true);
            tableview2.removeEventFilter(MouseEvent.ANY, me);
            label20combobox.setVisible(true);
            label21combobox.setVisible(true);
            label22combobox.setVisible(true);
            label23combobox.setVisible(true);
            tableview1.setMinHeight(149);
            tableview1.setMaxHeight(149);
            tableview1.setMinWidth(765);
            tableview1.setMaxWidth(765);
            tableview2.setMinWidth(500);
            tableview2.setMaxWidth(500);
            tableview3songs.setMinHeight(263);
            tableview3songs.setMaxHeight(263);
            if (tabl== 8)
            {
                textfield9.setContextMenu(new ContextMenu());
                textfield17.setVisible(false);
                textfield18.setVisible(false);
            }
        }
        if (tabl == 4 || tabl == 5)
        {
            HBox1.setPrefWidth(1120);
            textfield17.setEditable(true);
            label20combobox.setVisible(true);
            label21combobox.setVisible(true);
            combobox1.setVisible(true);
            combobox2.setVisible(true);
            tableview1.setMinHeight(149);
            tableview1.setMaxHeight(149);
            tableview1.setMinWidth(1270);
            tableview1.setMaxWidth(1270);
            tableview3.setMinHeight(388);
            tableview3.setMaxHeight(388);
        }
        if (tabl == 6)
        {
            HBox1.setPrefWidth(1120);
            textfield17.setEditable(true);
            label20combobox.setVisible(true);
            combobox1.setVisible(true);
            tableview1.setMinHeight(719);
            tableview1.setMaxHeight(719);
            tableview1.setMinWidth(1270);
            tableview1.setMaxWidth(1270);
        }
        if (tabl == 7)
        {
            HBox1.setPrefWidth(1120);
            textfield17.setEditable(true);
            label20combobox.setVisible(true);
            label21combobox.setVisible(true);
            combobox1.setVisible(true);
            combobox2.setVisible(true);
            tableview1.setMinHeight(645);
            tableview1.maxHeight(645);
            tableview1.setMinWidth(1270);
            tableview1.maxWidth(1270);
        }

        obslist.clear();
        tableview1.getColumns().clear();
        obslist.addAll(Arrays.asList(audiolibrarydb.SELECT(tabl)));
        TableColumn first = new TableColumn<>(label3.getText());
        TableColumn<String[], String> second = new TableColumn<>(label4.getText());
        TableColumn<String[], String> third = new TableColumn<>(label5.getText());
        TableColumn<String[], String> fourth = new TableColumn<>(label6.getText());
        if (tabl == 1 || tabl == 4 || tabl == 5 || tabl == 6 || tabl == 7)
        {
            if (tabl == 1 || tabl == 4 || tabl == 5 || tabl == 6)
            {
                if (tabl == 1 || tabl == 4 || tabl == 5)
                {
                    if (tabl==1)
                        second.setComparator(Comparator.comparingInt(Integer::parseInt));
                    third.setComparator(Comparator.comparingInt(Integer::parseInt));
                }
                fourth.setComparator(Comparator.comparing(s ->
                {
                    if (s.contains("d"))
                        return Duration.ofDays(Integer.parseInt(s.split("d")[0])).plus(Duration.ofHours(Integer.parseInt(s.substring(s.indexOf(' ')+1).split(":")[0])).plusMinutes(Integer.parseInt(s.substring(s.indexOf(' ')+1).split(":")[1])).plusSeconds(Integer.parseInt(s.substring(s.indexOf(' ')+1).split(":")[2])));
                    else if (s.split(":").length == 2)
                        return Duration.ofMinutes(Integer.parseInt(s.split(":")[0])).plusSeconds(Integer.parseInt(s.split(":")[1]));
                    else
                        return Duration.ofHours(Integer.parseInt(s.split(":")[0])).plusMinutes(Integer.parseInt(s.split(":")[1])).plusSeconds(Integer.parseInt(s.split(":")[2]));
                }));
            }
            if (tabl == 7)
                fourth.setComparator(Comparator.comparingInt(Integer::parseInt));
        }
        tableview1.getColumns().add(first);
        tableview1.getColumns().add(second);
        tableview1.getColumns().add(third);
        tableview1.getColumns().add(fourth);
        first.setCellValueFactory((Callback<TableColumn.CellDataFeatures<String[], String>, ObservableValue<String>>) v -> new SimpleStringProperty(v.getValue()[0]));
        second.setCellValueFactory(v -> new SimpleStringProperty(v.getValue()[1]));
        third.setCellValueFactory(v -> new SimpleStringProperty(v.getValue()[2]));
        fourth.setCellValueFactory(v -> new SimpleStringProperty(v.getValue()[3]));
        first.setPrefWidth(siz1);
        second.setPrefWidth(siz2);
        third.setPrefWidth(siz3);
        fourth.setPrefWidth(siz4);
        ColumnProperties(first, second, third, fourth, null, null, null, null, null);
        if (tabl == 3 || tabl == 4 || tabl == 5 || tabl == 7 || tabl == 8)
        {
            second.setVisible(false);
            if (tabl == 7)
                third.setVisible(false);
            Tip(first);
        }
        if (tabl == 3 || tabl == 6 || tabl == 7 || tabl == 8)
        {
            TableColumn<String[], String> fifth = new TableColumn<>(label7.getText());
            if (tabl == 3 || tabl == 8)
                fifth.setComparator(Comparator.comparingInt(Integer::parseInt));
            if (tabl == 7)
                fifth.setComparator(Comparator.comparing(s ->
                {
                    if (s.split(":").length == 2)
                        return Duration.ofMinutes(Integer.parseInt(s.split(":")[0])).plusSeconds(Integer.parseInt(s.split(":")[1]));
                    else
                        return Duration.ofHours(Integer.parseInt(s.split(":")[0])).plusMinutes(Integer.parseInt(s.split(":")[1])).plusSeconds(Integer.parseInt(s.split(":")[2]));
                }));
            tableview1.getColumns().add(fifth);
            fifth.setCellValueFactory(v -> new SimpleStringProperty(v.getValue()[4]));
            fifth.setPrefWidth(siz5);
            ColumnProperties(first, second, third, fourth, fifth, null, null, null, null);
            if (tabl == 3 || tabl == 6 || tabl == 8)
            {
                TableColumn<String[], String> sixth = new TableColumn<>(label8.getText());
                if (tabl == 3 || tabl == 8)
                    sixth.setComparator(Comparator.comparing(s ->
                    {
                        if (s.split(":").length == 2)
                            return Duration.ofMinutes(Integer.parseInt(s.split(":")[0])).plusSeconds(Integer.parseInt(s.split(":")[1]));
                        else
                            return Duration.ofHours(Integer.parseInt(s.split(":")[0])).plusMinutes(Integer.parseInt(s.split(":")[1])).plusSeconds(Integer.parseInt(s.split(":")[2]));
                    }));
                tableview1.getColumns().add(sixth);
                sixth.setCellValueFactory(v -> new SimpleStringProperty(v.getValue()[5]));
                sixth.setPrefWidth(siz6);
                ColumnProperties(first, second, third, fourth, fifth, sixth, null, null, null);
                if (tabl == 3 || tabl == 8)
                {
                    fourth.setVisible(false);
                    TableColumn seventh = new TableColumn<>(label9.getText());
                    tableview1.getColumns().add(seventh);
                    seventh.setCellValueFactory((Callback<TableColumn.CellDataFeatures<String[], String>, ObservableValue<String>>) v -> new SimpleStringProperty(v.getValue()[6]));
                    seventh.setVisible(false);
                    ColumnProperties(first, second, third, fourth, fifth, sixth, seventh, null, null);
                    TableColumn eighth = new TableColumn<>(label10.getText());
                    tableview1.getColumns().add(eighth);
                    eighth.setCellValueFactory((Callback<TableColumn.CellDataFeatures<String[], String>, ObservableValue<String>>) v -> new SimpleStringProperty(v.getValue()[7]));
                    eighth.setVisible(false);
                    ColumnProperties(first, second, third, fourth, fifth, sixth, seventh, eighth, null);
                    TableColumn ninth = new TableColumn<>(label12.getText());
                    tableview1.getColumns().add(ninth);
                    ninth.setCellValueFactory((Callback<TableColumn.CellDataFeatures<String[], String>, ObservableValue<String>>) v -> new SimpleStringProperty(v.getValue()[8]));
                    ninth.setVisible(false);
                    ColumnProperties(first, second, third, fourth, fifth, sixth, seventh, eighth, ninth);
                }
                if (tabl == 6)
                {
                    Tip(first);
                    Tip(second);
                    Tip(third);
                    Tip(fifth);
                    Tip(sixth);
                }
            }
        }
        tableview1.setItems(obslist);
        tableview1.getSelectionModel().selectedItemProperty().addListener((s, os, ns) ->
        {
            if (!textfield1.getText().equals("Жанр"))
                Clear();
            if (ns != null)
            {
                textfield1.setText(Arrays.asList(tableview1.getSelectionModel().getSelectedItem()).get(0));
                textfield2.setText(Arrays.asList(tableview1.getSelectionModel().getSelectedItem()).get(1));
                textfield3.setText(Arrays.asList(tableview1.getSelectionModel().getSelectedItem()).get(2));
                textfield4.setText(Arrays.asList(tableview1.getSelectionModel().getSelectedItem()).get(3));
                if (tabl == 3)
                    artbandid = Integer.parseInt(Arrays.asList(tableview1.getSelectionModel().getSelectedItem()).get(8));
                if (tabl == 4 || tabl == 5)
                    artbandid = Integer.parseInt(Arrays.asList(tableview1.getSelectionModel().getSelectedItem()).get(4));
                if (nodat > 4)
                {
                    textfield5.setText(Arrays.asList(tableview1.getSelectionModel().getSelectedItem()).get(4));
                    if (tabl == 7)
                        soundtrackid = Integer.parseInt(Arrays.asList(tableview1.getSelectionModel().getSelectedItem()).get(5));
                    if (nodat > 5)
                    {
                        textfield6.setText(Arrays.asList(tableview1.getSelectionModel().getSelectedItem()).get(5));
                        if (tabl == 6)
                            coverid = Integer.parseInt(Arrays.asList(tableview1.getSelectionModel().getSelectedItem()).get(6));
                        if (nodat > 6)
                        {
                            textfield7.setText(Arrays.asList(tableview1.getSelectionModel().getSelectedItem()).get(6));
                            textfield8.setText(Arrays.asList(tableview1.getSelectionModel().getSelectedItem()).get(7));
                            if (nodat == 9)
                                textfield9.setText(Arrays.asList(tableview1.getSelectionModel().getSelectedItem()).get(8));
                        }
                    }
                }
            }
        });
        if (tabl == 1 || tabl == 3 || tabl == 4 || tabl == 5 || tabl == 8)
        {
            if (tabl == 1)
                tableview2.addEventFilter(MouseEvent.ANY, me);
            if (tabl == 1 || tabl == 3 || tabl == 8)
            {
                tableview2.setVisible(true);
                tableview2.getColumns().clear();
            }
            if (tabl == 3 || tabl == 4 || tabl == 5 || tabl == 8)
            {
                if (tabl == 3 || tabl == 4 || tabl == 5)
                    textfield18.setVisible(true);
                tableview3.setVisible(true);
                tableview3.getColumns().clear();
            }
        }
        TableColumn sub1first = new TableColumn<>(label12.getText());
        TableColumn<String[], String> sub1second = new TableColumn<>(label13.getText());
        TableColumn<String[], String> sub1third = new TableColumn<>(label14.getText());
        TableColumn<String[], String> sub1fourth = new TableColumn<>(label15.getText());
        TableColumn sub1fifth = new TableColumn<>("");
        tableview2.getColumns().add(sub1first);
        tableview2.getColumns().add(sub1second);
        tableview2.getColumns().add(sub1third);
        tableview2.getColumns().add(sub1fourth);
        tableview2.getColumns().add(sub1fifth);
        sub1first.setPrefWidth(siz9);
        sub1second.setVisible(false);
        sub1third.setPrefWidth(siz11);
        sub1fourth.setPrefWidth(siz12);
        sub1fifth.setVisible(false);
        ColumnProperties(sub1first, sub1second, sub1third, sub1fourth, sub1fifth, null, null, null, null);
        if (tabl == 1)
        {
            HBox1.setPrefWidth(572);
            textfield17.setVisible(false);
            textfield17.setPrefWidth(0);
            tableview1.setMinHeight(149);
            tableview1.setMaxHeight(149);
            tableview1.setMinWidth(587);
            tableview1.setMaxWidth(587);
            tableview2.setPrefHeight(437);
            tableview2.setMinWidth(678);
            tableview2.setMaxWidth(678);
            tableview2.getColumns().clear();
            sub1obslist.clear();
            sub1obslist.addAll(Arrays.asList(audiolibrarydb.SELECT(2)));
            sub1first = new TableColumn<>(label12.getText());
            sub1second = new TableColumn<>(label13.getText());
            sub1third = new TableColumn<>(label14.getText());
            sub1fourth = new TableColumn<>(label15.getText());
            sub1second.setComparator(Comparator.comparingInt(Integer::parseInt));
            sub1third.setComparator(Comparator.comparingInt(Integer::parseInt));
            sub1fourth.setComparator(Comparator.comparing(s ->
            {
                if (s.contains("d"))
                    return Duration.ofDays(Integer.parseInt(s.split("d")[0])).plus(Duration.ofHours(Integer.parseInt(s.substring(s.indexOf(' ')+1).split(":")[0])).plusMinutes(Integer.parseInt(s.substring(s.indexOf(' ')+1).split(":")[1])).plusSeconds(Integer.parseInt(s.substring(s.indexOf(' ')+1).split(":")[2])));
                else if (s.split(":").length == 2)
                    return Duration.ofMinutes(Integer.parseInt(s.split(":")[0])).plusSeconds(Integer.parseInt(s.split(":")[1]));
                else
                    return Duration.ofHours(Integer.parseInt(s.split(":")[0])).plusMinutes(Integer.parseInt(s.split(":")[1])).plusSeconds(Integer.parseInt(s.split(":")[2]));
            }));
            tableview2.getColumns().add(sub1first);
            tableview2.getColumns().add(sub1second);
            tableview2.getColumns().add(sub1third);
            tableview2.getColumns().add(sub1fourth);
            sub1first.setCellValueFactory((Callback<TableColumn.CellDataFeatures<String[], String>, ObservableValue<String>>) v -> new SimpleStringProperty(v.getValue()[0]));
            sub1second.setCellValueFactory(v -> new SimpleStringProperty(v.getValue()[1]));
            sub1third.setCellValueFactory(v -> new SimpleStringProperty(v.getValue()[2]));
            sub1fourth.setCellValueFactory(v -> new SimpleStringProperty(v.getValue()[3]));
            sub1first.setPrefWidth(siz5);
            sub1second.setPrefWidth(siz6);
            sub1third.setPrefWidth(siz7);
            sub1fourth.setPrefWidth(siz8);
            ColumnProperties(sub1first, sub1second, sub1third, sub1fourth, null, null, null, null, null);
            tableview2.setItems(sub1obslist);
        }
        if (tabl == 3 || tabl == 4 || tabl == 5 || tabl == 8)
        {
            TableColumn sub2first = new TableColumn<>(label17.getText());
            TableColumn sub2second = new TableColumn<>(label18.getText());
            TableColumn sub2third = new TableColumn<>(label19.getText());
            TableColumn sub2fourth = new TableColumn<>("");
            tableview3.getColumns().add(sub2first);
            tableview3.getColumns().add(sub2second);
            tableview3.getColumns().add(sub2third);
            tableview3.getColumns().add(sub2fourth);
            sub2first.setPrefWidth(siz13);
            sub2second.setPrefWidth(siz14);
            sub2third.setPrefWidth(siz15);
            sub2fourth.setVisible(false);
            ColumnProperties(sub2first, sub2second, sub2third, sub2fourth, null, null, null, null, null);
        }

        textfield17.textProperty().addListener((o, ov, nv) -> Searching(textfield17, obslist));
        textfield18.textProperty().addListener((o, ov, nv) -> Searching(textfield18, sub2obslist));
        textfield18.textProperty().addListener((o, ov, nv) -> Searching(textfield18, sub2sub1obslist));

        if (tabl == 1)
        {
            NumberofArtistsSongsCountandDuration(tableview1, label24, 2);
            NumberofArtistsSongsCountandDuration(tableview2, label25, 2);
        }
        if (tabl == 3 || tabl == 8)
            NumberofArtistsSongsCountandDuration(tableview1, label24, 4);
        if (tabl == 4 || tabl == 5)
            NumberofArtistsSongsCountandDuration(tableview1, label24, 2);
        if (tabl == 6)
            NumberofArtistsSongsCountandDuration(tableview1, label24, 0);
        if (tabl == 7)
            NumberofArtistsSongsCountandDuration(tableview1, label24, 3);

        if (String.valueOf(textfield2.getText()).equals("null"))
            textfield2.setText("");
        if (tabl == 1)
        {
            textfield10.setText("");
            textfield11.setText("");
            textfield12.setText("");
            textfield13.setText("");
        }
    }

    public void button1audiolibrarygenre() throws Exception
    {
        StackPane1.getChildren().clear();
        StackPane1.setPrefHeight(485);
        FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource("audiolibrarygenre.fxml"));
        StackPane1.getChildren().add(fxmlloader.load());
        audiolibrarygenre audiolibrarygenre = fxmlloader.getController();
        label3 = audiolibrarygenre.label3change;
        label4 = audiolibrarygenre.label4change;
        label5 = audiolibrarygenre.label5change;
        label6 = audiolibrarygenre.label6change;
        label12 = audiolibrarygenre.label12change;
        label13 = audiolibrarygenre.label13change;
        label14 = audiolibrarygenre.label14change;
        label15 = audiolibrarygenre.label15change;
        textfield1 = audiolibrarygenre.textfield1change;
        textfield2 = audiolibrarygenre.textfield2change;
        textfield3 = audiolibrarygenre.textfield3change;
        textfield4 = audiolibrarygenre.textfield4change;
        textfield10 = audiolibrarygenre.textfield10change;
        textfield11 = audiolibrarygenre.textfield11change;
        textfield12 = audiolibrarygenre.textfield12change;
        textfield13 = audiolibrarygenre.textfield13change;

        tabl = 1;
        subtabl = 0;
        nodat = 4;
        rfnum = 0;
        rsnum = 0;
        rtnum = 0;
        Table(100, 225, 130, 130, 238, 175, 125, 125, 0, 0, 0, 0, 0, 0);
    }

    public void button2musicartistband() throws Exception
    {
        StackPane1.getChildren().clear();
        StackPane1.setPrefHeight(485);
        FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource("musicartistband.fxml"));
        StackPane1.getChildren().add(fxmlloader.load());
        musicartistband musicartistband = fxmlloader.getController();
        tableview1.scrollTo(0);
        label3 = musicartistband.label3change;
        label4 = musicartistband.label4change;
        label5 = musicartistband.label5change;
        label6 = musicartistband.label6change;
        label7 = musicartistband.label7change;
        label8 = musicartistband.label8change;
        label9 = musicartistband.label9change;
        label10 = musicartistband.label10change;
        label12 = musicartistband.label12change;
        label13 = musicartistband.label13change;
        label14 = musicartistband.label14change;
        label15 = musicartistband.label15change;
        label17 = musicartistband.label17change;
        label18 = musicartistband.label18change;
        label19 = musicartistband.label19change;
        textfield1 = musicartistband.textfield1change;
        textfield2 = musicartistband.textfield2change;
        textfield3 = musicartistband.textfield3change;
        textfield4 = musicartistband.textfield4change;
        textfield5 = musicartistband.textfield5change;
        textfield6 = musicartistband.textfield6change;
        textfield7 = musicartistband.textfield7change;
        textfield8 = musicartistband.textfield8change;
        textfield10 = musicartistband.textfield10change;
        textfield11 = musicartistband.textfield11change;
        textfield12 = musicartistband.textfield12change;
        textfield13 = musicartistband.textfield13change;
        textfield14 = musicartistband.textfield14change;
        textfield15 = musicartistband.textfield15change;
        textfield16 = musicartistband.textfield16change;

        tabl = 3;
        subtabl = 1;
        nodat = 8;
        rfnum = 0;
        rsnum = 1;
        rtnum = 0;
        nam9="Artist/band";
        siz9=215;
        nam10="Songs";
        siz10=0;
        nam11="Number of songs";
        siz11=135;
        nam12="Songs duration";
        siz12=135;
        nam13="Name";
        siz13=770;
        nam14="Duration";
        siz14=75;
        nam15="Featuring";
        siz15=410;
        Table(250, 0, 230, 0, 135, 135, 0, 0, 215, 135, 135, 770, 75, 410);

        label20combobox.setPrefWidth(160);
        label20combobox.setText("Genre");
        label21combobox.setPrefWidth(160);
        label21combobox.setText("Related artist(-s)/band(-s)");
        label22combobox.setText("Number of songs");
        label23combobox.setText("Songs duration");
        combobox1.setPrefWidth(285);
        combobox1.setVisible(true);
        combobox1.setVisibleRowCount(17);
        combobox1.setItems(FXCollections.observableArrayList(
                "Авторская песня, Шансон",
                "Альтернатива, Инди",
                "Блюз",
                "ВИА",
                "Вокальная музыка",
                "Джаз",
                "Кантри",
                "Легкая, Инструментальная музыка",
                "Метал, Ню-метал, Металкор",
                "Панк, Эмо, Постхардкор",
                "Поп",
                "Поп-рок",
                "Регги, Реггетон",
                "Рок",
                "Соул, Фанк, Диско",
                "Хип-хоп",
                "Электронная музыка"));
        combobox1.setOnAction(e -> Filtering());
        combobox2.setPrefWidth(285);
        combobox2.setVisible(true);
        combobox2.setVisibleRowCount(2);
        combobox2.setItems(FXCollections.observableArrayList(
                "With related artist(-s)/band(-s)",
                "Without related artist(-s)/band(-s)"));
        combobox2.setOnAction(e -> Filtering());
        combobox3.setVisible(true);
        combobox3.setVisibleRowCount(5);
        combobox3.setItems(FXCollections.observableArrayList(
                "<15",
                "15",
                "25",
                "55",
                ">55"));
        combobox3.setOnAction(e -> Filtering());
        combobox4.setVisible(true);
        combobox4.setVisibleRowCount(4);
        combobox4.setItems(FXCollections.observableArrayList(
                "<45:00",
                ">=45:00 & <1:15:00",
                ">=1:15:00 & <1:45:00",
                ">=1:45:00"));
        combobox4.setOnAction(e -> Filtering());
    }

    public void button3composers() throws Exception
    {
        StackPane1.getChildren().clear();
        StackPane1.setPrefHeight(360);
        FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource("composersbloggers.fxml"));
        StackPane1.getChildren().add(fxmlloader.load());
        composersbloggers composersbloggers = fxmlloader.getController();
        tableview1.scrollTo(0);
        composersbloggers.label2change.setText("Composers");
        composersbloggers.label3change.setText("Name");
        label3 = composersbloggers.label3change;
        label4 = composersbloggers.label4change;
        label5 = composersbloggers.label5change;
        label6 = composersbloggers.label6change;
        label17 = composersbloggers.label17change;
        label18 = composersbloggers.label18change;
        label19 = composersbloggers.label19change;
        textfield1 = composersbloggers.textfield1change;
        textfield2 = composersbloggers.textfield2change;
        textfield3 = composersbloggers.textfield3change;
        textfield4 = composersbloggers.textfield4change;
        textfield14 = composersbloggers.textfield14change;
        textfield15 = composersbloggers.textfield15change;
        textfield16 = composersbloggers.textfield16change;

        tabl = 4;
        subtabl = 1;
        nodat = 4;
        rfnum = 0;
        rsnum = 0;
        rtnum = 0;
        nam9="Artist/band";
        siz9=985;
        nam10="Songs";
        siz10=0;
        nam11="Number of songs";
        siz11=135;
        nam12="Songs duration";
        siz12=135;
        nam13="Name";
        siz13=770;
        nam14="Duration";
        siz14=75;
        nam15="Featuring";
        siz15=410;
        Table(985, 0, 135, 135, 0, 0, 0, 0, 50, 50, 50, 770, 75, 410);

        label20combobox.setPrefWidth(105);
        label20combobox.setText("Number of songs");
        label21combobox.setPrefWidth(105);
        label21combobox.setText("Songs duration");
        combobox1.setPrefWidth(100);
        combobox1.setVisibleRowCount(2);
        combobox1.setItems(FXCollections.observableArrayList(
                "<5",
                ">=5"));
        combobox1.setOnAction(e -> Filtering());
        combobox2.setPrefWidth(100);
        combobox2.setVisibleRowCount(2);
        combobox2.setItems(FXCollections.observableArrayList(
                "<20:00",
                ">=20:00"));
        combobox2.setOnAction(e -> Filtering());
    }

    public void button4bloggers() throws Exception
    {
        StackPane1.getChildren().clear();
        StackPane1.setPrefHeight(360);
        FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource("composersbloggers.fxml"));
        StackPane1.getChildren().add(fxmlloader.load());
        composersbloggers composersbloggers = fxmlloader.getController();
        tableview1.scrollTo(0);
        composersbloggers.label2change.setText("Bloggers");
        composersbloggers.label3change.setText("Nickname/name");
        label3 = composersbloggers.label3change;
        label4 = composersbloggers.label4change;
        label5 = composersbloggers.label5change;
        label6 = composersbloggers.label6change;
        label17 = composersbloggers.label17change;
        label18 = composersbloggers.label18change;
        label19 = composersbloggers.label19change;
        textfield1 = composersbloggers.textfield1change;
        textfield2 = composersbloggers.textfield2change;
        textfield3 = composersbloggers.textfield3change;
        textfield4 = composersbloggers.textfield4change;
        textfield14 = composersbloggers.textfield14change;
        textfield15 = composersbloggers.textfield15change;
        textfield16 = composersbloggers.textfield16change;

        tabl = 5;
        subtabl = 1;
        nodat = 4;
        rfnum = 0;
        rsnum = 0;
        rtnum = 0;
        nam9="Artist/band";
        siz9=985;
        nam10="Songs";
        siz10=0;
        nam11="Number of songs";
        siz11=135;
        nam12="Songs duration";
        siz12=135;
        nam13="Name";
        siz13=770;
        nam14="Duration";
        siz14=75;
        nam15="Featuring";
        siz15=410;
        Table(985, 0, 135, 135, 0, 0, 0, 0, 50, 50, 50, 770, 75, 410);

        label20combobox.setPrefWidth(105);
        label20combobox.setText("Number of songs");
        label21combobox.setPrefWidth(105);
        label21combobox.setText("Songs duration");
        combobox1.setPrefWidth(100);
        combobox1.setVisibleRowCount(2);
        combobox1.setItems(FXCollections.observableArrayList(
                "<5",
                ">=5"));
        combobox1.setOnAction(e -> Filtering());
        combobox2.setPrefWidth(100);
        combobox2.setVisibleRowCount(2);
        combobox2.setItems(FXCollections.observableArrayList(
                "<20:00",
                ">=20:00"));
        combobox2.setOnAction(e -> Filtering());
    }

    public void button5covers() throws Exception
    {
        StackPane1.getChildren().clear();
        StackPane1.setPrefHeight(206);
        FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource("covers.fxml"));
        StackPane1.getChildren().add(fxmlloader.load());
        covers covers = fxmlloader.getController();
        tableview1.scrollTo(0);
        label3 = covers.label3change;
        label4 = covers.label4change;
        label5 = covers.label5change;
        label6 = covers.label6change;
        label7 = covers.label7change;
        label8 = covers.label8change;
        textfield1 = covers.textfield1change;
        textfield2 = covers.textfield2change;
        textfield3 = covers.textfield3change;
        textfield4 = covers.textfield4change;
        textfield5 = covers.textfield5change;
        textfield6 = covers.textfield6change;

        tabl = 6;
        subtabl = 0;
        nodat = 6;
        rfnum = 0;
        rsnum = 1;
        rtnum = 4;
        Table(325, 250, 175, 80, 250, 175, 0, 0, 0, 0, 0, 0, 0, 0);

        label20combobox.setPrefWidth(90);
        label20combobox.setText("Song duration");
        combobox1.setPrefWidth(100);
        combobox1.setVisibleRowCount(2);
        combobox1.setItems(FXCollections.observableArrayList(
                "<4:00",
                ">=4:00"));
        combobox1.setOnAction(e -> Filtering());
    }

    public void button6soundtracks() throws Exception
    {
        StackPane1.getChildren().clear();
        StackPane1.setPrefHeight(280);
        FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource("soundtracks.fxml"));
        StackPane1.getChildren().add(fxmlloader.load());
        soundtracks soundtracks = fxmlloader.getController();
        tableview1.scrollTo(0);
        label3 = soundtracks.label3change;
        label4 = soundtracks.label4change;
        label5 = soundtracks.label5change;
        label6 = soundtracks.label6change;
        label7 = soundtracks.label7change;
        textfield1 = soundtracks.textfield1change;
        textfield2 = soundtracks.textfield2change;
        textfield3 = soundtracks.textfield3change;
        textfield4 = soundtracks.textfield4change;
        textfield5 = soundtracks.textfield5change;

        tabl = 7;
        subtabl = 0;
        nodat = 5;
        rfnum = 0;
        rsnum = 1;
        rtnum = 2;
        Table(985, 0, 0, 135, 135, 0, 0, 0, 0, 0, 0, 0, 0, 0);

        label20combobox.setPrefWidth(105);
        label20combobox.setText("Number of songs");
        label21combobox.setPrefWidth(105);
        label21combobox.setText("Songs duration");
        combobox1filtering.setPrefWidth(100);
        combobox1filtering.setVisibleRowCount(2);
        combobox1filtering.setItems(FXCollections.observableArrayList(
                "1",
                ">1"));
        combobox1filtering.setOnAction(e -> Filtering());
        combobox2filtering.setPrefWidth(100);
        combobox2filtering.setVisibleRowCount(2);
        combobox2filtering.setItems(FXCollections.observableArrayList(
                "<4:00",
                ">=4:00"));
        combobox2filtering.setOnAction(e -> Filtering());
    }

    public void button7favourites() throws Exception
    {
        StackPane1.getChildren().clear();
        StackPane1.setPrefHeight(485);
        FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource("favourites.fxml"));
        StackPane1.getChildren().add(fxmlloader.load());
        favourites favourites = fxmlloader.getController();
        tableview1.scrollTo(0);
        label3 = favourites.label3change;
        label4 = favourites.label4change;
        label5 = favourites.label5change;
        label6 = favourites.label6change;
        label7 = favourites.label7change;
        label8 = favourites.label8change;
        label9 = favourites.label9change;
        label10 = favourites.label10change;
        label12 = favourites.label12change;
        label13 = favourites.label13change;
        label14 = favourites.label14change;
        label15 = favourites.label15change;
        label17 = favourites.label17change;
        label18 = favourites.label18change;
        label19 = favourites.label19change;
        textfield1 = favourites.textfield1change;
        textfield2 = favourites.textfield2change;
        textfield3 = favourites.textfield3change;
        textfield4 = favourites.textfield4change;
        textfield5 = favourites.textfield5change;
        textfield6 = favourites.textfield6change;
        textfield7 = favourites.textfield7change;
        textfield8 = favourites.textfield8change;
        textfield9 = favourites.textfield9change;
        textfield10 = favourites.textfield10change;
        textfield11 = favourites.textfield11change;
        textfield12 = favourites.textfield12change;
        textfield13 = favourites.textfield13change;
        textfield14 = favourites.textfield14change;
        textfield15 = favourites.textfield15change;
        textfield16 = favourites.textfield16change;

        tabl = 8;
        subtabl = 1;
        nodat = 9;
        rfnum = 0;
        rsnum = 0;
        rtnum = 0;
        nam9="Artist/band";
        siz9=215;
        nam10="Songs";
        siz10=0;
        nam11="Number of songs";
        siz11=135;
        nam12="Songs duration";
        siz12=135;
        nam13="Name";
        siz13=770;
        nam14="Duration";
        siz14=75;
        nam15="Featuring";
        siz15=410;
        Table(250, 0, 230, 0, 135, 135, 0, 0, 215, 135, 135, 770, 75, 410);
    }

    public static int MusicArtistBandID()
    {
        return artbandid;
    }

    public static int RelatedMusicArtistBandID()
    {
        return relartbandid;
    }

    public static int SongID()
    {
        return songid;
    }

    public static int CoverID()
    {
        return coverid;
    }

    public static int SoundtrackID()
    {
        return soundtrackid;
    }

    public static void RowClick(TableView tabl, int rownum)
    {
        TableRow row = (TableRow)tabl.lookupAll(".table-row-cell").stream().filter(n -> ((TableRow)n).getIndex() == rownum).findFirst().orElse(null);
        MouseEvent me = new MouseEvent(MouseEvent.MOUSE_CLICKED, 0, 0, 0, 0, MouseButton.PRIMARY, 1, false, false, false, false, true, false, false, true, false, false, null);
        row.fireEvent(me);
    }

    public static void DataChanging(int chang, int songid) throws Exception
    {
        int relartband = audiolibrary.RelatedMusicArtistBandID();
        boolean empttext = textfield10.getText().isEmpty();

        textfield14.setText("");
        textfield15.setText("");
        textfield16.setText("");
        audiolibrary.obslist.clear();
        if (chang == 1)
            audiolibrary.obslist.addAll(Arrays.asList(audiolibrarydb.SELECTMUSICARTISTBAND(audiolibrarydb.NewMusicArtistBandID())));
        else
            audiolibrary.obslist.addAll(Arrays.asList(audiolibrarydb.SELECTMUSICARTISTBAND(audiolibrary.MusicArtistBandID())));
        audiolibrary.tableview1.setItems(audiolibrary.obslist);
        audiolibrary.tableview1.getSelectionModel().select(0);
        audiolibrary.tableview1.scrollTo(0);
        audiolibrary.RowClick(audiolibrary.tableview1, 0);
        if (tabl == 3)
        {
            if (!empttext)
            {
                for (int i = 0; i < audiolibrary.tableview2.getItems().size(); i++)
                {
                    audiolibrary.tableview2.getSelectionModel().select(i);
                    if (Integer.parseInt(Arrays.asList(audiolibrary.tableview2.getSelectionModel().getSelectedItem()).get(4)) == relartband)
                    {
                        audiolibrary.tableview2.getSelectionModel().select(i);
                        audiolibrary.RowClick(audiolibrary.tableview2, i);
                        break;
                    }
                }
                audiolibrary.label26.setText("Songs count/duration: " + textfield12.getText() + "/" + textfield13.getText());
            }
            else
                audiolibrary.label26.setText("Songs count/duration: " + textfield5.getText() + "/" + textfield6.getText());
            audiolibrary.label26.setText(audiolibrary.label26.getText().replace("null", "0:0"));
            audiolibrary.label24.setText("Number of artists; songs count/duration: 1; " + textfield5.getText() + "/" + textfield6.getText());
            audiolibrary.label24.setText(audiolibrary.label24.getText().replace("null", "0:0"));
        }
        else if (tabl == 4 || tabl == 5)
        {
            audiolibrary.label24.setText("Number of artists; songs count/duration: 1; " + textfield3.getText() + "/" + textfield4.getText());
            audiolibrary.label26.setText("Songs count/duration: " + textfield3.getText() + "/" + textfield4.getText());
        }
        if (chang == 3 || chang == 4 || chang == 5 || chang == 6)
            for (int i = 0; i < audiolibrary.tableview3.getItems().size(); i++)
            {
                audiolibrary.tableview3.getSelectionModel().select(i);
                if (Integer.parseInt(Arrays.asList(audiolibrary.tableview3.getSelectionModel().getSelectedItem()).get(3)) == songid)
                {
                    audiolibrary.tableview3.getSelectionModel().clearSelection();
                    audiolibrary.tableview3.getSelectionModel().select(i);
                    audiolibrary.tableview3.scrollTo(i);
                    break;
                }
            }
    }

    public void stop() throws Exception
    {
        if (backup == 1)
            Runtime.getRuntime().exec("/home/romankolin/audiolibrary/./.audiolibrarybackup.sh");
    }
}