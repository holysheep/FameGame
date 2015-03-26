package gamecore.materialtest;

import android.app.Application;
import android.content.Context;

import gamecore.database.DBgames;


public class MyApp extends Application {

    public static final String API_KEY = "a94ac164a19a3e2c8c2c7b406d36866b746e7130";
    private static MyApp sInstance;
    private static DBgames mDatabase;

    public synchronized static DBgames getWritableDatabase() {
        if (mDatabase == null) {
            mDatabase = new DBgames(getAppContext());
        }
        return mDatabase;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;
        mDatabase = new DBgames(this);
    }

    public static MyApp getInstance() {

        return sInstance;

    }

    public static Context getAppContext() {

        return sInstance.getApplicationContext();

    }

}
