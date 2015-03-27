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
import gamecore.callbacks.PCgamesLoadedListener;
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
import gamecore.task.TaskLoadGamesPCInterface;
import me.tatarka.support.job.JobParameters;
import me.tatarka.support.job.JobService;


public class TaskService extends JobService implements PCgamesLoadedListener {
    private JobParameters jobParameters;

    @Override
    public boolean onStartJob(JobParameters jobParameters) {
        L.t(this, "onStartJob");
        this.jobParameters = jobParameters;
        new TaskLoadGamesPCInterface(this).execute();
        return true;
    }

    @Override
    public boolean onStopJob(JobParameters jobParameters) {
        L.t(this, "onStopJob");
        return false;
    }

    public void onPCgamesLoaded(ArrayList<GameCat> listGames) {
        L.t(this, "onPCgamesLoaded");
        jobFinished(jobParameters, false);
    }
}


