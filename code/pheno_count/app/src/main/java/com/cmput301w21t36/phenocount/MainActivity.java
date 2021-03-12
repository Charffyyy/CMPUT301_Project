package com.cmput301w21t36.phenocount;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;

import java.util.ArrayList;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class MainActivity extends AppCompatActivity {

    ImageButton profileButton;
    FirebaseFirestore db;

    ListView experiments;
    ArrayList<Experiment> expDataList;
    ArrayAdapter<Experiment> expAdapter;

    String UUID;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        experiments = findViewById(R.id.expList);
        //Intent i = new Intent(this, LocationActivity.class);
        //a i.putExtra("info", info);
        //startActivityForResult(i, 1);
        expDataList = new ArrayList<Experiment>();

        // Will get instance of the database
        db = FirebaseFirestore.getInstance();

        Experiment exp = new Experiment("Coin Flip", "We flip a coin in this experiment","North America","Binomial", 10, false);
        expDataList.add(exp);

        SharedPreferences sharedPrefs = getSharedPreferences("sharedPrefs", MODE_PRIVATE);
        boolean firstStart = sharedPrefs.getBoolean("firstStart",true );


        if (firstStart) {
            final DocumentReference userReference = db.collection("User").document();
            // Will assign UUID as the Auto-ID created for the document
            UUID = userReference.getId();
        }

        profileButton = findViewById(R.id.profileButton);

        expAdapter = new ExperimentAdapter(this,expDataList);
        experiments.setAdapter(expAdapter);

        profileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openProfile();
            }
        });


        experiments.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent = new Intent (MainActivity.this,DisplayExperimentActivity.class);
                Experiment exp_obj = expDataList.get(position);
                intent.putExtra("experiment",exp_obj);
                intent.putExtra("position",position);

                int LAUNCH_SECOND_ACTIVITY = 1;
                startActivityForResult(intent,LAUNCH_SECOND_ACTIVITY);
            }
        });
    }

    public void displayExperiment(View view){
        Intent intent = new Intent(this, DisplayExperimentActivity.class);
        startActivity(intent);
    }

    public void addExperiment(View view){
        Intent intent = new Intent(this, PublishExperimentActivity.class);
        startActivity(intent);

    }

    public void openProfile(){
        Intent intent = new Intent(this, ProfileActivity.class);
        startActivity(intent);


    }

}