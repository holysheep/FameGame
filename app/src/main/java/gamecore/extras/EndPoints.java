package gamecore.extras;
import static gamecore.extras.UrlEndPoints.URL_PC_GAMES;
import gamecore.materialtest.MyApp;
import static gamecore.extras.UrlEndPoints.URL_CHAR_AMEPERSAND;
import static gamecore.extras.UrlEndPoints.URL_CHAR_QUESTION;
import static gamecore.extras.UrlEndPoints.URL_PARAM_API_KEY;
import static gamecore.extras.UrlEndPoints.URL_PARAM_LIMIT;

public class EndPoints {

    public static String getRequestUrl(int limit) {

    return URL_PC_GAMES
            + URL_CHAR_QUESTION
            + URL_PARAM_API_KEY + MyApp.API_KEY
            + URL_CHAR_AMEPERSAND
            + URL_PARAM_LIMIT + limit;
}
}
