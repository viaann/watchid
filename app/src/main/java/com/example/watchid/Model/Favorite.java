package com.example.watchid.Model;

import android.os.Parcel;
import android.os.Parcelable;

public class Favorite implements Parcelable {
    private int id;
    private int mId;
    private String favPoster;
    private String favTitle;
    private double favRating;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getmId() {
        return mId;
    }

    public void setmId(int mId) {
        this.mId = mId;
    }

    public String getFavPoster() {
        return favPoster;
    }

    public void setFavPoster(String favPoster) {
        this.favPoster = favPoster;
    }

    public String getFavTitle() {
        return favTitle;
    }

    public void setFavTitle(String favTitle) {
        this.favTitle = favTitle;
    }

    public double getFavRating() {
        return favRating;
    }

    public void setFavRating(double favRating) {
        this.favRating = favRating;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeInt(this.mId);
        dest.writeString(this.favPoster);
        dest.writeString(this.favTitle);
        dest.writeDouble(this.favRating);
    }

    public Favorite() {
    }

    public Favorite(int id, int mId, String favTitle, String favPoster, double favRating) {
        this.id = id;
        this.mId = mId;
        this.favTitle = favTitle;
        this.favPoster = favPoster;
        this.favRating = favRating;
    }

    protected Favorite(Parcel in) {
        this.id = in.readInt();
        this.mId = in.readInt();
        this.favPoster = in.readString();
        this.favTitle = in.readString();
        this.favRating = in.readDouble();
    }

    public static final Parcelable.Creator<Favorite> CREATOR = new Parcelable.Creator<Favorite>() {
        @Override
        public Favorite createFromParcel(Parcel source) {
            return new Favorite(source);
        }

        @Override
        public Favorite[] newArray(int size) {
            return new Favorite[size];
        }
    };
}
