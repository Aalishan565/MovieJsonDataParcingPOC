package com.moviejsonparsingusingparcelable;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

public class MainActivity extends Activity implements IMovieListener {
    ListView listViewData;
    MovieAdapter adapter;
    private List<MovieModel> movieModelList = new ArrayList<MovieModel>();
    private ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mProgressDialog = new ProgressDialog(MainActivity.this);
        mProgressDialog.setMessage("Loading...");
        mProgressDialog.setIndeterminate(false);
        mProgressDialog.show();
        new DownloadData(this).execute("http://jsonparsing.parseapp.com/jsonData/moviesData.txt");
        listViewData = (ListView) findViewById(R.id.listView);
        adapter = new MovieAdapter(this, R.layout.row, movieModelList);
        listViewData.setAdapter(adapter);
    }

    @Override
    public void onMoviesListSuccess(List<MovieModel> objects) {
        this.movieModelList = objects;
        adapter = new MovieAdapter(this, R.layout.row, movieModelList);
        listViewData.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        mProgressDialog.dismiss();
    }

    @Override
    public void onMoviesListFailure(String msg) {
        mProgressDialog.dismiss();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mProgressDialog.isShowing() || mProgressDialog != null) {
            mProgressDialog.dismiss();
        }

    }
}
