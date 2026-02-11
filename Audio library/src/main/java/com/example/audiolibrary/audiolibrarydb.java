package com.example.audiolibrary;

import java.util.*;
import java.sql.*;

public class audiolibrarydb
{
    static Connection conn;
    static int row;

    public static Connection conn() throws Exception
    {
        Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
        conn = DriverManager.getConnection("jdbc:mysql://localhost:/Audio library", "romankolin", "IT2017year");

        return conn;
    }

    public static void PreparedStatement(PreparedStatement pstat, String[] datarr, int itim) throws Exception
    {
        for (int i = 1; i <= itim; i++)
            if (String.valueOf(Arrays.asList(datarr).get(i-1)).equals(""))
                pstat.setString(i, null);
            else
                pstat.setString(i, Arrays.asList(datarr).get(i-1));
    }

    public static int MusicArtistBandID(String artband) throws Exception
    {
        int artbandid = 0;

        conn();
        PreparedStatement pstat;
        ResultSet resset;
        pstat = conn.prepareStatement("SELECT ID FROM `Music artist/band identifier` WHERE ID=(SELECT ID FROM `Music artist/band` WHERE artband=?) OR ID=(SELECT ID FROM `Related music artist/band` WHERE relartband=?);");
        pstat.setString(1, artband);
        pstat.setString(2, artband);
        resset = pstat.executeQuery();
        while (resset.next())
            artbandid = resset.getInt("ID");
        conn.close();

        return artbandid;
    }

    public static int SongID() throws Exception
    {
        int songid = 0;

        conn();
        Statement stat = conn.createStatement();
        ResultSet resset;
        resset = stat.executeQuery("SELECT MAX(ID) FROM Song;");
        while (resset.next())
            songid = resset.getInt("MAX(ID)");
        conn.close();

        return songid;
    }

    public static String[][] SELECT(int tabl) throws Exception
    {
        int count = 0;

        conn();
        Statement stat = conn.createStatement();
        ResultSet resset;
        String[][] datarr = new String[0][0];
        switch (tabl)
        {
            case 1:
                resset = stat.executeQuery("SELECT COUNT(cat) FROM `Audio library`;");
                while (resset.next())
                    count = resset.getInt("COUNT(cat)");
                datarr = new String[count][4];
                resset = stat.executeQuery("SELECT cat, noorigartsbands, nosongs, REPLACE(REPLACE(REPLACE(REPLACE(songsdur, ':0', ':'), '00:', '0:'), ' 0', ' '), ' :', ' 0:') FROM `Audio library` WHERE cat='Жанр' UNION (SELECT cat, noorigartsbands, nosongs, REPLACE(REPLACE(REPLACE(REPLACE(songsdur, ':0', ':'), '00:', '0:'), ' 0', ' '), ' :', ' 0:') FROM `Audio library` WHERE cat='Композиторы') UNION (SELECT cat, noorigartsbands, nosongs, REPLACE(REPLACE(REPLACE(REPLACE(songsdur, ':0', ':'), '00:', '0:'), ' 0', ' '), ' :', ' 0:') FROM `Audio library` WHERE cat='Блогеры') UNION (SELECT cat, noorigartsbands, nosongs, REPLACE(REPLACE(REPLACE(REPLACE(songsdur, ':0', ':'), '00:', '0:'), ' 0', ' '), ' :', ' 0:') FROM `Audio library` WHERE cat='Каверы') UNION (SELECT cat, noorigartsbands, nosongs, REPLACE(REPLACE(REPLACE(REPLACE(songsdur, ':0', ':'), '00:', '0:'), ' 0', ' '), ' :', ' 0:') FROM `Audio library` WHERE cat='Саундтреки');");
                count = 0;
                while (resset.next())
                {
                    String cat = resset.getString(1);
                    int noorigartsbands = resset.getInt(2);
                    int nosongs = resset.getInt(3);
                    String songsdur = resset.getString(4);
                    datarr[count][0] = cat;
                    datarr[count][1] = String.valueOf(noorigartsbands);
                    datarr[count][2] = String.valueOf(nosongs);
                    datarr[count][3] = String.valueOf(songsdur);
                    count += 1;
                }
                break;
            case 2:
                resset = stat.executeQuery("SELECT COUNT(nam) FROM Genre;");
                while (resset.next())
                    count = resset.getInt("COUNT(nam)");
                datarr = new String[count][4];
                resset = stat.executeQuery("SELECT nam, noartsbands, nosongs, REPLACE(songsdur, ':0', ':') FROM Genre ORDER BY nam;");
                count = 0;
                while (resset.next())
                {
                    String nam = resset.getString(1);
                    int noartsbands = resset.getInt(2);
                    int nosongs = resset.getInt(3);
                    String songsdur = resset.getString(4);
                    datarr[count][0] = nam;
                    datarr[count][1] = String.valueOf(noartsbands);
                    datarr[count][2] = String.valueOf(nosongs);
                    datarr[count][3] = String.valueOf(songsdur);
                    count += 1;
                }
                break;
            case 3:
                resset = stat.executeQuery("SELECT COUNT(ID) FROM `Music artist/band` WHERE cat='Жанр';");
                while (resset.next())
                    count = resset.getInt("COUNT(ID)");
                datarr = new String[count][9];
                resset = stat.executeQuery("WITH musicartistband AS(WITH musicartistband AS(SELECT `Music artist/band`.artband, `Related music artist/band`.relartband, `Music artist/band`.genr, Song.nam, `Music artist/band`.nosongs, `Music artist/band`.songsdur, `Music artist/band`.totnosongs, `Music artist/band`.totsongsdur, `Music artist/band`.ID, `Related music artist/band`.ID AS relartbandid FROM `Music artist/band` LEFT JOIN `Related music artist/band` ON `Music artist/band`.ID=`Related music artist/band`.artband LEFT JOIN `Music artist/band's song` ON `Music artist/band`.ID=`Music artist/band's song`.artband LEFT JOIN Song ON `Music artist/band's song`.song=Song.ID WHERE `Music artist/band`.cat='Жанр') SELECT artband, GROUP_CONCAT(DISTINCT relartband ORDER BY relartbandid SEPARATOR ', ') AS relartband, genr, nam, nosongs, songsdur, totnosongs, totsongsdur, ID FROM musicartistband GROUP BY artband, genr, nam, nosongs, songsdur, totnosongs, totsongsdur, ID) SELECT artband, relartband, genr, GROUP_CONCAT(nam ORDER BY nam SEPARATOR ', '), nosongs, TRIM(LEADING '0' FROM TRIM(LEADING '00:' FROM REPLACE(songsdur, ':0', ':'))), totnosongs, TRIM(LEADING '0' FROM TRIM(LEADING '00:' FROM REPLACE(totsongsdur, ':0', ':'))), ID FROM musicartistband GROUP BY artband, relartband, genr, nosongs, songsdur, totnosongs, totsongsdur, ID ORDER BY artband;");
                count = 0;
                while (resset.next())
                {
                    String artband = resset.getString(1);
                    String relartband = resset.getString(2);
                    String genr = resset.getString(3);
                    String songs = resset.getString(4);
                    int nosongs = resset.getInt(5);
                    String songsdur = resset.getString(6);
                    if (songsdur.equals(":0"))
                        songsdur = "0:0";
                    int totnosongs = resset.getInt(7);
                    String totsongsdur = resset.getString(8);
                    if (totsongsdur.equals(":0"))
                        totsongsdur = "0:0";
                    int id = resset.getInt(9);
                    datarr[count][0] = artband;
                    datarr[count][1] = relartband;
                    datarr[count][2] = genr;
                    datarr[count][3] = songs;
                    datarr[count][4] = String.valueOf(nosongs);
                    datarr[count][5] = songsdur;
                    datarr[count][6] = String.valueOf(totnosongs);
                    datarr[count][7] = totsongsdur;
                    datarr[count][8] = String.valueOf(id);
                    count += 1;
                }
                break;
            case 4:
                resset = stat.executeQuery("SELECT COUNT(ID) FROM `Music artist/band` WHERE cat='Композиторы';");
                while (resset.next())
                    count = resset.getInt("COUNT(ID)");
                datarr = new String[count][5];
                resset = stat.executeQuery("WITH composer AS(SELECT `Music artist/band`.artband, Song.nam, `Music artist/band`.nosongs, `Music artist/band`.songsdur, `Music artist/band`.ID FROM `Music artist/band` LEFT JOIN `Music artist/band's song` ON `Music artist/band`.ID=`Music artist/band's song`.artband LEFT JOIN Song ON `Music artist/band's song`.song=Song.ID WHERE `Music artist/band`.cat='Композиторы') SELECT artband, GROUP_CONCAT(nam ORDER BY nam SEPARATOR ', '), nosongs, TRIM(LEADING '0' FROM TRIM(LEADING '00:' FROM REPLACE(songsdur, ':0', ':'))), ID FROM composer GROUP BY artband, nosongs, songsdur, ID ORDER BY artband;");
                count = 0;
                while (resset.next())
                {
                    String nam = resset.getString(1);
                    String songs = resset.getString(2);
                    int nosongs = resset.getInt(3);
                    String songsdur = resset.getString(4);
                    if (songsdur.equals(":0"))
                        songsdur = "0:0";
                    int id = resset.getInt(5);
                    datarr[count][0] = nam;
                    datarr[count][1] = songs;
                    datarr[count][2] = String.valueOf(nosongs);
                    datarr[count][3] = songsdur;
                    datarr[count][4] = String.valueOf(id);
                    count += 1;
                }
                break;
            case 5:
                resset = stat.executeQuery("SELECT COUNT(ID) FROM `Music artist/band` WHERE cat='Блогеры';");
                while (resset.next())
                    count = resset.getInt("COUNT(ID)");
                datarr = new String[count][5];
                resset = stat.executeQuery("WITH blogger AS(SELECT `Music artist/band`.artband, Song.nam, `Music artist/band`.nosongs, `Music artist/band`.songsdur, `Music artist/band`.ID FROM `Music artist/band` LEFT JOIN `Music artist/band's song` ON `Music artist/band`.ID=`Music artist/band's song`.artband LEFT JOIN Song ON `Music artist/band's song`.song=Song.ID WHERE `Music artist/band`.cat='Блогеры') SELECT artband, GROUP_CONCAT(nam ORDER BY nam SEPARATOR ', '), nosongs, TRIM(LEADING '0' FROM TRIM(LEADING '00:' FROM REPLACE(songsdur, ':0', ':'))), ID FROM blogger GROUP BY artband, nosongs, songsdur, ID ORDER BY artband;");
                count = 0;
                while (resset.next())
                {
                    String nicknam = resset.getString(1);
                    String songs = resset.getString(2);
                    int nosongs = resset.getInt(3);
                    String songsdur = resset.getString(4);
                    if (songsdur.equals(":0"))
                        songsdur = "0:0";
                    int id = resset.getInt(5);
                    datarr[count][0] = nicknam;
                    datarr[count][1] = songs;
                    datarr[count][2] = String.valueOf(nosongs);
                    datarr[count][3] = songsdur;
                    datarr[count][4] = String.valueOf(id);
                    count += 1;
                }
                break;
            case 6:
                resset = stat.executeQuery("WITH cover AS(WITH cover AS(WITH cover AS(SELECT Song.nam, `Music artist/band`.artband, `Music artist/band's song`.feat, Song.dur, Song.ID FROM `Music artist/band's song` JOIN `Music artist/band` ON `Music artist/band's song`.artband=`Music artist/band`.ID JOIN Song ON `Music artist/band's song`.song=Song.ID WHERE Song.cat='Каверы' UNION (SELECT Song.nam, `Related music artist/band`.relartband, `Music artist/band's song`.feat, Song.dur, Song.ID FROM `Music artist/band's song` JOIN `Related music artist/band` ON `Music artist/band's song`.artband=`Related music artist/band`.ID JOIN Song ON `Music artist/band's song`.song=Song.ID WHERE Song.cat='Каверы')) SELECT cover.nam, cover.artband, cover.feat, cover.dur, `Music artist/band`.artband AS origartband, `Cover's original music artist/band`.feat AS origfeat, cover.ID FROM cover LEFT JOIN `Cover's original music artist/band` ON cover.ID=`Cover's original music artist/band`.song LEFT JOIN `Music artist/band` ON `Cover's original music artist/band`.artband=`Music artist/band`.ID UNION (SELECT cover.nam, cover.artband, cover.feat, cover.dur, `Related music artist/band`.relartband, `Cover's original music artist/band`.feat, cover.ID FROM cover LEFT JOIN `Cover's original music artist/band` ON cover.ID=`Cover's original music artist/band`.song LEFT JOIN `Related music artist/band` ON `Cover's original music artist/band`.artband=`Related music artist/band`.ID)) SELECT nam, artband, feat, TRIM(LEADING '00:' FROM REPLACE(dur, ':0', ':')), GROUP_CONCAT(origartband SEPARATOR ', '), GROUP_CONCAT(DISTINCT origfeat SEPARATOR ''), ID FROM cover GROUP BY artband, nam, feat, dur, ID ORDER BY nam, artband) SELECT COUNT(ID) FROM cover;");
                while (resset.next())
                    count = resset.getInt("COUNT(ID)");
                datarr = new String[count][7];
                resset = stat.executeQuery("WITH cover AS(WITH cover AS(SELECT Song.nam, `Music artist/band`.artband, `Music artist/band's song`.feat, Song.dur, Song.ID FROM `Music artist/band's song` JOIN `Music artist/band` ON `Music artist/band's song`.artband=`Music artist/band`.ID JOIN Song ON `Music artist/band's song`.song=Song.ID WHERE Song.cat='Каверы' UNION (SELECT Song.nam, `Related music artist/band`.relartband, `Music artist/band's song`.feat, Song.dur, Song.ID FROM `Music artist/band's song` JOIN `Related music artist/band` ON `Music artist/band's song`.artband=`Related music artist/band`.ID JOIN Song ON `Music artist/band's song`.song=Song.ID WHERE Song.cat='Каверы')) SELECT cover.nam, cover.artband, cover.feat, cover.dur, `Music artist/band`.artband AS origartband, `Cover's original music artist/band`.feat AS origfeat, cover.ID FROM cover LEFT JOIN `Cover's original music artist/band` ON cover.ID=`Cover's original music artist/band`.song LEFT JOIN `Music artist/band` ON `Cover's original music artist/band`.artband=`Music artist/band`.ID UNION (SELECT cover.nam, cover.artband, cover.feat, cover.dur, `Related music artist/band`.relartband, `Cover's original music artist/band`.feat, cover.ID FROM cover LEFT JOIN `Cover's original music artist/band` ON cover.ID=`Cover's original music artist/band`.song LEFT JOIN `Related music artist/band` ON `Cover's original music artist/band`.artband=`Related music artist/band`.ID)) SELECT nam, artband, feat, TRIM(LEADING '00:' FROM REPLACE(dur, ':0', ':')), GROUP_CONCAT(origartband SEPARATOR ', '), GROUP_CONCAT(DISTINCT origfeat SEPARATOR ''), ID FROM cover GROUP BY artband, nam, feat, dur, ID ORDER BY nam, artband;");
                count = 0;
                while (resset.next())
                {
                    String song = resset.getString(1);
                    String artband = resset.getString(2);
                    String feat = resset.getString(3);
                    String dur = resset.getString(4);
                    String origartband = resset.getString(5);
                    String origfeat = resset.getString(6);
                    int id = resset.getInt(7);
                    datarr[count][0] = song;
                    datarr[count][1] = artband;
                    datarr[count][2] = feat;
                    datarr[count][3] = dur;
                    datarr[count][4] = origartband;
                    datarr[count][5] = origfeat;
                    datarr[count][6] = String.valueOf(id);
                    count += 1;
                }

                ResultSet resset1;
                resset1 = stat.executeQuery("SELECT COUNT(artband) FROM `Music artist/band` WHERE cat='Каверы';");
                while (resset1.next())
                    count = resset1.getInt("COUNT(artband)");
                audiolibrary.coverartbandarr = new String[count];
                resset1 = stat.executeQuery("SELECT artband FROM `Music artist/band` WHERE cat='Каверы';");
                count = 0;
                while (resset1.next())
                {
                    String artband = resset1.getString(1);
                    audiolibrary.coverartbandarr[count] = artband;
                    count += 1;
                }
                break;
            case 7:
                resset = stat.executeQuery("SELECT COUNT(ID) FROM Soundtrack;");
                while (resset.next())
                    count = resset.getInt("COUNT(ID)");
                datarr = new String[count][6];
                resset = stat.executeQuery("SELECT movanimsergam, artband, song, nosongs, TRIM(LEADING '00:' FROM REGEXP_REPLACE(REGEXP_REPLACE(REPLACE(songsdur, ':0', ':'), '^01', '1'), '^02', '2')), ID FROM Soundtrack ORDER BY movanimsergam;");
                count = 0;
                while (resset.next())
                {
                    String movanimsergam = resset.getString(1);
                    String artband = resset.getString(2);
                    String song = resset.getString(3);
                    int nosongs = resset.getInt(4);
                    String songsdur = resset.getString(5);
                    int id = resset.getInt(6);
                    datarr[count][0] = movanimsergam;
                    datarr[count][1] = artband;
                    datarr[count][2] = song;
                    datarr[count][3] = String.valueOf(nosongs);
                    datarr[count][4] = songsdur;
                    datarr[count][5] = String.valueOf(id);
                    count += 1;
                }
                break;
            case 8:
                resset = stat.executeQuery("SELECT COUNT(num) FROM Favourites;");
                while (resset.next())
                    count = resset.getInt("COUNT(num)");
                datarr = new String[count][10];
                resset = stat.executeQuery("WITH favourites AS(WITH favourites AS(SELECT `Music artist/band`.artband, `Related music artist/band`.relartband, `Music artist/band`.genr, Song.nam, `Music artist/band`.nosongs, `Music artist/band`.songsdur, `Music artist/band`.totnosongs, `Music artist/band`.totsongsdur, Favourites.num FROM `Music artist/band` JOIN Favourites ON `Music artist/band`.ID=Favourites.artband LEFT JOIN `Related music artist/band` ON `Music artist/band`.ID=`Related music artist/band`.artband JOIN `Music artist/band's song` ON `Music artist/band`.ID=`Music artist/band's song`.artband JOIN Song ON `Music artist/band's song`.song=Song.ID WHERE `Music artist/band`.cat='Жанр') SELECT artband, GROUP_CONCAT(DISTINCT relartband SEPARATOR ', ') AS relartband, genr, nam, nosongs, songsdur, totnosongs, totsongsdur, num FROM favourites GROUP BY artband, genr, nam, nosongs, songsdur, totnosongs, totsongsdur, num) SELECT artband, relartband, genr, GROUP_CONCAT(nam ORDER BY nam SEPARATOR ', '), nosongs, TRIM(LEADING '0' FROM TRIM(LEADING '00:' FROM REPLACE(songsdur, ':0', ':'))), totnosongs, TRIM(LEADING '0' FROM TRIM(LEADING '00:' FROM REPLACE(totsongsdur, ':0', ':'))), num FROM favourites GROUP BY artband, relartband, genr, nosongs, songsdur, totnosongs, totsongsdur, num ORDER BY num;");
                count = 0;
                while (resset.next())
                {
                    String artband = resset.getString(1);
                    String relartband = resset.getString(2);
                    String genr = resset.getString(3);
                    String songs = resset.getString(4);
                    int nosongs = resset.getInt(5);
                    String songsdur = resset.getString(6);
                    int totnosongs = resset.getInt(7);
                    String totsongsdur = resset.getString(8);
                    int num = resset.getInt(9);
                    datarr[count][0] = artband;
                    datarr[count][1] = relartband;
                    datarr[count][2] = genr;
                    datarr[count][3] = songs;
                    datarr[count][4] = String.valueOf(nosongs);
                    datarr[count][5] = songsdur;
                    datarr[count][6] = String.valueOf(totnosongs);
                    datarr[count][7] = totsongsdur;
                    datarr[count][8] = String.valueOf(num);
                    count += 1;
                }
                break;
        }
        conn.close();

        return datarr;
    }

