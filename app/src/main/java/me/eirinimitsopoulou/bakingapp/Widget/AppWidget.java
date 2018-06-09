package me.eirinimitsopoulou.bakingapp.Widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import me.eirinimitsopoulou.bakingapp.R;
import me.eirinimitsopoulou.bakingapp.Data.Recipe;
import me.eirinimitsopoulou.bakingapp.Activities.DetailsActivity;
import me.eirinimitsopoulou.bakingapp.Activities.MainActivity;

/**
 * Created by eirinimitsopoulou on 30/05/2018.
 */

public class AppWidget extends AppWidgetProvider {
    public static Recipe Ingredients;

    private static void updateAppWidget(Context context, AppWidgetManager appWidgetManager, int appWidgetId) {

        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.app_widget);

        if (Ingredients != null) {
            StringBuilder sIngredients = new StringBuilder();
            for (me.eirinimitsopoulou.bakingapp.Data.Ingredients ing : Ingredients.getIngredients()) {
                sIngredients.append(ing.getIngredient() + " (" + ing.getQuantity() + " " +
                        ing.getMeasure() + ")");
                sIngredients.append("\n");
            }

            views.setTextViewText(R.id.name, Ingredients.getName());
            views.setTextViewText(R.id.ingredients, sIngredients);

            Intent intent = new Intent(context, DetailsActivity.class);
            intent.putExtra(DetailsActivity.RECIPE, Ingredients);
            PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, new Intent(context, MainActivity.class), 0);

            views.setOnClickPendingIntent(R.id.widget_layout, pendingIntent);
        }

        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    public static void updateWidget(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

}

