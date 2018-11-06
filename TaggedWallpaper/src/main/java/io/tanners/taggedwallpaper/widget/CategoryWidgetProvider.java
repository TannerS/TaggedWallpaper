package io.tanners.taggedwallpaper.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.widget.RemoteViews;
import io.tanners.taggedwallpaper.R;
import io.tanners.taggedwallpaper.SearchActivity;

/**
 * Implementation of App Widget functionality.
 */
public class CategoryWidgetProvider extends AppWidgetProvider {

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager, int appWidgetId) {
        // Construct the RemoteViews object
        RemoteViews views = getListViewLayout(context, appWidgetId);
        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }

    /**
     * Get the view hierarchy for our widget hierarchy
     * This is for gridview.
     *
     * @param mContext
     * @return
     */
    private static RemoteViews getListViewLayout(Context mContext, int appWidgetId)
    {
        // Construct the RemoteViews object
        RemoteViews mRemoteViews = new RemoteViews(mContext.getPackageName(), R.layout.category_widget);
        // intent for our grid view service to act as the adapter
        Intent intent = new Intent(mContext, WidgetListService.class);
        // pass in widget id
        // source: https://stackoverflow.com/questions/11350287/ongetviewfactory-only-called-once-for-multiple-widgets
        intent.setData(Uri.fromParts("", String.valueOf(appWidgetId), null));
        // set adapter
        mRemoteViews.setRemoteAdapter(R.id.widget_gridview, intent);
        // set on click item
        Intent searchIntent = new Intent(mContext, SearchActivity.class);
        // set pending intent to host the item intent
        PendingIntent searchPendingIntent = PendingIntent.getActivity(
                mContext,
                0,
                searchIntent,
                PendingIntent.FLAG_UPDATE_CURRENT
        );
        // set template
        mRemoteViews.setPendingIntentTemplate(R.id.widget_gridview, searchPendingIntent);
        // set empty view
        mRemoteViews.setEmptyView(R.id.widget_gridview, R.id.empty_view);
//        mRemoteViews.setEmptyView(R.id.widget_gridview, R.id.widget_gridview_empty);
        // return views
        return mRemoteViews;
    }
}

