package com.example.popularmovies.RecyclerAdapter;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.popularmovies.Activities.DetailActivity;
import com.example.popularmovies.Activities.MainActivity;
import com.example.popularmovies.Model.MoviesPojo;
import com.example.popularmovies.R;
import com.squareup.picasso.Picasso;
import java.util.List;
public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.Holder> {
    List<MoviesPojo> data;
    MainActivity mainActivity;
    public MoviesAdapter(MainActivity mainActivity, List<MoviesPojo> data) {
        this.mainActivity=mainActivity;
        this.data=data;
    }
    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.singlerow,viewGroup,false);
        Holder holder=new Holder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, final int i) {
        Picasso.with(mainActivity)
                .load("https://image.tmdb.org/t/p/w500"+data.get(i).getPosatePath())
                .placeholder(R.drawable.placeholder)
                .error(R.drawable.error_image)
                .into(holder.imageView);

        holder.imageView.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        Intent intent=new Intent(mainActivity, DetailActivity.class);
        intent.putExtra("image",data.get(i).getBackGroundpath());
        intent.putExtra("title",data.get(i).getOriginalTitle());
        intent.putExtra("overview",data.get(i).getOverView());
        intent.putExtra("Date",data.get(i).getReleaseDate());
        intent.putExtra("Average",data.get(i).getVoteAverage());
        intent.putExtra("id",data.get(i).getId());
        intent.putExtra("posterpath",data.get(i).getPosatePath());
        mainActivity.startActivity(intent);

    }
});
    }
    @Override
    public int getItemCount() {
        return data.size();
    }

    public void setWords(List<MoviesPojo> moviesInfos) {
        data = moviesInfos;
        notifyDataSetChanged();
    }

    public class Holder extends RecyclerView.ViewHolder {
        ImageView imageView;
        public Holder(@NonNull View itemView) {
            super(itemView);
            imageView=itemView.findViewById(R.id.image);
        }
    }
}