    public static String[][] SUB1SELECT(int ID) throws Exception
    {
        int count = 0;

        conn();
        ResultSet resset;
        String[][] datarr;
        PreparedStatement pstat = conn.prepareStatement("SELECT COUNT(ID) FROM `Related music artist/band` WHERE artband=?");
        pstat.setInt(1, ID);
        resset = pstat.executeQuery();
        while (resset.next())
            count = resset.getInt("COUNT(ID)");
        datarr = new String[count][5];
        pstat = conn.prepareStatement("WITH relmusicartistband AS(SELECT `Related music artist/band`.relartband, Song.nam, `Related music artist/band`.nosongs, `Related music artist/band`.songsdur, `Related music artist/band`.ID FROM `Music artist/band` JOIN `Related music artist/band` ON `Music artist/band`.ID=`Related music artist/band`.artband LEFT JOIN `Music artist/band's song` ON `Related music artist/band`.ID=`Music artist/band's song`.artband LEFT JOIN Song ON `Music artist/band's song`.song=Song.ID WHERE `Music artist/band`.ID=?) SELECT relartband, GROUP_CONCAT(DISTINCT nam ORDER BY nam SEPARATOR ', '), nosongs, TRIM(LEADING '0' FROM TRIM(LEADING '00:' FROM REPLACE(songsdur, ':0', ':'))), ID FROM relmusicartistband GROUP BY relartband, nosongs, songsdur, ID ORDER BY relartband;");
        pstat.setInt(1, ID);
        resset = pstat.executeQuery();
        count = 0;
        while (resset.next())
        {
            String relartband = resset.getString(1);
            String songs = resset.getString(2);
            int nosongs = resset.getInt(3);
            String songsdur = resset.getString(4);
            if (songsdur.equals(":0"))
                songsdur = "0:0";
            int id = resset.getInt(5);
            datarr[count][0] = relartband;
            datarr[count][1] = songs;
            datarr[count][2] = String.valueOf(nosongs);
            datarr[count][3] = songsdur;
            datarr[count][4] = String.valueOf(id);
            count += 1;
        }
        conn.close();

        return datarr;
    }

    public static String[][] SUB2SELECT(int ID, int rel) throws Exception
    {
        int count = 0;

        conn();
        ResultSet resset;
        String[][] datarr;
        PreparedStatement pstat = conn.prepareStatement("SELECT COUNT(song) FROM `Music artist/band's song` JOIN Song ON `Music artist/band's song`.song=Song.ID WHERE artband=? AND Song.cat IS NULL;");
        pstat.setInt(1, ID);
        resset = pstat.executeQuery();
        while (resset.next())
            count = resset.getInt("COUNT(song)");
        datarr = new String[count][4];
        if (rel == 0)
            pstat = conn.prepareStatement("SELECT Song.nam, TRIM(LEADING '00:' FROM REPLACE(Song.dur, ':0', ':')), `Music artist/band's song`.feat, Song.ID FROM `Music artist/band's song` JOIN `Music artist/band` ON `Music artist/band's song`.artband=`Music artist/band`.ID JOIN Song ON `Music artist/band's song`.song=Song.ID WHERE `Music artist/band's song`.artband=? AND Song.cat IS NULL ORDER BY Song.nam;");
        else
            pstat = conn.prepareStatement("SELECT Song.nam, TRIM(LEADING '00:' FROM REPLACE(Song.dur, ':0', ':')), `Music artist/band's song`.feat, Song.ID FROM `Music artist/band's song` JOIN `Related music artist/band` ON `Music artist/band's song`.artband=`Related music artist/band`.ID JOIN Song ON `Music artist/band's song`.song=Song.ID WHERE `Music artist/band's song`.artband=? AND Song.cat IS NULL ORDER BY Song.nam;");
        pstat.setInt(1, ID);
        resset = pstat.executeQuery();
        count = 0;
        while (resset.next())
        {
            String nam = resset.getString(1);
            String dur = resset.getString(2);
            String feat = resset.getString(3);
            int id = resset.getInt(4);
            datarr[count][0] = nam;
            datarr[count][1] = dur;
            datarr[count][2] = feat;
            datarr[count][3] = String.valueOf(id);
            count += 1;
        }
        conn.close();

        return datarr;
    }

