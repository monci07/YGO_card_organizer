package com.example.ygo_card_organizer;

import javafx.application.Application;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;
import java.util.function.Predicate;

public class mainWindow extends Application {
    private TableView<Card> table = new TableView();
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


        table.setEditable(false);
        TableColumn<Card, Integer> Id = new TableColumn<>("Id");
        TableColumn<Card, String> Binder = new TableColumn<>("Carpeta");
        TableColumn<Card, String> Type = new TableColumn<>("Tipo de carta");
        TableColumn<Card, String> Name = new TableColumn<>("Nombre");
        TableColumn<Card, Integer> count = new TableColumn<>("Cuenta");

        table.getColumns().addAll(Id, Binder, Type, Name, count);

        Id.setPrefWidth(50);
        Id.setCellValueFactory(
                new PropertyValueFactory<>("id"));
        Binder.setPrefWidth(110);
        Binder.setCellValueFactory(
                new PropertyValueFactory<>("bin"));
        Type.setPrefWidth(160);
        Type.setCellValueFactory(
                new PropertyValueFactory<>("type"));
        Name.setPrefWidth(290);
        Name.setCellValueFactory(
                new PropertyValueFactory<>("name"));
        count.setPrefWidth(60);
        count.setCellValueFactory(
                new PropertyValueFactory<>("count"));
        for (int i = 0; i < table.getColumns().size(); i++) {
            ((TableColumn)(table.getColumns().get(i))).setReorderable(false);
            ((TableColumn)(table.getColumns().get(i))).setResizable(false);
        };

        ObservableList<Card> cards = this.handler.getCards();
        FilteredList<Card> items = new FilteredList<>(cards);
        table.setItems(items);

        grid.setHgap(10);
        grid.setVgap(10);

        Label Search = new Label("Search");
        Search.setFont(titles);

        Label cNameL = new Label("Card name:");
        cNameL.setFont(normalT);
        TextField cNameT = new TextField();
        cNameT.setFont(normalT);
        cNameT.addEventHandler(KeyEvent.KEY_RELEASED, (KeyEvent key)->{
            Predicate<Card> name = i -> i.getName().contains(cNameT.getCharacters());
            items.setPredicate(name);
        });

        Label addOldC = new Label("Add card");
        addOldC.setFont(titles);

        table.addEventHandler(MouseEvent.MOUSE_RELEASED, e->{
            Card cName = table.getSelectionModel().getSelectedItem();
            cNameT.setText(cName.getName());
        });

        Button cNameB = new Button("Add card");
        cNameB.setFont(buttons);
        cNameB.setMaxWidth(100);
        cNameB.setOnAction(event ->{
            try{
                Card test = table.getSelectionModel().getSelectedItem();
                if(test.getCount()!=3){
                    this.handler.updateCount(test.getId(), test.getCount()+1);
                    test.setCount(test.getCount()+1);
                    this.table.setRowFactory(card -> new TableRow<Card>(){
                        public void updateItem(Card item){
                            item = test;
                        }
                    });
                }
            }catch (Exception e){
                System.out.println(e);
            }
        });


        Label addCard = new Label("Add new Card");
        addCard.setFont(titles);

        Label cTypeL = new Label("Type:");
        cTypeL.setFont(normalT);
        ComboBox cTypes = new ComboBox<>();
        cTypes.getItems().addAll(this.handler.getCardType());

        Label binderL = new Label("Binder:");
        cTypeL.setFont(normalT);
        ComboBox binder = new ComboBox<>();
        binder.getItems().addAll(this.handler.getBinders());

        Button nCNameB = new Button("Add new card");
        nCNameB.setFont(buttons);
        nCNameB.setMaxWidth(125);
        nCNameB.setOnAction(actionEvent -> {
            if(cTypes.getValue() != null || binder.getValue() != null) {
                Card newCard = new Card(binder.getValue().toString(), cTypes.getValue().toString(), cNameT.getCharacters().toString(), 1);
                int id = this.handler.insertCard(newCard);
                newCard.setId(id);
                cards.add(newCard);
            }
        });

        grid.add(Search,0,0);
        grid.add(cNameL,0,1);grid.add(cNameT,1,1);


        grid.add(addOldC,0,2);
        grid.add(cNameB,0,3,2,2);


        grid.add(addCard,0,5);
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