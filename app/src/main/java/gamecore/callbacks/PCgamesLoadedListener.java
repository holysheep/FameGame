package gamecore.callbacks;


import java.util.ArrayList;

import gamecore.pojo.GameCat;

public interface PCgamesLoadedListener {
    public void onPCgamesLoaded(ArrayList<GameCat> listGames);
}
