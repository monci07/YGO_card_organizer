package com.example.ygo_card_organizer;

public class Card {
    private int id;
    private String bin;
    private String type;
    private String name;
    private int count;

    public Card(int id, String bin, String type, String name, int count) {
        this.id = id;
        this.bin = bin;
        this.type = type;
        this.name = name;
        this.count = count;
    }

    public int getId() {
        return id;
    }

    public String getBin() {
        return bin;
    }

    public String getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public int getCount() {
        return count;
    }

    public void setId(int id) {
        this.id = id;
    }public void setBin(String bin) {
        this.bin = bin;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
