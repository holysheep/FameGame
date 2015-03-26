package gamecore.services;

import android.os.AsyncTask;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.RequestFuture;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormatSymbols;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import gamecore.Logging.L;
import gamecore.extras.Constants;
import static gamecore.extras.Keys.EndPointPC.KEY_DECK;
import static gamecore.extras.Keys.EndPointPC.KEY_ICON;
import static gamecore.extras.Keys.EndPointPC.KEY_ID;
import static gamecore.extras.Keys.EndPointPC.KEY_IMAGE;
import static gamecore.extras.Keys.EndPointPC.KEY_NAME;
import static gamecore.extras.Keys.EndPointPC.KEY_RELEASE_DAY;
import static gamecore.extras.Keys.EndPointPC.KEY_RELEASE_MONTH;
import static gamecore.extras.Keys.EndPointPC.KEY_RESULTS;
import gamecore.fragment.PCFragment;
import gamecore.materialtest.MyApp;
import gamecore.network.VolleySingleton;
import gamecore.pojo.GameCat;
import me.tatarka.support.job.JobParameters;
import me.tatarka.support.job.JobService;


public class TaskService extends JobService {
    @Override
    public boolean onStartJob(JobParameters jobParameters) {
        L.t(this, "onStartJob");
        new CustomTask(this).execute(jobParameters);
        return true;
    }

    @Override
    public boolean onStopJob(JobParameters jobParameters) {
        L.t(this, "onStopJob");
        return false;
    }


    //TODO:static
    public class CustomTask extends AsyncTask<JobParameters, Void, JobParameters> {
        TaskService taskService;

        private VolleySingleton volleySingleton;
        private RequestQueue requestQueue;


        CustomTask(TaskService taskService) {
            this.taskService = taskService;
            volleySingleton = VolleySingleton.getInstance();
            requestQueue = volleySingleton.getRequestQueue();
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected JobParameters doInBackground(JobParameters... params) {

            JSONObject response = sendJsonRequest();
            ArrayList<GameCat> listGames = parseJSONResponse(response);
            MyApp.getWritableDatabase().insertGamesPC(listGames, true);
            return params[0];
        }

        @Override
        protected void onPostExecute(JobParameters jobParameters) {
            jobFinished(jobParameters, false);
        }

        private JSONObject sendJsonRequest() {
            JSONObject response = null;
            RequestFuture<JSONObject> requestFuture = RequestFuture.newFuture();

            JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, PCFragment.URL_PC_NEWGAMES,
                    (JSONObject) null, requestFuture, requestFuture);
            requestQueue.add(request);
            try {
                response = requestFuture.get(30000, TimeUnit.MILLISECONDS);
            } catch (InterruptedException e) {
                L.m(e + "");
            } catch (ExecutionException e) {
                L.m(e + "");
            } catch (TimeoutException e) {
                L.m(e + "");
            }
            return response;
        }

        private ArrayList<GameCat> parseJSONResponse(JSONObject response) {
            ArrayList<GameCat> listPCGames = new ArrayList<>();

            if (response != null && response.length() > 0) try {
                if (response.has(KEY_RESULTS)) {
                    JSONArray arrayGames = response.getJSONArray(KEY_RESULTS);
                    for (int i = 0; i < arrayGames.length(); i++) {

                        Integer id = -1;
                        String name = Constants.NA;
                        String deck = Constants.NA;
                        Integer releaseDay = -1;
                        String releaseMonth = Constants.NA;
                        String typeImage = Constants.NA;

                        JSONObject currentGame = arrayGames.getJSONObject(i);

                        if (contains(currentGame, KEY_ID)) {
                            id = currentGame.getInt(KEY_ID);
                        }

                        if (contains(currentGame, KEY_NAME)) {
                            name = currentGame.getString(KEY_NAME);
                        }

                        releaseDay = null;
                        if (contains(currentGame, KEY_RELEASE_DAY)) {
                            releaseDay = currentGame.getInt(KEY_RELEASE_DAY);
                        }

                        if (contains(currentGame, KEY_DECK)) {
                            deck = currentGame.getString(KEY_DECK);
                        }

                        releaseMonth = null;

                        if (contains(currentGame, KEY_RELEASE_MONTH)) {
                            Integer monthNumber = currentGame.getInt(KEY_RELEASE_MONTH);
                            releaseMonth = getMonth(monthNumber);
                        }

                        typeImage = null;
                        if (contains(currentGame, KEY_IMAGE)) {
                            JSONObject objectImage = currentGame.getJSONObject(KEY_IMAGE);

                            if (contains(objectImage, KEY_ICON)) {
                                typeImage = objectImage.getString(KEY_ICON);
                            }
                        }

                        GameCat gameCat = new GameCat();
                        gameCat.setId(Integer.parseInt(String.valueOf(id)));
                        gameCat.setName(name);
                        gameCat.setDeck(deck);
                        gameCat.setReleaseDay(releaseDay);
                        gameCat.setReleaseMonth(releaseMonth);
                        gameCat.setTypeImage(typeImage);

                        if (id != -1 && !name.equals(Constants.NA)) {
                            listPCGames.add(gameCat);
                        }
                    }
                }
            } catch (JSONException e) {
                Log.d("JSON Parser", "log" + e.getMessage());
            }
            return listPCGames;
        }

        public String getMonth(int month) {
            return new DateFormatSymbols().getMonths()[month - 1];
        }

        private boolean contains(JSONObject jsonObject, String key) {
            return (jsonObject != null && jsonObject.has(key) && !jsonObject.isNull(key)) ? true : false;
        }

    }
}
