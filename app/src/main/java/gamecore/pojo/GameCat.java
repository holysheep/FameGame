package gamecore.pojo;


import android.os.Parcel;
import android.os.Parcelable;


public class GameCat implements Parcelable {
    public static final Parcelable.Creator<GameCat> CREATOR
            = new Parcelable.Creator<GameCat>() {
        public GameCat createFromParcel(Parcel in) {
            return new GameCat(in);
        }

        public GameCat[] newArray(int size) {
            return new GameCat[size];
        }
    };

    private String name;
    private int id;
    private String typeImage;
    private Integer releaseDay;
    private String releaseMonth;
    private String deck;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDeveloper() {
        return developer;
    }

    public void setDeveloper(String developer) {
        this.developer = developer;
    }

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    private String description;
    private String developer;
    private String platform;
    private String genre;


    public GameCat() {
    }

    public GameCat(Parcel input) {
        id = input.readInt();
        name = input.readString();
        typeImage = input.readString();
        deck = input.readString();
        releaseMonth = input.readString();
        releaseDay = input.readInt();
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeString(name);
        dest.writeInt(id);
        dest.writeString(typeImage);
        dest.writeInt(releaseDay);
        dest.writeString(releaseMonth);
        dest.writeString(deck);
    }
}
