CREATE DATABASE `Audio library`;
USE `Audio library`;

CREATE TABLE `Audio library`(cat varchar(15) PRIMARY KEY CHECK (cat IN('Жанр', 'Композиторы', 'Блогеры', 'Каверы', 'Саундтреки')), noorigartsbands smallint DEFAULT 0, nosongs smallint DEFAULT 0, songsdur varchar(15) DEFAULT '0:0:0');
CREATE TABLE Genre(nam varchar(50) PRIMARY KEY, noartsbands smallint DEFAULT 0, nosongs smallint DEFAULT 0, songsdur varchar(15) DEFAULT '0:0:0');
CREATE TABLE `Music artist/band`(ID smallint PRIMARY KEY, artband varchar(100) NOT NULL, genr varchar(50) NULL, nosongs smallint DEFAULT 0, songsdur time DEFAULT '0:0:0', totnosongs smallint DEFAULT 0, totsongsdur time DEFAULT '0:0:0', cat varchar(15) CHECK (cat IN('Жанр', 'Композиторы', 'Блогеры', 'Каверы')), FOREIGN KEY (cat) REFERENCES `Audio library`(cat), FOREIGN KEY (genr) REFERENCES Genre(nam));
CREATE TABLE `Related music artist/band`(ID smallint PRIMARY KEY, relartband varchar(100) NOT NULL, artband smallint, nosongs tinyint DEFAULT 0, songsdur time DEFAULT '0:0:0', FOREIGN KEY (artband) REFERENCES `Music artist/band`(ID) ON DELETE CASCADE);
CREATE TABLE `Music artist/band identifier`(ID smallint PRIMARY KEY, artband smallint NULL, relartband smallint NULL, FOREIGN KEY (artband) REFERENCES `Music artist/band`(ID) ON DELETE CASCADE, FOREIGN KEY (relartband) REFERENCES `Related music artist/band`(ID) ON DELETE CASCADE);
CREATE TABLE Song(ID int PRIMARY KEY auto_increment, nam varchar(150) NOT NULL, dur time NOT NULL, cat varchar(15) NULL CHECK (cat='Каверы'), FOREIGN KEY (cat) REFERENCES `Audio library`(cat));
CREATE TABLE `Music artist/band's song`(song int, artband smallint, feat varchar(100) NULL, FOREIGN KEY (song) REFERENCES Song(ID), FOREIGN KEY (artband) REFERENCES `Music artist/band identifier`(ID));
CREATE TABLE `Cover's original music artist/band`(song int, artband smallint, feat varchar(100) NULL, FOREIGN KEY (song) REFERENCES Song(ID), FOREIGN KEY (artband) REFERENCES `Music artist/band identifier`(ID));
CREATE TABLE Favourites(num tinyint PRIMARY KEY, artband smallint, FOREIGN KEY (artband) REFERENCES `Music artist/band`(ID));
CREATE TABLE Soundtrack(ID smallint PRIMARY KEY auto_increment, movanimsergam varchar(150) NOT NULL, artband varchar(500) NOT NULL, song varchar(750) NOT NULL, nosongs smallint NOT NULL, songsdur time NOT NULL, cat varchar(15) CHECK (cat='Саундтреки'), FOREIGN KEY (cat) REFERENCES `Audio library`(cat));
CREATE VIEW TotalArtistsStatisticsVIEW AS SELECT REPLACE(REPLACE(REPLACE(CONCAT('Total: ', (SELECT SUM(noorigartsbands) FROM `Audio library`)-(SELECT COUNT(ID) FROM `Related music artist/band`), ', ', (SELECT SUM(nosongs) FROM `Audio library` WHERE cat!='Саундтреки')-(SELECT SUM(nosongs) FROM `Related music artist/band`), '/', FLOOR(((SELECT SUM(TIME_TO_SEC(dur)) FROM Song)-(SELECT SUM(TIME_TO_SEC(songsdur)) FROM `Related music artist/band`))/86400), 'd ', FLOOR((((SELECT SUM(TIME_TO_SEC(dur)) FROM Song)-(SELECT SUM(TIME_TO_SEC(songsdur)) FROM `Related music artist/band`))%86400)/3600), ':', FLOOR(((((SELECT SUM(TIME_TO_SEC(dur)) FROM Song)-(SELECT SUM(TIME_TO_SEC(songsdur)) FROM `Related music artist/band`))%86400)%3600)/60), ':', FLOOR(((((SELECT SUM(TIME_TO_SEC(dur)) FROM Song)-(SELECT SUM(TIME_TO_SEC(songsdur)) FROM `Related music artist/band`))%86400)%3600)%60), '; ', 'norelarts', ', ', SUM(`Music artist/band`.totnosongs)-SUM(`Music artist/band`.nosongs), '/', FLOOR((SUM(TIME_TO_SEC(`Music artist/band`.totsongsdur))-SUM(TIME_TO_SEC(`Music artist/band`.songsdur)))/86400), 'd ', FLOOR(((SUM(TIME_TO_SEC(`Music artist/band`.totsongsdur))-SUM(TIME_TO_SEC(`Music artist/band`.songsdur)))%86400)/3600), ':', FLOOR((((SUM(TIME_TO_SEC(`Music artist/band`.totsongsdur))-SUM(TIME_TO_SEC(`Music artist/band`.songsdur)))%86400)%3600)/60), ':', FLOOR((((SUM(TIME_TO_SEC(`Music artist/band`.totsongsdur))-SUM(TIME_TO_SEC(`Music artist/band`.songsdur)))%86400)%3600)%60)), "; 0, 0/0d 0:0:0", ""), "0d ", ""), "norelarts", (SELECT SUM(noartsbands) FROM Genre)-(SELECT COUNT(ID) FROM `Music artist/band` WHERE cat='Жанр')) AS 'Number of artists and related artists, songs count/duration' FROM `Music artist/band` UNION (SELECT REPLACE(REPLACE(REPLACE(CONCAT('Жанр: ', COUNT(`Music artist/band`.ID), ', ', SUM(`Music artist/band`.nosongs), '/', FLOOR(SUM(TIME_TO_SEC(`Music artist/band`.songsdur))/86400), 'd ', FLOOR((SUM(TIME_TO_SEC(`Music artist/band`.songsdur))%86400)/3600), ':', FLOOR(((SUM(TIME_TO_SEC(`Music artist/band`.songsdur))%86400)%3600)/60), ':', FLOOR(((SUM(TIME_TO_SEC(`Music artist/band`.songsdur))%86400)%3600)%60), '; ', 'norelarts', ', ', SUM(`Music artist/band`.totnosongs)-SUM(`Music artist/band`.nosongs), '/', FLOOR((SUM(TIME_TO_SEC(`Music artist/band`.totsongsdur))-SUM(TIME_TO_SEC(`Music artist/band`.songsdur)))/86400), 'd ', FLOOR(((SUM(TIME_TO_SEC(`Music artist/band`.totsongsdur))-SUM(TIME_TO_SEC(`Music artist/band`.songsdur)))%86400)/3600), ':', FLOOR((((SUM(TIME_TO_SEC(`Music artist/band`.totsongsdur))-SUM(TIME_TO_SEC(`Music artist/band`.songsdur)))%86400)%3600)/60), ':', FLOOR((((SUM(TIME_TO_SEC(`Music artist/band`.totsongsdur))-SUM(TIME_TO_SEC(`Music artist/band`.songsdur)))%86400)%3600)%60)), "; 0, 0/0d 0:0:0", ""), "0d ", ""), "norelarts", (SELECT SUM(noartsbands) FROM Genre)-(SELECT COUNT(ID) FROM `Music artist/band` WHERE cat='Жанр')) AS 'Number of artists and related artists, songs count/duration' FROM `Music artist/band` WHERE `Music artist/band`.cat='Жанр') UNION (SELECT REPLACE(REPLACE(CONCAT(`Music artist/band`.genr, ': ', COUNT(`Music artist/band`.ID), ', ', SUM(`Music artist/band`.nosongs), '/', FLOOR(SUM(TIME_TO_SEC(`Music artist/band`.songsdur))/86400), 'd ', FLOOR((SUM(TIME_TO_SEC(`Music artist/band`.songsdur))%86400)/3600), ':', FLOOR(((SUM(TIME_TO_SEC(`Music artist/band`.songsdur))%86400)%3600)/60), ':', FLOOR(((SUM(TIME_TO_SEC(`Music artist/band`.songsdur))%86400)%3600)%60), '; ', Genre.noartsbands-COUNT(`Music artist/band`.ID), ', ', SUM(`Music artist/band`.totnosongs)-SUM(`Music artist/band`.nosongs), '/', FLOOR((SUM(TIME_TO_SEC(`Music artist/band`.totsongsdur))-SUM(TIME_TO_SEC(`Music artist/band`.songsdur)))/86400), 'd ', FLOOR(((SUM(TIME_TO_SEC(`Music artist/band`.totsongsdur))-SUM(TIME_TO_SEC(`Music artist/band`.songsdur)))%86400)/3600), ':', FLOOR((((SUM(TIME_TO_SEC(`Music artist/band`.totsongsdur))-SUM(TIME_TO_SEC(`Music artist/band`.songsdur)))%86400)%3600)/60), ':', FLOOR((((SUM(TIME_TO_SEC(`Music artist/band`.totsongsdur))-SUM(TIME_TO_SEC(`Music artist/band`.songsdur)))%86400)%3600)%60)), "; 0, 0/0d 0:0:0", ""), "0d ", "") AS 'Number of artists and related artists, songs count/duration' FROM `Music artist/band` JOIN Genre ON Genre.nam=`Music artist/band`.genr GROUP BY Genre.nam ORDER BY Genre.nam) UNION (WITH artistsstatisticscovers AS(SELECT CONCAT('Каверы: ', COUNT(DISTINCT `Music artist/band's song`.artband), ', ', COUNT(Song.nam), '/', FLOOR(SUM(TIME_TO_SEC(Song.dur))/86400), 'd ', FLOOR((SUM(TIME_TO_SEC(Song.dur))%86400)/3600), ':', FLOOR(((SUM(TIME_TO_SEC(Song.dur))%86400)%3600)/60), ':', FLOOR(((SUM(TIME_TO_SEC(Song.dur))%86400)%3600)%60)) AS 'Number of artists and related artists, songs count/duration' FROM `Music artist/band's song` JOIN `Music artist/band` ON `Music artist/band`.ID=`Music artist/band's song`.artband JOIN Song ON Song.ID=`Music artist/band's song`.song WHERE Song.cat='Каверы' UNION (SELECT REPLACE(CONCAT(COUNT(DISTINCT `Music artist/band's song`.artband), ', ', COUNT(Song.nam), '/', FLOOR(SUM(TIME_TO_SEC(Song.dur))/86400), 'd ', FLOOR((SUM(TIME_TO_SEC(Song.dur))%86400)/3600), ':', FLOOR(((SUM(TIME_TO_SEC(Song.dur))%86400)%3600)/60), ':', FLOOR(((SUM(TIME_TO_SEC(Song.dur))%86400)%3600)%60)), "0d ", "") FROM `Music artist/band's song` JOIN `Related music artist/band` ON `Related music artist/band`.ID=`Music artist/band's song`.artband JOIN Song ON Song.ID=`Music artist/band's song`.song WHERE Song.cat='Каверы' ORDER BY COUNT(song))) SELECT GROUP_CONCAT(`Number of artists and related artists, songs count/duration` SEPARATOR '; ') FROM artistsstatisticscovers);
CREATE VIEW TotalSongsStatisticsVIEW AS WITH songsstatisticsasc AS(SELECT `Music artist/band`.artband, `Music artist/band's song`.feat, Song.nam, Song.dur FROM `Music artist/band` JOIN `Music artist/band's song` ON `Music artist/band`.ID=`Music artist/band's song`.artband JOIN Song ON Song.ID=`Music artist/band's song`.song UNION SELECT `Related music artist/band`.relartband, `Music artist/band's song`.feat, Song.nam, Song.dur FROM `Related music artist/band` JOIN `Music artist/band's song` ON `Related music artist/band`.ID=`Music artist/band's song`.artband JOIN Song ON Song.ID=`Music artist/band's song`.song) SELECT REPLACE(CONCAT(songsstatisticsasc.artband, REGEXP_REPLACE(REGEXP_REPLACE(REGEXP_REPLACE(IFNULL(songsstatisticsasc.feat, ''), '^', ', '), ',(?!.*,)', ' &'), '(?=.*[А-Яа-я])&', 'и'), ' - ', songsstatisticsasc.nam, ' (', TRIM(LEADING '00:' FROM REPLACE(songsstatisticsasc.dur, ':0', ':')), ')'), ',  -', ' -') AS 'Shortest songs', REPLACE(CONCAT(songsstatisticsdesc.artband, REGEXP_REPLACE(REGEXP_REPLACE(REGEXP_REPLACE(IFNULL(songsstatisticsdesc.feat, ''), '^', ', '), ',(?!.*,)', ' &'), '(?=.*[А-Яа-я])&', 'и'), ' - ', songsstatisticsdesc.nam, ' (', TRIM(LEADING '00:' FROM REPLACE(songsstatisticsdesc.dur, ':0', ':')), ')'), ',  -', ' -') AS 'Longest songs' FROM (SELECT *, ROW_NUMBER() OVER(ORDER BY dur, nam) AS rnasc FROM songsstatisticsasc) AS songsstatisticsasc JOIN (SELECT *, ROW_NUMBER() OVER(ORDER BY dur DESC, nam ASC) AS rndesc FROM songsstatisticsasc) AS songsstatisticsdesc ON songsstatisticsasc.rnasc=songsstatisticsdesc.rndesc;

