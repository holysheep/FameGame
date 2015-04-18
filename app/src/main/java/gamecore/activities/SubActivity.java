package gamecore.activities;


import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.github.ksoichiro.android.observablescrollview.ObservableScrollView;
import com.github.ksoichiro.android.observablescrollview.ObservableScrollViewCallbacks;
import com.github.ksoichiro.android.observablescrollview.ScrollState;
import com.github.ksoichiro.android.observablescrollview.ScrollUtils;
import com.nineoldandroids.view.ViewHelper;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import gamecore.R;
import gamecore.extras.EndPoints;
import gamecore.json.Parser;
import gamecore.json.Requestor;
import gamecore.network.VolleySingleton;
import gamecore.pojo.Game;

public class SubActivity extends ActionBarActivity implements ObservableScrollViewCallbacks {

    private View mImageView;
    private View mToolbarView;
    private ObservableScrollView mScrollView;
    private int mParallaxImageHeight;
    private TextView gameDev;
    private TextView gameTitle;
    private TextView gamePlatform;
    private TextView gameGenre;
    private TextView gameDescription;
    private ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_page_activity);

        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        gameTitle = (TextView) findViewById(R.id.singleGameTitle);
        gamePlatform = (TextView) findViewById(R.id.gamePlatform);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        gameDev = (TextView) findViewById(R.id.gameDeveloper);
        gameGenre = (TextView) findViewById(R.id.gameGenre);
        gameDescription = (TextView) findViewById(R.id.gameDescription);
        mImageView = findViewById(R.id.gameImage);
        mToolbarView = findViewById(R.id.toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mToolbarView.setBackgroundColor(ScrollUtils.getColorWithAlpha(0, getResources().getColor(R.color.colorPrimary)));
        mScrollView = (ObservableScrollView) findViewById(R.id.scroll);
        mScrollView.setScrollViewCallbacks(this);
        mParallaxImageHeight = getResources().getDimensionPixelSize(R.dimen.parallax_image_height);
        mImageView.setVisibility(View.INVISIBLE);

        String detailUrl = getIntent().getStringExtra("gameDetailUrl");
        new TaskLoadSinglePage().execute(detailUrl);
    }


    public class TaskLoadSinglePage extends AsyncTask<String, Void, Game> {

        private VolleySingleton volleySingleton;
        private RequestQueue requestQueue;

        public TaskLoadSinglePage() {
            volleySingleton = VolleySingleton.getInstance();
            requestQueue = volleySingleton.getRequestQueue();
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected Game doInBackground(String... params) {

            JSONObject response = Requestor.sendJsonRequest(requestQueue, getSingleRequestUrl(params[0]));
            if (response != null) {
                Game gameInfo = Parser.parseSinglePageResponse(response);
                Log.e("game", gameInfo.getGenre());
                return gameInfo;
            } else {
                Log.e("game", "null");
                return null;
            }
        }

        public String getSingleRequestUrl(String url) {
            return EndPoints.appendApiKey(url + "?format=json&");
        }

        @Override
        protected void onPostExecute(Game game) {
            loadGame(game);
            setTitle(game.getName());
            progressBar.setVisibility(View.GONE);
            mImageView.setVisibility(View.VISIBLE);
        }
    }

    public void loadGame(Game game) {
        gameTitle.setText(game.getName());
        gameDev.setText(game.getDeveloper());
        gamePlatform.setText("Platforms: " + game.getPlatform());
        gameGenre.setText("Genre: " + game.getGenre());
        gameDescription.setText((Html.fromHtml(game.getDescription().replace("</tr><tr>",
                "<br> &nbsp;").replaceAll("<img.+?>", "<br>").replace("<h2>", "<h3>").replace("</li><li>", "<br><br>"))));
        Picasso.with(this).load(game.getPageImage()).placeholder(R.drawable.noimage).into((android.widget.ImageView) mImageView);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        onScrollChanged(mScrollView.getCurrentScrollY(), false, false);
    }

    @Override
    public void onScrollChanged(int scrollY, boolean firstScroll, boolean dragging) {
        int baseColor = getResources().getColor(R.color.colorPrimary);
        float alpha = 1 - (float) Math.max(0, mParallaxImageHeight - scrollY) / mParallaxImageHeight;
        mToolbarView.setBackgroundColor(ScrollUtils.getColorWithAlpha(alpha, baseColor));
        ViewHelper.setTranslationY(mImageView, scrollY / 4);
    }

    @Override
    public void onDownMotionEvent() {

    }

    @Override
    public void onUpOrCancelMotionEvent(ScrollState scrollState) {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_sub, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}


