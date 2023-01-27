package com.example.ygo_card_organizer;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class MySQL_Connector {
    private String url="jdbc:mysql://localhost:3306/ygo_card_org";
    private String username="root";
    private String password="";
    private Connection connection;
    private Statement statement;
    public MySQL_Connector(){
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            this.connection = DriverManager.getConnection(this.url,this.username,this.password);
            this.statement = this.connection.createStatement();
        }catch (Exception e){
            System.out.println(e);
        }
    }
    public ObservableList<Card> getCards(){
        String query = "Select c.idCard, CONCAT(b.idBinder, ' ', b.color), ct.type, c.name, c.count from card as c inner join binder as b on b.idBinder = c.idBinder inner join ctypes as ct on ct.idTypes = c.idType;";
        ObservableList<Card> result = FXCollections.observableArrayList();
        try{
            ResultSet resultSet = this.statement.executeQuery(query);
            while(resultSet.next()){
                result.add( new Card(resultSet.getInt(1),resultSet.getString(2), resultSet.getString(3), resultSet.getString(4), resultSet.getInt(5)));
            }
        }catch(Exception e){
            System.out.println(e);
        }
        return result;
    }

    public List<String> getBinders(){
        String query = "Select CONCAT(idBinder,' ', color) from binder;";
        return getStrings(query);
    }

    public List<String> getCardType(){
        String query = "Select type from ctypes;";
        return getStrings(query);
    }

    private List<String> getStrings(String query) {
        List<String> ctype = new ArrayList<>();
        try {
            ResultSet resultSet = this.statement.executeQuery(query);
            while(resultSet.next()){
                ctype.add(resultSet.getString(1));
            }
        }catch (Exception e){
            System.out.println(e);
        }
        return ctype;
    }

    public void closeConnection(){
        try {
            this.connection.close();
        }catch (Exception e) {
            System.out.println(e);
        }
    }
}
