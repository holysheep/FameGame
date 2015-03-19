package com.example.lexy.materialtest.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.lexy.materialtest.R;
import com.example.lexy.materialtest.adapters.AdapterPCgames;
import com.example.lexy.materialtest.extras.Constants;
import com.example.lexy.materialtest.network.VolleySingleton;
import com.example.lexy.materialtest.pojo.GameCat;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormatSymbols;
import java.util.ArrayList;

import static com.example.lexy.materialtest.extras.Keys.EndPointPC.KEY_DECK;
import static com.example.lexy.materialtest.extras.Keys.EndPointPC.KEY_ICON;
import static com.example.lexy.materialtest.extras.Keys.EndPointPC.KEY_ID;
import static com.example.lexy.materialtest.extras.Keys.EndPointPC.KEY_IMAGE;
import static com.example.lexy.materialtest.extras.Keys.EndPointPC.KEY_NAME;
import static com.example.lexy.materialtest.extras.Keys.EndPointPC.KEY_RELEASE_DAY;
import static com.example.lexy.materialtest.extras.Keys.EndPointPC.KEY_RELEASE_MONTH;
import static com.example.lexy.materialtest.extras.Keys.EndPointPC.KEY_RESULTS;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PCFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PCFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    public static final String URL_PC_NEWGAMES = "http://www.giantbomb.com/api/games/" +
            "?api_key=a94ac164a19a3e2c8c2c7b406d36866b746e7130&format=json" +
            "&filter=expected_release_quarter:2,platforms:94&sort=number_of_user_reviews:desc";
    private static final String STATE_GAMES = "state_games";


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private VolleySingleton volleySingleton;
    private ImageLoader imageLoader;
    private RequestQueue requestQueue;
    private RecyclerView listPCnewgames;
    private AdapterPCgames adapterPCgames;
    ArrayList<GameCat> listPCGames = new ArrayList<>();
    private TextView textVolleyError;


    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MmoFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PCFragment newInstance(String param1, String param2) {
        PCFragment fragment = new PCFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(STATE_GAMES, listPCGames);
    }

    public PCFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

        volleySingleton = VolleySingleton.getInstance();
        requestQueue = volleySingleton.getRequestQueue();
        sendJsonRequest();
    }


    private void sendJsonRequest() {
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, URL_PC_NEWGAMES,
                (JSONObject) null,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        textVolleyError.setVisibility(View.GONE);
                        listPCGames = parseJSONResponse(response);
                        adapterPCgames.setGamelist(listPCGames);
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                hadleVolleyError(error);
            }
        });
        requestQueue.add(request);
    }


    private void hadleVolleyError(VolleyError error) {

        textVolleyError.setVisibility(View.VISIBLE);
        if (error instanceof TimeoutError || error instanceof NoConnectionError) {
            textVolleyError.setText(R.string.error_timout);

        } else if (error instanceof NetworkError) {
            textVolleyError.setText(R.string.error_network);

        } else if (error instanceof ParseError) {
            textVolleyError.setText(R.string.error_parser);
        }
    }


    private ArrayList<GameCat> parseJSONResponse(JSONObject response) {
        ArrayList<GameCat> listPCGames = new ArrayList<>();

        if (response != null && response.length() > 0) {
            try {
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

                        if (currentGame.has(KEY_ID) && !currentGame.isNull(KEY_ID)) {
                            id = currentGame.getInt(KEY_ID);
                        }

                        if (currentGame.has(KEY_NAME) && !currentGame.isNull(KEY_NAME)) {
                            name = currentGame.getString(KEY_NAME);
                        }

                        releaseDay = null;
                        if (currentGame.has(KEY_RELEASE_DAY) && !currentGame.isNull(KEY_RELEASE_DAY)) {
                            releaseDay = currentGame.getInt(KEY_RELEASE_DAY);
                        }

                        if (currentGame.has(KEY_DECK) && !currentGame.isNull(KEY_DECK)) {
                            deck = currentGame.getString(KEY_DECK);
                        }

                        releaseMonth = null;

                        if (currentGame.has(KEY_RELEASE_MONTH) && !currentGame.isNull(KEY_RELEASE_MONTH)) {
                            Integer monthNumber = currentGame.getInt(KEY_RELEASE_MONTH);
                            releaseMonth = getMonth(monthNumber);
                        }


                        typeImage = null;
                        if (currentGame.has(KEY_IMAGE) && !currentGame.isNull(KEY_IMAGE)) {

                            JSONObject objectImage = currentGame.getJSONObject(KEY_IMAGE);

                            if (objectImage.has(KEY_ICON) && objectImage.has(KEY_ICON)) {

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
        }
        return listPCGames;
    }


    public String getMonth(int month) {
        return new DateFormatSymbols().getMonths()[month - 1];
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_pc, container, false);
        textVolleyError = (TextView) view.findViewById(R.id.volleyTextError);
        listPCnewgames = (RecyclerView) view.findViewById(R.id.listPCgames);
        adapterPCgames = new AdapterPCgames(getActivity());
        listPCnewgames.setAdapter(adapterPCgames);
        listPCnewgames.setLayoutManager(new LinearLayoutManager(getActivity()));
        if (savedInstanceState != null) {
            listPCGames = savedInstanceState.getParcelableArrayList(STATE_GAMES);
            adapterPCgames.setGamelist(listPCGames);
        } else {
            sendJsonRequest();
        }
        return view;
    }
}
