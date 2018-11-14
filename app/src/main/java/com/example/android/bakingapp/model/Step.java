package com.example.android.bakingapp.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class Step extends ArrayList<Parcelable> implements Parcelable {

    private int stepId;
    private String shortDescription;
    private String description;
    private String videoUrl;
    private String thumbnailUrl;

    public Step(int stepId, String shortDescription, String description, String videoUrl, String
            thumbnailUrl) {
        this.stepId = stepId;
        this.shortDescription = shortDescription;
        this.description = description;
        this.videoUrl = videoUrl;
        this.thumbnailUrl = thumbnailUrl;
    }

    protected Step(Parcel parcel) {
        stepId = parcel.readInt();
        shortDescription = parcel.readString();
        description = parcel.readString();
        videoUrl = parcel.readString();
        thumbnailUrl = parcel.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(stepId);
        dest.writeString(shortDescription);
        dest.writeString(description);
        dest.writeString(videoUrl);
        dest.writeString(thumbnailUrl);
    }

    @SuppressWarnings("SameReturnValue")
    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Step> CREATOR = new Creator<Step>() {
        @Override
        public Step createFromParcel(Parcel p) {
            return new Step(p);
        }

        @Override
        public Step[] newArray(int size) {
            return new Step[size];
        }
    };

    public int getStepId() {
        return stepId;
    }

    public void setStepId(int stepId) {
        this.stepId = stepId;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }
}
