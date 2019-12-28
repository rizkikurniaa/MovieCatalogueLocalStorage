package com.kikulabs.moviecataloguelocalstorage.stackwidget;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.os.Binder;
import android.os.Bundle;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.bumptech.glide.Glide;
import com.kikulabs.moviecataloguelocalstorage.R;
import com.kikulabs.moviecataloguelocalstorage.database.DatabaseContract;
import com.kikulabs.moviecataloguelocalstorage.database.DatabaseHelper;
import com.kikulabs.moviecataloguelocalstorage.model.MoviesAndTvData;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import static com.kikulabs.moviecataloguelocalstorage.database.DatabaseContract.TABLE_MOVIES;

public class StackRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {
    private ArrayList<MoviesAndTvData> moviesAndTvData = new ArrayList<>();
    private Context context;
    private Cursor cursor;
    private DatabaseHelper dataBaseHelper;
    private String DATABASE_TABLE = TABLE_MOVIES;

    public StackRemoteViewsFactory(Context context) {
        this.context = context;
    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onDataSetChanged() {
        dataBaseHelper = new DatabaseHelper(context);

        if (moviesAndTvData.size() != 0) {
            moviesAndTvData.clear();
        }

        final long identifyToken = Binder.clearCallingIdentity();

        //querying
        cursor = dataBaseHelper.getWritableDatabase().query(DATABASE_TABLE,
                null,
                null,
                null,
                null,
                null,
                DatabaseContract.MoviesColumns._ID,
                null);

        if (cursor != null) {
            for (int i = 0; i < cursor.getCount(); i++) {
                cursor.moveToPosition(i);
                MoviesAndTvData moviesItem = new MoviesAndTvData(cursor);
                moviesAndTvData.add(moviesItem);
            }
        }
        Binder.restoreCallingIdentity(identifyToken);
    }

    @Override
    public void onDestroy() {
        if (cursor != null) {
            cursor.close();
        }
    }

    @Override
    public int getCount() {
        if (moviesAndTvData == null) {
            return 0;
        } else {
            return moviesAndTvData.size();
        }
    }

    @Override
    public RemoteViews getViewAt(int position) {
        RemoteViews moviesRv = new RemoteViews(context.getPackageName(), R.layout.widget_item);
        if (moviesAndTvData.size() != 0) {
            Object backdropUrl = moviesAndTvData.get(position).getBackdrop();
            if (backdropUrl != null) {
                String pathImg = "https://image.tmdb.org/t/p/w500" + backdropUrl.toString();
                try {
                    Bitmap bitmap = Glide.with(context)
                            .asBitmap()
                            .load(pathImg)
                            .submit(500, 500)
                            .get();

                    moviesRv.setImageViewBitmap(R.id.iv_widget, bitmap);
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

        Bundle extras = new Bundle();
        extras.putInt(ImageBannerWidget.EXTRA_ITEM, position);
        Intent fillInIntent = new Intent();
        fillInIntent.putExtras(extras);

        moviesRv.setOnClickFillInIntent(R.id.iv_widget, fillInIntent);
        return moviesRv;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int position) {
        return cursor.moveToPosition(position) ? cursor.getLong(0) : position;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }
}