    public static String INSERT(int chang, String[] datarr) throws Exception
    {
        String swap;

        conn();
        PreparedStatement pstat = null;
        switch (chang)
        {
            case 1:
                pstat = conn.prepareStatement("INSERT INTO `Music artist/band`(artband, genr, cat) VALUES(?, ?, 'Жанр');");
                PreparedStatement(pstat, datarr, 2);
                break;
            case 2:
                pstat = conn.prepareStatement("INSERT INTO `Related music artist/band`(relartband, artband) VALUES(?, ?);");
                PreparedStatement(pstat, datarr, 2);
                break;
            case 3:
                if (datarr.length == 4)
                {
                    pstat = conn.prepareStatement("INSERT INTO Song(nam, dur, cat) VALUES(?, ?, null);");
                    PreparedStatement(pstat, datarr, 2);
                    try
                    {
                        row = pstat.executeUpdate();
                    }
                    catch (Exception e)
                    {
                        row = 0;
                    }
                    if (row == 0)
                        return "Your data hasn't been saved";
                    datarr = Arrays.copyOf(datarr, datarr.length+1);
                    datarr = Arrays.copyOfRange(datarr, 2, datarr.length);
                    datarr[datarr.length-1] = String.valueOf(audiolibrarydb.SongID());
                    swap = datarr[0];
                    datarr[0] = datarr[2];
                    datarr[2] = swap;
                    swap = datarr[1];
                    datarr[1] = datarr[2];
                    datarr[2] = swap;
                    INSERT(3, datarr);
                    return "Your data has been saved";
                }
                pstat = conn.prepareStatement("INSERT INTO `Music artist/band's song`(song, artband, feat) VALUES(?, ?, ?);");
                PreparedStatement(pstat, datarr, 3);
                break;
            case 4:
                pstat = conn.prepareStatement("INSERT INTO `Music artist/band`(artband, cat) VALUES(?, 'Композиторы');");
                PreparedStatement(pstat, datarr, 1);
                break;
            case 5:
                pstat = conn.prepareStatement("INSERT INTO `Music artist/band`(artband, cat) VALUES(?, 'Блогеры');");
                PreparedStatement(pstat, datarr, 1);
                break;
            case 6:
                if (datarr.length == 6)
                {
                    pstat = conn.prepareStatement("INSERT INTO Song(nam, dur, cat) VALUES(?, ?, 'Каверы');");
                    PreparedStatement(pstat, datarr, 2);
                    try
                    {
                        row = pstat.executeUpdate();
                    }
                    catch (Exception e)
                    {
                        row = 0;
                    }
                    if (row == 0)
                        return "Your data hasn't been saved";
                    datarr[0] = String.valueOf(SongID());
                    datarr[1] = String.valueOf(MusicArtistBandID(datarr[2]));
                    datarr[2] = datarr[3];
                    datarr[3] = datarr[4];
                    datarr[4] = datarr[5];
                    datarr = Arrays.copyOfRange(datarr, 0, 5);
                    INSERT(6, datarr);
                    return "Your data has been saved";
                }
                if (datarr.length == 5)
                {
                    pstat = conn.prepareStatement("INSERT INTO `Music artist/band's song`(song, artband, feat) VALUES(?, ?, ?);");
                    PreparedStatement(pstat, datarr, 3);
                    try
                    {
                        row = pstat.executeUpdate();
                    }
                    catch (Exception e)
                    {
                        row = 0;
                    }
                    if (row == 0)
                        return "Your data hasn't been saved";
                    datarr[1] = String.valueOf(MusicArtistBandID(datarr[3]));
                    datarr[2] = datarr[4];
                    datarr = Arrays.copyOfRange(datarr, 0, 3);
                    INSERT(6, datarr);
                    return "Your data has been saved";
                }
                pstat = conn.prepareStatement("INSERT INTO `Cover's original music artist/band`(song, artband, feat) VALUES(?, ?, ?);");
                PreparedStatement(pstat, datarr, 3);
                break;
            case 7:
                pstat = conn.prepareStatement("INSERT INTO Soundtrack(movanimsergam, artband, song, nosongs, songsdur, cat) VALUES(?, ?, ?, ?, ?, 'Саундтреки');");
                PreparedStatement(pstat, datarr, 5);
                break;
        }
        try
        {
            row = pstat.executeUpdate();
        }
        catch (Exception e)
        {
            row = 0;
        }
        conn.close();

        if (row > 0)
            return "Your data has been saved";
        else
            return "Your data hasn't been saved";
    }

    public static String UPDATE(int chang, String[] datarr) throws Exception
    {
        String swap;

        conn();
        PreparedStatement pstat = null;
        switch (chang)
        {
            case 1:
                pstat = conn.prepareStatement("UPDATE `Music artist/band` SET artband=?, genr=? WHERE ID=?;");
                PreparedStatement(pstat, datarr, 3);
                break;
            case 2:
                pstat = conn.prepareStatement("UPDATE `Related music artist/band` SET relartband=?, artband=? WHERE ID=?;");
                PreparedStatement(pstat, datarr, 3);
                break;
            case 3:
                if (datarr.length == 4)
                {
                    pstat = conn.prepareStatement("UPDATE Song SET nam=?, dur=? WHERE ID=?;");
                    PreparedStatement(pstat, datarr, 3);
                    try
                    {
                        row = pstat.executeUpdate();
                    }
                    catch (Exception e)
                    {
                        row = 0;
                    }
                    if (row == 0)
                        return "Your data hasn't been updated";
                    datarr = Arrays.copyOfRange(datarr, 2, datarr.length);
                    UPDATE(3, datarr);
                    return "Your data has been updated";
                }
                swap = datarr[0];
                datarr[0] = datarr[1];
                datarr[1] = swap;
                pstat = conn.prepareStatement("UPDATE `Music artist/band's song`SET feat=? WHERE song=?;");
                PreparedStatement(pstat, datarr, 2);
                break;
            case 4:
                pstat = conn.prepareStatement("UPDATE `Music artist/band` SET artband=? WHERE ID=?;");
                PreparedStatement(pstat, datarr, 2);
                break;
            case 5:
                pstat = conn.prepareStatement("UPDATE Song SET nam=?, dur=? WHERE ID=?;");
                PreparedStatement(pstat, datarr, 3);
                break;
            case 6:
                pstat = conn.prepareStatement("UPDATE Soundtrack SET movanimsergam=?, artband=?, song=?, nosongs=?, songsdur=? WHERE ID=?;");
                PreparedStatement(pstat, datarr, 6);
                break;
        }
        try
        {
            row = pstat.executeUpdate();
        }
        catch (Exception e)
        {
            row = 0;
        }
        conn.close();

        if (row > 0)
            return "Your data has been updated";
        else
            return "Your data hasn't been updated";
    }

    public static String DELETE(int chang, String[] datarr) throws Exception
    {
        conn();
        PreparedStatement pstat = null;
        switch (chang)
        {
            case 1:
                pstat = conn.prepareStatement("DELETE FROM `Music artist/band's song` WHERE song=?;");
                PreparedStatement(pstat, datarr, 1);
                break;
            case 2:
                pstat = conn.prepareStatement("DELETE FROM `Cover's original music artist/band` WHERE song=?;");
                PreparedStatement(pstat, datarr, 1);
                try
                {
                    row = pstat.executeUpdate();
                }
                catch (Exception e)
                {
                    row = 0;
                }
                if (row == 0)
                    return "Your data hasn't been deleted";
                DELETE(1, datarr);
                return "Your data has been deleted";
        }
        try
        {
            row = pstat.executeUpdate();
        }
        catch (Exception e)
        {
            row = 0;
        }
        conn.close();

        if (row > 0)
            return "Your data has been deleted";
        else
            return "Your data hasn't been deleted";
    }

    public static String[][] SELECTMUSICARTISTBAND(int ID) throws Exception
    {
        PreparedStatement pstat = null;
        ResultSet resset;

        conn();
        String[][] datarr = new String[1][9];
        if (audiolibrary.tabl == 3)
            pstat = conn.prepareStatement("WITH musicartistband AS(WITH musicartistband AS(SELECT `Music artist/band`.artband, `Related music artist/band`.relartband, `Music artist/band`.genr, Song.nam, `Music artist/band`.nosongs, `Music artist/band`.songsdur, `Music artist/band`.totnosongs, `Music artist/band`.totsongsdur, `Music artist/band`.ID FROM `Music artist/band` LEFT JOIN `Related music artist/band` ON `Music artist/band`.ID=`Related music artist/band`.artband LEFT JOIN `Music artist/band's song` ON `Music artist/band`.ID=`Music artist/band's song`.artband LEFT JOIN Song ON `Music artist/band's song`.song=Song.ID WHERE `Music artist/band`.ID=?) SELECT artband, GROUP_CONCAT(DISTINCT relartband SEPARATOR ', ') AS relartband, genr, nam, nosongs, songsdur, totnosongs, totsongsdur, ID FROM musicartistband GROUP BY artband, genr, nam, nosongs, songsdur, totnosongs, totsongsdur, ID) SELECT artband, relartband, genr, GROUP_CONCAT(nam ORDER BY nam SEPARATOR ', '), nosongs, TRIM(LEADING '0' FROM TRIM(LEADING '00:' FROM REPLACE(songsdur, ':0', ':'))), totnosongs, TRIM(LEADING '0' FROM TRIM(LEADING '00:' FROM REPLACE(totsongsdur, ':0', ':'))), ID FROM musicartistband GROUP BY artband, relartband, genr, nosongs, songsdur, totnosongs, totsongsdur, ID ORDER BY artband;");
        else if (audiolibrary.tabl == 4 || audiolibrary.tabl == 5)
            pstat = conn.prepareStatement("WITH musicartistband AS(SELECT `Music artist/band`.artband, Song.nam, `Music artist/band`.nosongs, `Music artist/band`.songsdur, `Music artist/band`.ID FROM `Music artist/band` LEFT JOIN `Music artist/band's song` ON `Music artist/band`.ID=`Music artist/band's song`.artband LEFT JOIN Song ON `Music artist/band's song`.song=Song.ID WHERE `Music artist/band`.ID=?) SELECT artband, GROUP_CONCAT(nam ORDER BY nam SEPARATOR ', '), nosongs, TRIM(LEADING '0' FROM TRIM(LEADING '00:' FROM REPLACE(songsdur, ':0', ':'))), ID FROM musicartistband GROUP BY artband, nosongs, songsdur, ID ORDER BY artband;");
        pstat.setInt(1, ID);
        resset = pstat.executeQuery();
        while (resset.next())
        {
            String artband = resset.getString(1);
            String relartband = resset.getString(2);
            String genr = resset.getString(3);
            String songs = resset.getString(4);
            int nosongs = resset.getInt(5);
            datarr[0][0] = artband;
            datarr[0][1] = relartband;
            datarr[0][2] = genr;
            datarr[0][3] = songs;
            datarr[0][4] = String.valueOf(nosongs);
            if (audiolibrary.tabl == 3)
            {
                String songsdur = resset.getString(6);
                int totnosongs = resset.getInt(7);
                String totsongsdur = resset.getString(8);
                int id = resset.getInt(9);
                datarr[0][5] = songsdur;
                datarr[0][6] = String.valueOf(totnosongs);
                datarr[0][7] = totsongsdur;
                datarr[0][8] = String.valueOf(id);
            }
        }
        conn.close();

        return datarr;
    }

    public static String[][] SELECTCOVER(int ID) throws Exception
    {
        PreparedStatement pstat;
        ResultSet resset;

        conn();
        String[][] datarr = new String[1][7];
        pstat = conn.prepareStatement("WITH cover AS(WITH cover AS(SELECT Song.nam, `Music artist/band`.artband, `Music artist/band's song`.feat, Song.dur, Song.ID FROM `Music artist/band's song` JOIN `Music artist/band` ON `Music artist/band's song`.artband=`Music artist/band`.ID JOIN Song ON `Music artist/band's song`.song=Song.ID WHERE Song.cat='Каверы' UNION (SELECT Song.nam, `Related music artist/band`.relartband, `Music artist/band's song`.feat, Song.dur, Song.ID FROM `Music artist/band's song` JOIN `Related music artist/band` ON `Music artist/band's song`.artband=`Related music artist/band`.ID JOIN Song ON `Music artist/band's song`.song=Song.ID WHERE Song.cat='Каверы')) SELECT cover.nam, cover.artband, cover.feat, cover.dur, `Music artist/band`.artband AS origartband, `Cover's original music artist/band`.feat AS origfeat, cover.ID FROM cover JOIN `Cover's original music artist/band` ON cover.ID=`Cover's original music artist/band`.song JOIN `Music artist/band` ON `Cover's original music artist/band`.artband=`Music artist/band`.ID UNION (SELECT cover.nam, cover.artband, cover.feat, cover.dur, `Related music artist/band`.relartband, `Cover's original music artist/band`.feat, cover.ID FROM cover JOIN `Cover's original music artist/band` ON cover.ID=`Cover's original music artist/band`.song JOIN `Related music artist/band` ON `Cover's original music artist/band`.artband=`Related music artist/band`.ID)) SELECT nam, artband, feat, TRIM(LEADING '00:' FROM REPLACE(dur, ':0', ':')), GROUP_CONCAT(origartband SEPARATOR ', '), origfeat, ID FROM cover WHERE ID=? GROUP BY artband, nam, feat, dur, origfeat, ID;");
        pstat.setInt(1, ID);
        resset = pstat.executeQuery();
        while (resset.next())
        {
            String song = resset.getString(1);
            String artband = resset.getString(2);
            String feat = resset.getString(3);
            String dur = resset.getString(4);
            String origartband = resset.getString(5);
            String origfeat = resset.getString(6);
            int id = resset.getInt(7);
            datarr[0][0] = song;
            datarr[0][1] = artband;
            datarr[0][2] = feat;
            datarr[0][3] = dur;
            datarr[0][4] = origartband;
            datarr[0][5] = origfeat;
            datarr[0][6] = String.valueOf(id);
        }
        conn.close();

        return datarr;
    }

    public static String[][] SELECTSOUNDTRACK(int ID) throws Exception
    {
        PreparedStatement pstat;
        ResultSet resset;

        conn();
        String[][] datarr = new String[1][6];
        pstat = conn.prepareStatement("SELECT movanimsergam, artband, song, nosongs, TRIM(LEADING '00:' FROM REGEXP_REPLACE(REGEXP_REPLACE(REPLACE(songsdur, ':0', ':'), '^01', '1'), '^02', '2')), ID FROM Soundtrack WHERE ID=? ORDER BY movanimsergam;");
        pstat.setInt(1, ID);
        resset = pstat.executeQuery();
        while (resset.next())
        {
            String movanimsergam = resset.getString(1);
            String artband = resset.getString(2);
            String song = resset.getString(3);
            int nosongs = resset.getInt(4);
            String songsdur = resset.getString(5);
            int id = resset.getInt(6);
            datarr[0][0] = movanimsergam;
            datarr[0][1] = artband;
            datarr[0][2] = song;
            datarr[0][3] = String.valueOf(nosongs);
            datarr[0][4] = songsdur;
            datarr[0][5] = String.valueOf(id);
        }
        conn.close();

        return datarr;
    }

