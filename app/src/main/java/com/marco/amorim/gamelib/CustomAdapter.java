package com.marco.amorim.gamelib;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder> {

    private Context context;
    private Activity activity;
    private ArrayList game_id, game_title, game_studio, game_store_link;

    CustomAdapter(Activity activity, Context context, ArrayList game_id, ArrayList game_title, ArrayList game_studio,
                  ArrayList game_store_link){
        this.activity = activity;
        this.context = context;
        this.game_id = game_id;
        this.game_title = game_title;
        this.game_studio = game_studio;
        this.game_store_link = game_store_link;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.my_row, parent, false);
        return new MyViewHolder(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {
        holder.game_id_txt.setText(String.valueOf(game_id.get(position)));
        holder.game_title_txt.setText(String.valueOf(game_title.get(position)));
        holder.game_studio_txt.setText(String.valueOf(game_studio.get(position)));
        holder.game_store_link_txt.setText(String.valueOf(game_store_link.get(position)));
        // Recyclerview onClickListener
        holder.mainLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, UpdateActivity.class);
                intent.putExtra("id", String.valueOf(game_id.get(position)));
                intent.putExtra("title", String.valueOf(game_title.get(position)));
                intent.putExtra("author", String.valueOf(game_studio.get(position)));
                intent.putExtra("pages", String.valueOf(game_store_link.get(position)));
                activity.startActivityForResult(intent, 1);
            }
        });


    }

    @Override
    public int getItemCount() {
        return game_id.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView game_id_txt, game_title_txt, game_studio_txt, game_store_link_txt;
        LinearLayout mainLayout;

        MyViewHolder(@NonNull View itemView) {
            super(itemView);
            game_id_txt = itemView.findViewById(R.id.game_id_txt);
            game_title_txt = itemView.findViewById(R.id.game_title_txt);
            game_studio_txt = itemView.findViewById(R.id.game_studio_txt);
            game_store_link_txt = itemView.findViewById(R.id.game_store_link_txt);
            mainLayout = itemView.findViewById(R.id.mainLayout);
            //Animate Recyclerview
            Animation translate_anim = AnimationUtils.loadAnimation(context, R.anim.translate_anim);
            mainLayout.setAnimation(translate_anim);
        }

    }

}
