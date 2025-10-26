package com.example.audiolibrary;

import javafx.fxml.*;
import javafx.scene.control.*;
import java.util.*;

public class musicartistband
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
    Button button8add;
    @FXML
    Button button9edit;
    @FXML
    Button button10add;
    @FXML
    Button button11edit;
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
            String[] datarr = new String[3];
            datarr[0] = textfield1change.getText();
            datarr[1] = textfield3change.getText();
            if (chang == 1)
                al.setContentText(audiolibrarydb.INSERT(1, datarr));
            else if (chang == 2)
            {
                datarr[2] = String.valueOf(audiolibrary.MusicArtistBandID());
                al.setContentText(audiolibrarydb.UPDATE(1, datarr));
            }
            if (al.getContentText().equals("Your data has been saved") || al.getContentText().equals("Your data has been updated"))
            {
                if (chang == 1)
                    audiolibrary.DataChanging(chang, 0);
                else if (chang == 2)
                    audiolibrary.DataChanging(chang, 0);
            }
        }
        if (chang == 3 || chang == 4)
        {
            audiolibrary.textfield18.setText("");
            String[] datarr = new String[3];
            datarr[0] = textfield10change.getText();
            datarr[1] = String.valueOf(audiolibrary.MusicArtistBandID());
            if (chang == 3)
                al.setContentText(audiolibrarydb.INSERT(2, datarr));
            else if (chang == 4)
            {
                datarr[2] = String.valueOf(audiolibrary.RelatedMusicArtistBandID());
                al.setContentText(audiolibrarydb.UPDATE(2, datarr));
            }
            if (al.getContentText().equals("Your data has been saved") || al.getContentText().equals("Your data has been updated"))
            {
                int relartbandid = audiolibrary.RelatedMusicArtistBandID();
                audiolibrary.DataChanging(chang, 0);
                for (int i = 0; i < audiolibrary.tableview2.getItems().size(); i++)
                {
                    audiolibrary.tableview2.getSelectionModel().select(i);
                    if (chang == 3)
                    {
                        if (Integer.parseInt(Arrays.asList(audiolibrary.tableview2.getSelectionModel().getSelectedItem()).get(4)) == audiolibrarydb.NewMusicArtistBandID())
                        {
                            audiolibrary.tableview2.getSelectionModel().select(i);
                            audiolibrary.RowClick(audiolibrary.tableview2, i);
                            break;
                        }
                    }
                    else if (chang == 4)
                    {
                        if (Integer.parseInt(Arrays.asList(audiolibrary.tableview2.getSelectionModel().getSelectedItem()).get(4)) == relartbandid)
                        {
                            audiolibrary.tableview2.getSelectionModel().select(i);
                            audiolibrary.RowClick(audiolibrary.tableview2, i);
                            break;
                        }
                    }
                }
            }
        }
        if (chang == 5 || chang == 6 || chang == 7)
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
            if (!textfield10change.getText().equals(""))
                datarr[2] = String.valueOf(audiolibrary.RelatedMusicArtistBandID());
            datarr[3] = textfield16change.getText();
            if (chang == 5)
            {
                al.setContentText(audiolibrarydb.INSERT(3, datarr));
                ind = audiolibrarydb.SongID();
            }
            else if (chang == 6)
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
            else if (chang == 7)
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

    public void button10add() throws Exception
    {
        chang = 3;
        ChangeData();
    }

    public void button11edit() throws Exception
    {
        chang = 4;
        ChangeData();
    }

    public void button12add() throws Exception
    {
        chang = 5;
        ChangeData();
    }

    public void button13edit() throws Exception
    {
        chang = 6;
        ChangeData();
    }
    public void button14delete() throws Exception
    {
        chang = 7;
        ChangeData();
    }
}