package com.example.diagnosticreport;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class NewJelentesActivity extends AppCompatActivity {
    private static final String LOG_TAG = NewJelentesActivity.class.getName();
    private static final String FILE_Name="report.json";

    private static final String PREF_KEY = MainActivity.class.getPackage().toString();
    private SharedPreferences preferences;


    private EditText editTextIdentifier;
    private RadioGroup radioGroupMinta;
    private EditText editTextFertozes;
    private EditText editTextPaciensName;
    private EditText editTextConclusion;
    private Button mentesButton;
    private Button kezdooldalButton;

    private FirebaseFirestore mFirestore;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_jelentes);
        editTextIdentifier = findViewById(R.id.editTextIdentifier);
        radioGroupMinta = findViewById(R.id.radioGroupMinta);
        radioGroupMinta.check(R.id.verRadioButton);
        editTextFertozes = findViewById(R.id.editTextFertozes);
        editTextPaciensName = findViewById(R.id.editTextPaciensName);
        editTextConclusion = findViewById(R.id.editTextConclusion);
        mentesButton = findViewById(R.id.mentesButton);
        kezdooldalButton = findViewById(R.id.kezdooldalButton);

        preferences = getSharedPreferences(PREF_KEY, MODE_PRIVATE);
        preferences = getSharedPreferences(PREF_KEY, MODE_PRIVATE);

        mFirestore = FirebaseFirestore.getInstance();


        Log.i(LOG_TAG, "onCreate");
    }


    public void jelentesMentes(View view) {
        String identifier = editTextIdentifier.getText().toString();
        String fertozes = editTextFertozes.getText().toString();
        String paciensNeve = editTextPaciensName.getText().toString();
        String conclusion = editTextConclusion.getText().toString();


        int checkedId = radioGroupMinta.getCheckedRadioButtonId();
        RadioButton radioButton = radioGroupMinta.findViewById(checkedId);
        String mintaType = radioButton.getText().toString();


        Map<String, String> jelentesMap = new HashMap<>();
        jelentesMap.put("identifier", identifier);
        jelentesMap.put("category", mintaType);
        jelentesMap.put("code", fertozes);
        jelentesMap.put("paciens", paciensNeve);
        jelentesMap.put("conclusion", conclusion);

        mFirestore.collection("jelentes").add(jelentesMap).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                Toast.makeText(NewJelentesActivity.this, "Sikeres felvétel", Toast.LENGTH_SHORT).show();
                Log.d(LOG_TAG, "Hozzáadva");

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                String error = e.getMessage();
                Toast.makeText(NewJelentesActivity.this, "Sikertelen. Hiba: " + error, Toast.LENGTH_SHORT).show();
            }
        });
        Toast.makeText(NewJelentesActivity.this,"Hozzáadás sikeres",Toast.LENGTH_LONG).show();
        Log.i(LOG_TAG,"Id: "+ identifier+ ", páciens: "+paciensNeve);

    }


    public void veg(View view) {
        finish();
    }

    @Override
    protected void onPause() {
        super.onPause();
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("identifier", editTextIdentifier.getText().toString());
        editor.putString("paciensNeve", editTextPaciensName.getText().toString());
        editor.apply();

        Log.i(LOG_TAG, "onPause");
    }



}