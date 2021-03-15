package com.cmput301w21t36.phenocount;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class Measurement extends AppCompatActivity {
    Trial trial;
    Experiment newexp;//defining the Experiment object
    Boolean location=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        // receiving intent object
        super.onCreate(savedInstanceState);
        setContentView(R.layout.trial_measurement);

        newexp = (Experiment) getIntent().getSerializableExtra("experiment");//defining the Experiment object
        trial = new Trial(newexp.getName(),newexp.getDescription(),newexp.getOwner(),newexp.getExpType());

        // Capture the layout's TextView and set the string as its text

        TextView desc = findViewById(R.id.desc3);
        desc.setText("Description:" + String.valueOf(newexp.getOwner()));

        TextView owner = findViewById(R.id.owner3);
        owner.setText("Owner:" + String.valueOf(newexp.getOwner()));

        TextView status = findViewById(R.id.status3);
        status.setText("Status:" + String.valueOf(newexp.getExpStatus()));

        TextView exptype= findViewById(R.id.exptype3);
        exptype.setText("Experiment Type: Measurement");

        EditText measurement = findViewById(R.id.measurement_editText);


        final Button recordvbtn = findViewById((R.id.recordvbtn));
        recordvbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(location || !newexp.isRequireLocation()) {

                    String temp = measurement.getText().toString();
                    float value = 0;
                    if (!"".equals(temp)) {
                        value = Float.parseFloat(temp);    //https://javawithumer.com/2019/07/get-value-edittext.html
                    }
                    trial.setMeasurement(value);
                    newexp.getTrials().add(trial);

                    Toast.makeText(
                            Measurement.this,
                            "Measurement Recorded",
                            Toast.LENGTH_SHORT).show();

                    Intent returnIntent = new Intent();
                    returnIntent.putExtra("experiment", newexp);
                    setResult(Activity.RESULT_OK, returnIntent);

                    finish();
                }else{  Toast.makeText(
                        Measurement.this,
                        "Please add a location first",
                        Toast.LENGTH_LONG).show();
                }

            }
        });

        final Button lbtn = findViewById(R.id.locationbtn3);
        lbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent (Measurement.this,MapsActivity.class);
                intent.putExtra("trial_obj",trial);

                int LAUNCH_SECOND_ACTIVITY = 1;
                startActivityForResult(intent,LAUNCH_SECOND_ACTIVITY); }
        });

        final Button cameraButton = findViewById(R.id.cameraButton);
        cameraButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Measurement.this, ScanBarcodeActivity.class);
                startActivityForResult(i, 1);
            }
        });
    }

    @Override
    //Sends the experiment object and retrieves the updated object
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        int LAUNCH_SECOND_ACTIVITY = 1;
        if (requestCode == LAUNCH_SECOND_ACTIVITY) {
            if(resultCode == Activity.RESULT_OK){
                location = true;
                Trial trial = (Trial) data.getSerializableExtra("trial_obj");

                if (trial != null) {
                    newexp.getTrials().add(trial);
                } else {
                    String scannedText = data.getSerializableExtra("scannedText").toString();
                    EditText input = findViewById(R.id.measurement_editText);
                    input.setText(scannedText, TextView.BufferType.EDITABLE);
                }

            }
            if (resultCode == Activity.RESULT_CANCELED) {
                System.out.println("No Data");
            }
        }
    }
}
