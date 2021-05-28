package com.example.diagnosticreport;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;


public class MainActivity extends AppCompatActivity {
    private static final String LOG_TAG = NewJelentesActivity.class.getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Random Async Task
        Button button = findViewById(R.id.button4);
        new RandomAsyncTask(button).execute();

        Log.i(LOG_TAG, "onCreate");
    }

    //Jelentések listázása
    public void jelentesek(View view) {
        Intent intent = new Intent(this, ListJelentesekActivity.class);
        startActivity(intent);
    }

    //új jelentések felvétele
    public void ujJelentes(View view) {
        Intent intent = new Intent(this, NewJelentesActivity.class);
        startActivity(intent);
    }

    //vérminta alapú jelentések
    public void verMintaJelentes(View view) {
        Intent intent = new Intent(this, ListVerJelentesActivity.class);
        startActivity(intent);
    }

    //bakteriális fertőzés vérminta alapján
    public void bakterialisMintaJelentes(View view) {
        Intent intent = new Intent(this, ListKomplexBaktActivity.class);
        startActivity(intent);
    }


    public void updateDocument(View view){
        DocumentReference docRef = FirebaseFirestore.getInstance()
                .collection("jelentes")
                .document("wEKdClfsjPr75BxU6xEH");

        Map<String, Object> map = new HashMap<>();
        map.put("paciens", "Teszt El");

        // esetleg, ha a code-ot szeretnénk updatelni, arra :
        //map.put("code", "vírusos");

        docRef.update(map)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(LOG_TAG, "onSuccess: updated the doc");
                        Toast.makeText(MainActivity.this, "Sikeres update", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e(LOG_TAG, "onFailure", e);
                    }
                });

    }

}