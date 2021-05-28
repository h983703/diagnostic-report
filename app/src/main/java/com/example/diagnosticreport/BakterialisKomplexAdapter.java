package com.example.diagnosticreport;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class BakterialisKomplexAdapter extends RecyclerView.Adapter<BakterialisKomplexAdapter.ViewHolder> {

    private ArrayList<Jelentes> mJelentesekData;
    private Context mContext;
    private int last = -1;


    BakterialisKomplexAdapter (Context context, ArrayList<Jelentes> jelentesek){
        this.mJelentesekData = jelentesek;
        this.mContext = context;
    }


    @Override
    public BakterialisKomplexAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new BakterialisKomplexAdapter.ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.list_bakt_item, parent, false));
    }

    @Override
    public void onBindViewHolder( BakterialisKomplexAdapter.ViewHolder holder, int position) {
        Jelentes aktualis = mJelentesekData.get(position);

        holder.bindTo(aktualis);

        if(holder.getAdapterPosition() > last){
            Animation animation = AnimationUtils.loadAnimation(mContext, R.anim.slide_in_row);
            holder.itemView.startAnimation(animation);
            last = holder.getAdapterPosition();
        }
    }

    @Override
    public int getItemCount() { return mJelentesekData.size(); }

    class ViewHolder extends RecyclerView.ViewHolder {

        private TextView mIdText;
        private TextView mCategoryText;
        private TextView mCodeText;
        private TextView mPaciensText;
        private TextView mConclusionText;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            mIdText = itemView.findViewById(R.id.itemId);
            mCategoryText = itemView.findViewById(R.id.itemMinta);
            mCodeText = itemView.findViewById(R.id.itemFertozes);
            mPaciensText = itemView.findViewById(R.id.itemPaciensNeve);
            mConclusionText = itemView.findViewById(R.id.itemConclusion);


        }

        public void bindTo(Jelentes aktualis) {

            mIdText.setText(aktualis.getIdentifier());
            mCategoryText.setText(aktualis.getCategory());
            mCodeText.setText(aktualis.getCode());
            mPaciensText.setText(aktualis.getPaciens());
            mConclusionText.setText(aktualis.getConclusion());

        }
    }
}
