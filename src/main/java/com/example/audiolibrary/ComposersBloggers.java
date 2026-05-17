package com.example.audiolibrary;

import javafx.fxml.*;
import javafx.scene.control.*;
import java.util.*;

public class ComposersBloggers
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
    Label label16change;
    @FXML
    Label label17change;
    @FXML
    Label label18change;
    @FXML
    Label label19change;
    @FXML
    TextField textfield1change;
    @FXML
    TextArea textfield2change;
    @FXML
    TextArea textfield3change;
    @FXML
    TextArea textfield4change;
    @FXML
    TextField textfield14change;
    @FXML
    TextField textfield15change;
    @FXML
    TextField textfield16change;
    @FXML
    Button button8add;
    @FXML
    Button button9edit;
    @FXML
    Button button12add;
    @FXML
    Button button13edit;
    @FXML
    Button button14delete;

    static int chang;

    public void changedata() throws Exception
    {
        int ind;
        Alert al;
        DialogPane dp;

        al = new Alert(Alert.AlertType.NONE);
        dp = al.getDialogPane();
        dp.getButtonTypes().add(ButtonType.OK);
        dp.getStylesheets().add("/alert.css");

        if (chang == 1 || chang == 2)
        {
            AudioLibrary.textfield18.setText("");
            String[] datarr = new String[2];
            datarr[0] = textfield1change.getText();
            if (chang == 1)
            {
                if (AudioLibrary.tabl == 4)
                    al.setContentText(AudioLibraryDB.insert(4, datarr));
                else if (AudioLibrary.tabl == 5)
                    al.setContentText(AudioLibraryDB.insert(5, datarr));
            }
            else if (chang == 2)
            {
                datarr[1] = String.valueOf(AudioLibrary.musicartistbandid());
                al.setContentText(AudioLibraryDB.update(4, datarr));
            }
            if (al.getContentText().equals("Your data has been saved") || al.getContentText().equals("Your data has been updated"))
            {
                if (chang == 1)
                    AudioLibrary.datachanging(chang, 0);
                else if (chang == 2)
                    AudioLibrary.datachanging(chang, 0);
            }
        }
        if (chang == 3 || chang == 4 || chang == 5)
        {
            ind = 0;
            String[] datarr = new String[4];
            datarr[0] = textfield14change.getText();
            if((textfield15change.getText().length()>=3 && textfield15change.getText().length()<=5) && textfield15change.getText().lastIndexOf(":") != 3)
                datarr[1] = "00:" + textfield15change.getText();
            else
                datarr[1] = textfield15change.getText();
            if (!textfield1change.getText().equals(""))
                datarr[2] = String.valueOf(AudioLibrary.musicartistbandid());
            datarr[3] = textfield16change.getText();
            if (chang == 3)
            {
                al.setContentText(AudioLibraryDB.insert(3, datarr));
                ind = AudioLibraryDB.songid();
            }
            else if (chang == 4)
            {
                try
                {
                    ind = Integer.parseInt(Arrays.asList(AudioLibrary.tableview3.getSelectionModel().getSelectedItem()).get(3));
                }
                catch (Exception e)
                {
                    ind = -1;
                }
                datarr[2]=String.valueOf(AudioLibrary.songid());
                al.setContentText(AudioLibraryDB.update(3, datarr));
            }
            else if (chang == 5)
            {
                datarr = Arrays.copyOfRange(datarr, 0, 1);
                datarr[0]=String.valueOf(AudioLibrary.songid());
                al.setContentText(AudioLibraryDB.delete(1, datarr));
            }
            if (al.getContentText().equals("Your data has been saved") || al.getContentText().equals("Your data has been updated") || al.getContentText().equals("Your data has been deleted"))
                AudioLibrary.datachanging(chang, ind);
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

    public void button12add() throws Exception
    {
        chang = 3;
        changedata();
    }

    public void button13edit() throws Exception
    {
        chang = 4;
        changedata();
    }
    public void button14delete() throws Exception
    {
        chang = 5;
        changedata();
    }
}