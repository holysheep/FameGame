package com.example.lexy.materialtest.pojo;


import java.util.Date;

public class GameCat {


    private String name;

    private int id;

    private String typeImage;

    private Integer releaseDay;

    private String releaseMonth;

    private String deck;


    public GameCat() {

    }


    public GameCat(String name,
                   int id,
                   String typeImage,
                   Integer releaseDay,
                   String releaseMonth,
                   String deck ) {

        this.id = id;
        this.name = name;
        this.typeImage = typeImage;
        this.releaseDay = releaseDay;
        this.releaseMonth = releaseMonth;
        this.deck = deck;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTypeImage() {
        return typeImage;
    }

    public void setTypeImage(String typeImage) {
        this.typeImage = typeImage;
    }

    public Integer getReleaseDay() {
        return releaseDay;
    }

    public void setReleaseDay(Integer releaseDay) {
        this.releaseDay = releaseDay;
    }

    public String getReleaseMonth() {
        return releaseMonth;
    }

    public void setReleaseMonth(String releaseMonth) {
        this.releaseMonth = releaseMonth;
    }

    public String getDeck() {
        return deck;
    }

    public void setDeck(String deck) {
        this.deck = deck;
    }




    @Override
    public String toString() {

        return  "\nID: " + id +
                "\nName " + name +
                "\nDate Day " + releaseDay +
                "\nDate month " + releaseMonth +
                "\nDeck " + deck +
                "\n";
    }



}


