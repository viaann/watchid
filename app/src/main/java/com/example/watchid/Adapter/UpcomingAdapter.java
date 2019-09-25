package com.example.watchid.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.example.watchid.Model.MovieUpComing;
import com.example.watchid.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.watchid.Utils.ApiUtils.IMAGE_URL;

public class UpcomingAdapter extends RecyclerView.Adapter<UpcomingAdapter.ViewHolder> {

    private final Context context;
    private List<MovieUpComing> items;
    private PostItemListener itemListener;

    public UpcomingAdapter(Context context, List<MovieUpComing> items, PostItemListener itemListener) {
        this.context = context;
        this.items = items;
        this.itemListener = itemListener;
    }


    @NonNull
    @Override
    public UpcomingAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_upcoming_list, parent, false);
        return new ViewHolder(view, this.itemListener);
    }

    @Override
    public void onBindViewHolder(@NonNull UpcomingAdapter.ViewHolder holder, int position) {
        holder.bind(items.get(position));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void addMovies(List<MovieUpComing> item) {
        items = item;
        notifyDataSetChanged();
    }


    private MovieUpComing getItem(int adapterPosition) {
        return items.get(adapterPosition);
    }

    public interface PostItemListener {
        void onPostClick(int id);
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        PostItemListener itemListener;

        @BindView(R.id.upPoster)
        ImageView upPoster;
        @BindView(R.id.upTitle)
        TextView upTitle;

        public ViewHolder(@NonNull View itemView, PostItemListener itemListener) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            this.itemListener = itemListener;
            itemView.setOnClickListener(this);
        }

        void bind(MovieUpComing movieUpComing) {
            upTitle.setText(movieUpComing.getReleaseDate());

            Glide.with(itemView.getContext())
                    .load(IMAGE_URL + movieUpComing.getPosterPath())
                    .transform(new RoundedCorners(100))
                    .into(upPoster);

        }

        @Override
        public void onClick(View view) {
            MovieUpComing item = getItem(getAdapterPosition());
            this.itemListener.onPostClick(item.getId());
            notifyDataSetChanged();
        }
    }
}
