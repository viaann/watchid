package com.example.watchid.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.watchid.Model.MovieCredits;
import com.example.watchid.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.watchid.Utils.ApiUtils.IMAGE_URL;

public class CreditsAdapter extends RecyclerView.Adapter<CreditsAdapter.ViewHolder> {

    private ArrayList<MovieCredits> creditsList = new ArrayList<>();

    public void addCredits(ArrayList<MovieCredits> credits) {
        creditsList.clear();
        creditsList.addAll(credits);
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_credit_list, parent, false);
        return new CreditsAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(creditsList.get(position));
    }

    @Override
    public int getItemCount() {
        return creditsList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.creditsImage)
        ImageView creditsImage;
        @BindView(R.id.creditsName)
        TextView creditsName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        void bind(MovieCredits credits) {

            creditsName.setText(credits.getName());

            Glide.with(itemView.getContext())
                    .load(IMAGE_URL + credits.getProfilePath())
                    .into(creditsImage);
        }
    }
}
