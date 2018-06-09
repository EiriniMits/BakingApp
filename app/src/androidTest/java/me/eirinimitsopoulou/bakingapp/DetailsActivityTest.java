package me.eirinimitsopoulou.bakingapp;

import android.content.Intent;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import me.eirinimitsopoulou.bakingapp.Activities.DetailsActivity;
import me.eirinimitsopoulou.bakingapp.Data.Ingredients;
import me.eirinimitsopoulou.bakingapp.Data.Recipe;
import me.eirinimitsopoulou.bakingapp.Data.Steps;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import me.eirinimitsopoulou.bakingapp.R;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

/**
 * Created by eirinimitsopoulou on 30/05/2018.
 */

@RunWith(AndroidJUnit4.class)
public class DetailsActivityTest {

    private final String RECIPE_NAME = "Yellow Cake";

    @Rule
    public ActivityTestRule<DetailsActivity> mRule =
            new ActivityTestRule<DetailsActivity>(DetailsActivity.class) {
                @Override
                protected Intent getActivityIntent() {
                    Ingredients[] ingredients = new Ingredients[1];
                    ingredients[0] = new Ingredients();
                    ingredients[0].setQuantity(400);
                    ingredients[0].setMeasure("G");
                    ingredients[0].setIngredient("sifted cake flour");

                    Steps[] steps = new Steps[2];
                    steps[0] = new Steps();
                    steps[0].setId(1);
                    steps[0].setShortDescription("Recipe Introduction");
                    steps[0].setDescription("Recipe Introduction");
                    steps[0].setVideoURL("https://d17h27t6h515a5.cloudfront.net/topher/2017/April/58ffd974_-intro-creampie/-intro-creampie.mp4");
                    steps[0].setThumbnailURL("");

                    steps[1] = new Steps();
                    steps[1].setId(2);
                    steps[1].setShortDescription("Starting prep");
                    steps[1].setDescription("1. Preheat the oven ....");
                    steps[1].setVideoURL("");
                    steps[1].setThumbnailURL("");

                    Recipe recipe = new Recipe(1, RECIPE_NAME, ingredients, steps);

                    Intent intent = new Intent();
                    intent.putExtra(DetailsActivity.RECIPE, recipe);
                    return intent;
                }
            };

    @Test
    public void recipeDetailsVideoClick() {
        onView(withId(R.id.steps_recyclerview))
                .perform(actionOnItemAtPosition(0, click()));

        onView(withId(R.id.step_description)).check(matches(withText("Recipe Introduction")));
        onView(withId(R.id.exoPlayer)).check(matches(isDisplayed()));
    }

    @Test
    public void recipeDetailsNoVideoClick() {
        onView(withId(R.id.steps_recyclerview))
                .perform(actionOnItemAtPosition(1, click()));

        onView(withId(R.id.step_description)).check(matches(withText("1. Preheat the oven ....")));
        onView(withId(R.id.no_video)).check(matches(isDisplayed()));
    }
}
