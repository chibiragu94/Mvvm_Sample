package com.example.mvvm_sample.modules.restuarants.data.model;

import android.widget.ImageView;
import android.widget.TextView;

import androidx.databinding.BindingAdapter;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.squareup.picasso.Picasso;

@Entity(tableName = "Restuarant")
public class RestuarantDb {

    @PrimaryKey
    @ColumnInfo(name = "restuarant_Id")
    private int restuarantId;

    @ColumnInfo(name = "restuarant_Name")
    private String name;

    @ColumnInfo(name = "restuarant_Address")
    private String address;

    @ColumnInfo(name = "restuarant_Locality")
    private String locality;

    @ColumnInfo(name = "restuarant_Latitude")
    private double latitude;

    @ColumnInfo(name = "restuarant_Longitude")
    private double longitude;

    @ColumnInfo(name = "restuarant_Timings")
    private String timings;

    @ColumnInfo(name = "restuarant_ThumbImage")
    private String thumbImage;

    @ColumnInfo(name = "restuarant_cost_for_two")
    private int costForTwo;

    @ColumnInfo(name = "restuarant_pickUpStatus")
    private int pickUpStatus;

    @ColumnInfo(name = "restuarant_takeAwayStatus")
    private int takeAwayStatus;

    @ColumnInfo(name = "restuarant_averageRating")
    private String averageRating;

    public int getRestuarantId() {
        return restuarantId;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public String getTimings() {
        return timings;
    }

    public String getThumbImage() {
        return thumbImage;
    }

    public int getCostForTwo() {
        return costForTwo;
    }

    public int getPickUpStatus() {
        return pickUpStatus;
    }

    public int getTakeAwayStatus() {
        return takeAwayStatus;
    }

    public String getLocality() {
        return locality;
    }

    public String getAverageRating() {
        return averageRating;
    }

    public void setAverageRating(String averageRating) {
        this.averageRating = averageRating;
    }

    public void setLocality(String locality) {
        this.locality = locality;
    }

    public void setRestuarantId(int restuarantId) {
        this.restuarantId = restuarantId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public void setTimings(String timings) {
        this.timings = timings;
    }

    public void setThumbImage(String thumbImage) {
        this.thumbImage = thumbImage;
    }

    public void setCostForTwo(int costForTwo) {
        this.costForTwo = costForTwo;
    }

    public void setPickUpStatus(int pickUpStatus) {
        this.pickUpStatus = pickUpStatus;
    }

    public void setTakeAwayStatus(int takeAwayStatus) {
        this.takeAwayStatus = takeAwayStatus;
    }

    // for binding the adapters to item views
    @BindingAdapter("android:text")
    public static void setText(TextView view, int value) {
        view.setText(""+value);
    }


    @BindingAdapter("android:src")
    public static void setsrc(ImageView imageView, String url) {
        Picasso.get().load(url).into(imageView);
    }
}

