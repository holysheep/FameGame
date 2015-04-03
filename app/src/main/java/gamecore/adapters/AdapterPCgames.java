package gamecore.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;


import java.util.ArrayList;

import gamecore.R;
import gamecore.activities.SubActivity;
import gamecore.extras.Constants;
import gamecore.network.VolleySingleton;
import gamecore.pojo.GameCat;


public class AdapterPCgames extends RecyclerView.Adapter<AdapterPCgames.ViewHolderPCgames> {


    private ArrayList<GameCat> listGames = new ArrayList<>();
    private LayoutInflater layoutInflater;
    private VolleySingleton volleySingleton;
    private ImageLoader mImageLoader;
    private Context context;
    private ClickListener clickListener;


    public AdapterPCgames(Context context) {
        layoutInflater = LayoutInflater.from(context);
        volleySingleton = VolleySingleton.getInstance();
        mImageLoader = volleySingleton.getmImageLoader();
        this.context = context;
    }


    public void setGamelist(ArrayList<GameCat> listGames) {
        this.listGames = listGames;
        notifyItemRangeChanged(0, listGames.size());
    }


    @Override
    public ViewHolderPCgames onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.row_pc_games, parent, false);
        return new ViewHolderPCgames(view);
    }


    @Override
    public void onBindViewHolder(final ViewHolderPCgames holder, int position) {

        GameCat currentGamecat = listGames.get(position);
        holder.gameTitle.setText(currentGamecat.getName());

        String deck = currentGamecat.getDeck();
        if (deck != null) {
            holder.gameDescription.setText(currentGamecat.getDeck());
        }
        if (currentGamecat.getReleaseDay() != null)
            holder.gameDay.setText(currentGamecat.getReleaseDay().toString());

        if (currentGamecat.getReleaseMonth() != null)
            holder.gameMonth.setText(currentGamecat.getReleaseMonth());

        /* String typeImage = currentGamecat.getTypeImage();
        if (typeImage != null) {
            mImageLoader.get(typeImage, new ImageLoader.ImageListener() {
                @Override
                public void onResponse(ImageLoader.ImageContainer response, boolean isImmediate) {
                    holder.gameIcon.setImageBitmap(response.getBitmap());
                }

                @Override
                public void onErrorResponse(VolleyError error) {

                }
            });
        } */

        String urlImage = currentGamecat.getTypeImage();
        loadImages(urlImage, holder);
    }

    public void setClickListener(ClickListener clickListener) {
        this.clickListener = clickListener;
    }


     public void loadImages(String typeImage, final ViewHolderPCgames holder) {
        if (typeImage != null) {
            mImageLoader.get(typeImage, new ImageLoader.ImageListener() {
                @Override
                public void onResponse(ImageLoader.ImageContainer response, boolean isImmediate) {
                    holder.gameIcon.setImageBitmap(response.getBitmap());
                }

                @Override
                public void onErrorResponse(VolleyError error) {

                }
            });
        }
    }


    @Override
    public int getItemCount() {
        return listGames.size();
    }


    class ViewHolderPCgames extends RecyclerView.ViewHolder implements View.OnClickListener {

        private ImageView gameIcon;
        private TextView gameTitle;
        private TextView gameDescription;
        private TextView gameDay;
        private TextView gameMonth;

        public ViewHolderPCgames(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            gameIcon = (ImageView) itemView.findViewById(R.id.gameIcon);
            gameTitle = (TextView) itemView.findViewById(R.id.gameTitle);
            gameDay = (TextView) itemView.findViewById(R.id.dayRelease);
            gameMonth = (TextView) itemView.findViewById(R.id.monthRelease);
            gameDescription = (TextView) itemView.findViewById(R.id.gameDescript);

        }

        @Override
        public void onClick(View v) {
            context.startActivity(new Intent(context, SubActivity.class));

            if (clickListener != null){
                clickListener.itemClicked(v, getPosition());
            }
        }
    }

    public interface ClickListener {

        public void itemClicked(View view, int position);
    }
}
