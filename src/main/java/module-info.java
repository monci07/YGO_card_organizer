module com.example.ygo_card_organizer {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;

    opens com.example.ygo_card_organizer to javafx.fxml;
    exports com.example.ygo_card_organizer;
}