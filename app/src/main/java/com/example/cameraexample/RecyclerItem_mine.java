package com.example.cameraexample;

import android.os.Parcel;
import android.os.Parcelable;

public class RecyclerItem_mine implements Parcelable{
    private int idForFoodDrawable;
    private String titleStr;
    private String hashStr;
    private String url;

    public RecyclerItem_mine() {

    };


    public RecyclerItem_mine(Parcel parcel) {
        this.idForFoodDrawable = parcel.readInt();
        this.titleStr = parcel.readString();
        this.hashStr = parcel.readString();
        this.url = parcel.readString();
    }

    public static final Creator<RecyclerItem_mine> CREATOR = new Creator<RecyclerItem_mine>() {
        @Override
        public RecyclerItem_mine createFromParcel(Parcel parcel) {
            return new RecyclerItem_mine(parcel);
        }

        @Override
        public RecyclerItem_mine[] newArray(int size) {
            return new RecyclerItem_mine[size];
        }
    };

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.idForFoodDrawable);
        dest.writeString(this.titleStr);
        dest.writeString(this.hashStr);
        dest.writeString(this.url);
    }

    @Override
    public int describeContents() {
        return 0;
    }


    public void setIdForFoodDrawable(int id) {
        idForFoodDrawable = id;
    }
    public void setTitle(String title) {
        titleStr = title ;
    }
    public void setHash(String hash) {
        hashStr = hash ;
    }
    public void setUrl(String url) {
        this.url = url;
    }


    public int getIdForFoodDrawable() {
        return this.idForFoodDrawable;
    }
    public String getTitle() {
        return this.titleStr ;
    }
    public String getHash() {
        return this.hashStr ;
    }
    public String getUrl() {
        return this.url;
    }

}
