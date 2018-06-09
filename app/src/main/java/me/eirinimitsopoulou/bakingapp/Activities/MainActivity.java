package me.eirinimitsopoulou.bakingapp.Activities;

import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import me.eirinimitsopoulou.bakingapp.Adapaters.RecipesAdapter;
import me.eirinimitsopoulou.bakingapp.Data.Recipe;
import me.eirinimitsopoulou.bakingapp.R;
import me.eirinimitsopoulou.bakingapp.Loader.ListLoader;
import me.eirinimitsopoulou.bakingapp.Utils.JsonUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by eirinimitsopoulou on 30/05/2018.
 */

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<String>, RecipesAdapter.ItemClickListener {

    @BindView(R.id.activity_main)
    ConstraintLayout activity_main;
    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;

    private List<Recipe> mRecipes;
    private final static String JSON = "https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/baking.json";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        loader();
    }

    private void loader() {
        getSupportLoaderManager().restartLoader(1, null, this).forceLoad();
    }

    @Override
    public Loader<String> onCreateLoader(int id, Bundle args) {
        return new ListLoader(this, JSON);
    }

    @Override
    public void onLoadFinished(Loader<String> loader, String data) {
        if (data.isEmpty()) {
            Snackbar.make(activity_main, R.string.no_internet, Snackbar.LENGTH_INDEFINITE);
            return;
        }

        mRecipes = JsonUtils.parseRecipesJson(data);
        RecipesAdapter adapter = new RecipesAdapter(this, mRecipes);
        adapter.setRecipeClickListener(this);
        recyclerview.setAdapter(adapter);
        if (getResources().getBoolean(R.bool.tablet))
            recyclerview.setLayoutManager(new GridLayoutManager(this, 3));
        else
            recyclerview.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public void onLoaderReset(Loader<String> loader) {
    }

    @Override
    public void onRecipeClick(View view, int pos) {
        Intent intent = new Intent(this, DetailsActivity.class);
        intent.putExtra(DetailsActivity.RECIPE, mRecipes.get(pos));
        startActivity(intent);
    }
}
