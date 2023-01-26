package com.example.ygo_card_organizer;

import javafx.application.Application;

import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

public class mainWindow extends Application {
    private TableView table = new TableView();
    private MySQL_Connector handler = new MySQL_Connector();

    @Override
    public void start(Stage stage) throws IOException {

        stage.setTitle("YGO Card Organizer!");
        stage.setResizable(false);
        GridPane grid = new GridPane();
        Scene scene = new Scene(grid,985,400);
        Font titles = Font.font("Default", FontWeight.BOLD, FontPosture.REGULAR, 20);
        Font normalT = Font.font("Default", FontPosture.REGULAR, 12);
        Font buttons = Font.font("Default",  FontWeight.BOLD, FontPosture.REGULAR, 13);
        Label Search = new Label("Search/add card");
        Search.setFont(titles);

        Label cNameSL = new Label("Card name:");
        cNameSL.setFont(normalT);
        TextField cNameST = new TextField();
        cNameST.setFont(normalT);
        Button cNameB = new Button("Add card");
        cNameB.setFont(buttons);
        cNameB.setMaxWidth(100);

        Label addCard = new Label("Add new Card");
        addCard.setFont(titles);

        Label cNameAL = new Label("Card name:");
        cNameAL.setFont(normalT);
        TextField cNameAT = new TextField();
        cNameAT.setFont(normalT);
        Label cTypeL = new Label("Type:");
        cTypeL.setFont(normalT);
        ComboBox cTypes = new ComboBox<>();
        //the next line will be replaced on the future.
        cTypes.getItems().addAll("Monster", "Pendulum", "Fusion", "Ritual", "XYZ", "Link", "Synchro");
        Label binderL = new Label("Binder:");
        cTypeL.setFont(normalT);
        ComboBox binder = new ComboBox<>();
        //the next line will be replaced on the future.
        binder.getItems().addAll("Roja 1", "Negra 1");
        Button nCNameB = new Button("Add new card");
        nCNameB.setFont(buttons);
        nCNameB.setMaxWidth(125);

        table.setEditable(false);
        System.out.println(table.isEditable());
        TableColumn<Card, Integer> Id = new TableColumn<>("Id");
        TableColumn<Card, String> Binder = new TableColumn<>("Carpeta");
        TableColumn<Card, String> Type = new TableColumn<>("Tipo de carta");
        TableColumn<Card, String> Name = new TableColumn<>("Nombre");
        TableColumn<Card, Integer> count = new TableColumn<>("Cuenta");

        table.getColumns().addAll(Id, Binder, Type, Name, count);

        Id.setPrefWidth(50);
        Id.setCellValueFactory(
                new PropertyValueFactory<>("id"));
        Binder.setPrefWidth(100);
        Binder.setCellValueFactory(
                new PropertyValueFactory<>("bin"));
        Type.setPrefWidth(150);
        Type.setCellValueFactory(
                new PropertyValueFactory<>("type"));
        Name.setPrefWidth(300);
        Name.setCellValueFactory(
                new PropertyValueFactory<>("name"));
        count.setPrefWidth(50);
        count.setCellValueFactory(
                new PropertyValueFactory<>("count"));
        for (int i = 0; i < table.getColumns().size(); i++) {
            ((TableColumn)(table.getColumns().get(i))).setReorderable(false);
            ((TableColumn)(table.getColumns().get(i))).setResizable(false);
        };

        List<Card> cards = this.handler.getCards();
        for(int i = 0; i< cards.size(); i++){
            table.getItems().add(cards.get(i));
        }


        grid.setHgap(10);
        grid.setVgap(10);

        grid.add(Search,0,0);
        grid.add(cNameSL,0,1);grid.add(cNameST,1,1);
        grid.add(cNameB,0,2,2,2);


        grid.add(addCard,0,4);
        grid.add(cNameAL,0,5);grid.add(cNameAT,1,5);
        grid.add(cTypeL,0,6);grid.add(cTypes,1,6);
        grid.add(binderL,0,7);grid.add(binder,1,7);
        grid.add(nCNameB,0,8,2,2);

        grid.add(table,2,0,1,15);
        stage.setScene(scene);
        stage.show();
    }
    @Override
    public void stop(){
        this.handler.closeConnection();
    }
    public static void main(String[] args) {
        launch();
    }
}