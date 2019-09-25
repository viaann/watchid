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
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.example.watchid.Model.MovieNowPlaying;
import com.example.watchid.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.watchid.Utils.ApiUtils.IMAGE_URL;

public class NowPlayingAdapter extends RecyclerView.Adapter<NowPlayingAdapter.ViewHolder> {


    private final Context context;
    private List<MovieNowPlaying> items;
    private PostItemListener itemListener;

    public NowPlayingAdapter(Context context, List<MovieNowPlaying> items , PostItemListener itemListener) {
        this.context = context;
        this.items = items;

        this.itemListener = itemListener;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_nowplaying_list, parent, false);
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

    public void addMovies(List<MovieNowPlaying> item) {
        items = item;
        notifyDataSetChanged();
    }



    private MovieNowPlaying getItem(int adapterPosition) {
        return items.get(adapterPosition);
    }

    public interface PostItemListener {
        void onPostClick(int id);
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        PostItemListener itemListener;

        @BindView(R.id.nsImageView)
        ImageView nsImageView;
        @BindView(R.id.nsRatingBar)
        RatingBar nsRatingBar;
        @BindView(R.id.nsTvTitle)
        TextView nsTvTitle;

        public ViewHolder(@NonNull View itemView, PostItemListener itemListener) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            this.itemListener = itemListener;
            itemView.setOnClickListener(this);
        }

        void bind(MovieNowPlaying movie) {
            double rating = movie.getRating() / 2;
            nsRatingBar.setRating((float) rating);

            nsTvTitle.setText(movie.getTitle());

            Glide.with(itemView.getContext())
                    .load(IMAGE_URL + movie.getPosterPath())
                    .transform(new RoundedCorners(45))
                    .into(nsImageView);

        }
        @Override
        public void onClick(View view) {
            MovieNowPlaying item = getItem(getAdapterPosition());
            this.itemListener.onPostClick(item.getId());
            notifyDataSetChanged();
        }
    }
}