    public static String[][] SELECTSEARCHING(int tabl, int obslist, String search) throws Exception
    {
        int count = 0;
        PreparedStatement pstat;
        ResultSet resset;

        conn();
        String[][] datarr = new String[0][0];
        switch (tabl)
        {
            case 3:
                if (obslist == 0)
                {
                    pstat = conn.prepareStatement("WITH musicartistband AS(WITH musicartistband AS((WITH musicartistband AS(WITH musicartistband AS(SELECT `Music artist/band`.artband, `Related music artist/band`.relartband, `Music artist/band`.genr, Song.nam, `Music artist/band`.nosongs, `Music artist/band`.songsdur, `Music artist/band`.totnosongs, `Music artist/band`.totsongsdur, `Music artist/band`.ID, `Related music artist/band`.ID AS relartbandid FROM `Music artist/band` LEFT JOIN `Related music artist/band` ON `Music artist/band`.ID=`Related music artist/band`.artband LEFT JOIN `Music artist/band's song` ON `Music artist/band`.ID=`Music artist/band's song`.artband LEFT JOIN Song ON `Music artist/band's song`.song=Song.ID WHERE `Music artist/band`.cat='Жанр' AND `Music artist/band`.artband LIKE ?) SELECT artband, GROUP_CONCAT(DISTINCT relartband ORDER BY relartbandid SEPARATOR ', ') AS relartband, genr, nam, nosongs, songsdur, totnosongs, totsongsdur, ID FROM musicartistband GROUP BY artband, genr, nam, nosongs, songsdur, totnosongs, totsongsdur, ID) SELECT artband, relartband, genr, GROUP_CONCAT(nam ORDER BY nam SEPARATOR ', ') AS nam, nosongs, TRIM(LEADING '0' FROM TRIM(LEADING '00:' FROM REPLACE(songsdur, ':0', ':'))) AS songsdur, totnosongs, TRIM(LEADING '0' FROM TRIM(LEADING '00:' FROM REPLACE(totsongsdur, ':0', ':'))) AS totsongsdur, ID FROM musicartistband GROUP BY artband, relartband, genr, nosongs, songsdur, totnosongs, totsongsdur, ID ORDER BY artband) UNION (WITH musicartistband AS(WITH musicartistband AS(SELECT `Music artist/band`.artband, `Related music artist/band`.relartband, `Music artist/band`.genr, Song.nam, `Music artist/band`.nosongs, `Music artist/band`.songsdur, `Music artist/band`.totnosongs, `Music artist/band`.totsongsdur, `Music artist/band`.ID, `Related music artist/band`.ID AS relartbandid FROM `Music artist/band` LEFT JOIN `Related music artist/band` ON `Music artist/band`.ID=`Related music artist/band`.artband LEFT JOIN `Music artist/band's song` ON `Music artist/band`.ID=`Music artist/band's song`.artband LEFT JOIN Song ON `Music artist/band's song`.song=Song.ID WHERE `Music artist/band`.cat='Жанр' AND `Related music artist/band`.relartband LIKE ?) SELECT artband, GROUP_CONCAT(DISTINCT relartband ORDER BY relartbandid SEPARATOR ', ') AS relartband, genr, nam, nosongs, songsdur, totnosongs, totsongsdur, ID FROM musicartistband GROUP BY artband, genr, nam, nosongs, songsdur, totnosongs, totsongsdur, ID) SELECT artband, relartband, genr, GROUP_CONCAT(nam ORDER BY nam SEPARATOR ', ') AS nam, nosongs, TRIM(LEADING '0' FROM TRIM(LEADING '00:' FROM REPLACE(songsdur, ':0', ':'))) AS songsdur, totnosongs, TRIM(LEADING '0' FROM TRIM(LEADING '00:' FROM REPLACE(totsongsdur, ':0', ':'))) AS totsongsdur, ID FROM musicartistband GROUP BY artband, relartband, genr, nosongs, songsdur, totnosongs, totsongsdur, ID ORDER BY artband)) SELECT artband, relartband, genr, nam, nosongs, songsdur, totnosongs, totsongsdur, ID FROM musicartistband ORDER BY artband) SELECT COUNT(ID) FROM musicartistband");
                    pstat.setString(1, search);
                    pstat.setString(2, search);
                    resset = pstat.executeQuery();
                    while (resset.next())
                        count = resset.getInt("COUNT(ID)");
                    datarr = new String[count][9];
                    pstat = conn.prepareStatement("WITH musicartistband AS((WITH musicartistband AS(WITH musicartistband AS(SELECT `Music artist/band`.artband, `Related music artist/band`.relartband, `Music artist/band`.genr, Song.nam, `Music artist/band`.nosongs, `Music artist/band`.songsdur, `Music artist/band`.totnosongs, `Music artist/band`.totsongsdur, `Music artist/band`.ID, `Related music artist/band`.ID AS relartbandid FROM `Music artist/band` LEFT JOIN `Related music artist/band` ON `Music artist/band`.ID=`Related music artist/band`.artband LEFT JOIN `Music artist/band's song` ON `Music artist/band`.ID=`Music artist/band's song`.artband LEFT JOIN Song ON `Music artist/band's song`.song=Song.ID WHERE `Music artist/band`.cat='Жанр' AND `Music artist/band`.artband LIKE ?) SELECT artband, GROUP_CONCAT(DISTINCT relartband ORDER BY relartbandid SEPARATOR ', ') AS relartband, genr, nam, nosongs, songsdur, totnosongs, totsongsdur, ID FROM musicartistband GROUP BY artband, genr, nam, nosongs, songsdur, totnosongs, totsongsdur, ID) SELECT artband, relartband, genr, GROUP_CONCAT(nam ORDER BY nam SEPARATOR ', ') AS nam, nosongs, TRIM(LEADING '0' FROM TRIM(LEADING '00:' FROM REPLACE(songsdur, ':0', ':'))) AS songsdur, totnosongs, TRIM(LEADING '0' FROM TRIM(LEADING '00:' FROM REPLACE(totsongsdur, ':0', ':'))) AS totsongsdur, ID FROM musicartistband GROUP BY artband, relartband, genr, nosongs, songsdur, totnosongs, totsongsdur, ID ORDER BY artband) UNION (WITH musicartistband AS(WITH musicartistband AS(SELECT `Music artist/band`.artband, `Related music artist/band`.relartband, `Music artist/band`.genr, Song.nam, `Music artist/band`.nosongs, `Music artist/band`.songsdur, `Music artist/band`.totnosongs, `Music artist/band`.totsongsdur, `Music artist/band`.ID, `Related music artist/band`.ID AS relartbandid FROM `Music artist/band` LEFT JOIN `Related music artist/band` ON `Music artist/band`.ID=`Related music artist/band`.artband LEFT JOIN `Music artist/band's song` ON `Music artist/band`.ID=`Music artist/band's song`.artband LEFT JOIN Song ON `Music artist/band's song`.song=Song.ID WHERE `Music artist/band`.cat='Жанр' AND `Related music artist/band`.relartband LIKE ?) SELECT artband, GROUP_CONCAT(DISTINCT relartband ORDER BY relartbandid SEPARATOR ', ') AS relartband, genr, nam, nosongs, songsdur, totnosongs, totsongsdur, ID FROM musicartistband GROUP BY artband, genr, nam, nosongs, songsdur, totnosongs, totsongsdur, ID) SELECT artband, relartband, genr, GROUP_CONCAT(nam ORDER BY nam SEPARATOR ', ') AS nam, nosongs, TRIM(LEADING '0' FROM TRIM(LEADING '00:' FROM REPLACE(songsdur, ':0', ':'))) AS songsdur, totnosongs, TRIM(LEADING '0' FROM TRIM(LEADING '00:' FROM REPLACE(totsongsdur, ':0', ':'))) AS totsongsdur, ID FROM musicartistband GROUP BY artband, relartband, genr, nosongs, songsdur, totnosongs, totsongsdur, ID ORDER BY artband)) SELECT artband, relartband, genr, nam, nosongs, songsdur, totnosongs, totsongsdur, ID FROM musicartistband ORDER BY artband;");
                    pstat.setString(1, search);
                    pstat.setString(2, search);
                    resset = pstat.executeQuery();
                    count = 0;
                    while (resset.next())
                    {
                        String artband = resset.getString(1);
                        String relartband = resset.getString(2);
                        String genr = resset.getString(3);
                        String songs = resset.getString(4);
                        int nosongs = resset.getInt(5);
                        String songsdur = resset.getString(6);
                        if (songsdur.equals(":0"))
                            songsdur = "0:0";
                        int totnosongs = resset.getInt(7);
                        String totsongsdur = resset.getString(8);
                        if (totsongsdur.equals(":0"))
                            totsongsdur = "0:0";
                        int id = resset.getInt(9);
                        datarr[count][0] = artband;
                        datarr[count][1] = relartband;
                        datarr[count][2] = genr;
                        datarr[count][3] = songs;
                        datarr[count][4] = String.valueOf(nosongs);
                        datarr[count][5] = songsdur;
                        datarr[count][6] = String.valueOf(totnosongs);
                        datarr[count][7] = totsongsdur;
                        datarr[count][8] = String.valueOf(id);
                        count += 1;
                    }
                }
                else if (obslist == 1 || obslist == 2)
                {
                    if (obslist == 1)
                    {
                        pstat = conn.prepareStatement("WITH song AS(SELECT Song.nam, TRIM(LEADING '00:' FROM REPLACE(Song.dur, ':0', ':')), `Music artist/band's song`.feat, Song.ID FROM `Music artist/band's song` JOIN `Music artist/band` ON `Music artist/band's song`.artband=`Music artist/band`.ID JOIN Song ON `Music artist/band's song`.song=Song.ID WHERE `Music artist/band's song`.artband=? AND Song.cat IS NULL AND Song.nam LIKE ? ORDER BY Song.nam) SELECT COUNT(ID) FROM song;");
                        pstat.setInt(1, audiolibrary.MusicArtistBandID());
                    }
                    else
                    {
                        pstat = conn.prepareStatement("WITH song AS(SELECT Song.nam, TRIM(LEADING '00:' FROM REPLACE(Song.dur, ':0', ':')), `Music artist/band's song`.feat, Song.ID FROM `Music artist/band's song` JOIN `Related music artist/band` ON `Music artist/band's song`.artband=`Related music artist/band`.ID JOIN Song ON `Music artist/band's song`.song=Song.ID WHERE `Music artist/band's song`.artband=? AND Song.cat IS NULL AND Song.nam LIKE ? ORDER BY Song.nam) SELECT COUNT(ID) FROM song;");
                        pstat.setInt(1, audiolibrary.RelatedMusicArtistBandID());
                    }
                    pstat.setString(2, search);
                    resset = pstat.executeQuery();
                    while (resset.next())
                        count = resset.getInt("COUNT(ID)");
                    datarr = new String[count][4];
                    if (obslist == 1)
                    {
                        pstat = conn.prepareStatement("SELECT Song.nam, TRIM(LEADING '00:' FROM REPLACE(Song.dur, ':0', ':')), `Music artist/band's song`.feat, Song.ID FROM `Music artist/band's song` JOIN `Music artist/band` ON `Music artist/band's song`.artband=`Music artist/band`.ID JOIN Song ON `Music artist/band's song`.song=Song.ID WHERE `Music artist/band's song`.artband=? AND Song.cat IS NULL AND Song.nam LIKE ? ORDER BY Song.nam;");
                        pstat.setInt(1, audiolibrary.MusicArtistBandID());
                    }
                    else
                    {
                        pstat = conn.prepareStatement("SELECT Song.nam, TRIM(LEADING '00:' FROM REPLACE(Song.dur, ':0', ':')), `Music artist/band's song`.feat, Song.ID FROM `Music artist/band's song` JOIN `Related music artist/band` ON `Music artist/band's song`.artband=`Related music artist/band`.ID JOIN Song ON `Music artist/band's song`.song=Song.ID WHERE `Music artist/band's song`.artband=? AND Song.cat IS NULL AND Song.nam LIKE ? ORDER BY Song.nam;");
                        pstat.setInt(1, audiolibrary.RelatedMusicArtistBandID());
                    }
                    pstat.setString(2, search);
                    resset = pstat.executeQuery();
                    count = 0;
                    while (resset.next())
                    {
                        String nam = resset.getString(1);
                        String dur = resset.getString(2);
                        String feat = resset.getString(3);
                        int id = resset.getInt(4);
                        datarr[count][0] = nam;
                        datarr[count][1] = dur;
                        datarr[count][2] = feat;
                        datarr[count][3] = String.valueOf(id);
                        count += 1;
                    }
                }
                break;
            case 4:
            case 5:
                if (obslist == 0)
                {
                    if (tabl == 4)
                        pstat = conn.prepareStatement("SELECT COUNT(ID) FROM `Music artist/band` WHERE cat='Композиторы' AND artband LIKE ?;");
                    else
                        pstat = conn.prepareStatement("SELECT COUNT(ID) FROM `Music artist/band` WHERE cat='Блогеры' AND artband LIKE ?;");
                    pstat.setString(1, search);
                    resset = pstat.executeQuery();
                    while (resset.next())
                        count = resset.getInt("COUNT(ID)");
                    datarr = new String[count][5];
                    if (tabl == 4)
                        pstat = conn.prepareStatement("WITH composer AS(SELECT `Music artist/band`.artband, Song.nam, `Music artist/band`.nosongs, `Music artist/band`.songsdur, `Music artist/band`.ID FROM `Music artist/band` LEFT JOIN `Music artist/band's song` ON `Music artist/band`.ID=`Music artist/band's song`.artband LEFT JOIN Song ON `Music artist/band's song`.song=Song.ID WHERE `Music artist/band`.cat='Композиторы' AND `Music artist/band`.artband LIKE ?) SELECT artband, GROUP_CONCAT(nam ORDER BY nam SEPARATOR ', '), nosongs, TRIM(LEADING '0' FROM TRIM(LEADING '00:' FROM REPLACE(songsdur, ':0', ':'))), ID FROM composer GROUP BY artband, nosongs, songsdur, ID ORDER BY artband;");
                    else
                        pstat = conn.prepareStatement("WITH blogger AS(SELECT `Music artist/band`.artband, Song.nam, `Music artist/band`.nosongs, `Music artist/band`.songsdur, `Music artist/band`.ID FROM `Music artist/band` LEFT JOIN `Music artist/band's song` ON `Music artist/band`.ID=`Music artist/band's song`.artband LEFT JOIN Song ON `Music artist/band's song`.song=Song.ID WHERE `Music artist/band`.cat='Блогеры' AND `Music artist/band`.artband LIKE ?) SELECT artband, GROUP_CONCAT(nam ORDER BY nam SEPARATOR ', '), nosongs, TRIM(LEADING '0' FROM TRIM(LEADING '00:' FROM REPLACE(songsdur, ':0', ':'))), ID FROM blogger GROUP BY artband, nosongs, songsdur, ID ORDER BY artband;");
                    pstat.setString(1, search);
                    resset = pstat.executeQuery();
                    count = 0;
                    while (resset.next())
                    {
                        String nam = resset.getString(1);
                        String songs = resset.getString(2);
                        int nosongs = resset.getInt(3);
                        String songsdur = resset.getString(4);
                        if (songsdur.equals(":0"))
                            songsdur = "0:0";
                        int id = resset.getInt(5);
                        datarr[count][0] = nam;
                        datarr[count][1] = songs;
                        datarr[count][2] = String.valueOf(nosongs);
                        datarr[count][3] = songsdur;
                        datarr[count][4] = String.valueOf(id);
                        count += 1;
                    }
                }
                else if (obslist == 1)
                {
                    pstat = conn.prepareStatement("WITH song AS(SELECT Song.nam, TRIM(LEADING '00:' FROM REPLACE(Song.dur, ':0', ':')), `Music artist/band's song`.feat, Song.ID FROM `Music artist/band's song` JOIN `Music artist/band` ON `Music artist/band's song`.artband=`Music artist/band`.ID JOIN Song ON `Music artist/band's song`.song=Song.ID WHERE `Music artist/band's song`.artband=? AND Song.cat IS NULL AND Song.nam LIKE ? ORDER BY Song.nam) SELECT COUNT(ID) FROM song;");
                    pstat.setInt(1, audiolibrary.MusicArtistBandID());
                    pstat.setString(2, search);
                    resset = pstat.executeQuery();
                    while (resset.next())
                        count = resset.getInt("COUNT(ID)");
                    datarr = new String[count][4];
                    pstat = conn.prepareStatement("SELECT Song.nam, TRIM(LEADING '00:' FROM REPLACE(Song.dur, ':0', ':')), `Music artist/band's song`.feat, Song.ID FROM `Music artist/band's song` JOIN `Music artist/band` ON `Music artist/band's song`.artband=`Music artist/band`.ID JOIN Song ON `Music artist/band's song`.song=Song.ID WHERE `Music artist/band's song`.artband=? AND Song.cat IS NULL AND Song.nam LIKE ? ORDER BY Song.nam;");
                    pstat.setInt(1, audiolibrary.MusicArtistBandID());
                    pstat.setString(2, search);
                    resset = pstat.executeQuery();
                    count = 0;
                    while (resset.next())
                    {
                        String nam = resset.getString(1);
                        String dur = resset.getString(2);
                        String feat = resset.getString(3);
                        int id = resset.getInt(4);
                        datarr[count][0] = nam;
                        datarr[count][1] = dur;
                        datarr[count][2] = feat;
                        datarr[count][3] = String.valueOf(id);
                        count += 1;
                    }
                }
                break;
            case 6:
                pstat = conn.prepareStatement("WITH cover AS(WITH cover AS((WITH cover AS(WITH cover AS(SELECT Song.nam, `Music artist/band`.artband, `Music artist/band's song`.feat, Song.dur, Song.ID FROM `Music artist/band's song` JOIN `Music artist/band` ON `Music artist/band's song`.artband=`Music artist/band`.ID JOIN Song ON `Music artist/band's song`.song=Song.ID WHERE Song.cat='Каверы' AND Song.nam LIKE ? UNION (SELECT Song.nam, `Related music artist/band`.relartband, `Music artist/band's song`.feat, Song.dur, Song.ID FROM `Music artist/band's song` JOIN `Related music artist/band` ON `Music artist/band's song`.artband=`Related music artist/band`.ID JOIN Song ON `Music artist/band's song`.song=Song.ID WHERE Song.cat='Каверы' AND Song.nam LIKE ?)) SELECT cover.nam, cover.artband, cover.feat, cover.dur, `Music artist/band`.artband AS origartband, `Cover's original music artist/band`.feat AS origfeat, cover.ID FROM cover LEFT JOIN `Cover's original music artist/band` ON cover.ID=`Cover's original music artist/band`.song LEFT JOIN `Music artist/band` ON `Cover's original music artist/band`.artband=`Music artist/band`.ID UNION (SELECT cover.nam, cover.artband, cover.feat, cover.dur, `Related music artist/band`.relartband, `Cover's original music artist/band`.feat, cover.ID FROM cover LEFT JOIN `Cover's original music artist/band` ON cover.ID=`Cover's original music artist/band`.song LEFT JOIN `Related music artist/band` ON `Cover's original music artist/band`.artband=`Related music artist/band`.ID)) SELECT nam, artband, feat, TRIM(LEADING '00:' FROM REPLACE(dur, ':0', ':')) AS dur, GROUP_CONCAT(origartband SEPARATOR ', ') AS origartband, GROUP_CONCAT(DISTINCT origfeat SEPARATOR '') AS origfeat, ID FROM cover GROUP BY artband, nam, feat, dur, ID ORDER BY nam, artband) UNION (WITH cover AS(WITH cover AS(SELECT Song.nam, `Music artist/band`.artband, `Music artist/band's song`.feat, Song.dur, Song.ID FROM `Music artist/band's song` JOIN `Music artist/band` ON `Music artist/band's song`.artband=`Music artist/band`.ID JOIN Song ON `Music artist/band's song`.song=Song.ID WHERE Song.cat='Каверы' AND `Music artist/band`.artband LIKE ? UNION (SELECT Song.nam, `Related music artist/band`.relartband, `Music artist/band's song`.feat, Song.dur, Song.ID FROM `Music artist/band's song` JOIN `Related music artist/band` ON `Music artist/band's song`.artband=`Related music artist/band`.ID JOIN Song ON `Music artist/band's song`.song=Song.ID WHERE Song.cat='Каверы' AND `Related music artist/band`.relartband LIKE ?)) SELECT cover.nam, cover.artband, cover.feat, cover.dur, `Music artist/band`.artband AS origartband, `Cover's original music artist/band`.feat AS origfeat, cover.ID FROM cover LEFT JOIN `Cover's original music artist/band` ON cover.ID=`Cover's original music artist/band`.song LEFT JOIN `Music artist/band` ON `Cover's original music artist/band`.artband=`Music artist/band`.ID UNION (SELECT cover.nam, cover.artband, cover.feat, cover.dur, `Related music artist/band`.relartband, `Cover's original music artist/band`.feat, cover.ID FROM cover LEFT JOIN `Cover's original music artist/band` ON cover.ID=`Cover's original music artist/band`.song LEFT JOIN `Related music artist/band` ON `Cover's original music artist/band`.artband=`Related music artist/band`.ID)) SELECT nam, artband, feat, TRIM(LEADING '00:' FROM REPLACE(dur, ':0', ':')) AS dur, GROUP_CONCAT(origartband SEPARATOR ', ') AS origartband, GROUP_CONCAT(DISTINCT origfeat SEPARATOR '') AS origfeat, ID FROM cover GROUP BY artband, nam, feat, dur, ID ORDER BY nam, artband) UNION (WITH cover AS(WITH cover AS(SELECT Song.nam, `Music artist/band`.artband, `Music artist/band's song`.feat, Song.dur, Song.ID FROM `Music artist/band's song` JOIN `Music artist/band` ON `Music artist/band's song`.artband=`Music artist/band`.ID JOIN Song ON `Music artist/band's song`.song=Song.ID WHERE Song.cat='Каверы' UNION (SELECT Song.nam, `Related music artist/band`.relartband, `Music artist/band's song`.feat, Song.dur, Song.ID FROM `Music artist/band's song` JOIN `Related music artist/band` ON `Music artist/band's song`.artband=`Related music artist/band`.ID JOIN Song ON `Music artist/band's song`.song=Song.ID WHERE Song.cat='Каверы')) SELECT cover.nam, cover.artband, cover.feat, cover.dur, `Music artist/band`.artband AS origartband, `Cover's original music artist/band`.feat AS origfeat, cover.ID FROM cover LEFT JOIN `Cover's original music artist/band` ON cover.ID=`Cover's original music artist/band`.song LEFT JOIN `Music artist/band` ON `Cover's original music artist/band`.artband=`Music artist/band`.ID WHERE `Music artist/band`.artband LIKE ? UNION (SELECT cover.nam, cover.artband, cover.feat, cover.dur, `Related music artist/band`.relartband, `Cover's original music artist/band`.feat, cover.ID FROM cover LEFT JOIN `Cover's original music artist/band` ON cover.ID=`Cover's original music artist/band`.song LEFT JOIN `Related music artist/band` ON `Cover's original music artist/band`.artband=`Related music artist/band`.ID WHERE `Related music artist/band`.relartband LIKE ?)) SELECT nam, artband, feat, TRIM(LEADING '00:' FROM REPLACE(dur, ':0', ':')) AS dur, GROUP_CONCAT(origartband SEPARATOR ', ') AS origartband, GROUP_CONCAT(DISTINCT origfeat SEPARATOR '') AS origfeat, ID FROM cover GROUP BY artband, nam, feat, dur, ID ORDER BY nam, artband)) SELECT nam, artband, feat, dur, origartband, origfeat, ID FROM cover ORDER BY nam) SELECT COUNT(ID) FROM cover;");
                pstat.setString(1, search);
                pstat.setString(2, search);
                pstat.setString(3, search);
                pstat.setString(4, search);
                pstat.setString(5, search);
                pstat.setString(6, search);
                resset = pstat.executeQuery();
                while (resset.next())
                    count = resset.getInt("COUNT(ID)");
                datarr = new String[count][7];
                pstat = conn.prepareStatement("WITH cover AS((WITH cover AS(WITH cover AS(SELECT Song.nam, `Music artist/band`.artband, `Music artist/band's song`.feat, Song.dur, Song.ID FROM `Music artist/band's song` JOIN `Music artist/band` ON `Music artist/band's song`.artband=`Music artist/band`.ID JOIN Song ON `Music artist/band's song`.song=Song.ID WHERE Song.cat='Каверы' AND Song.nam LIKE ? UNION (SELECT Song.nam, `Related music artist/band`.relartband, `Music artist/band's song`.feat, Song.dur, Song.ID FROM `Music artist/band's song` JOIN `Related music artist/band` ON `Music artist/band's song`.artband=`Related music artist/band`.ID JOIN Song ON `Music artist/band's song`.song=Song.ID WHERE Song.cat='Каверы' AND Song.nam LIKE ?)) SELECT cover.nam, cover.artband, cover.feat, cover.dur, `Music artist/band`.artband AS origartband, `Cover's original music artist/band`.feat AS origfeat, cover.ID FROM cover LEFT JOIN `Cover's original music artist/band` ON cover.ID=`Cover's original music artist/band`.song LEFT JOIN `Music artist/band` ON `Cover's original music artist/band`.artband=`Music artist/band`.ID UNION (SELECT cover.nam, cover.artband, cover.feat, cover.dur, `Related music artist/band`.relartband, `Cover's original music artist/band`.feat, cover.ID FROM cover LEFT JOIN `Cover's original music artist/band` ON cover.ID=`Cover's original music artist/band`.song LEFT JOIN `Related music artist/band` ON `Cover's original music artist/band`.artband=`Related music artist/band`.ID)) SELECT nam, artband, feat, TRIM(LEADING '00:' FROM REPLACE(dur, ':0', ':')) AS dur, GROUP_CONCAT(origartband SEPARATOR ', ') AS origartband, GROUP_CONCAT(DISTINCT origfeat SEPARATOR '') AS origfeat, ID FROM cover GROUP BY artband, nam, feat, dur, ID ORDER BY nam, artband) UNION (WITH cover AS(WITH cover AS(SELECT Song.nam, `Music artist/band`.artband, `Music artist/band's song`.feat, Song.dur, Song.ID FROM `Music artist/band's song` JOIN `Music artist/band` ON `Music artist/band's song`.artband=`Music artist/band`.ID JOIN Song ON `Music artist/band's song`.song=Song.ID WHERE Song.cat='Каверы' AND `Music artist/band`.artband LIKE ? UNION (SELECT Song.nam, `Related music artist/band`.relartband, `Music artist/band's song`.feat, Song.dur, Song.ID FROM `Music artist/band's song` JOIN `Related music artist/band` ON `Music artist/band's song`.artband=`Related music artist/band`.ID JOIN Song ON `Music artist/band's song`.song=Song.ID WHERE Song.cat='Каверы' AND `Related music artist/band`.relartband LIKE ?)) SELECT cover.nam, cover.artband, cover.feat, cover.dur, `Music artist/band`.artband AS origartband, `Cover's original music artist/band`.feat AS origfeat, cover.ID FROM cover LEFT JOIN `Cover's original music artist/band` ON cover.ID=`Cover's original music artist/band`.song LEFT JOIN `Music artist/band` ON `Cover's original music artist/band`.artband=`Music artist/band`.ID UNION (SELECT cover.nam, cover.artband, cover.feat, cover.dur, `Related music artist/band`.relartband, `Cover's original music artist/band`.feat, cover.ID FROM cover LEFT JOIN `Cover's original music artist/band` ON cover.ID=`Cover's original music artist/band`.song LEFT JOIN `Related music artist/band` ON `Cover's original music artist/band`.artband=`Related music artist/band`.ID)) SELECT nam, artband, feat, TRIM(LEADING '00:' FROM REPLACE(dur, ':0', ':')) AS dur, GROUP_CONCAT(origartband SEPARATOR ', ') AS origartband, GROUP_CONCAT(DISTINCT origfeat SEPARATOR '') AS origfeat, ID FROM cover GROUP BY artband, nam, feat, dur, ID ORDER BY nam, artband) UNION (WITH cover AS(WITH cover AS(SELECT Song.nam, `Music artist/band`.artband, `Music artist/band's song`.feat, Song.dur, Song.ID FROM `Music artist/band's song` JOIN `Music artist/band` ON `Music artist/band's song`.artband=`Music artist/band`.ID JOIN Song ON `Music artist/band's song`.song=Song.ID WHERE Song.cat='Каверы' UNION (SELECT Song.nam, `Related music artist/band`.relartband, `Music artist/band's song`.feat, Song.dur, Song.ID FROM `Music artist/band's song` JOIN `Related music artist/band` ON `Music artist/band's song`.artband=`Related music artist/band`.ID JOIN Song ON `Music artist/band's song`.song=Song.ID WHERE Song.cat='Каверы')) SELECT cover.nam, cover.artband, cover.feat, cover.dur, `Music artist/band`.artband AS origartband, `Cover's original music artist/band`.feat AS origfeat, cover.ID FROM cover LEFT JOIN `Cover's original music artist/band` ON cover.ID=`Cover's original music artist/band`.song LEFT JOIN `Music artist/band` ON `Cover's original music artist/band`.artband=`Music artist/band`.ID WHERE `Music artist/band`.artband LIKE ? UNION (SELECT cover.nam, cover.artband, cover.feat, cover.dur, `Related music artist/band`.relartband, `Cover's original music artist/band`.feat, cover.ID FROM cover LEFT JOIN `Cover's original music artist/band` ON cover.ID=`Cover's original music artist/band`.song LEFT JOIN `Related music artist/band` ON `Cover's original music artist/band`.artband=`Related music artist/band`.ID WHERE `Related music artist/band`.relartband LIKE ?)) SELECT nam, artband, feat, TRIM(LEADING '00:' FROM REPLACE(dur, ':0', ':')) AS dur, GROUP_CONCAT(origartband SEPARATOR ', ') AS origartband, GROUP_CONCAT(DISTINCT origfeat SEPARATOR '') AS origfeat, ID FROM cover GROUP BY artband, nam, feat, dur, ID ORDER BY nam, artband)) SELECT nam, artband, feat, dur, origartband, origfeat, ID FROM cover ORDER BY nam;");
                pstat.setString(1, search);
                pstat.setString(2, search);
                pstat.setString(3, search);
                pstat.setString(4, search);
                pstat.setString(5, search);
                pstat.setString(6, search);
                resset = pstat.executeQuery();
                count = 0;
                while (resset.next())
                {
                    String song = resset.getString(1);
                    String artband = resset.getString(2);
                    String feat = resset.getString(3);
                    String dur = resset.getString(4);
                    String origartband = resset.getString(5);
                    String origfeat = resset.getString(6);
                    int id = resset.getInt(7);
                    datarr[count][0] = song;
                    datarr[count][1] = artband;
                    datarr[count][2] = feat;
                    datarr[count][3] = dur;
                    datarr[count][4] = origartband;
                    datarr[count][5] = origfeat;
                    datarr[count][6] = String.valueOf(id);
                    count += 1;
                }
                break;
            case 7:
                pstat = conn.prepareStatement("WITH soundtrack AS(WITH soundtrack AS((SELECT movanimsergam, artband, song, nosongs, TRIM(LEADING '00:' FROM REGEXP_REPLACE(REGEXP_REPLACE(REPLACE(songsdur, ':0', ':'), '^01', '1'), '^02', '2')) AS songsdur, ID FROM Soundtrack WHERE movanimsergam LIKE ? ORDER BY movanimsergam) UNION (SELECT movanimsergam, artband, song, nosongs, TRIM(LEADING '00:' FROM REGEXP_REPLACE(REGEXP_REPLACE(REPLACE(songsdur, ':0', ':'), '^01', '1'), '^02', '2')) AS songsdur, ID FROM Soundtrack WHERE artband LIKE ? ORDER BY movanimsergam) UNION (SELECT movanimsergam, artband, song, nosongs, TRIM(LEADING '00:' FROM REGEXP_REPLACE(REGEXP_REPLACE(REPLACE(songsdur, ':0', ':'), '^01', '1'), '^02', '2')) AS songsdur, ID FROM Soundtrack WHERE song LIKE ? ORDER BY movanimsergam)) SELECT movanimsergam, artband, song, nosongs, songsdur, ID FROM soundtrack ORDER BY movanimsergam) SELECT COUNT(ID) FROM soundtrack;");
                pstat.setString(1, search);
                pstat.setString(2, search);
                pstat.setString(3, search);
                resset = pstat.executeQuery();
                while (resset.next())
                    count = resset.getInt("COUNT(ID)");
                datarr = new String[count][6];
                pstat = conn.prepareStatement("WITH soundtrack AS((SELECT movanimsergam, artband, song, nosongs, TRIM(LEADING '00:' FROM REGEXP_REPLACE(REGEXP_REPLACE(REPLACE(songsdur, ':0', ':'), '^01', '1'), '^02', '2')) AS songsdur, ID FROM Soundtrack WHERE movanimsergam LIKE ? ORDER BY movanimsergam) UNION (SELECT movanimsergam, artband, song, nosongs, TRIM(LEADING '00:' FROM REGEXP_REPLACE(REGEXP_REPLACE(REPLACE(songsdur, ':0', ':'), '^01', '1'), '^02', '2')) AS songsdur, ID FROM Soundtrack WHERE artband LIKE ? ORDER BY movanimsergam) UNION (SELECT movanimsergam, artband, song, nosongs, TRIM(LEADING '00:' FROM REGEXP_REPLACE(REGEXP_REPLACE(REPLACE(songsdur, ':0', ':'), '^01', '1'), '^02', '2')) AS songsdur, ID FROM Soundtrack WHERE song LIKE ? ORDER BY movanimsergam)) SELECT movanimsergam, artband, song, nosongs, songsdur, ID FROM soundtrack ORDER BY movanimsergam;");
                pstat.setString(1, search);
                pstat.setString(2, search);
                pstat.setString(3, search);
                resset = pstat.executeQuery();
                count = 0;
                while (resset.next())
                {
                    String movanimsergam = resset.getString(1);
                    String artband = resset.getString(2);
                    String song = resset.getString(3);
                    int nosongs = resset.getInt(4);
                    String songsdur = resset.getString(5);
                    int id = resset.getInt(6);
                    datarr[count][0] = movanimsergam;
                    datarr[count][1] = artband;
                    datarr[count][2] = song;
                    datarr[count][3] = String.valueOf(nosongs);
                    datarr[count][4] = songsdur;
                    datarr[count][5] = String.valueOf(id);
                    count += 1;
                }
                break;
        }
        conn.close();

        return datarr;
    }

