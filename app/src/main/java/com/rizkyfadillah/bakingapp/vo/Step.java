package com.rizkyfadillah.bakingapp.vo;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * @author rizkyfadillah on 30/07/2017.
 */
@Entity(foreignKeys = {
        @ForeignKey(entity = Recipe.class,
                parentColumns = "id",
                childColumns = "recipeId")}, indices = {
        @Index(value = "recipeId")
})
public class Step {
    @PrimaryKey
    public final String stepId;
    public final int id;
    public final int recipeId;
    public final String shortDescription;
    public final String description;
    public final String videoURL;
    public final String thumbnailURL;

    public Step(String stepId, int id, int recipeId, String shortDescription, String description, String videoURL, String thumbnailURL) {
        this.stepId = stepId;
        this.id = id;
        this.recipeId = recipeId;
        this.shortDescription = shortDescription;
        this.description = description;
        this.videoURL = videoURL;
        this.thumbnailURL = thumbnailURL;
    }

    public Step(Step step, String stepId, int recipeId) {
        this.stepId = stepId;
        this.id = step.id;
        this.recipeId = recipeId;
        this.shortDescription = step.shortDescription;
        this.description = step.description;
        this.videoURL = step.videoURL;
        this.thumbnailURL = step.thumbnailURL;
    }

    public Step(int id, String description, String videoUrl) {
        this.description = description;
        this.videoURL = videoUrl;
        this.thumbnailURL = null;
        this.recipeId = 0;
        this.stepId = null;
        this.id = 0;
        this.shortDescription = null;
    }

}
