package com.rizkyfadillah.bakingapp.vo;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;

/**
 * @author rizkyfadillah on 30/07/2017.
 */
@Entity(foreignKeys = {
        @ForeignKey(entity = Recipe.class,
                parentColumns = "id",
                childColumns = "recipe_id")}, indices = {
        @Index(value = "recipe_id")
})
public class Step {
    @PrimaryKey
    public final String stepId;
    public final int id;
    public final int recipe_id;
    public final String shortDescription;
    public final String description;
    public final String videoURL;
    public final String thumbnailURL;

    public Step(String stepId, int id, int recipe_id, String shortDescription, String description, String videoURL, String thumbnailURL) {
        this.stepId = stepId;
        this.id = id;
        this.recipe_id = recipe_id;
        this.shortDescription = shortDescription;
        this.description = description;
        this.videoURL = videoURL;
        this.thumbnailURL = thumbnailURL;
    }

    public Step(Step step, String stepId, int recipe_id) {
        this.stepId = stepId;
        this.id = step.id;
        this.recipe_id = recipe_id;
        this.shortDescription = step.shortDescription;
        this.description = step.description;
        this.videoURL = step.videoURL;
        this.thumbnailURL = step.thumbnailURL;
    }

}
