package gamecore.task;

import android.os.AsyncTask;
import android.widget.Toast;

import com.android.volley.RequestQueue;

import java.util.ArrayList;

import gamecore.callbacks.PCgamesLoadedListener;
import gamecore.extras.GameUtils;
import gamecore.network.VolleySingleton;
import gamecore.pojo.Game;


public class TaskLoadMain extends AsyncTask<Void, Void, ArrayList<Game>> {

    private PCgamesLoadedListener myComponent;
    private VolleySingleton volleySingleton;
    private RequestQueue requestQueue;

    public TaskLoadMain(PCgamesLoadedListener myComponent) {
        this.myComponent = myComponent;
        volleySingleton = VolleySingleton.getInstance();
        requestQueue = volleySingleton.getRequestQueue();
    }


    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected ArrayList<Game> doInBackground(Void... params) {
        return GameUtils.loadGames(requestQueue);
    }

    @Override
    protected void onPostExecute(ArrayList<Game> listGames) {
        if (myComponent != null) {
            myComponent.onPCgamesLoaded(listGames);
        }
    }
}
