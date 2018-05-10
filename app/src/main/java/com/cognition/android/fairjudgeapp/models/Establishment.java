package com.cognition.android.fairjudgeapp.models;

public class Establishment {

    public static final String TABLE_NAME = "establishments";

    public static final String COLUMN_ID = "id";
    public static final String COLUMN_REVIEWER_ID = "reviewer_id";
    public static final String COLUMN_ESTABLISHMENT_NAME = "establishment_name";
    public static final String COLUMN_ESTABLISHMENT_TYPE = "establishment_type";
    public static final String COLUMN_FOOD_TYPE = "food_type";
    public static final String COLUMN_LOCATION = "location";
    public static final String COLUMN_RATING = "rating";
    public static final String COLUMN_TIMESTAMP = "timestamp";

    public static final String CREATE_TABLE = String.format(
            "CREATE TABLE %s (" +
                    "%s INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "%s VARCHAR, " +
                    "%s VARCHAR, " +
                    "%s VARCHAR, " +
                    "%s VARCHAR, " +
                    "%s VARCHAR, " +
                    "%s INTEGER," +
                    "%s DATETIME DEFAULT CURRENT_TIMESTAMP" +
                    ")",

            TABLE_NAME,
            COLUMN_ID,
            COLUMN_REVIEWER_ID,
            COLUMN_ESTABLISHMENT_NAME,
            COLUMN_ESTABLISHMENT_TYPE,
            COLUMN_FOOD_TYPE,
            COLUMN_LOCATION,
            COLUMN_RATING,
            COLUMN_TIMESTAMP
    );

    public static final String SELECT_ALL = String.format(
            "SELECT * FROM %s ORDER BY %s DESC",
            TABLE_NAME,
            COLUMN_TIMESTAMP
    );

    public static String[] getAllColumns() {
        return new String[]{
                COLUMN_ID,
                COLUMN_REVIEWER_ID,
                COLUMN_ESTABLISHMENT_NAME,
                COLUMN_ESTABLISHMENT_TYPE,
                COLUMN_FOOD_TYPE,
                COLUMN_LOCATION,
                COLUMN_RATING,
                COLUMN_TIMESTAMP
        };
    }

    private int id;
    private String reviewerId;
    private String establishmentName;
    private String establishmentType;
    private String foodType;
    private String location;
    private int rating;
    private String timeStamp;

    public Establishment(String reviewerId, String establishmentName, String establishmentType, String foodType, String location, int rating) {
        this.reviewerId = reviewerId;
        this.establishmentName = establishmentName;
        this.establishmentType = establishmentType;
        this.foodType = foodType;
        this.location = location;
        this.rating = rating;
    }

    public Establishment(int id, String reviewerId, String establishmentName, String establishmentType, String foodType, String location, int rating, String timeStamp) {
        this.id = id;
        this.reviewerId = reviewerId;
        this.establishmentName = establishmentName;
        this.establishmentType = establishmentType;
        this.foodType = foodType;
        this.location = location;
        this.rating = rating;
        this.timeStamp = timeStamp;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getReviewerId() {
        return reviewerId;
    }

    public void setReviewerId(String reviewerId) {
        this.reviewerId = reviewerId;
    }

    public String getEstablishmentName() {
        return establishmentName;
    }

    public void setEstablishmentName(String establishmentName) {
        this.establishmentName = establishmentName;
    }

    public String getEstablishmentType() {
        return establishmentType;
    }

    public void setEstablishmentType(String establishmentType) {
        this.establishmentType = establishmentType;
    }

    public String getFoodType() {
        return foodType;
    }

    public void setFoodType(String foodType) {
        this.foodType = foodType;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }
}
