package gamecore.extras;

import com.android.volley.RequestQueue;

import org.json.JSONObject;

import java.util.ArrayList;

import gamecore.json.Parser;
import gamecore.json.Requestor;
import gamecore.materialtest.MyApp;
import gamecore.pojo.Game;


public class GameUtils {
    public static ArrayList<Game> loadGames(RequestQueue requestQueue) {
        JSONObject response = Requestor.sendJsonRequest(requestQueue, EndPoints.getRequestUrl());
        ArrayList<Game> listGames = Parser.parseJSONResponse(response);
        MyApp.getWritableDatabase().insertGamesPC(listGames, true);
        return listGames;
    }
}
