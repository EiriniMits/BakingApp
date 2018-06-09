package me.eirinimitsopoulou.bakingapp.Adapaters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import me.eirinimitsopoulou.bakingapp.R;
import me.eirinimitsopoulou.bakingapp.Data.Recipe;
import me.eirinimitsopoulou.bakingapp.Data.Images;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by eirinimitsopoulou on 30/05/2018.
 */

public class RecipesAdapter extends RecyclerView.Adapter<RecipesAdapter.RecipeViewHolder> {
    private final Context mContext;
    private final List<Recipe> mList;
    private ItemClickListener mItemClickListener;
    List<String> imagesList = new ArrayList<>();
    Images images = new Images();


    public RecipesAdapter(Context context, List<Recipe> recipesList) {
        mContext = context;
        mList = recipesList;
    }

    public void setRecipeClickListener(ItemClickListener clicker) {
        mItemClickListener = clicker;
    }

    @Override
    public RecipeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.recipe_image_fragment, parent, false);
        return new RecipeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecipeViewHolder holder, int position) {
        imagesList = images.CreateImages();
        holder.bind(mList.get(position), imagesList.get(position));
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class RecipeViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.name)
        TextView name;
        @BindView(R.id.image)
        ImageView image;

        public RecipeViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        void bind(final Recipe recipeModel, final String recipeImage) {
            Glide.with(mContext)
                    .load(recipeImage)
                    .asBitmap()
                    .into(image);
            name.setText(recipeModel.getName());
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            mItemClickListener.onRecipeClick(view, getAdapterPosition());
        }
    }

    public interface ItemClickListener {
        void onRecipeClick(View view, int pos);
    }
}
