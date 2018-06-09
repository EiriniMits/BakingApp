package me.eirinimitsopoulou.bakingapp.Activities;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import me.eirinimitsopoulou.bakingapp.Data.Recipe;
import me.eirinimitsopoulou.bakingapp.Fragments.StepsFragment;
import me.eirinimitsopoulou.bakingapp.R;

/**
 * Created by eirinimitsopoulou on 30/05/2018.
 */

public class StepsActivity extends AppCompatActivity implements StepsFragment.OnRecipeStepDetailInteractionListener {

    public static final String RECIPE = "recipe";
    public static final String STEP_ID = "step_id";
    private static final String STEP_FRAGMENT = "step_fragment";
    private StepsFragment mStepsFragment;
    private Recipe mRecipe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step_detail);

        Bundle bundle = getIntent().getExtras();
        int stepId;

        if (bundle == null) {
            return;
        }

        mRecipe = bundle.getParcelable(RECIPE);
        stepId = bundle.getInt(STEP_ID);

        getSupportActionBar().setTitle(mRecipe.getName());
        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment retainedFragment = fragmentManager.findFragmentByTag(STEP_FRAGMENT);
        if (retainedFragment == null) {
            mStepsFragment = StepsFragment.newInstance(
                    mRecipe.getSteps()[stepId], mRecipe.getSteps().length - 1);
            fragmentManager.beginTransaction()
                    .add(R.id.activity_step_detail, mStepsFragment, STEP_FRAGMENT)
                    .commit();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (isFinishing()) {
            if (mStepsFragment == null)
                return;
            getSupportFragmentManager().beginTransaction().remove(mStepsFragment)
                    .commit();
        }
    }

    @Override
    public void onChangeToStep(int pos) {
        mStepsFragment = StepsFragment.newInstance(mRecipe.getSteps()[pos], mRecipe.getSteps().length - 1);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.activity_step_detail, mStepsFragment, STEP_FRAGMENT);
        transaction.commit();
    }
}
