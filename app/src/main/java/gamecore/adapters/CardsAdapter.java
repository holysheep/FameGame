package gamecore.adapters;


import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import gamecore.R;
import gamecore.pojo.Game;

public class CardsAdapter extends RecyclerView.Adapter<CardsAdapter.ViewHolder> {


    private LayoutInflater layoutInflater;


    public CardsAdapter(Context context) {
        layoutInflater = LayoutInflater.from(context);
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.cards, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.cardGameName.setText("Test");
        holder.cardGameImage.setImageResource(R.drawable.sc2cat);
    }

    @Override
    public int getItemCount() {
        return 6;
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private TextView cardGameName;
        private ImageView cardGameImage;

        public ViewHolder(View itemView) {
            super(itemView);
            cardGameName = (TextView) itemView.findViewById(R.id.countryName);
            cardGameImage = (ImageView) itemView.findViewById(R.id.countryImage);
        }
    }
}