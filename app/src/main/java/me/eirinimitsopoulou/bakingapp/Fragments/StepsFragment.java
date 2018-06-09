package me.eirinimitsopoulou.bakingapp.Fragments;

import android.content.Context;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import me.eirinimitsopoulou.bakingapp.Data.Steps;
import me.eirinimitsopoulou.bakingapp.R;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by eirinimitsopoulou on 30/05/2018.
 */

public class StepsFragment extends Fragment implements View.OnClickListener {

    private static final String ARG_STEP = "step";
    private static final String ARG_MAX_STEP_ID = "max_step_id";
    private static final String ARG_VIDEO_POSITION = "video_position";
    private static final String ARG_VIDEO_PLAY_STATE = "video_state";

    private boolean mPlayWhenReady = true;
    private long mCurrentPosition = 0;
    private int mMaxStepId;
    private Steps mStep;
    private SimpleExoPlayer mExoPlayer;
    private OnRecipeStepDetailInteractionListener mListener;

    @BindView(R.id.no_video)
    TextView noVideo;
    @Nullable
    @BindView(R.id.step_description)
    TextView description;
    @BindView(R.id.exoPlayer)
    PlayerView playerView;
    @Nullable
    @BindView(R.id.next_fab)
    FloatingActionButton fabNext;
    @Nullable
    @BindView(R.id.pre_fab)
    FloatingActionButton fabPrevious;

    public StepsFragment() {
    }

    public static StepsFragment newInstance(Steps step, int maxStepId) {
        StepsFragment fragment = new StepsFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_STEP, step);
        args.putInt(ARG_MAX_STEP_ID, maxStepId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);

        if (getArguments() != null) {
            mStep = getArguments().getParcelable(ARG_STEP);
            mMaxStepId = getArguments().getInt(ARG_MAX_STEP_ID);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.step_detail_fragment, container, false);
        ButterKnife.bind(this, rootView);

        boolean isTablet = getResources().getBoolean(R.bool.tablet);
        int orientation = getResources().getConfiguration().orientation;

        if (description != null) {
            description.setText(mStep.getDescription());
        }
        if (fabNext != null) {
            fabNext.setOnClickListener(this);
            fabPrevious.setOnClickListener(this);
            NextPreviousButtons();
        }

        if (!isTablet) {
            if (orientation == Configuration.ORIENTATION_PORTRAIT && !mStep.getVideoURL().isEmpty()) {
                noVideo.setVisibility(View.GONE);
            } else {
                playerView.setVisibility(View.GONE);
            }
        } else {
            if (mStep.getVideoURL().isEmpty())
                playerView.setVisibility(View.GONE);
            else
                noVideo.setVisibility(View.GONE);
        }

        if (savedInstanceState != null) {
            mPlayWhenReady = savedInstanceState.getBoolean(ARG_VIDEO_PLAY_STATE);
            mCurrentPosition = savedInstanceState.getLong(ARG_VIDEO_POSITION);
        }
        prepareVideo();

        return rootView;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        if (mExoPlayer != null) {
            outState.putLong(ARG_VIDEO_POSITION, mExoPlayer.getCurrentPosition());
            outState.putBoolean(ARG_VIDEO_PLAY_STATE, mExoPlayer.getPlayWhenReady());
        }
    }

    private void NextPreviousButtons() {
        if (mStep.getId() <= 0) {
            fabPrevious.setVisibility(View.INVISIBLE);
            fabNext.setVisibility(View.VISIBLE);
        } else if (mStep.getId() >= mMaxStepId) {
            fabPrevious.setVisibility(View.VISIBLE);
            fabNext.setVisibility(View.INVISIBLE);
        } else {
            fabPrevious.setVisibility(View.VISIBLE);
            fabNext.setVisibility(View.VISIBLE);
        }
    }

    private void prepareVideo() {
        TrackSelector trackSelector = new DefaultTrackSelector();
        LoadControl loadControl = new DefaultLoadControl();
        mExoPlayer = ExoPlayerFactory.newSimpleInstance(getActivity(), trackSelector, loadControl);

        DataSource.Factory dataSourceFactory = new DefaultDataSourceFactory(getActivity(),
                Util.getUserAgent(getActivity(), getString(R.string.app_name)), null);

        MediaSource mediaSource = new ExtractorMediaSource.Factory(dataSourceFactory)
                .createMediaSource(Uri.parse(mStep.getVideoURL()));

        mExoPlayer.prepare(mediaSource);
        mExoPlayer.setPlayWhenReady(mPlayWhenReady);
        mExoPlayer.seekTo(mCurrentPosition);
        playerView.setPlayer(mExoPlayer);
    }

    @Override
    public void onPause() {
        super.onPause();
        releasePlayer();
    }

    @Override
    public void onStop() {
        super.onStop();
        releasePlayer();
    }

    private void releasePlayer() {
        if (mExoPlayer != null) {
            mPlayWhenReady = mExoPlayer.getPlayWhenReady();
            mCurrentPosition = mExoPlayer.getCurrentPosition();
            mExoPlayer.stop();
            mExoPlayer.release();
            mExoPlayer = null;
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnRecipeStepDetailInteractionListener) {
            mListener = (OnRecipeStepDetailInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnRecipeStepDetailInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onClick(View view) {
        releasePlayer();
        if (view.getId() == R.id.next_fab) {
            mListener.onChangeToStep(mStep.getId() + 1);
        } else if (view.getId() == R.id.pre_fab) {
            mListener.onChangeToStep(mStep.getId() - 1);
        }
        NextPreviousButtons();
    }

    public interface OnRecipeStepDetailInteractionListener {
        void onChangeToStep(int pos);
    }
}
