package com.example.ygo_card_organizer;

import com.microsoft.sqlserver.jdbc.SQLServerDriver;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class MySQL_Connector {
    //private String url="jdbc:sqlserver://DESKTOP-P82HCC1;encrypt=true;trustServerCertificate=true;integratedSecurity=true;databaseName=ygo_card_org";
    private String url="jdbc:sqlserver://DESKTOP-P82HCC1;encrypt=true;trustServerCertificate=true;database=ygo_card_org";
    private String username="dmoncivais";
    private String password="1234";
    private Connection connection;
    private Statement statement;
    public MySQL_Connector(){
        try{
            //Class.forName("com.mysql.cj.jdbc.Driver");
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");

            DriverManager.registerDriver(new com.microsoft.sqlserver.jdbc.SQLServerDriver());
            //this.connection = DriverManager.getConnection(this.url);
            this.connection = DriverManager.getConnection(url,username,password);
            this.statement = this.connection.createStatement();
        }catch (SQLException e){
            System.out.println(e);
        }
        catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }/**/
    }
    public ObservableList<Card> getCards(){
        String query = "Select c.idCard, CONCAT(b.idBinder, ' ', b.color), ct.type, c.name, c.count from card as c inner join binder as b on  c.idBinder = b.idBinder inner join ctypes as ct on c.idType = ct.idTypes ORDER BY c.idCard;";
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

    public void updateCount(int cardID, int count){
        String query = "Update card set count = " + count + " WHERE idCard = " + cardID + ";";
        try{
            this.statement.executeUpdate(query);
        }catch (Exception e){
            System.out.println(e);
        }
    }

    public int insertCard(Card card){
        String query="SELECT insert_card('"+card.getName()+"', "+Character.getNumericValue(card.getBin().toString().charAt(0))+", '"+card.getType()+"', "+card.getCount()+")";
        ResultSet resultSet;
        int id=0;
        try{
            resultSet = this.statement.executeQuery(query);
            while(resultSet.next()){
                id = resultSet.getInt(1);
            }
        }catch (Exception e){
            System.out.println(e);
        }
        return id;
    }

    public void closeConnection(){
        try {
            this.connection.close();
        }catch (Exception e) {
            System.out.println(e);
        }
    }
}
