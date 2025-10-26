module com.example.audiolibrary
{
    requires javafx.fxml;
    requires javafx.controls;
    requires javafx.base;
    requires java.desktop;
    requires java.sql;

    opens com.example.audiolibrary to javafx.fxml;
    exports com.example.audiolibrary;
}