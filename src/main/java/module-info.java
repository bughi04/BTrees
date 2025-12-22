module org.example.adsproject {
    requires javafx.controls;
    requires javafx.fxml;
    requires guru.nidi.graphviz;


    opens org.example.adsproject to javafx.fxml;
    exports org.example.adsproject;
}