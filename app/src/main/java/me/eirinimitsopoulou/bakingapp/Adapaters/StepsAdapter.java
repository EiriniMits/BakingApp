package me.eirinimitsopoulou.bakingapp.Adapaters;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import me.eirinimitsopoulou.bakingapp.R;
import me.eirinimitsopoulou.bakingapp.Data.Steps;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by eirinimitsopoulou on 30/05/2018.
 */

public class StepsAdapter extends RecyclerView.Adapter<StepsAdapter.RecipeStepViewHolder> {

    private final Context mContext;
    private final Steps[] mStepList;
    private OnItemClickListener mListener;
    private int mSelectedItemPosition = -1;

    public StepsAdapter(Context context, Steps[] stepList) {
        mContext = context;
        mStepList = stepList;
    }

    public void setOnStepClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    @Override
    public RecipeStepViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(mContext).inflate(R.layout.step_list_item, parent, false);
        return new RecipeStepViewHolder(view);
    }


    @Override
    public void onBindViewHolder(RecipeStepViewHolder holder, int position) {
        holder.description.setText(mStepList[position].getShortDescription());
    }

    @Override
    public int getItemCount() {
        return mStepList.length;
    }

    public class RecipeStepViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.step_list_item)
        CardView card;
        @BindView(R.id.step_short_description)
        TextView description;


        public RecipeStepViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            card.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mListener != null) {
                mListener.onRecipeStepClicked(getAdapterPosition());
                mSelectedItemPosition = getAdapterPosition();
                notifyDataSetChanged();
            }
        }
    }

    public interface OnItemClickListener {
        void onRecipeStepClicked(int position);
    }
}