    public static String[][] SELECTFILTERING(int tabl) throws Exception
    {
        int count = 0;
        PreparedStatement pstat;
        ResultSet resset;

        conn();
        String[][] datarr = new String[0][0];
        switch (tabl)
        {
            case 3:
                pstat = conn.prepareStatement("WITH musicartistband AS(WITH musicartistband AS(WITH musicartistband AS(SELECT `Music artist/band`.artband, `Related music artist/band`.relartband, `Music artist/band`.genr, Song.nam, `Music artist/band`.nosongs, `Music artist/band`.songsdur, `Music artist/band`.totnosongs, `Music artist/band`.totsongsdur, `Music artist/band`.ID, `Related music artist/band`.ID AS relartbandid FROM `Music artist/band` LEFT JOIN `Related music artist/band` ON `Music artist/band`.ID=`Related music artist/band`.artband LEFT JOIN `Music artist/band's song` ON `Music artist/band`.ID=`Music artist/band's song`.artband LEFT JOIN Song ON `Music artist/band's song`.song=Song.ID WHERE `Music artist/band`.cat='Жанр' AND (`Music artist/band`.genr LIKE ? AND `Music artist/band`.genr IS NOT ?) AND IFNULL(`Related music artist/band`.relartband, '') LIKE ? AND `Music artist/band`.totnosongs BETWEEN ? AND ? AND `Music artist/band`.totsongsdur BETWEEN CAST(? AS TIME) AND CAST(? AS TIME)) SELECT artband, GROUP_CONCAT(DISTINCT relartband ORDER BY relartbandid SEPARATOR ', ') AS relartband, genr, nam, nosongs, songsdur, totnosongs, totsongsdur, ID FROM musicartistband GROUP BY artband, genr, nam, nosongs, songsdur, totnosongs, totsongsdur, ID) SELECT artband, relartband, genr, GROUP_CONCAT(nam ORDER BY nam SEPARATOR ', '), nosongs, TRIM(LEADING '0' FROM TRIM(LEADING '00:' FROM REPLACE(songsdur, ':0', ':'))), totnosongs, TRIM(LEADING '0' FROM TRIM(LEADING '00:' FROM REPLACE(totsongsdur, ':0', ':'))), ID FROM musicartistband GROUP BY artband, relartband, genr, nosongs, songsdur, totnosongs, totsongsdur, ID ORDER BY artband) SELECT COUNT(ID) FROM musicartistband;");
                if (audiolibrary.combobox1.getSelectionModel().isEmpty())
                {
                    pstat.setString(1, "%");
                    pstat.setString(2, null);
                }
                else
                {
                    pstat.setString(1, String.valueOf(audiolibrary.combobox1.getValue()));
                    pstat.setString(2, null);
                }
                if (audiolibrary.combobox2.getSelectionModel().isEmpty())
                    pstat.setString(3, "%");
                else if (audiolibrary.combobox2.getValue().equals("With related artist(-s)/band(-s)"))
                    pstat.setString(3, "%_%");
                else if (audiolibrary.combobox2.getValue().equals("Without related artist(-s)/band(-s)"))
                    pstat.setString(3, "");
                if (audiolibrary.combobox3.getSelectionModel().isEmpty())
                {
                    pstat.setString(4, "1");
                    pstat.setString(5, "999");
                }
                else if (audiolibrary.combobox3.getValue().equals("<10"))
                {
                    pstat.setString(4, "1");
                    pstat.setString(5, "9");
                }
                else if (audiolibrary.combobox3.getValue().equals("15"))
                {
                    pstat.setString(4, "15");
                    pstat.setString(5, "15");
                }
                else if (audiolibrary.combobox3.getValue().equals("25"))
                {
                    pstat.setString(4, "25");
                    pstat.setString(5, "25");
                }
                else if (audiolibrary.combobox3.getValue().equals("55"))
                {
                    pstat.setString(4, "55");
                    pstat.setString(5, "55");
                }
                else if (audiolibrary.combobox3.getValue().equals("70"))
                {
                    pstat.setString(4, "70");
                    pstat.setString(5, "70");
                }
                else if (audiolibrary.combobox3.getValue().equals(">70"))
                {
                    pstat.setString(4, "71");
                    pstat.setString(5, "999");
                }
                if (audiolibrary.combobox4.getSelectionModel().isEmpty())
                {
                    pstat.setString(6, "0:0:1");
                    pstat.setString(7, "71:59:59");
                }
                else if (audiolibrary.combobox4.getValue().equals("<45:00"))
                {
                    pstat.setString(6, "0:0:1");
                    pstat.setString(7, "0:44:59");
                }
                else if (audiolibrary.combobox4.getValue().equals(">=45:00 & <1:15:00"))
                {
                    pstat.setString(6, "0:45:0");
                    pstat.setString(7, "1:14:59");
                }
                else if (audiolibrary.combobox4.getValue().equals(">=1:15:00 & <2:5:00"))
                {
                    pstat.setString(6, "1:15:0");
                    pstat.setString(7, "2:4:59");
                }
                else if (audiolibrary.combobox4.getValue().equals(">=2:5:00"))
                {
                    pstat.setString(6, "2:5:00");
                    pstat.setString(7, "71:59:59");
                }
                resset = pstat.executeQuery();
                while (resset.next())
                    count = resset.getInt("COUNT(ID)");
                datarr = new String[count][9];

                pstat = conn.prepareStatement("WITH musicartistband AS(WITH musicartistband AS(SELECT `Music artist/band`.artband, `Related music artist/band`.relartband, `Music artist/band`.genr, Song.nam, `Music artist/band`.nosongs, `Music artist/band`.songsdur, `Music artist/band`.totnosongs, `Music artist/band`.totsongsdur, `Music artist/band`.ID, `Related music artist/band`.ID AS relartbandid FROM `Music artist/band` LEFT JOIN `Related music artist/band` ON `Music artist/band`.ID=`Related music artist/band`.artband LEFT JOIN `Music artist/band's song` ON `Music artist/band`.ID=`Music artist/band's song`.artband LEFT JOIN Song ON `Music artist/band's song`.song=Song.ID WHERE `Music artist/band`.cat='Жанр' AND (`Music artist/band`.genr LIKE ? AND `Music artist/band`.genr IS NOT ?) AND IFNULL(`Related music artist/band`.relartband, '') LIKE ? AND `Music artist/band`.totnosongs BETWEEN ? AND ? AND `Music artist/band`.totsongsdur BETWEEN CAST(? AS TIME) AND CAST(? AS TIME)) SELECT artband, GROUP_CONCAT(DISTINCT relartband ORDER BY relartbandid SEPARATOR ', ') AS relartband, genr, nam, nosongs, songsdur, totnosongs, totsongsdur, ID FROM musicartistband GROUP BY artband, genr, nam, nosongs, songsdur, totnosongs, totsongsdur, ID) SELECT artband, relartband, genr, GROUP_CONCAT(nam ORDER BY nam SEPARATOR ', '), nosongs, TRIM(LEADING '0' FROM TRIM(LEADING '00:' FROM REPLACE(songsdur, ':0', ':'))), totnosongs, TRIM(LEADING '0' FROM TRIM(LEADING '00:' FROM REPLACE(totsongsdur, ':0', ':'))), ID FROM musicartistband GROUP BY artband, relartband, genr, nosongs, songsdur, totnosongs, totsongsdur, ID ORDER BY artband;");
                if (audiolibrary.combobox1.getSelectionModel().isEmpty())
                {
                    pstat.setString(1, "%");
                    pstat.setString(2, null);
                }
                else
                {
                    pstat.setString(1, String.valueOf(audiolibrary.combobox1.getValue()));
                    pstat.setString(2, null);
                }
                if (audiolibrary.combobox2.getSelectionModel().isEmpty())
                    pstat.setString(3, "%");
                else if (audiolibrary.combobox2.getValue().equals("With related artist(-s)/band(-s)"))
                    pstat.setString(3, "%_%");
                else if (audiolibrary.combobox2.getValue().equals("Without related artist(-s)/band(-s)"))
                    pstat.setString(3, "");
                if (audiolibrary.combobox3.getSelectionModel().isEmpty())
                {
                    pstat.setString(4, "1");
                    pstat.setString(5, "999");
                }
                else if (audiolibrary.combobox3.getValue().equals("<10"))
                {
                    pstat.setString(4, "1");
                    pstat.setString(5, "9");
                }
                else if (audiolibrary.combobox3.getValue().equals("15"))
                {
                    pstat.setString(4, "15");
                    pstat.setString(5, "15");
                }
                else if (audiolibrary.combobox3.getValue().equals("25"))
                {
                    pstat.setString(4, "25");
                    pstat.setString(5, "25");
                }
                else if (audiolibrary.combobox3.getValue().equals("55"))
                {
                    pstat.setString(4, "55");
                    pstat.setString(5, "55");
                }
                else if (audiolibrary.combobox3.getValue().equals("70"))
                {
                    pstat.setString(4, "70");
                    pstat.setString(5, "70");
                }
                else if (audiolibrary.combobox3.getValue().equals(">70"))
                {
                    pstat.setString(4, "71");
                    pstat.setString(5, "999");
                }
                if (audiolibrary.combobox4.getSelectionModel().isEmpty())
                {
                    pstat.setString(6, "0:0:1");
                    pstat.setString(7, "71:59:59");
                }
                else if (audiolibrary.combobox4.getValue().equals("<45:00"))
                {
                    pstat.setString(6, "0:0:1");
                    pstat.setString(7, "0:44:59");
                }
                else if (audiolibrary.combobox4.getValue().equals(">=45:00 & <1:15:00"))
                {
                    pstat.setString(6, "0:45:00");
                    pstat.setString(7, "1:14:59");
                }
                else if (audiolibrary.combobox4.getValue().equals(">=1:15:00 & <2:5:00"))
                {
                    pstat.setString(6, "1:15:00");
                    pstat.setString(7, "2:4:59");
                }
                else if (audiolibrary.combobox4.getValue().equals(">=2:5:00"))
                {
                    pstat.setString(6, "2:5:00");
                    pstat.setString(7, "71:59:59");
                }
                resset = pstat.executeQuery();
                count = 0;
                while (resset.next())
                {
                    String artband = resset.getString(1);
                    String relartband = resset.getString(2);
                    String genr = resset.getString(3);
                    String songs = resset.getString(4);
                    int nosongs = resset.getInt(5);
                    String songsdur = resset.getString(6);
                    int totnosongs = resset.getInt(7);
                    String totsongsdur = resset.getString(8);
                    int id = resset.getInt(9);
                    datarr[count][0] = artband;
                    datarr[count][1] = relartband;
                    datarr[count][2] = genr;
                    datarr[count][3] = songs;
                    datarr[count][4] = String.valueOf(nosongs);
                    datarr[count][5] = songsdur;
                    datarr[count][6] = String.valueOf(totnosongs);
                    datarr[count][7] = totsongsdur;
                    datarr[count][8] = String.valueOf(id);
                    count += 1;
                }
                break;
            case 4:
            case 5:
                pstat = null;
                if (tabl == 4)
                    pstat = conn.prepareStatement("WITH composer AS(WITH composer AS(SELECT `Music artist/band`.artband, Song.nam, `Music artist/band`.nosongs, `Music artist/band`.songsdur, `Music artist/band`.ID FROM `Music artist/band` LEFT JOIN `Music artist/band's song` ON `Music artist/band`.ID=`Music artist/band's song`.artband LEFT JOIN Song ON `Music artist/band's song`.song=Song.ID WHERE `Music artist/band`.cat='Композиторы' AND `Music artist/band`.nosongs BETWEEN ? AND ? AND `Music artist/band`.songsdur BETWEEN CAST(? AS TIME) AND CAST(? AS TIME)) SELECT artband, GROUP_CONCAT(nam ORDER BY nam SEPARATOR ', '), nosongs, TRIM(LEADING '0' FROM TRIM(LEADING '00:' FROM REPLACE(songsdur, ':0', ':'))), ID FROM composer GROUP BY artband, nosongs, songsdur, ID ORDER BY artband) SELECT COUNT(ID) FROM composer;");
                if (tabl == 5)
                    pstat = conn.prepareStatement("WITH blogger AS(WITH blogger AS(SELECT `Music artist/band`.artband, Song.nam, `Music artist/band`.nosongs, `Music artist/band`.songsdur, `Music artist/band`.ID FROM `Music artist/band` LEFT JOIN `Music artist/band's song` ON `Music artist/band`.ID=`Music artist/band's song`.artband LEFT JOIN Song ON `Music artist/band's song`.song=Song.ID WHERE `Music artist/band`.cat='Блогеры' AND `Music artist/band`.nosongs BETWEEN ? AND ? AND `Music artist/band`.songsdur BETWEEN CAST(? AS TIME) AND CAST(? AS TIME)) SELECT artband, GROUP_CONCAT(nam ORDER BY nam SEPARATOR ', '), nosongs, TRIM(LEADING '0' FROM TRIM(LEADING '00:' FROM REPLACE(songsdur, ':0', ':'))), ID FROM blogger GROUP BY artband, nosongs, songsdur, ID ORDER BY artband) SELECT COUNT(ID) FROM blogger;");
                if (audiolibrary.combobox1.getSelectionModel().isEmpty())
                {
                    pstat.setString(1, "0");
                    pstat.setString(2, "999");
                }
                else if (audiolibrary.combobox1.getValue().equals("<5"))
                {
                    pstat.setString(1, "1");
                    pstat.setString(2, "4");
                }
                else if (audiolibrary.combobox1.getValue().equals(">=5"))
                {
                    pstat.setString(1, "5");
                    pstat.setString(2, "999");
                }
                if (audiolibrary.combobox2.getSelectionModel().isEmpty())
                {
                    pstat.setString(3, "0:0:1");
                    pstat.setString(4, "71:59:59");
                }
                else if (audiolibrary.combobox2.getValue().equals("<20:00"))
                {
                    pstat.setString(3, "0:0:1");
                    pstat.setString(4, "0:19:59");
                }
                else if (audiolibrary.combobox2.getValue().equals(">=20:00"))
                {
                    pstat.setString(3, "0:20:00");
                    pstat.setString(4, "71:59:59");
                }
                resset = pstat.executeQuery();
                while (resset.next())
                    count = resset.getInt("COUNT(ID)");
                datarr = new String[count][5];
                if (tabl == 4)
                    pstat = conn.prepareStatement("WITH composer AS(SELECT `Music artist/band`.artband, Song.nam, `Music artist/band`.nosongs, `Music artist/band`.songsdur, `Music artist/band`.ID FROM `Music artist/band` LEFT JOIN `Music artist/band's song` ON `Music artist/band`.ID=`Music artist/band's song`.artband LEFT JOIN Song ON `Music artist/band's song`.song=Song.ID WHERE `Music artist/band`.cat='Композиторы' AND `Music artist/band`.nosongs BETWEEN ? AND ? AND `Music artist/band`.songsdur BETWEEN CAST(? AS TIME) AND CAST(? AS TIME)) SELECT artband, GROUP_CONCAT(nam ORDER BY nam SEPARATOR ', '), nosongs, TRIM(LEADING '0' FROM TRIM(LEADING '00:' FROM REPLACE(songsdur, ':0', ':'))), ID FROM composer GROUP BY artband, nosongs, songsdur, ID ORDER BY artband;");
                if (tabl == 5)
                    pstat = conn.prepareStatement("WITH blogger AS(SELECT `Music artist/band`.artband, Song.nam, `Music artist/band`.nosongs, `Music artist/band`.songsdur, `Music artist/band`.ID FROM `Music artist/band` LEFT JOIN `Music artist/band's song` ON `Music artist/band`.ID=`Music artist/band's song`.artband LEFT JOIN Song ON `Music artist/band's song`.song=Song.ID WHERE `Music artist/band`.cat='Блогеры' AND `Music artist/band`.nosongs BETWEEN ? AND ? AND `Music artist/band`.songsdur BETWEEN CAST(? AS TIME) AND CAST(? AS TIME)) SELECT artband, GROUP_CONCAT(nam ORDER BY nam SEPARATOR ', '), nosongs, TRIM(LEADING '0' FROM TRIM(LEADING '00:' FROM REPLACE(songsdur, ':0', ':'))), ID FROM blogger GROUP BY artband, nosongs, songsdur, ID ORDER BY artband;");
                if (audiolibrary.combobox1.getSelectionModel().isEmpty())
                {
                    pstat.setString(1, "0");
                    pstat.setString(2, "999");
                }
                else if (audiolibrary.combobox1.getValue().equals("<5"))
                {
                    pstat.setString(1, "1");
                    pstat.setString(2, "4");
                }
                else if (audiolibrary.combobox1.getValue().equals(">=5"))
                {
                    pstat.setString(1, "5");
                    pstat.setString(2, "999");
                }
                if (audiolibrary.combobox2.getSelectionModel().isEmpty())
                {
                    pstat.setString(3, "0:0:1");
                    pstat.setString(4, "71:59:59");
                }
                else if (audiolibrary.combobox2.getValue().equals("<20:00"))
                {
                    pstat.setString(3, "0:0:1");
                    pstat.setString(4, "0:19:59");
                }
                else if (audiolibrary.combobox2.getValue().equals(">=20:00"))
                {
                    pstat.setString(3, "0:20:00");
                    pstat.setString(4, "71:59:59");
                }
                resset = pstat.executeQuery();
                count = 0;
                while (resset.next())
                {
                    String nam = resset.getString(1);
                    String songs = resset.getString(2);
                    int nosongs = resset.getInt(3);
                    String songsdur = resset.getString(4);
                    int id = resset.getInt(5);
                    datarr[count][0] = nam;
                    datarr[count][1] = songs;
                    datarr[count][2] = String.valueOf(nosongs);
                    datarr[count][3] = songsdur;
                    datarr[count][4] = String.valueOf(id);
                    count += 1;
                }
                break;
            case 6:
            pstat = conn.prepareStatement("WITH cover AS(WITH cover AS(WITH cover AS(SELECT Song.nam, `Music artist/band`.artband, `Music artist/band's song`.feat, Song.dur, Song.ID FROM `Music artist/band's song` JOIN `Music artist/band` ON `Music artist/band's song`.artband=`Music artist/band`.ID JOIN Song ON `Music artist/band's song`.song=Song.ID WHERE Song.cat='Каверы' AND Song.dur BETWEEN CAST(? AS TIME) AND CAST(? AS TIME) UNION (SELECT Song.nam, `Related music artist/band`.relartband, `Music artist/band's song`.feat, Song.dur, Song.ID FROM `Music artist/band's song` JOIN `Related music artist/band` ON `Music artist/band's song`.artband=`Related music artist/band`.ID JOIN Song ON `Music artist/band's song`.song=Song.ID WHERE Song.cat='Каверы' AND Song.dur BETWEEN CAST(? AS TIME) AND CAST(? AS TIME))) SELECT cover.nam, cover.artband, cover.feat, cover.dur, `Music artist/band`.artband AS origartband, `Cover's original music artist/band`.feat AS origfeat, cover.ID FROM cover JOIN `Cover's original music artist/band` ON cover.ID=`Cover's original music artist/band`.song JOIN `Music artist/band` ON `Cover's original music artist/band`.artband=`Music artist/band`.ID UNION (SELECT cover.nam, cover.artband, cover.feat, cover.dur, `Related music artist/band`.relartband, `Cover's original music artist/band`.feat, cover.ID FROM cover JOIN `Cover's original music artist/band` ON cover.ID=`Cover's original music artist/band`.song JOIN `Related music artist/band` ON `Cover's original music artist/band`.artband=`Related music artist/band`.ID)) SELECT nam, artband, feat, TRIM(LEADING '00:' FROM REPLACE(dur, ':0', ':')), GROUP_CONCAT(origartband SEPARATOR ', '), origfeat, ID FROM cover GROUP BY artband, nam, feat, dur, origfeat, ID ORDER BY nam, artband) SELECT COUNT(ID) FROM cover;");
            if (audiolibrary.combobox1.getSelectionModel().isEmpty())
            {
                pstat.setString(1, "0:0:1");
                pstat.setString(2, "71:59:59");
                pstat.setString(3, "0:0:1");
                pstat.setString(4, "71:59:59");
            }
            else if (audiolibrary.combobox1.getValue().equals("<4:00"))
            {
                pstat.setString(1, "0:0:1");
                pstat.setString(2, "0:3:59");
                pstat.setString(3, "0:0:1");
                pstat.setString(4, "0:3:59");
            }
            else if (audiolibrary.combobox1.getValue().equals(">=4:00"))
            {
                pstat.setString(1, "0:4:00");
                pstat.setString(2, "71:59:59");
                pstat.setString(3, "0:4:00");
                pstat.setString(4, "71:59:59");
            }
            resset = pstat.executeQuery();
            while (resset.next())
                count = resset.getInt("COUNT(ID)");
            datarr = new String[count][7];
            pstat = conn.prepareStatement("WITH cover AS(WITH cover AS(SELECT Song.nam, `Music artist/band`.artband, `Music artist/band's song`.feat, Song.dur, Song.ID FROM `Music artist/band's song` JOIN `Music artist/band` ON `Music artist/band's song`.artband=`Music artist/band`.ID JOIN Song ON `Music artist/band's song`.song=Song.ID WHERE Song.cat='Каверы' AND Song.dur BETWEEN CAST(? AS TIME) AND CAST(? AS TIME) UNION (SELECT Song.nam, `Related music artist/band`.relartband, `Music artist/band's song`.feat, Song.dur, Song.ID FROM `Music artist/band's song` JOIN `Related music artist/band` ON `Music artist/band's song`.artband=`Related music artist/band`.ID JOIN Song ON `Music artist/band's song`.song=Song.ID WHERE Song.cat='Каверы' AND Song.dur BETWEEN CAST(? AS TIME) AND CAST(? AS TIME))) SELECT cover.nam, cover.artband, cover.feat, cover.dur, `Music artist/band`.artband AS origartband, `Cover's original music artist/band`.feat AS origfeat, cover.ID FROM cover JOIN `Cover's original music artist/band` ON cover.ID=`Cover's original music artist/band`.song JOIN `Music artist/band` ON `Cover's original music artist/band`.artband=`Music artist/band`.ID UNION (SELECT cover.nam, cover.artband, cover.feat, cover.dur, `Related music artist/band`.relartband, `Cover's original music artist/band`.feat, cover.ID FROM cover JOIN `Cover's original music artist/band` ON cover.ID=`Cover's original music artist/band`.song JOIN `Related music artist/band` ON `Cover's original music artist/band`.artband=`Related music artist/band`.ID)) SELECT nam, artband, feat, TRIM(LEADING '00:' FROM REPLACE(dur, ':0', ':')), GROUP_CONCAT(origartband SEPARATOR ', '), origfeat, ID FROM cover GROUP BY artband, nam, feat, dur, origfeat, ID ORDER BY nam, artband;");
                if (audiolibrary.combobox1.getSelectionModel().isEmpty())
                {
                    pstat.setString(1, "0:0:1");
                    pstat.setString(2, "71:59:59");
                    pstat.setString(3, "0:0:1");
                    pstat.setString(4, "71:59:59");
                }
                else if (audiolibrary.combobox1.getValue().equals("<4:00"))
                {
                    pstat.setString(1, "0:0:1");
                    pstat.setString(2, "0:3:59");
                    pstat.setString(3, "0:0:1");
                    pstat.setString(4, "0:3:59");
                }
                else if (audiolibrary.combobox1.getValue().equals(">=4:00"))
                {
                    pstat.setString(1, "0:4:00");
                    pstat.setString(2, "71:59:59");
                    pstat.setString(3, "0:4:00");
                    pstat.setString(4, "71:59:59");
                }
            resset = pstat.executeQuery();
            count = 0;
            while (resset.next())
            {
                String song = resset.getString(1);
                String artband = resset.getString(2);
                String feat = resset.getString(3);
                String dur = resset.getString(4);
                String origartband = resset.getString(5);
                String origfeat = resset.getString(6);
                int id = resset.getInt(7);
                datarr[count][0] = song;
                datarr[count][1] = artband;
                datarr[count][2] = feat;
                datarr[count][3] = dur;
                datarr[count][4] = origartband;
                datarr[count][5] = origfeat;
                datarr[count][6] = String.valueOf(id);
                count += 1;
            }
            break;
            case 7:
                pstat = conn.prepareStatement("WITH soundtrack AS(SELECT movanimsergam, artband, song, nosongs, TRIM(LEADING '00:' FROM REGEXP_REPLACE(REGEXP_REPLACE(REPLACE(songsdur, ':0', ':'), '^01', '1'), '^02', '2')), ID FROM Soundtrack WHERE nosongs BETWEEN ? AND ? AND songsdur BETWEEN CAST(? AS TIME) AND CAST(? AS TIME) ORDER BY movanimsergam) SELECT COUNT(ID) FROM soundtrack;");
                if (audiolibrary.combobox1.getSelectionModel().isEmpty())
                {
                    pstat.setString(1, "1");
                    pstat.setString(2, "999");
                }
                else if (audiolibrary.combobox1.getValue().equals("1"))
                {
                    pstat.setString(1, "1");
                    pstat.setString(2, "1");
                }
                else if (audiolibrary.combobox1.getValue().equals(">1"))
                {
                    pstat.setString(1, "2");
                    pstat.setString(2, "999");
                }
                if (audiolibrary.combobox2.getSelectionModel().isEmpty())
                {
                    pstat.setString(3, "0:0:1");
                    pstat.setString(4, "71:59:59");
                }
                else if (audiolibrary.combobox2.getValue().equals("<4:00"))
                {
                    pstat.setString(3, "0:0:1");
                    pstat.setString(4, "0:3:59");
                }
                else if (audiolibrary.combobox2.getValue().equals(">=4:00"))
                {
                    pstat.setString(3, "0:4:00");
                    pstat.setString(4, "71:59:59");
                }
                resset = pstat.executeQuery();
                while (resset.next())
                    count = resset.getInt("COUNT(ID)");
                datarr = new String[count][6];
                pstat = conn.prepareStatement("SELECT movanimsergam, artband, song, nosongs, TRIM(LEADING '00:' FROM REGEXP_REPLACE(REGEXP_REPLACE(REPLACE(songsdur, ':0', ':'), '^01', '1'), '^02', '2')), ID FROM Soundtrack WHERE nosongs BETWEEN ? AND ? AND songsdur BETWEEN CAST(? AS TIME) AND CAST(? AS TIME) ORDER BY movanimsergam;");
                if (audiolibrary.combobox1.getSelectionModel().isEmpty())
                {
                    pstat.setString(1, "1");
                    pstat.setString(2, "999");
                }
                else if (audiolibrary.combobox1.getValue().equals("1"))
                {
                    pstat.setString(1, "1");
                    pstat.setString(2, "1");
                }
                else if (audiolibrary.combobox1.getValue().equals(">1"))
                {
                    pstat.setString(1, "2");
                    pstat.setString(2, "999");
                }
                if (audiolibrary.combobox2.getSelectionModel().isEmpty())
                {
                    pstat.setString(3, "0:0:1");
                    pstat.setString(4, "71:59:59");
                }
                else if (audiolibrary.combobox2.getValue().equals("<4:00"))
                {
                    pstat.setString(3, "0:0:1");
                    pstat.setString(4, "0:3:59");
                }
                else if (audiolibrary.combobox2.getValue().equals(">=4:00"))
                {
                    pstat.setString(3, "0:4:00");
                    pstat.setString(4, "71:59:59");
                }
                resset = pstat.executeQuery();
                count = 0;
                while (resset.next())
                {
                    String movanimsergam = resset.getString(1);
                    String artband = resset.getString(2);
                    String song = resset.getString(3);
                    int nosongs = resset.getInt(4);
                    String songsdur = resset.getString(5);
                    int id = resset.getInt(6);
                    datarr[count][0] = movanimsergam;
                    datarr[count][1] = artband;
                    datarr[count][2] = song;
                    datarr[count][3] = String.valueOf(nosongs);
                    datarr[count][4] = songsdur;
                    datarr[count][5] = String.valueOf(id);
                    count += 1;
                }
                break;
        }
        conn.close();

        return datarr;
    }

