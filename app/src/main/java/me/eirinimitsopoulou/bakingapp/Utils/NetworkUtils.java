package me.eirinimitsopoulou.bakingapp.Utils;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

/**
 * Created by eirinimitsopoulou on 30/05/2018.
 */

public class NetworkUtils {

    public String sendHTTPRequest(String url) {
        HttpURLConnection urlConnection = null;
        try {
            urlConnection = (HttpURLConnection) new URL(url).openConnection();
            InputStream in = new BufferedInputStream(urlConnection.getInputStream());
            Scanner scanner = new Scanner(in).useDelimiter("\\A");
            if (!scanner.hasNext())
                return "";
            return scanner.next();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            assert urlConnection != null;
            urlConnection.disconnect();
        }
        return "";
    }
}
