package me.eirinimitsopoulou.bakingapp.Data;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by eirinimitsopoulou on 30/05/2018.
 */

public class Recipe implements Parcelable {
    private final int id;
    private final String name;
    private final Ingredients[] ingredients;
    private final Steps[] steps;

    public Recipe(int id, String name, Ingredients[] ingredients, Steps[] steps) {
        this.id = id;
        this.name = name;
        this.ingredients = ingredients;
        this.steps = steps;
    }

    private Recipe(Parcel in) {
        id = in.readInt();
        name = in.readString();
        ingredients = in.createTypedArray(Ingredients.CREATOR);
        steps = in.createTypedArray(Steps.CREATOR);
    }

    public static final Creator<Recipe> CREATOR = new Creator<Recipe>() {
        @Override
        public Recipe createFromParcel(Parcel in) {
            return new Recipe(in);
        }

        @Override
        public Recipe[] newArray(int size) {
            return new Recipe[size];
        }
    };

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Ingredients[] getIngredients() {
        return ingredients;
    }

    public Steps[] getSteps() {
        return steps;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(name);
        parcel.writeTypedArray(ingredients, i);
        parcel.writeTypedArray(steps, i);
    }
}
