package me.eirinimitsopoulou.bakingapp.Fragments;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import me.eirinimitsopoulou.bakingapp.Adapaters.StepsAdapter;
import me.eirinimitsopoulou.bakingapp.Data.Recipe;
import me.eirinimitsopoulou.bakingapp.Widget.AppWidget;
import me.eirinimitsopoulou.bakingapp.R;
import me.eirinimitsopoulou.bakingapp.Data.Ingredients;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by eirinimitsopoulou on 30/05/2018.
 */

public class RecipeDetailsFragment extends Fragment implements StepsAdapter.OnItemClickListener {

    @BindView(R.id.ingredients)
    TextView ingredients;
    @BindView(R.id.steps_recyclerview)
    RecyclerView steps;

    private static final String RECIPES_KEY = "recipe";

    private Recipe mRecipe;
    private OnRecipeDetailInteractionListener mListener;

    public RecipeDetailsFragment() {}

    public static RecipeDetailsFragment newInstance(Recipe recipe) {
        RecipeDetailsFragment fragment = new RecipeDetailsFragment();
        Bundle args = new Bundle();
        args.putParcelable(RECIPES_KEY, recipe);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mRecipe = getArguments().getParcelable(RECIPES_KEY);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.recipe_details_fragment, container, false);
        ButterKnife.bind(this, rootView);

        StringBuilder stringIngredients = new StringBuilder();
        for (Ingredients ing : mRecipe.getIngredients()) {
            stringIngredients.append("- " + ing.getIngredient() + " (" + ing.getQuantity() + " " +
                    ing.getMeasure() + ")");
            stringIngredients.append("\n");
        }
        ingredients.setText(stringIngredients);

        StepsAdapter stepsAdapter = new StepsAdapter(getActivity(), mRecipe.getSteps());
        stepsAdapter.setOnStepClickListener(this);
        steps.setAdapter(stepsAdapter);
        steps.setLayoutManager(new LinearLayoutManager(getActivity()));
        steps.setNestedScrollingEnabled(false);

        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(getActivity());
        int[] appWidgetIDs = appWidgetManager.getAppWidgetIds(new ComponentName(getActivity(), AppWidget.class));
        AppWidget.Ingredients = mRecipe;
        AppWidget.updateWidget(getActivity(), appWidgetManager, appWidgetIDs);

        return rootView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnRecipeDetailInteractionListener) {
            mListener = (OnRecipeDetailInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnRecipeDetailInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onRecipeStepClicked(int position) {
        if (mListener != null) {
            mListener.onStepClicked(position);
        }
    }

    public interface OnRecipeDetailInteractionListener {
        void onStepClicked(int pos);
    }
}
