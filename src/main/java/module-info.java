module pers.cierra_runis.diary {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires com.google.gson;
    requires java.net.http;

    opens pers.cierra_runis.diary to javafx.fxml;

    exports pers.cierra_runis.diary.api to com.google.gson;
    exports pers.cierra_runis.diary;
}