    public static String[][] SongsStatistics(String cat) throws Exception
    {
        int count;

        conn();
        Statement stat = conn.createStatement();
        PreparedStatement pstat;
        ResultSet resset;
        String[][] datarr = new String[15][2];
        if (cat.equals("Total") || cat.equals("Жанр") || cat.equals("Композиторы") || cat.equals("Блогеры") || cat.equals("Каверы") || cat.equals("Саундтреки"))
        {
            switch (cat)
            {
                case "Жанр":
                    resset = stat.executeQuery("CALL SongsStatisticsTotalGenre();");
                    break;
                case "Композиторы":
                    resset = stat.executeQuery("CALL SongsStatisticsComposers();");
                    break;
                case "Блогеры":
                    resset = stat.executeQuery("CALL SongsStatisticsBloggers();");
                    break;
                case "Каверы":
                    resset = stat.executeQuery("CALL SongsStatisticsCovers();");
                    break;
                case "Саундтреки":
                    resset = stat.executeQuery("CALL SongsStatisticsSoundtracks();");
                    break;
                default:
                    resset = stat.executeQuery("SELECT * FROM TotalSongsStatisticsVIEW;");
                    break;
            }
        }
        else
        {
            pstat = conn.prepareStatement("CALL SongsStatisticsGenre(?);");
            pstat.setString(1, cat);
            resset = pstat.executeQuery();
        }
        count = 0;
        while (resset.next() && count < 15)
        {
            String shortestsongs = resset.getString(1);
            String longestsongs = resset.getString(2);
            datarr[count][0] = shortestsongs;
            datarr[count][1] = longestsongs;
            count += 1;
        }
        conn.close();

        return datarr;
    }

    public static int NewMusicArtistBandID() throws Exception
    {
        int artbandid = 0;

        conn();
        Statement stat = conn.createStatement();
        ResultSet resset;
        resset = stat.executeQuery("SELECT MAX(ID) FROM `Music artist/band identifier`;");
        while (resset.next())
            artbandid = resset.getInt("MAX(ID)");
        conn.close();

        return artbandid;
    }

    public static int SoundtrackID() throws Exception
    {
        int soundtrackid = 0;

        conn();
        Statement stat = conn.createStatement();
        ResultSet resset;
        resset = stat.executeQuery("SELECT MAX(ID) FROM Soundtrack;");
        while (resset.next())
            soundtrackid = resset.getInt("MAX(ID)");
        conn.close();

        return soundtrackid;
    }
}