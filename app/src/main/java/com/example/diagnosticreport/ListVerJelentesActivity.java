package com.example.diagnosticreport;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.os.Bundle;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;

public class ListVerJelentesActivity extends AppCompatActivity {

    private static final String LOG_TAG = ListJelentesekActivity.class.getName();
    private int gridNumber = 1;

    private RecyclerView mRecyclerView;
    private ArrayList<Jelentes> mJelentesList;
    private KomplexVerAdapter mAdapter;

    private FirebaseFirestore mFirestore;
    private CollectionReference mJelentesek;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_ver_jelentes);

        mRecyclerView = findViewById(R.id.recyclerViewVer);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager( new GridLayoutManager(this, gridNumber));
        mJelentesList = new ArrayList<>();

        mAdapter = new KomplexVerAdapter(this, mJelentesList);
        mRecyclerView.setAdapter(mAdapter);

        mFirestore = FirebaseFirestore.getInstance();
        mJelentesek = mFirestore.collection("jelentes");

        queryData();


    }

    private void queryData(){
        mJelentesList.clear();

        mJelentesek.whereEqualTo("category", "vér")
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
            for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                Jelentes jelentesek = document.toObject(Jelentes.class);
                jelentesek.setId(document.getId());
                mJelentesList.add(jelentesek);
            }
            mAdapter.notifyDataSetChanged();
        });

    }

}