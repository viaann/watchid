package com.example.watchid.Adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.watchid.Model.Favorite;
import com.example.watchid.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.watchid.Utils.ApiUtils.IMAGE_URL;

public class FavoriteAdapter extends RecyclerView.Adapter<FavoriteAdapter.ViewHolder> {

    private final ArrayList<Favorite> favorites = new ArrayList<>();
    private final Activity activity;
    private final PostItemListener itemListener;

    public FavoriteAdapter(Activity activity, PostItemListener itemListener) {
        this.activity = activity;
        this.itemListener = itemListener;
    }

    public ArrayList<Favorite> getListFavorite() {
        return favorites;
    }

    public void setListFavorite(ArrayList<Favorite> listFavorite) {

        if (listFavorite.size() > 0) {
            this.favorites.clear();
        }
        this.favorites.addAll(listFavorite);

        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_favorite_list, parent, false);
        return new ViewHolder(view, this.itemListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(favorites.get(position));
    }

    @Override
    public int getItemCount() {
        return favorites.size();
    }

    private Favorite getItem(int adapterPosition) {
        return favorites.get(adapterPosition);
    }

    public interface PostItemListener {
        void onPostClick(int mId);
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        @BindView(R.id.favTitle)
        TextView favTitle;
        @BindView(R.id.favPoster)
        ImageView favPoster;
        @BindView(R.id.favRating)
        RatingBar favRating;
        PostItemListener itemListener;

        public ViewHolder(@NonNull View itemView, PostItemListener itemListener) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            this.itemListener = itemListener;
            itemView.setOnClickListener(this);
        }

        void bind(Favorite favorite) {
            double score = favorite.getFavRating() / 2;

            favRating.setRating((float) score);
            favTitle.setText(favorite.getFavTitle());

            Glide.with(itemView.getContext())
                    .load(IMAGE_URL + favorite.getFavPoster())
                    .into(favPoster);

        }
        @Override
        public void onClick(View view) {
            Favorite item = getItem(getAdapterPosition());
            this.itemListener.onPostClick(item.getmId());
            notifyDataSetChanged();
        }
    }
}
