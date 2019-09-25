package com.example.watchid.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.watchid.Model.Movie;
import com.example.watchid.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.watchid.Utils.ApiUtils.IMAGE_URL;

public class ResultAdapter extends RecyclerView.Adapter<ResultAdapter.ViewHolder> {

    private final Context context;
    private List<Movie> items;
    private PostItemListener itemListener;

    public ResultAdapter(Context context, List<Movie> items , PostItemListener itemListener) {
        this.context = context;
        this.items = items;
        this.itemListener = itemListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_popular_list, parent, false);
        return new ViewHolder(view, this.itemListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(items.get(position));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void addMovies(List<Movie> item) {
        items = item;
        notifyDataSetChanged();
    }


    private Movie getItem(int adapterPosition) {
        return items.get(adapterPosition);
    }

    public interface PostItemListener {
        void onPostClick(int id);
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.popularPoster)
        ImageView popularPoster;
        @BindView(R.id.popularTitle)
        TextView popularTitle;
        @BindView(R.id.popularRating)
        RatingBar popularRating;
        PostItemListener itemListener;

        public ViewHolder(@NonNull View itemView, PostItemListener itemListener) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            this.itemListener = itemListener;
            itemView.setOnClickListener(this);
        }

        void bind(Movie movie) {
            double score = movie.getRating() / 2;

            popularTitle.setText(movie.getTitle());
            popularRating.setRating((float) score);

            Glide.with(itemView.getContext())
                    .load(IMAGE_URL + movie.getPosterPath())
                    .into(popularPoster);
        }

        @Override
        public void onClick(View view) {
            Movie item = getItem(getAdapterPosition());
            this.itemListener.onPostClick(item.getId());
            notifyDataSetChanged();
        }
    }
}
