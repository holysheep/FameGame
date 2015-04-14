package gamecore.json;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormatSymbols;
import java.util.ArrayList;

import gamecore.extras.Constants;
import gamecore.pojo.Game;

import static gamecore.extras.Keys.EndPointPC.KEY_DECK;
import static gamecore.extras.Keys.EndPointPC.KEY_DESCRIPTION;
import static gamecore.extras.Keys.EndPointPC.KEY_DETAIL_URL;
import static gamecore.extras.Keys.EndPointPC.KEY_DEVELOPER;
import static gamecore.extras.Keys.EndPointPC.KEY_GENRE;
import static gamecore.extras.Keys.EndPointPC.KEY_GENRENAME;
import static gamecore.extras.Keys.EndPointPC.KEY_GETDEVELOPER;
import static gamecore.extras.Keys.EndPointPC.KEY_ICON;
import static gamecore.extras.Keys.EndPointPC.KEY_ID;
import static gamecore.extras.Keys.EndPointPC.KEY_IMAGE;
import static gamecore.extras.Keys.EndPointPC.KEY_NAME;
import static gamecore.extras.Keys.EndPointPC.KEY_PAGEICON;
import static gamecore.extras.Keys.EndPointPC.KEY_PAGEIMAGE;
import static gamecore.extras.Keys.EndPointPC.KEY_PLATFORMNAME;
import static gamecore.extras.Keys.EndPointPC.KEY_PLATFORMS;
import static gamecore.extras.Keys.EndPointPC.KEY_RELEASE_DAY;
import static gamecore.extras.Keys.EndPointPC.KEY_RELEASE_MONTH;
import static gamecore.extras.Keys.EndPointPC.KEY_RESULTS;


public class Parser {

