package me.eirinimitsopoulou.bakingapp.Utils;

import me.eirinimitsopoulou.bakingapp.Data.Ingredients;
import me.eirinimitsopoulou.bakingapp.Data.Recipe;
import me.eirinimitsopoulou.bakingapp.Data.Steps;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by eirinimitsopoulou on 30/05/2018.
 */

public class JsonUtils {
    private static final String RECIPE_ID = "id";
    private static final String RECIPE_NAME = "name";
    private static final String INGREDIENTS = "ingredients";
    private static final String INGREDIENTS_QUANTITY = "quantity";
    private static final String INGREDIENTS_MEASURE = "measure";
    private static final String INGREDIENTS_INGREDIENT = "ingredient";
    private static final String STEPS = "steps";
    private static final String STEPS_ID = "id";
    private static final String STEPS_SHORT_DESCRIPTION = "shortDescription";
    private static final String STEPS_DESCRIPTION = "description";
    private static final String STEPS_VIDEO_URL = "videoURL";
    private static final String STEPS_THUMBNAIL_URL = "thumbnailURL";

    public static List<Recipe> parseRecipesJson(String json) {

        try {
            List<Recipe> recipesList = new ArrayList<>();
            JSONArray recipesJson = new JSONArray(json);
            for (int i = 0; i < recipesJson.length(); ++i) {
                JSONObject recipeJson = recipesJson.getJSONObject(i);
                int id = recipeJson.optInt(RECIPE_ID);
                String name = recipeJson.optString(RECIPE_NAME);
                Ingredients[] ingredients = extractIngredients(recipeJson.getJSONArray(INGREDIENTS));
                Steps[] steps = extractSteps(recipeJson.getJSONArray(STEPS));
                recipesList.add(new Recipe(id, name, ingredients, steps));
            }
            return recipesList;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }


    private static Ingredients[] extractIngredients(JSONArray ingredientsJson) throws JSONException {

        Ingredients[] ingredients = new Ingredients[ingredientsJson.length()];
        for (int i = 0; i < ingredientsJson.length(); ++i) {
            JSONObject ingredientJson = ingredientsJson.getJSONObject(i);
            ingredients[i] = new Ingredients();
            ingredients[i].setIngredient(ingredientJson.optString(INGREDIENTS_INGREDIENT));
            ingredients[i].setMeasure(ingredientJson.optString(INGREDIENTS_MEASURE));
            ingredients[i].setQuantity(ingredientJson.optInt(INGREDIENTS_QUANTITY));
        }
        return ingredients;
    }

    private static Steps[] extractSteps(JSONArray stepsJson) throws JSONException {

        Steps[] steps = new Steps[stepsJson.length()];
        for (int i = 0; i < stepsJson.length(); ++i) {
            JSONObject stepJson = stepsJson.getJSONObject(i);
            steps[i] = new Steps();
            steps[i].setId(stepJson.optInt(STEPS_ID));
            steps[i].setDescription(stepJson.optString(STEPS_DESCRIPTION));
            steps[i].setShortDescription(stepJson.optString(STEPS_SHORT_DESCRIPTION));
            steps[i].setThumbnailURL(stepJson.optString(STEPS_THUMBNAIL_URL));
            steps[i].setVideoURL(stepJson.optString(STEPS_VIDEO_URL));
        }
        return steps;
    }
}
