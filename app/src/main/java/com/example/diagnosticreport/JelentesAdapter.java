package com.example.diagnosticreport;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class JelentesAdapter  extends RecyclerView.Adapter<JelentesAdapter.ViewHolder> {

    private ArrayList<Jelentes> mJelentesList;
    private Context mContext;
    private int lastPosition = -1;

    JelentesAdapter(Context context, ArrayList<Jelentes> itemsData){
        this.mJelentesList = itemsData;
        this.mContext = context;
    }


    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
         return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.list_item, parent, false));
    }

    @Override
    public void onBindViewHolder( JelentesAdapter.ViewHolder holder, int position) {
        Jelentes aktualisItem = mJelentesList.get(position);

        holder.bindTo(aktualisItem);

        if(holder.getAdapterPosition() > lastPosition){
            Animation animation = AnimationUtils.loadAnimation(mContext, R.anim.slide_in_row);
            holder.itemView.startAnimation(animation);
            lastPosition = holder.getAdapterPosition();
        }
    }

    @Override
    public int getItemCount() {
        return mJelentesList.size();
    }




    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView mIdText;
        private TextView mCategoryText;
        private TextView mCodeText;
        private TextView mPaciensText;
        private TextView mConclusionText;

        View mView;

        public ViewHolder(View itemView){
            super(itemView);
            mView = itemView;
            mIdText = itemView.findViewById(R.id.itemId);
            mCategoryText = itemView.findViewById(R.id.itemMinta);
            mCodeText = itemView.findViewById(R.id.itemFertozes);
            mPaciensText = itemView.findViewById(R.id.itemPaciensNeve);
            mConclusionText = itemView.findViewById(R.id.itemConclusion);

            itemView.setOnClickListener(this);

        }


        public void bindTo(Jelentes aktualisItem) {
            mIdText.setText(aktualisItem.getIdentifier());
            mCategoryText.setText(aktualisItem.getCategory());
            mCodeText.setText(aktualisItem.getCode());
            mPaciensText.setText(aktualisItem.getPaciens());
            mConclusionText.setText(aktualisItem.getConclusion());

            itemView.findViewById(R.id.torlesImageView).setOnClickListener(view -> ((ListJelentesekActivity)mContext).deleteJelentes(aktualisItem));
        }

        @Override
        public void onClick(View v) {
            Jelentes jelentesek = mJelentesList.get(getAdapterPosition());
            Intent intent = new Intent(mContext, MainActivity.class);
            intent.putExtra("jelentes", String.valueOf(jelentesek));
            mContext.startActivity(intent);

        }
    };

};


