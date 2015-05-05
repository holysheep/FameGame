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
import gamecore.pojo.DrawerPoints;

public class DrawerAdapter extends RecyclerView.Adapter <DrawerAdapter.DrawerViewHolder>{

    private LayoutInflater inflater;
    List<DrawerPoints> data = Collections.emptyList();

    public DrawerAdapter(Context context, List<DrawerPoints> data) {
        inflater = LayoutInflater.from(context);
        this.data = data;
    }

    @Override
    public DrawerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.drawer_row, parent, false);
        return new DrawerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(DrawerViewHolder holder, int position) {
        DrawerPoints current = data.get(position);
        holder.title.setText(current.title);
        holder.icon.setImageResource(current.iconId);
    }

    @Override
    public int getItemCount() {
        return 3;
    }

    class DrawerViewHolder extends RecyclerView.ViewHolder {

        TextView title;
        ImageView icon;

        public DrawerViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.listDrawerText);
            icon = (ImageView) itemView.findViewById(R.id.listDrawerIcon);
        }
    }
}
