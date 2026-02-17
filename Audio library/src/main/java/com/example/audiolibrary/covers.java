package com.example.audiolibrary;

import javafx.fxml.*;
import javafx.scene.control.*;
import java.util.*;

public class covers
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
    Button button8add;
    @FXML
    Button button9edit;
    @FXML
    Button button10delete;

    static int chang;

    public void ChangeData() throws Exception
    {
        int ind = 0;
        Alert al;
        DialogPane dp;

        al = new Alert(Alert.AlertType.NONE);
        dp = al.getDialogPane();
        dp.getButtonTypes().add(ButtonType.OK);
        dp.getStylesheets().add("/alert.css");

        String[] datarr = new String[6], datarr1 = new String[7];
        datarr[0] = textfield1change.getText();
        if((textfield4change.getText().length()>=3 && textfield4change.getText().length()<=5) && textfield4change.getText().lastIndexOf(":") != 3)
            datarr[1] = "00:" + textfield4change.getText();
        else
            datarr[1] = textfield4change.getText();
        datarr[2] = textfield2change.getText();
        datarr[3] = textfield3change.getText();
        datarr[4] = textfield5change.getText();
        datarr[5] = textfield6change.getText();
        datarr1[0] = textfield1change.getText();
        datarr1[1] = textfield2change.getText();
        datarr1[2] = textfield3change.getText();
        datarr1[3] = textfield4change.getText();
        datarr1[4] = textfield5change.getText();
        datarr1[5] = textfield6change.getText();
        if (chang == 1)
        {
            if (!String.valueOf(audiolibrarydb.MusicArtistBandID(datarr[2])).equals("0") && (!String.valueOf(audiolibrarydb.MusicArtistBandID(datarr[4])).equals("0") || datarr[4].equals("")))
                al.setContentText(audiolibrarydb.INSERT(6, datarr));
            else
                al.setContentText("Your data hasn't been saved");
        }
        else if (chang == 2)
        {
            if (!audiolibrary.tableview1.getSelectionModel().isEmpty())
            {
                ind = Integer.parseInt(Arrays.asList(audiolibrary.tableview1.getSelectionModel().getSelectedItem()).get(6));
                datarr[2] = String.valueOf(audiolibrary.CoverID());
                al.setContentText(audiolibrarydb.UPDATE(5, datarr));
            }
            else
                al.setContentText("Your data hasn't been updated");
        }
        else if (chang == 3)
        {
            datarr = Arrays.copyOfRange(datarr, 0, 1);
            if (!textfield1change.getText().isEmpty())
                datarr[0] = String.valueOf(audiolibrary.CoverID());
            al.setContentText(audiolibrarydb.DELETE(2, datarr));
        }
        if (al.getContentText().equals("Your data has been saved") || al.getContentText().equals("Your data has been updated") || al.getContentText().equals("Your data has been deleted"))
        {
            int noart = 0;

            audiolibrary.textfield17.setText("");
            audiolibrary.obslist.clear();
            if (chang == 1)
            {
                audiolibrary.obslist.addAll(Arrays.asList(audiolibrarydb.SELECTCOVER(audiolibrarydb.SongID())));
                if (!audiolibrary.obslist.isEmpty() && audiolibrary.obslist.get(0)[0] == null)
                {
                    datarr1[6] = String.valueOf(audiolibrarydb.SongID());
                    audiolibrary.obslist.clear();
                    audiolibrary.obslist.addAll(datarr1);
                }
            }
            else if (chang == 2)
                audiolibrary.obslist.addAll(Arrays.asList(audiolibrarydb.SELECTCOVER(ind)));
            audiolibrary.tableview1.setItems(audiolibrary.obslist);
            audiolibrary.tableview1.getSelectionModel().select(0);
            audiolibrary.tableview1.scrollTo(0);
            audiolibrary.RowClick(audiolibrary.tableview1, 0);
            if (!audiolibrary.tableview1.getItems().isEmpty())
                for (String artband:audiolibrary.coverartbandarr)
                    if (Arrays.asList(audiolibrary.tableview1.getSelectionModel().getSelectedItem()).get(1).equals(artband))
                        noart = 1;
            if (!textfield1change.getText().isEmpty())
                audiolibrary.label24.setText("Number of artists; songs count/duration: " + noart + "; 1/" + textfield4change.getText());
            else
                audiolibrary.button5.fire();
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

    public void button10delete() throws Exception
    {
        chang = 3;
        ChangeData();
    }
}