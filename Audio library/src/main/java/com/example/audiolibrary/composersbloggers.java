package com.example.audiolibrary;

import javafx.fxml.*;
import javafx.scene.control.*;
import java.util.*;

public class composersbloggers
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

    public void ChangeData() throws Exception
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
            audiolibrary.textfield18.setText("");
            String[] datarr = new String[2];
            datarr[0] = textfield1change.getText();
            if (chang == 1)
            {
                if (audiolibrary.tabl == 4)
                    al.setContentText(audiolibrarydb.INSERT(4, datarr));
                else if (audiolibrary.tabl == 5)
                    al.setContentText(audiolibrarydb.INSERT(5, datarr));
            }
            else if (chang == 2)
            {
                datarr[1] = String.valueOf(audiolibrary.MusicArtistBandID());
                al.setContentText(audiolibrarydb.UPDATE(4, datarr));
            }
            if (al.getContentText().equals("Your data has been saved") || al.getContentText().equals("Your data has been updated"))
            {
                if (chang == 1)
                    audiolibrary.DataChanging(chang, 0);
                else if (chang == 2)
                    audiolibrary.DataChanging(chang, 0);
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
                datarr[2] = String.valueOf(audiolibrary.MusicArtistBandID());
            datarr[3] = textfield16change.getText();
            if (chang == 3)
            {
                al.setContentText(audiolibrarydb.INSERT(3, datarr));
                ind = audiolibrarydb.SongID();
            }
            else if (chang == 4)
            {
                try
                {
                    ind = Integer.parseInt(Arrays.asList(audiolibrary.tableview3.getSelectionModel().getSelectedItem()).get(3));
                }
                catch (Exception e)
                {
                    ind = -1;
                }
                datarr[2]=String.valueOf(audiolibrary.SongID());
                al.setContentText(audiolibrarydb.UPDATE(3, datarr));
            }
            else if (chang == 5)
            {
                datarr = Arrays.copyOfRange(datarr, 0, 1);
                datarr[0]=String.valueOf(audiolibrary.SongID());
                al.setContentText(audiolibrarydb.DELETE(1, datarr));
            }
            if (al.getContentText().equals("Your data has been saved") || al.getContentText().equals("Your data has been updated") || al.getContentText().equals("Your data has been deleted"))
                audiolibrary.DataChanging(chang, ind);
        }
        al.show();

        if (audiolibrary.backup == 0)
            audiolibrary.backup += 1;
    }

    public void button8add() throws Exception
    {
        chang = 1;
        ChangeData();
    }

    public void button9edit() throws Exception
    {
        chang = 2;
        ChangeData();
    }

    public void button12add() throws Exception
    {
        chang = 3;
        ChangeData();
    }

    public void button13edit() throws Exception
    {
        chang = 4;
        ChangeData();
    }
    public void button14delete() throws Exception
    {
        chang = 5;
        ChangeData();
    }
}