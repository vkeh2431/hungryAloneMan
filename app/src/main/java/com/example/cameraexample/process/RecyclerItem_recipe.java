package com.example.cameraexample.process;

import android.os.Parcel;
import android.os.Parcelable;

public class RecyclerItem_recipe implements Parcelable{
    private int idForFoodDrawable;
    private String titleStr;
    private String hashStr;
    private String url;
    private boolean checkedOrNot;

    public RecyclerItem_recipe() {

    };


    public RecyclerItem_recipe(Parcel parcel) {
        this.idForFoodDrawable = parcel.readInt();
        this.titleStr = parcel.readString();
        this.hashStr = parcel.readString();
        this.url = parcel.readString();
        this.checkedOrNot = parcel.readBoolean();
    }

    public static final Parcelable.Creator<RecyclerItem_recipe> CREATOR = new Parcelable.Creator<RecyclerItem_recipe>() {
        @Override
        public RecyclerItem_recipe createFromParcel(Parcel parcel) {
            return new RecyclerItem_recipe(parcel);
        }

        @Override
        public RecyclerItem_recipe[] newArray(int size) {
            return new RecyclerItem_recipe[size];
        }
    };

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.idForFoodDrawable);
        dest.writeString(this.titleStr);
        dest.writeString(this.hashStr);
        dest.writeString(this.url);
        dest.writeBoolean(this.checkedOrNot);
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
    public void setCheckedOrNot(boolean checkedOrNot) {
        this.checkedOrNot = checkedOrNot;
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
    public boolean getCheckedOrNot() {
        return this.checkedOrNot;
    }
    public String getUrl() {
        return this.url;
    }
}
