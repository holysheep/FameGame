package gamecore.task;

import android.os.AsyncTask;
import android.view.View;
import android.widget.ProgressBar;

import com.android.volley.RequestQueue;

import java.util.ArrayList;

import gamecore.callbacks.PCgamesLoadedListener;
import gamecore.extras.GameUtils;
import gamecore.fragment.PCFragment;
import gamecore.network.VolleySingleton;
import gamecore.pojo.GameCat;
import gamecore.services.TaskService;


public class TaskLoadGamesPC extends AsyncTask<Void, Void, ArrayList<GameCat>> {

    private PCgamesLoadedListener myComponent;
    private VolleySingleton volleySingleton;
    private RequestQueue requestQueue;

    public TaskLoadGamesPC(PCgamesLoadedListener myComponent) {
        this.myComponent = myComponent;
        volleySingleton = VolleySingleton.getInstance();
        requestQueue = volleySingleton.getRequestQueue();
    }


    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected ArrayList<GameCat> doInBackground(Void... params) {
        return GameUtils.loadPCgames(requestQueue);
    }

    @Override
    protected void onPostExecute(ArrayList<GameCat> listGames) {
        if (myComponent != null) {
            myComponent.onPCgamesLoaded(listGames);
        }
    }
}