DELIMITER $
CREATE PROCEDURE NumberOfArtistsBands(category varchar(15), genre varchar(50))
BEGIN
SET @cat=category;
SET @genr=genre;
SET @noartsbands=(SELECT COUNT(ID) FROM `Music artist/band` WHERE genr=@genr);
SET @norelartsbands=(SELECT COUNT(`Related music artist/band`.artband) FROM `Music artist/band` JOIN `Related music artist/band` ON `Music artist/band`.ID=`Related music artist/band`.artband WHERE `Music artist/band`.genr=@genr);
SET @noorigartsbands=(SELECT COUNT(ID) FROM `Music artist/band` WHERE cat=@cat);
UPDATE Genre SET noartsbands=@noartsbands+@norelartsbands WHERE nam=@genr;
IF @cat='Жанр' THEN
UPDATE `Audio library` SET noorigartsbands=(SELECT SUM(noartsbands) FROM Genre) WHERE cat='Жанр';
ELSE
UPDATE `Audio library` SET noorigartsbands=@noorigartsbands WHERE cat=@cat;
END IF;
END$
CREATE PROCEDURE SongsCountandDuration(songid smallint, artistbandid smallint)
BEGIN
SET @song=songid;
SET @artbandid=artistbandid;
IF (SELECT cat FROM `Music artist/band` WHERE ID=@artbandid)!='Каверы' OR (SELECT cat FROM `Music artist/band` WHERE ID=(SELECT artband FROM `Related music artist/band` WHERE ID=@artbandid))!='Каверы' THEN
SET @noartbandsongs=(SELECT COUNT(song) FROM `Music artist/band's song` WHERE artband=@artbandid AND song NOT IN(SELECT ID FROM Song WHERE cat='Каверы'));
SET @artbandsongsdur=(SELECT SEC_TO_TIME(SUM(TIME_TO_SEC(dur))) FROM Song WHERE ID IN(SELECT song FROM `Music artist/band's song` WHERE artband=@artbandid AND song NOT IN(SELECT ID FROM Song WHERE cat='Каверы')));
ELSE
SET @noartbandsongs=(SELECT COUNT(song) FROM `Music artist/band's song` WHERE artband=@artbandid AND song IN(SELECT ID FROM Song WHERE cat='Каверы'));
SET @artbandsongsdur=(SELECT SEC_TO_TIME(SUM(TIME_TO_SEC(dur))) FROM Song WHERE ID IN(SELECT song FROM `Music artist/band's song` WHERE artband=@artbandid AND song IN(SELECT ID FROM Song WHERE cat='Каверы')));
END IF;
UPDATE `Related music artist/band` SET nosongs=@noartbandsongs, songsdur=@artbandsongsdur WHERE ID=@artbandid;
SET @relartbandnosongs=(SELECT SUM(nosongs) FROM `Related music artist/band` WHERE artband=(SELECT artband FROM `Related music artist/band` WHERE ID=@artbandid));
SET @relartbandsongsdur=(SELECT SUM(TIME_TO_SEC(songsdur)) FROM `Related music artist/band` WHERE artband=(SELECT artband FROM `Related music artist/band` WHERE ID=@artbandid));
IF @relartbandnosongs IS NULL AND @relartbandsongsdur IS NULL THEN
SET @relartbandnosongs=(SELECT SUM(nosongs) FROM `Related music artist/band` WHERE artband=@artbandid);
SET @relartbandsongsdur=(SELECT SUM(TIME_TO_SEC(songsdur)) FROM `Related music artist/band` WHERE artband=@artbandid);
IF @relartbandnosongs IS NULL AND @relartbandsongsdur IS NULL THEN
SET @relartbandnosongs=0;
SET @relartbandsongsdur=0;
END IF;
ELSE
SET @artbandid=(SELECT artband FROM `Related music artist/band` WHERE ID=@artbandid);
SET @noartbandsongs=(SELECT COUNT(song) FROM `Music artist/band's song` WHERE artband=@artbandid AND song NOT IN(SELECT ID FROM Song WHERE cat='Каверы'));
SET @artbandsongsdur=(SELECT SEC_TO_TIME(SUM(TIME_TO_SEC(dur))) FROM Song WHERE ID IN(SELECT song FROM `Music artist/band's song` WHERE artband=@artbandid AND song NOT IN(SELECT ID FROM Song WHERE cat='Каверы')));
END IF;
UPDATE `Music artist/band` SET nosongs=@noartbandsongs, songsdur=@artbandsongsdur, totnosongs=nosongs+@relartbandnosongs, totsongsdur=(SELECT SEC_TO_TIME(TIME_TO_SEC(songsdur)+@relartbandsongsdur)) WHERE ID=@artbandid;
SET @artbandnosongs=(SELECT SUM(totnosongs) FROM `Music artist/band` WHERE genr=(SELECT genr FROM `Music artist/band` WHERE ID=@artbandid));
SET @artbandsongsdurgenr=(SELECT SEC_TO_TIME(SUM(TIME_TO_SEC(totsongsdur))) FROM `Music artist/band` WHERE genr=(SELECT genr FROM `Music artist/band` WHERE ID=@artbandid));
SET @artbandsongsdur=(SELECT REPLACE(CONCAT(FLOOR(TIME_FORMAT(@artbandsongsdurgenr, '%H')/24), 'd ', MOD(TIME_FORMAT(@artbandsongsdurgenr, '%H'), 24), ':', TIME_FORMAT(@artbandsongsdurgenr, '%i:%s')), '0d ', ''));
UPDATE Genre SET nosongs=@artbandnosongs, songsdur=@artbandsongsdur WHERE nam=(SELECT genr FROM `Music artist/band` WHERE ID=@artbandid);
IF (SELECT cat FROM Song WHERE ID=@song) IS NULL THEN
SET @artbandnosongs=(SELECT SUM(totnosongs) FROM `Music artist/band` WHERE cat=(SELECT cat FROM `Music artist/band` WHERE ID=@artbandid));
SET @artbandsongsduraudiolibrary=(SELECT SUM(TIME_TO_SEC(totsongsdur)) FROM `Music artist/band` WHERE cat=(SELECT cat FROM `Music artist/band` WHERE ID=@artbandid));
SET @artbandsongsdur=REPLACE((SELECT DATE_FORMAT(DATE('1000-01-01 00:00:00') + INTERVAL @artbandsongsduraudiolibrary SECOND - INTERVAL 1 DAY, '%jd %H:%i:%s')), '365d ', '');
UPDATE `Audio library` SET nosongs=@artbandnosongs, songsdur=@artbandsongsdur WHERE cat=(SELECT cat FROM `Music artist/band` WHERE ID=@artbandid);
ELSE
SET @artbandnosongs=(SELECT COUNT(Song.ID) FROM `Music artist/band's song` JOIN Song ON `Music artist/band's song`.song=Song.ID WHERE Song.cat='Каверы');
SET @artbandsongsduraudiolibrary=(SELECT SUM(TIME_TO_SEC(Song.dur)) FROM `Music artist/band's song` JOIN Song ON `Music artist/band's song`.song=Song.ID WHERE Song.cat='Каверы');
SET @artbandsongsdur=REPLACE((SELECT DATE_FORMAT(DATE('1000-01-01 00:00:00') + INTERVAL @artbandsongsduraudiolibrary SECOND - INTERVAL 1 DAY, '%jd %H:%i:%s')), '365d ', '');
UPDATE `Audio library` SET nosongs=@artbandnosongs, songsdur=@artbandsongsdur WHERE cat='Каверы';
END IF;
UPDATE `Audio library` SET songsdur=TRIM(LEADING '0' FROM songsdur);
END$
CREATE PROCEDURE SongsStatisticsTotalGenre()
BEGIN
WITH songsstatisticsasc AS(SELECT `Music artist/band`.artband, `Music artist/band's song`.feat, Song.nam, Song.dur FROM `Music artist/band` JOIN `Music artist/band's song` ON `Music artist/band`.ID=`Music artist/band's song`.artband JOIN Song ON Song.ID=`Music artist/band's song`.song WHERE `Music artist/band`.cat='Жанр' AND Song.cat IS NULL UNION SELECT `Related music artist/band`.relartband, `Music artist/band's song`.feat, Song.nam, Song.dur FROM `Related music artist/band` JOIN `Music artist/band` ON `Music artist/band`.ID=`Related music artist/band`.artband JOIN `Music artist/band's song` ON `Related music artist/band`.ID=`Music artist/band's song`.artband JOIN Song ON Song.ID=`Music artist/band's song`.song WHERE `Music artist/band`.cat='Жанр' AND Song.cat IS NULL) SELECT REPLACE(CONCAT(songsstatisticsasc.artband, REGEXP_REPLACE(REGEXP_REPLACE(REGEXP_REPLACE(IFNULL(songsstatisticsasc.feat, ''), '^', ', '), ',(?!.*,)', ' &'), '(?=.*[А-Яа-я])&', 'и'), ' - ', songsstatisticsasc.nam, ' (', TRIM(LEADING '00:' FROM REPLACE(songsstatisticsasc.dur, ':0', ':')), ')'), ',  -', ' -') AS 'Shortest songs', REPLACE(CONCAT(songsstatisticsdesc.artband, REGEXP_REPLACE(REGEXP_REPLACE(REGEXP_REPLACE(IFNULL(songsstatisticsdesc.feat, ''), '^', ', '), ',(?!.*,)', ' &'), '(?=.*[А-Яа-я])&', 'и'), ' - ', songsstatisticsdesc.nam, ' (', TRIM(LEADING '00:' FROM REPLACE(songsstatisticsdesc.dur, ':0', ':')), ')'), ',  -', ' -') AS 'Longest songs' FROM (SELECT *, ROW_NUMBER() OVER(ORDER BY dur, nam) AS rnasc FROM songsstatisticsasc) AS songsstatisticsasc JOIN (SELECT *, ROW_NUMBER() OVER(ORDER BY dur DESC, nam ASC) AS rndesc FROM songsstatisticsasc) AS songsstatisticsdesc ON songsstatisticsasc.rnasc=songsstatisticsdesc.rndesc;
END$
CREATE PROCEDURE SongsStatisticsGenre(genre varchar(50))
BEGIN
SET @genr=genre;
WITH songsstatisticsasc AS(SELECT `Music artist/band`.artband, `Music artist/band's song`.feat, Song.nam, Song.dur FROM `Music artist/band` JOIN `Music artist/band's song` ON `Music artist/band`.ID=`Music artist/band's song`.artband JOIN Song ON Song.ID=`Music artist/band's song`.song WHERE `Music artist/band`.genr=@genr AND Song.cat IS NULL UNION SELECT `Related music artist/band`.relartband, `Music artist/band's song`.feat, Song.nam, Song.dur FROM `Related music artist/band` JOIN `Music artist/band` ON `Music artist/band`.ID=`Related music artist/band`.artband JOIN `Music artist/band's song` ON `Related music artist/band`.ID=`Music artist/band's song`.artband JOIN Song ON Song.ID=`Music artist/band's song`.song WHERE `Music artist/band`.genr=@genr AND Song.cat IS NULL) SELECT REPLACE(CONCAT(songsstatisticsasc.artband, REGEXP_REPLACE(REGEXP_REPLACE(REGEXP_REPLACE(IFNULL(songsstatisticsasc.feat, ''), '^', ', '), ',(?!.*,)', ' &'), '(?=.*[А-Яа-я])&', 'и'), ' - ', songsstatisticsasc.nam, ' (', TRIM(LEADING '00:' FROM REPLACE(songsstatisticsasc.dur, ':0', ':')), ')'), ',  -', ' -') AS 'Shortest songs', REPLACE(CONCAT(songsstatisticsdesc.artband, REGEXP_REPLACE(REGEXP_REPLACE(REGEXP_REPLACE(IFNULL(songsstatisticsdesc.feat, ''), '^', ', '), ',(?!.*,)', ' &'), '(?=.*[А-Яа-я])&', 'и'), ' - ', songsstatisticsdesc.nam, ' (', TRIM(LEADING '00:' FROM REPLACE(songsstatisticsdesc.dur, ':0', ':')), ')'), ',  -', ' -') AS 'Longest songs' FROM (SELECT *, ROW_NUMBER() OVER(ORDER BY dur, nam) AS rnasc FROM songsstatisticsasc) AS songsstatisticsasc JOIN (SELECT *, ROW_NUMBER() OVER(ORDER BY dur DESC, nam ASC) AS rndesc FROM songsstatisticsasc) AS songsstatisticsdesc ON songsstatisticsasc.rnasc=songsstatisticsdesc.rndesc;
END$
CREATE PROCEDURE SongsStatisticsComposers()
BEGIN
WITH songsstatisticsasc AS(SELECT `Music artist/band`.artband, `Music artist/band's song`.feat, Song.nam, Song.dur FROM `Music artist/band` JOIN `Music artist/band's song` ON `Music artist/band`.ID=`Music artist/band's song`.artband JOIN Song ON Song.ID=`Music artist/band's song`.song WHERE `Music artist/band`.cat='Композиторы' AND Song.cat IS NULL) SELECT REPLACE(CONCAT(songsstatisticsasc.artband, REGEXP_REPLACE(REGEXP_REPLACE(REGEXP_REPLACE(IFNULL(songsstatisticsasc.feat, ''), '^', ', '), ',(?!.*,)', ' &'), '(?=.*[А-Яа-я])&', 'и'), ' - ', songsstatisticsasc.nam, ' (', TRIM(LEADING '00:' FROM REPLACE(songsstatisticsasc.dur, ':0', ':')), ')'), ',  -', ' -') AS 'Shortest songs', REPLACE(CONCAT(songsstatisticsdesc.artband, REGEXP_REPLACE(REGEXP_REPLACE(REGEXP_REPLACE(IFNULL(songsstatisticsdesc.feat, ''), '^', ', '), ',(?!.*,)', ' &'), '(?=.*[А-Яа-я])&', 'и'), ' - ', songsstatisticsdesc.nam, ' (', TRIM(LEADING '00:' FROM REPLACE(songsstatisticsdesc.dur, ':0', ':')), ')'), ',  -', ' -') AS 'Longest songs' FROM (SELECT *, ROW_NUMBER() OVER(ORDER BY dur, nam) AS rnasc FROM songsstatisticsasc) AS songsstatisticsasc JOIN (SELECT *, ROW_NUMBER() OVER(ORDER BY dur DESC, nam ASC) AS rndesc FROM songsstatisticsasc) AS songsstatisticsdesc ON songsstatisticsasc.rnasc=songsstatisticsdesc.rndesc;
END$
CREATE PROCEDURE SongsStatisticsBloggers()
WITH songsstatisticsasc AS(SELECT `Music artist/band`.artband, `Music artist/band's song`.feat, Song.nam, Song.dur FROM `Music artist/band` JOIN `Music artist/band's song` ON `Music artist/band`.ID=`Music artist/band's song`.artband JOIN Song ON Song.ID=`Music artist/band's song`.song WHERE `Music artist/band`.cat='Блогеры' AND Song.cat IS NULL) SELECT REPLACE(CONCAT(songsstatisticsasc.artband, REGEXP_REPLACE(REGEXP_REPLACE(REGEXP_REPLACE(IFNULL(songsstatisticsasc.feat, ''), '^', ', '), ',(?!.*,)', ' &'), '(?=.*[А-Яа-я])&', 'и'), ' - ', songsstatisticsasc.nam, ' (', TRIM(LEADING '00:' FROM REPLACE(songsstatisticsasc.dur, ':0', ':')), ')'), ',  -', ' -') AS 'Shortest songs', REPLACE(CONCAT(songsstatisticsdesc.artband, REGEXP_REPLACE(REGEXP_REPLACE(REGEXP_REPLACE(IFNULL(songsstatisticsdesc.feat, ''), '^', ', '), ',(?!.*,)', ' &'), '(?=.*[А-Яа-я])&', 'и'), ' - ', songsstatisticsdesc.nam, ' (', TRIM(LEADING '00:' FROM REPLACE(songsstatisticsdesc.dur, ':0', ':')), ')'), ',  -', ' -') AS 'Longest songs' FROM (SELECT *, ROW_NUMBER() OVER(ORDER BY dur, nam) AS rnasc FROM songsstatisticsasc) AS songsstatisticsasc JOIN (SELECT *, ROW_NUMBER() OVER(ORDER BY dur DESC, nam ASC) AS rndesc FROM songsstatisticsasc) AS songsstatisticsdesc ON songsstatisticsasc.rnasc=songsstatisticsdesc.rndesc;
BEGIN
END$
CREATE PROCEDURE SongsStatisticsCovers()
WITH songsstatisticsasc AS(SELECT `Music artist/band`.artband, `Music artist/band's song`.feat, Song.nam, Song.dur FROM `Music artist/band` JOIN `Music artist/band's song` ON `Music artist/band`.ID=`Music artist/band's song`.artband JOIN Song ON Song.ID=`Music artist/band's song`.song WHERE Song.cat='Каверы' UNION SELECT `Related music artist/band`.relartband, `Music artist/band's song`.feat, Song.nam, Song.dur FROM `Related music artist/band` JOIN `Music artist/band` ON `Music artist/band`.ID=`Related music artist/band`.artband JOIN `Music artist/band's song` ON `Related music artist/band`.ID=`Music artist/band's song`.artband JOIN Song ON Song.ID=`Music artist/band's song`.song WHERE Song.cat='Каверы') SELECT REPLACE(CONCAT(songsstatisticsasc.artband, REGEXP_REPLACE(REGEXP_REPLACE(REGEXP_REPLACE(IFNULL(songsstatisticsasc.feat, ''), '^', ', '), ',(?!.*,)', ' &'), '(?=.*[А-Яа-я])&', 'и'), ' - ', songsstatisticsasc.nam, ' (', TRIM(LEADING '00:' FROM REPLACE(songsstatisticsasc.dur, ':0', ':')), ')'), ',  -', ' -') AS 'Shortest songs', REPLACE(CONCAT(songsstatisticsdesc.artband, REGEXP_REPLACE(REGEXP_REPLACE(REGEXP_REPLACE(IFNULL(songsstatisticsdesc.feat, ''), '^', ', '), ',(?!.*,)', ' &'), '(?=.*[А-Яа-я])&', 'и'), ' - ', songsstatisticsdesc.nam, ' (', TRIM(LEADING '00:' FROM REPLACE(songsstatisticsdesc.dur, ':0', ':')), ')'), ',  -', ' -') AS 'Longest songs' FROM (SELECT *, ROW_NUMBER() OVER(ORDER BY dur, nam) AS rnasc FROM songsstatisticsasc) AS songsstatisticsasc JOIN (SELECT *, ROW_NUMBER() OVER(ORDER BY dur DESC, nam ASC) AS rndesc FROM songsstatisticsasc) AS songsstatisticsdesc ON songsstatisticsasc.rnasc=songsstatisticsdesc.rndesc;
BEGIN
END$
CREATE PROCEDURE SongsStatisticsSoundtracks()
WITH soundtracksstatisticsasc AS(SELECT movanimsergam, nosongs, songsdur FROM Soundtrack) SELECT CONCAT(soundtracksstatisticsasc.movanimsergam, ' (', soundtracksstatisticsasc.nosongs, ', ', TRIM(LEADING '00:' FROM REPLACE(soundtracksstatisticsasc.songsdur, ':0', ':')), ')') AS 'Shortest soundtrack', CONCAT(soundtracksstatisticsdesc.movanimsergam, ' (', soundtracksstatisticsdesc.nosongs, ', ', TRIM(LEADING '00:' FROM REGEXP_REPLACE(REGEXP_REPLACE(REPLACE(soundtracksstatisticsdesc.songsdur, ':0', ':'), '^01', '1'), '^02', '2')), ')') AS 'Longest soundtrack' FROM (SELECT *, ROW_NUMBER() OVER(ORDER BY songsdur, movanimsergam) AS rnasc FROM soundtracksstatisticsasc) AS soundtracksstatisticsasc JOIN (SELECT *, ROW_NUMBER() OVER(ORDER BY songsdur DESC, movanimsergam ASC) AS rndesc FROM soundtracksstatisticsasc) AS soundtracksstatisticsdesc ON soundtracksstatisticsasc.rnasc=soundtracksstatisticsdesc.rndesc;
BEGIN
END$