    public static ArrayList<Game> parseJSONResponse(JSONObject response) {
        ArrayList<Game> listPCGames = new ArrayList<>();

        if (response != null && response.length() > 0) try {
            if (response.has(KEY_RESULTS)) {
                JSONArray arrayGames = response.getJSONArray(KEY_RESULTS);
                for (int i = 0; i < arrayGames.length(); i++) {

                    Integer id = -1;
                    String name = Constants.NA;
                    String deck = Constants.NA;
                    Integer releaseDay;
                    String releaseMonth;
                    String detailUrl = Constants.NA;
                    String mainPageImage;


                    JSONObject currentGame = arrayGames.getJSONObject(i);

                    if (Utils.contains(currentGame, KEY_ID)) {
                        id = currentGame.getInt(KEY_ID);
                    }
                    if (Utils.contains(currentGame, KEY_DETAIL_URL)) {
                        detailUrl = currentGame.getString(KEY_DETAIL_URL);
                    }else{
                        detailUrl="no Url";
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

                    mainPageImage = null;
                    if (Utils.contains(currentGame, KEY_IMAGE)) {
                        JSONObject objectImage = currentGame.getJSONObject(KEY_IMAGE);
                        if (Utils.contains(objectImage, KEY_ICON)) {
                            mainPageImage = objectImage.getString(KEY_ICON);
                        }
                    }

                    Game game = new Game();
                    game.setId(Integer.parseInt(String.valueOf(id)));
                    game.setName(name);
                    game.setDeck(deck);
                    game.setDetailUrl(detailUrl);
                    game.setReleaseDay(releaseDay);
                    game.setReleaseMonth(releaseMonth);
                    game.setMainImage(mainPageImage);

                    if (id != -1 && !name.equals(Constants.NA)) {
                        listPCGames.add(game);
                    }
                }
            }
        } catch (JSONException e) {
            Log.d("JSON Parser", "log" + e.getMessage());
        }
        return listPCGames;
    }


    public static Game parseSinglePageResponse(JSONObject response) {
        Log.e("PARSE singlepage",response.toString());
        Game gameDetail = new Game();

        if ((response.has(KEY_RESULTS))) {
            try {
                JSONObject gameInfo = response.getJSONObject(KEY_RESULTS);

                String name = Constants.NA;
                Integer id = -1;
                String singlePageImage;
                String platform;
                String genre;
                String description = Constants.NA;
                String developer;


                if (Utils.contains(gameInfo, KEY_DESCRIPTION)) {
                    description = gameInfo.getString(KEY_DESCRIPTION);
                }

                platform = null;
                if (Utils.contains(gameInfo, KEY_PLATFORMS)) {

                    JSONArray objectPlatforms = gameInfo.getJSONArray(KEY_PLATFORMS);
                    for (int i = 0; i < objectPlatforms.length(); i++) {
                        if (Utils.contains(objectPlatforms.getJSONObject(i),KEY_PLATFORMNAME )) {
                            platform = objectPlatforms.getJSONObject(i).getString(KEY_PLATFORMNAME);
                            if (i!=objectPlatforms.length()-1)
                                platform+=", ";
                        }
                    }

//                    JSONObject objectPlatform = gameInfo.getJSONObject(KEY_PLATFORMS);
//                    if (Utils.contains(objectPlatform, KEY_PLATFORMNAME)) {
//                        platform = objectPlatform.getString(KEY_PLATFORMNAME);
//                    }
                }


                if (Utils.contains(gameInfo, KEY_NAME)) {
                    name = gameInfo.getString(KEY_NAME);
                }

                developer = null;
                if (Utils.contains(gameInfo, KEY_DEVELOPER)) {

                    JSONArray objectDeveloper = gameInfo.getJSONArray(KEY_DEVELOPER);
                    for (int i = 0; i < objectDeveloper.length(); i++) {
                        if (Utils.contains(objectDeveloper.getJSONObject(i),KEY_GETDEVELOPER )) {
                            developer = objectDeveloper.getJSONObject(i).getString(KEY_GETDEVELOPER);
                            if (i!=objectDeveloper.length()-1)
                                developer+=", ";
                        }
                    }

//                    JSONObject objectDeveloper = gameInfo.getJSONObject(KEY_DEVELOPER);
//                    if (Utils.contains(objectDeveloper, KEY_GETDEVELOPER)) {
//                        developer = objectDeveloper.getString(KEY_GETDEVELOPER);
//                    }
                }

                genre = null;
                if (Utils.contains(gameInfo, KEY_GENRE)) {

                    JSONArray objectGenre = gameInfo.getJSONArray(KEY_GENRE);
                    for (int i = 0; i < objectGenre.length(); i++) {
                        if (Utils.contains(objectGenre.getJSONObject(i),KEY_GENRENAME )) {
                            genre = objectGenre.getJSONObject(i).getString(KEY_GENRENAME);
                            if (i!=objectGenre.length()-1)
                                genre+=", ";
                        }
                    }

//                    JSONObject objectGenre = gameInfo.getJSONObject(KEY_GENRE);
//                    if (Utils.contains(objectGenre, KEY_GENRENAME)) {
//                        genre = objectGenre.getString(KEY_GENRENAME);
//                    }
                }

                singlePageImage = null;
                if (Utils.contains(gameInfo, KEY_PAGEIMAGE)) {
                    JSONObject objectPageImage = gameInfo.getJSONObject(KEY_PAGEIMAGE);
                    if (Utils.contains(objectPageImage, KEY_PAGEICON)) {

                        singlePageImage = objectPageImage.getString(KEY_PAGEICON);

                    }
                }

//                Game gamePoints = new Game();
                gameDetail.setPlatform(platform);
                gameDetail.setName(name);
                gameDetail.setDeveloper(developer);
                gameDetail.setGenre(genre);
                gameDetail.setPageImage(singlePageImage);
                gameDetail.setDescription(description);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return gameDetail;
    }


    public static String getMonth(int month) {
        return new DateFormatSymbols().getMonths()[month - 1];
    }
}
