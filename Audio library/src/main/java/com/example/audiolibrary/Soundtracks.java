package com.example.audiolibrary;

import javafx.fxml.*;
import javafx.scene.control.*;
import java.util.*;

public class Soundtracks
{
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
    Button button8add;
    @FXML
    Button button9edit;

    static int chang;

    public void changedata() throws Exception
    {
        int ind = 0;
        Alert al;
        DialogPane dp;

        al = new Alert(Alert.AlertType.NONE);
        dp = al.getDialogPane();
        dp.getButtonTypes().add(ButtonType.OK);
        dp.getStylesheets().add("/alert.css");

        String[] datarr = new String[6];
        datarr[0] = textfield1change.getText();
        datarr[1] = textfield2change.getText();
        datarr[2] = textfield3change.getText();
        datarr[3] = textfield4change.getText();
        if((textfield5change.getText().length()>=3 && textfield5change.getText().length()<=5) && textfield5change.getText().lastIndexOf(":") != 3)
            datarr[4] = "00:" + textfield5change.getText();
        else
            datarr[4] = textfield5change.getText();
        if (chang == 1)
            al.setContentText(AudioLibraryDB.insert(7, datarr));
        else if (chang == 2)
        {
            if (!AudioLibrary.tableview1.getSelectionModel().isEmpty())
            {
                ind = Integer.parseInt(Arrays.asList(AudioLibrary.tableview1.getSelectionModel().getSelectedItem()).get(5));
                datarr[5] = String.valueOf(AudioLibrary.soundtrackid());
                al.setContentText(AudioLibraryDB.update(6, datarr));
            }
            else
                al.setContentText("Your data hasn't been updated");
        }
        if (al.getContentText().equals("Your data has been saved") || al.getContentText().equals("Your data has been updated"))
        {
            AudioLibrary.textfield17.setText("");
            AudioLibrary.obslist.clear();
            if (chang == 1)
                AudioLibrary.obslist.addAll(Arrays.asList(AudioLibraryDB.selectsoundtrack(AudioLibraryDB.soundtrackid())));
            else if (chang == 2)
                AudioLibrary.obslist.addAll(Arrays.asList(AudioLibraryDB.selectsoundtrack(ind)));
            AudioLibrary.tableview1.setItems(AudioLibrary.obslist);
            AudioLibrary.tableview1.getSelectionModel().select(0);
            AudioLibrary.tableview1.scrollTo(0);
            AudioLibrary.rowclick(AudioLibrary.tableview1, 0);
            AudioLibrary.label24.setText("Number of artists; songs count/duration: 0; " + textfield4change.getText() + "/" + textfield5change.getText());
        }
        al.show();
        AudioLibrary.flowpane1.requestFocus();

        if (AudioLibrary.backup == 0)
            AudioLibrary.backup += 1;
    }

    public void button8add() throws Exception
    {
        chang = 1;
        changedata();
    }

    public void button9edit() throws Exception
    {
        chang = 2;
        changedata();
    }
}