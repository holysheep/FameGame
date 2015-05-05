package gamecore.adapters;

import android.content.Context;
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
import gamecore.network.VolleySingleton;
import gamecore.pojo.Game;


public class AdapterNewGames extends RecyclerView.Adapter<AdapterNewGames.ViewHolderGames> {


    private ArrayList<Game> listGames = new ArrayList<>();
    private LayoutInflater layoutInflater;
    private VolleySingleton volleySingleton;
    private ImageLoader mImageLoader;
    private Context context;
    private ClickListener clickListener;


    public AdapterNewGames(Context context) {
        layoutInflater = LayoutInflater.from(context);
        volleySingleton = VolleySingleton.getInstance();
        mImageLoader = volleySingleton.getmImageLoader();
        this.context = context;
    }


    public void setGamelist(ArrayList<Game> listGames) {
        this.listGames = listGames;
        notifyItemRangeChanged(0, listGames.size());
    }


    @Override
    public ViewHolderGames onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.pc_games_row, parent, false);
        return new ViewHolderGames(view);
    }


    @Override
    public void onBindViewHolder(final ViewHolderGames holder, int position) {

        Game currentGame = listGames.get(position);
        holder.gameTitle.setText(currentGame.getName());

        String deck = currentGame.getDeck();
        if (deck != null) {
            holder.gameDesc.setText(currentGame.getDeck() + "...");
        }

        if (currentGame.getReleaseDay() != null && currentGame.getReleaseDay() != 0) {
            holder.gameDay.setText(currentGame.getReleaseDay().toString());
        } else {
            holder.gameDay.setText("  ");
        }

        if (currentGame.getReleaseMonth() != null) {
            holder.gameMonth.setText(currentGame.getReleaseMonth());
        } else {
            holder.gameMonth.setText("N/A");
        }

        String urlImage = currentGame.getMainImage();
        if (urlImage != null) {
            loadImages(urlImage, holder);
        } else {
            holder.gameIcon.setImageResource(R.drawable.no_image);
        }

    }

    public void setClickListener(ClickListener clickListener) {
        this.clickListener = clickListener;
    }


    public void loadImages(String typeImage, final ViewHolderGames holder) {

        if (typeImage != null) {
            mImageLoader.get(typeImage, new ImageLoader.ImageListener() {
                @Override
                public void onResponse(ImageLoader.ImageContainer response, boolean isImmediate) {
                    holder.gameIcon.setImageBitmap(response.getBitmap());
                }

                @Override
                public void onErrorResponse(VolleyError error) {
                    holder.gameIcon.setImageResource(R.drawable.no_image);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return listGames.size();
    }

    class ViewHolderGames extends RecyclerView.ViewHolder implements View.OnClickListener {

        private ImageView gameIcon;
        private TextView gameTitle;
        private TextView gameDesc;
        private TextView gameDay;
        private TextView gameMonth;

        public ViewHolderGames(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            gameIcon = (ImageView) itemView.findViewById(R.id.gameIcon);
            gameIcon.setContentDescription("Test");
            gameTitle = (TextView) itemView.findViewById(R.id.gameTitle);
            gameDay = (TextView) itemView.findViewById(R.id.dayRelease);
            gameMonth = (TextView) itemView.findViewById(R.id.monthRelease);
            gameDesc = (TextView) itemView.findViewById(R.id.gameDescript);

        }

        @Override
        public void onClick(View v) {
            if (clickListener != null) {
                clickListener.itemClicked(v, getPosition());
            }
        }
    }

    public interface ClickListener {
        void itemClicked(View view, int position);
    }
}
