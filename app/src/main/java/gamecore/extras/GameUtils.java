package gamecore.extras;

import com.android.volley.RequestQueue;

import org.json.JSONObject;

import java.util.ArrayList;

import gamecore.fragment.PCFragment;
import gamecore.json.Parser;
import gamecore.json.Requestor;
import gamecore.materialtest.MyApp;
import gamecore.pojo.GameCat;


public class GameUtils {
    public static ArrayList<GameCat> loadPCgames(RequestQueue requestQueue) {
        JSONObject response = Requestor.sendJsonRequest(requestQueue, EndPoints.getRequestUrl());
        ArrayList<GameCat> listGames = Parser.parseJSONResponse(response);
        MyApp.getWritableDatabase().insertGamesPC(listGames, true);
        return listGames;
    }
}
