package com.example.diagnosticreport;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;

public class ListKomplexBaktActivity extends AppCompatActivity {

    private static final String LOG_TAG = ListJelentesekActivity.class.getName();
    private int gridNumber = 1;

    private RecyclerView mRecyclerView;
    private ArrayList<Jelentes> mJelentesList;
    private BakterialisKomplexAdapter mAdapter;

    private FirebaseFirestore mFirestore;
    private CollectionReference mJelentesek;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_komplex_bakt);

        mRecyclerView = findViewById(R.id.recyclerViewBakt);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager( new GridLayoutManager(this, gridNumber));
        mJelentesList = new ArrayList<>();

        mAdapter = new BakterialisKomplexAdapter(this, mJelentesList);
        mRecyclerView.setAdapter(mAdapter);

        mFirestore = FirebaseFirestore.getInstance();
        mJelentesek = mFirestore.collection("jelentes");

        queryData();
    }

    private void queryData(){
        mJelentesList.clear();

        mJelentesek.whereEqualTo("category", "vér")
                .whereEqualTo("code", "bakteriális")
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