CREATE TRIGGER MusicArtistBandIDBEFOREINSERTONMUSICARTISTBAND BEFORE INSERT ON `Music artist/band` FOR EACH ROW
BEGIN
SET @artbandid=(SELECT MAX(ID) FROM `Music artist/band`);
SET @relartbandid=(SELECT MAX(ID) FROM `Related music artist/band`);
IF @artbandid<@relartbandid THEN
SET @artbandid=@relartbandid;
END IF;
IF @artbandid IS NULL THEN
SET @artbandid=0;
END IF;
SET NEW.ID=@artbandid+1;
END$
CREATE TRIGGER RelatedMusicArtistBandIDBEFOREINSERTONRELATEDMUSICARTISTBAND BEFORE INSERT ON `Related music artist/band` FOR EACH ROW
BEGIN
SET @relartbandid=(SELECT MAX(ID) FROM `Related music artist/band`);
SET @artbandid=(SELECT MAX(ID) FROM `Music artist/band`);
IF @relartbandid<@artbandid THEN
SET @relartbandid=@artbandid;
END IF;
IF @relartbandid IS NULL THEN
SET @relartbandid=@artbandid;
END IF;
SET NEW.ID=@relartbandid+1;
END$
DELIMITER ;
CREATE TRIGGER MusicArtistBandIDAFTERINSERTONMUSICARTISTBAND AFTER INSERT ON `Music artist/band` FOR EACH ROW
INSERT INTO `Music artist/band identifier`(ID, artband, relartband) VALUES(NEW.ID, NEW.ID, null);
CREATE TRIGGER RelatedMusicArtistBandIDAFTERINSERTONRELATEDMUSICARTISTBAND AFTER INSERT ON `Related music artist/band` FOR EACH ROW
INSERT INTO `Music artist/band identifier`(ID, artband, relartband) VALUES(NEW.ID, null, NEW.ID);
CREATE TRIGGER ArtistsBandsCountAFTERINSERTONMUSICARTISTBAND AFTER INSERT ON `Music artist/band` FOR EACH ROW
CALL NumberOfArtistsBands(NEW.cat, NEW.genr);
DELIMITER $
CREATE TRIGGER ArtistsBandsCountAFTERUPDATEONMUSICARTISTBAND AFTER UPDATE ON `Music artist/band` FOR EACH ROW
BEGIN
CALL NumberOfArtistsBands(OLD.cat, OLD.genr);
CALL NumberOfArtistsBands(NEW.cat, NEW.genr);
END$
DELIMITER ;
CREATE TRIGGER RelatedArtistsBandsCountAFTERINSERTONRELATEDMUSICARTISTBAND AFTER INSERT ON `Related music artist/band` FOR EACH ROW
CALL NumberOfArtistsBands('Жанр', (SELECT `Music artist/band`.genr FROM `Music artist/band` JOIN `Related music artist/band` ON `Music artist/band`.ID=`Related music artist/band`.artband WHERE `Related music artist/band`.artband=NEW.artband LIMIT 1));
DELIMITER $
CREATE TRIGGER SongsCountandDurationAFTERUPDATEONSONG AFTER UPDATE ON Song FOR EACH ROW
BEGIN
IF NEW.dur!=OLD.dur OR NEW.cat!=OLD.cat THEN
CALL SongsCountandDuration(NEW.ID, (SELECT artband FROM `Music artist/band's song` WHERE song=NEW.ID));
END IF;
END$
DELIMITER ;
CREATE TRIGGER SongsCountandDurationAFTERINSERTMUSICARTISTBAND AFTER INSERT ON `Music artist/band's song` FOR EACH ROW
CALL SongsCountandDuration(NEW.song, NEW.artband);
DELIMITER $
CREATE TRIGGER SongsCountandDurationAFTERDELETEONMUSICARTISTBAND AFTER DELETE ON `Music artist/band's song` FOR EACH ROW
BEGIN
CALL SongsCountandDuration(OLD.song, OLD.artband);
DELETE FROM Song WHERE ID=OLD.song;
END$
DELIMITER ;
CREATE TRIGGER SoundtracksCountandDurationAFTERINSERTONSOUNDTRACK AFTER INSERT ON Soundtrack FOR EACH ROW
UPDATE `Audio library` SET noorigartsbands=0, nosongs=(SELECT SUM(nosongs) FROM Soundtrack), songsdur=REPLACE((SELECT DATE_FORMAT(DATE('1000-01-01 00:00:00') + INTERVAL SUM(TIME_TO_SEC(songsdur)) SECOND, '%jd %H:%i:%s') FROM Soundtrack), '001d ', '') WHERE cat='Саундтреки';
CREATE TRIGGER SoundtracksCountandDurationAFTERUPDATEONSOUNDTRACK AFTER UPDATE ON Soundtrack FOR EACH ROW
UPDATE `Audio library` SET noorigartsbands=0, nosongs=(SELECT SUM(nosongs) FROM Soundtrack), songsdur=REPLACE((SELECT DATE_FORMAT(DATE('1000-01-01 00:00:00') + INTERVAL SUM(TIME_TO_SEC(songsdur)) SECOND, '%jd %H:%i:%s') FROM Soundtrack), '001d ', '') WHERE cat='Саундтреки';

INSERT INTO `Audio library`(cat) VALUES('Жанр'),
                                                                          ('Композиторы'),
                                                                          ('Блогеры'),
                                                                          ('Каверы'),
                                                                          ('Саундтреки');
INSERT INTO Genre(nam) VALUES('Авторская песня, Шансон'),
                                                               ('Альтернатива, Инди'),
                                                               ('Блюз'),
                                                               ('ВИА'),
                                                               ('Вокальная музыка'),
                                                               ('Джаз'),
                                                               ('Кантри'),
                                                               ('Легкая, Инструментальная музыка'),
                                                               ('Метал, Ню-метал, Металкор'),
                                                               ('Панк, Эмо, Постхардкор'),
                                                               ('Поп'),
                                                               ('Поп-рок'),
                                                               ('Регги, Реггетон'),
                                                               ('Рок'),
                                                               ('Соул, Фанк, Диско'),
                                                               ('Хип-хоп'),
                                                               ('Электронная музыка');
