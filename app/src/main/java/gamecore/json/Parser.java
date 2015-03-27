package gamecore.json;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormatSymbols;
import java.util.ArrayList;

import gamecore.extras.Constants;

import static gamecore.extras.Keys.EndPointPC.KEY_DECK;
import static gamecore.extras.Keys.EndPointPC.KEY_ICON;
import static gamecore.extras.Keys.EndPointPC.KEY_ID;
import static gamecore.extras.Keys.EndPointPC.KEY_IMAGE;
import static gamecore.extras.Keys.EndPointPC.KEY_NAME;
import static gamecore.extras.Keys.EndPointPC.KEY_RELEASE_DAY;
import static gamecore.extras.Keys.EndPointPC.KEY_RELEASE_MONTH;
import static gamecore.extras.Keys.EndPointPC.KEY_RESULTS;
import gamecore.pojo.GameCat;


public class Parser {

    public static ArrayList<GameCat> parseJSONResponse(JSONObject response) {
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

                    if (Utils.contains(currentGame, KEY_ID)) {
                        id = currentGame.getInt(KEY_ID);
                    }

                    if (Utils.contains(currentGame, KEY_NAME)) {
                        name = currentGame.getString(KEY_NAME);
                    }

                    releaseDay = null;
                    if (Utils.contains(currentGame, KEY_RELEASE_DAY)) {
                        releaseDay = currentGame.getInt(KEY_RELEASE_DAY);
                    }

                    if (Utils.contains(currentGame, KEY_DECK)) {
                        deck = currentGame.getString(KEY_DECK);
                    }

                    releaseMonth = null;

                    if (Utils.contains(currentGame, KEY_RELEASE_MONTH)) {
                        Integer monthNumber = currentGame.getInt(KEY_RELEASE_MONTH);
                        releaseMonth = getMonth(monthNumber);
                    }

                    typeImage = null;
                    if (Utils.contains(currentGame, KEY_IMAGE)) {
                        JSONObject objectImage = currentGame.getJSONObject(KEY_IMAGE);

                        if (Utils.contains(objectImage, KEY_ICON)) {
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

    public static String getMonth(int month) {
        return new DateFormatSymbols().getMonths()[month - 1];
    }
}
