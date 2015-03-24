package gamecore.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import java.util.Collections;
import java.util.List;

import gamecore.R;
import gamecore.pojo.Information;


public class InfAdapter extends RecyclerView.Adapter<InfAdapter.MyViewHolder> {


    private final Context context;
    private List<Information> data = Collections.emptyList();


    // adapter's Constructor
    public InfAdapter(Context context, List<Information> data) {
        this.data = data;
        this.context = context;
    }


    //delete menu line
    public void delete(int position) {
        data.remove(position);
        notifyItemRemoved(position);

    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_row, parent, false);
        return new MyViewHolder(v);
    }


    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {

        Information current = data.get(position);
        holder.title.setText(current.title);
        holder.icon.setImageResource(current.iconid);

    }


    @Override
    public int getItemCount() {
        return data.size();
    }


    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView title;
        ImageView icon;

        public MyViewHolder(View itemView) {

            super(itemView);
            title = (TextView) itemView.findViewById(R.id.listText);
            icon = (ImageView) itemView.findViewById(R.id.listIcon);
            icon.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            delete(getPosition());
        }
    }

}
