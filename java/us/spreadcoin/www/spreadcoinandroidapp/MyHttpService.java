package us.spreadcoin.www.spreadcoinandroidapp;

import android.app.IntentService;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Intent;
import android.widget.RemoteViews;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.lang.reflect.Field;

public class MyHttpService extends IntentService {

    private int getResourseId(String pVariableName)
    {
        Class res = R.drawable.class;
        int drawableId = 0;
        try {
            Field field = res.getField(pVariableName);
            drawableId = field.getInt(null);
        }
        catch (Exception e) {
        }
        finally {
            return drawableId;
        }

    }

    public MyHttpService() {
        super("MyHttpService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        RemoteViews remoteViews;
        AppWidgetManager appWidgetManager;
        ComponentName thisWidget;
        remoteViews = new RemoteViews(getPackageName(), R.layout.widget);
        thisWidget = new ComponentName(this, spreadcoin.class);
        appWidgetManager = AppWidgetManager.getInstance(this.getApplicationContext());
        HttpClient httpClient = new DefaultHttpClient();
        HttpGet request = new HttpGet("http://spreadcoin.us/api/android_widget_ticker.php");
        try {
            HttpResponse response = httpClient.execute(request);
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK)
            {
                String s = EntityUtils.toString(response.getEntity());
                String[] arr = s.split(" ");
                remoteViews.setTextViewText(R.id.textView7, arr[0]);
                remoteViews.setTextViewText(R.id.textView8, arr[1]);
                remoteViews.setTextViewText(R.id.textView9, arr[2]);
                remoteViews.setTextViewText(R.id.textView10, arr[3]);
                remoteViews.setImageViewResource(R.id.imageView2, getResourseId(arr[4]));
                remoteViews.setImageViewResource(R.id.imageView3, getResourseId(arr[5]));
                remoteViews.setImageViewResource(R.id.imageView4, getResourseId(arr[6]));
                remoteViews.setImageViewResource(R.id.imageView5, getResourseId(arr[7]));
                appWidgetManager.updateAppWidget(thisWidget, remoteViews);
            }

        } catch (ClientProtocolException e) {

        }
        catch (IOException e) {

        }
    }

}