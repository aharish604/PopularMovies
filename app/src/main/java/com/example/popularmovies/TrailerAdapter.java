package com.example.popularmovies;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.popularmovies.Activities.DetailActivity;

import java.util.ArrayList;

/**
 * Created by ADMIN on 12/5/2018.
 */

public class TrailerAdapter extends RecyclerView.Adapter<TrailerAdapter.ViewHolder>{
    DetailActivity context;
    ArrayList<TrailerInfo> list;

    public TrailerAdapter(DetailActivity context, ArrayList<TrailerInfo> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.trailer_row,parent,false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final TrailerInfo info=list.get(position);

        holder.trailerTv.setText(info.getName());
        holder.trailerTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri uri = Uri.parse("https://www.youtube.com/watch?v=" +info.getKey());
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView trailerTv;
        public ViewHolder(View itemView) {
            super(itemView);
            trailerTv=itemView.findViewById(R.id.trailerUrl_id);
        }
    }
}
