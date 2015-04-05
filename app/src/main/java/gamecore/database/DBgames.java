package gamecore.database;


import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;

import java.sql.Date;
import java.util.ArrayList;

import gamecore.Logging.L;
import gamecore.pojo.GameCat;

public class DBgames {

    private GamesHelper mHelper;
    private SQLiteDatabase mDatabase;

    public DBgames(Context context) {
        mHelper = new GamesHelper(context);
        mDatabase = mHelper.getWritableDatabase();
    }

    public void insertGamesPC(ArrayList<GameCat> listGames, boolean clearPrevious) {
        if (clearPrevious) {
            deleteAll();
        }

        //create a sql prepared statement
        String sql = "INSERT INTO " + GamesHelper.TABLE_PC + " ("+ GamesHelper.COLUMN_UID + "," +
                GamesHelper.COLUMN_NAME + "," + GamesHelper.COLUMN_ICON + "," + GamesHelper.COLUMN_RELEASE_DAY  +
                "," + GamesHelper.COLUMN_RELEASE_MONTH + "," + GamesHelper.COLUMN_DECK + ") VALUES (?,?,?,?,?,?);";
        //compile the statement and start a transaction
        SQLiteStatement statement = mDatabase.compileStatement(sql);
        mDatabase.beginTransaction();
        for (int i = 0; i < listGames.size(); i++) {
            GameCat currentGame = listGames.get(i);
            statement.clearBindings();
            //for a given column index, simply bind the data to be put inside that index
            statement.bindString(2, currentGame.getName());
            if (currentGame.getTypeImage() != null) {
                statement.bindString(3, currentGame.getTypeImage());
            }
            if (currentGame.getReleaseDay() != null) {
                Integer releaseDay = currentGame.getReleaseDay();
                statement.bindLong(4, releaseDay);
            }
            L.m(currentGame.getReleaseMonth());
            if (currentGame.getReleaseMonth() != null) {
                statement.bindString(5, currentGame.getReleaseMonth());
            }

            statement.bindString(6, currentGame.getDeck());
            statement.execute();
        }
        //set the transaction as successful and end the transaction
        L.m("inserting entries " + listGames.size() + new Date(System.currentTimeMillis()));
        mDatabase.setTransactionSuccessful();
        mDatabase.endTransaction();
    }


    public ArrayList<GameCat> getAllgamesBoxOffice() {
        ArrayList<GameCat> listGames = new ArrayList<>();

        //get a list of columns to be retrieved
        String[] columns = {GamesHelper.COLUMN_UID,
                GamesHelper.COLUMN_NAME,
                GamesHelper.COLUMN_ICON,
                GamesHelper.COLUMN_RELEASE_DAY,
                GamesHelper.COLUMN_RELEASE_MONTH,
                GamesHelper.COLUMN_DECK,
        };
        try (Cursor cursor = mDatabase.query(GamesHelper.TABLE_PC, columns, null, null, null, null, null)) {
            if (cursor != null && cursor.moveToFirst()) {
                L.m("loading entries " + cursor.getCount() + new Date(System.currentTimeMillis()));
                do {
                    //create a new game object and retrieve the data from the cursor to be stored in this game object
                    GameCat game = new GameCat();
                    //each step is a 2 part process, find the index of the column first, find the data of that column using
                    //that index and finally set our blank game object to contain data
                    game.setName(cursor.getString(cursor.getColumnIndex(GamesHelper.COLUMN_NAME)));
                    game.setTypeImage(cursor.getString(cursor.getColumnIndex(GamesHelper.COLUMN_ICON)));
                    //Integer releaseDay = cursor.getInt(cursor.getColumnIndex(GamesHelper.COLUMN_RELEASE_DAY))
                    game.setReleaseDay(cursor.getInt(cursor.getColumnIndex(GamesHelper.COLUMN_RELEASE_DAY)));
                    game.setReleaseMonth(cursor.getString(cursor.getColumnIndex(GamesHelper.COLUMN_RELEASE_MONTH)));
                    game.setDeck(cursor.getString(cursor.getColumnIndex(GamesHelper.COLUMN_DECK)));

                    //add the game to the list of game objects which we plan to return
                    listGames.add(game);
                }
                while (cursor.moveToNext());
            }
        }
        return listGames;
    }

    public void deleteAll() {
        mDatabase.delete(GamesHelper.TABLE_PC, null, null);
    }

    private static class GamesHelper extends SQLiteOpenHelper {

        public static final String TABLE_PC = "games_pc";
        public static final String COLUMN_UID = "_id";
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_ICON = "icon";
        public static final String COLUMN_RELEASE_DAY = "expected_release_day";
        public static final String COLUMN_RELEASE_MONTH = "expected_release_month";
        public static final String COLUMN_DECK = "deck";
        public static final String CREATE_TABLE_PC = "CREATE TABLE " + TABLE_PC + " (" +
                COLUMN_UID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                COLUMN_NAME + " TEXT," +
                COLUMN_ICON + " TEXT," +
                COLUMN_RELEASE_DAY + " INTEGER," +
                COLUMN_RELEASE_MONTH + " INTEGER," +
                COLUMN_DECK + " TEXT" +
                ");";
        private static final String DB_NAME = "games_db098";
        private static final int DB_VERSION = 1;
        private Context mContext;
        public GamesHelper(Context context) {
            super(context, DB_NAME, null, DB_VERSION);
            mContext = context;
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            try {
                db.execSQL(CREATE_TABLE_PC);
                L.m("create pc table executed");
            } catch (SQLiteException exception) {
                L.t(mContext, exception + "");
            }
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            try {
                L.m("upgrade pc table executed");
                db.execSQL(" DROP TABLE " + TABLE_PC + " IF EXIST;");
                onCreate(db);
            } catch (SQLiteException exception) {
                L.t(mContext, exception + "");
             }
        }
    }
}
