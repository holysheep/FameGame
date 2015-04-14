package gamecore.extras;

import gamecore.materialtest.MyApp;

public class EndPoints {

    public static final String URL_PC_GAMES = "http://www.giantbomb.com/api/games/";
    public static final String URL_PARAM_API_KEY = "api_key";

    public static String getRequestUrl() {

        return appendApiKey(URL_PC_GAMES
                + "?format=json"
                + "&filter=expected_release_quarter:2"
                + "&sort=number_of_user_reviews:desc&");
    }


    public static String appendApiKey(String base) {
        return base + URL_PARAM_API_KEY + "=" + MyApp.API_KEY;
    }
}
