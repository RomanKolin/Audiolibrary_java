package com.example.audiolibrary;

import javafx.fxml.*;
import javafx.scene.control.*;
import java.util.*;

public class Covers
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

    public void changedata() throws Exception
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
            if (!String.valueOf(AudioLibraryDB.musicartistbandid(datarr[2])).equals("0") && (!String.valueOf(AudioLibraryDB.musicartistbandid(datarr[4])).equals("0") || datarr[4].equals("")))
                al.setContentText(AudioLibraryDB.insert(6, datarr));
            else
                al.setContentText("Your data hasn't been saved");
        }
        else if (chang == 2)
        {
            if (!AudioLibrary.tableview1.getSelectionModel().isEmpty())
            {
                ind = Integer.parseInt(Arrays.asList(AudioLibrary.tableview1.getSelectionModel().getSelectedItem()).get(6));
                datarr[2] = String.valueOf(AudioLibrary.coverid());
                al.setContentText(AudioLibraryDB.update(5, datarr));
            }
            else
                al.setContentText("Your data hasn't been updated");
        }
        else if (chang == 3)
        {
            datarr = Arrays.copyOfRange(datarr, 0, 1);
            if (!textfield1change.getText().isEmpty())
                datarr[0] = String.valueOf(AudioLibrary.coverid());
            al.setContentText(AudioLibraryDB.delete(2, datarr));
        }
        if (al.getContentText().equals("Your data has been saved") || al.getContentText().equals("Your data has been updated") || al.getContentText().equals("Your data has been deleted"))
        {
            int noart = 0;

            AudioLibrary.textfield17.setText("");
            AudioLibrary.obslist.clear();
            if (chang == 1)
            {
                AudioLibrary.obslist.addAll(Arrays.asList(AudioLibraryDB.selectcover(AudioLibraryDB.songid())));
                if (!AudioLibrary.obslist.isEmpty() && AudioLibrary.obslist.get(0)[0] == null)
                {
                    datarr1[6] = String.valueOf(AudioLibraryDB.songid());
                    AudioLibrary.obslist.clear();
                    AudioLibrary.obslist.addAll(datarr1);
                }
            }
            else if (chang == 2)
                AudioLibrary.obslist.addAll(Arrays.asList(AudioLibraryDB.selectcover(ind)));
            AudioLibrary.tableview1.setItems(AudioLibrary.obslist);
            AudioLibrary.tableview1.getSelectionModel().select(0);
            AudioLibrary.tableview1.scrollTo(0);
            AudioLibrary.rowclick(AudioLibrary.tableview1, 0);
            if (!AudioLibrary.tableview1.getItems().isEmpty())
                for (String artband: AudioLibrary.coverartbandarr)
                    if (Arrays.asList(AudioLibrary.tableview1.getSelectionModel().getSelectedItem()).get(1).equals(artband))
                        noart = 1;
            if (!textfield1change.getText().isEmpty())
                AudioLibrary.label24.setText("Number of artists; songs count/duration: " + noart + "; 1/" + textfield4change.getText());
            else
                AudioLibrary.button5.fire();
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

    public void button10delete() throws Exception
    {
        chang = 3;
        changedata();
    }
}