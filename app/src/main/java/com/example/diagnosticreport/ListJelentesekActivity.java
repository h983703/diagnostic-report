package com.example.diagnosticreport;



import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;

public class ListJelentesekActivity extends AppCompatActivity {
    private static final String LOG_TAG = ListJelentesekActivity.class.getName();
    private int gridNumber = 1;
    private int queryLimit = 10;

    private RecyclerView mRecyclerView;
    private ArrayList<Jelentes> mJelentesList;
    private JelentesAdapter mAdapter;

    private FirebaseFirestore mFirestore;
    private CollectionReference mJelentesek;

    private NotificationHandler mNotificationHandler;


    private boolean viewRow = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_jelentesek);

        mRecyclerView = findViewById(R.id.recyclerView);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager( new GridLayoutManager(this, gridNumber));
        mJelentesList = new ArrayList<>();

        mAdapter = new JelentesAdapter(this, mJelentesList);
        mRecyclerView.setAdapter(mAdapter);

        mFirestore = FirebaseFirestore.getInstance();
        mJelentesek = mFirestore.collection("jelentes");

        queryData();


        IntentFilter filter = new IntentFilter();
        filter.addAction(Intent.ACTION_POWER_CONNECTED);
        filter.addAction(Intent.ACTION_POWER_DISCONNECTED);
        this.registerReciver(powerReciver, filter);

        mNotificationHandler = new NotificationHandler(this);
    }

    private void registerReciver(Object o, IntentFilter filter) {
    }

    BroadcastReceiver powerReciver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();

            if (action == null)
                return;

            switch (action){
                case Intent.ACTION_POWER_CONNECTED:
                    queryLimit = 10;
                    break;
                case Intent.ACTION_POWER_DISCONNECTED:
                    queryLimit = 5;
                    break;
            }
            queryData();

        }
    };




    private void queryData(){
        mJelentesList.clear();

        mJelentesek.orderBy("identifier").limit(queryLimit).get().addOnSuccessListener(queryDocumentSnapshots -> {
            for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                Jelentes jelentesek = document.toObject(Jelentes.class);
                jelentesek.setId(document.getId());
                mJelentesList.add(jelentesek);
            }
            mAdapter.notifyDataSetChanged();
        });

    }



    public void deleteJelentes(Jelentes item) {
        DocumentReference docRef = mJelentesek.document(item._getId());
        docRef.delete().addOnSuccessListener(success -> {
            Log.d(LOG_TAG, "Jelentés törölve" + item._getId());
            Toast.makeText(ListJelentesekActivity.this, "Jelentés törölve", Toast.LENGTH_SHORT).show();
        })
                .addOnFailureListener(fail -> {
                    Toast.makeText(this, "Nem törlődött: " + item._getId(), Toast.LENGTH_LONG).show();
                });


        mNotificationHandler.send(item.getIdentifier());
        queryData();
//        mNotificationHandler.cancel();
    }




    @Override
    protected void onDestroy() {
        super.onDestroy();
//        unregisterReceiver(powerReciver);
    }



    //    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        super.onCreateOptionsMenu(menu);
//        getMenuInflater().inflate(R.menu.jelentes_list_menu, menu);
//        MenuItem menuItem = menu.findItem(R.id.search_bar);
//        SearchView searchView = (SearchView) MenuItemCompat.getActionView(menuItem);
//        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//            @Override
//            public boolean onQueryTextSubmit(String query) {
//                return false;
//            }
//
//            @Override
//            public boolean onQueryTextChange(String newText) {
//                return false;
//            }
//        });
//
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
//        switch (item.getItemId()){
//            case R.id.kezdooldalButton:
//                finish();
//                return true;
//            case R.id.view_selector:
//                if(viewRow){
//                    changeSpanCount(item, R.drawable.ic_view_grid, 1);
//                }else {
//                    changeSpanCount(item, R.drawable.ic_view_stream, 2);
//                }
//                return true;
//            default:
//                return super.onOptionsItemSelected(item);
//        }
//    }
//
//    private void changeSpanCount(MenuItem item, int drawableId, int spanCount) {
//        viewRow = !viewRow;
//        item.setIcon(drawableId);
//        GridLayoutManager layoutManager = (GridLayoutManager) mRecyclerView.getLayoutManager();
//        layoutManager.setSpanCount(spanCount);
//
//    }
//
//    @Override
//    public boolean onPrepareOptionsMenu(Menu menu) {
//        return super.onPrepareOptionsMenu(menu);
//    }



}