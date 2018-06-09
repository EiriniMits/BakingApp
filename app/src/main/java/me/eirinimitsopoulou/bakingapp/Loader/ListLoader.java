package me.eirinimitsopoulou.bakingapp.Loader;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

import me.eirinimitsopoulou.bakingapp.Utils.NetworkUtils;

/**
 * Created by eirinimitsopoulou on 30/05/2018.
 */

public class ListLoader extends AsyncTaskLoader<String> {
    private final String mUrl;

    public ListLoader(Context context, String url) {
        super(context);
        mUrl = url;
    }

    @Override
    public String loadInBackground() {
        return new NetworkUtils().sendHTTPRequest(mUrl);
    }

    @Override
    public void deliverResult(String data) {
        super.deliverResult(data);
    }
}
