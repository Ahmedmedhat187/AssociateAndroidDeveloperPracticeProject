package com.ahmed.androiddeveloperpracticeproject.adapters;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;
import com.ahmed.androiddeveloperpracticeproject.R;
import com.ahmed.androiddeveloperpracticeproject.models.LearnerModel;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import java.util.ArrayList;
import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>{

    LayoutInflater layoutInflater;
    Context context;
    List<LearnerModel> learners = new ArrayList<>();

    public RecyclerViewAdapter(Context context) {
        this.context = context;
        layoutInflater = LayoutInflater.from(this.context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.learners_item_card, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        LearnerModel learnerModel = learners.get(position);
        holder.nameTextView.setText(learnerModel.getName());

        if(learnerModel.getScore() > 0){
            holder.detailsTextView.setText(learnerModel.getScore() + context.getString(R.string.skill_iq_score_text) + learnerModel.getCountry());
        }
        else{
            holder.detailsTextView.setText(learnerModel.getHours() + context.getString(R.string.learning_hours_text) + learnerModel.getCountry());
        }

        Glide.with(context)
                .load(learnerModel.getBadgeUrl())
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        holder.progressBar.setVisibility(View.GONE);
                        return false;
                    }
                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        holder.progressBar.setVisibility(View.GONE);
                        return false;
                    }
                })
                .into(holder.badgeImageView);

    }

    @Override
    public int getItemCount() {
        return learners.size();
    }


    public void updateLearnerList(List<LearnerModel> learners) {
        this.learners = learners;
        notifyDataSetChanged();
    }



    class ViewHolder extends RecyclerView.ViewHolder{
        TextView nameTextView;
        TextView detailsTextView;
        ImageView badgeImageView;
        ProgressBar progressBar;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.name);
            detailsTextView = itemView.findViewById(R.id.details);
            badgeImageView = itemView.findViewById(R.id.imageView);
            progressBar = itemView.findViewById(R.id.progressBar);
        }
    }

}
