package me.eirinimitsopoulou.bakingapp.Activities;

import android.content.Intent;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import me.eirinimitsopoulou.bakingapp.Fragments.RecipeDetailsFragment;
import me.eirinimitsopoulou.bakingapp.Fragments.StepsFragment;
import me.eirinimitsopoulou.bakingapp.Data.Recipe;
import me.eirinimitsopoulou.bakingapp.R;

/**
 * Created by eirinimitsopoulou on 30/05/2018.
 */

public class DetailsActivity extends AppCompatActivity implements RecipeDetailsFragment.OnRecipeDetailInteractionListener,
        StepsFragment.OnRecipeStepDetailInteractionListener {

    public static final String RECIPE = "recipe";

    private Recipe mRecipe;
    private boolean mIsTablet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null && bundle.containsKey(RECIPE)) {
            mRecipe = bundle.getParcelable(RECIPE);
        }

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(mRecipe.getName());
        mIsTablet = getResources().getBoolean(R.bool.tablet);

        if (savedInstanceState == null) {
            RecipeDetailsFragment recipeDetailsFragment = RecipeDetailsFragment.newInstance(mRecipe);
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.activity_detail, recipeDetailsFragment)
                    .commit();
        }
    }

    @Override
    public void onStepClicked(int pos) {

        if (!mIsTablet) {
            Intent intent = new Intent(this, StepsActivity.class);
            intent.putExtra(StepsActivity.RECIPE, mRecipe);
            intent.putExtra(StepsActivity.STEP_ID, pos);
            startActivity(intent);
        } else {
            StepsFragment fragment = StepsFragment.newInstance(mRecipe.getSteps()[pos],
                    mRecipe.getSteps().length - 1);

            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.activity_step_detail, fragment);
            transaction.commit();
        }
    }

    @Override
    public void onChangeToStep(int pos) {
        onStepClicked(pos);
    }
}
