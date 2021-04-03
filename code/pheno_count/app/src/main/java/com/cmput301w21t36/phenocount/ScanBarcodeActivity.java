package com.cmput301w21t36.phenocount;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.Toast;

import com.budiyev.android.codescanner.CodeScanner;
import com.budiyev.android.codescanner.CodeScannerView;
import com.budiyev.android.codescanner.DecodeCallback;
import com.google.zxing.Result;

/** REFERENCES
Ralf Kistner, 27-09-19,  Apache-2.0 License, https://github.com/journeyapps/zxing-android-embedded */

public class ScanBarcodeActivity extends AppCompatActivity {
    private CodeScanner mCodeScanner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qr_scan);

        // ensure that the app has permissions to use the camera
        if (ContextCompat.checkSelfPermission(ScanBarcodeActivity.this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_DENIED){
            ActivityCompat.requestPermissions(ScanBarcodeActivity.this, new String[] {Manifest.permission.CAMERA}, 123);
        } else {
            //startScanning();
        }
    }

/*    private void startScanning() {
        CodeScannerView scannerView = findViewById(R.id.scanner_view);
        mCodeScanner = new CodeScanner(ScanBarcodeActivity.this, scannerView);
        mCodeScanner.setDecodeCallback(new DecodeCallback() {
            @Override
            public void onDecoded(@NonNull final Result result) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        // pass the scanned text back to the experiment
                        Intent i = new Intent();
                        i.putExtra("scannedText", result.getText());
                        setResult(RESULT_OK, i);
                        finish();                    }
                });
            }
        });
    }*/

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 123) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Camera permission granted", Toast.LENGTH_LONG).show();
                //startScanning();
            } else {
                Toast.makeText(this, "Camera permission denied", Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(mCodeScanner != null) {
            mCodeScanner.startPreview();
        }
    }

    @Override
    protected void onPause() {
        if(mCodeScanner != null) {
            mCodeScanner.releaseResources();
        }
        super.onPause();
    }
//    /**
//     * Get the data from the scan
//     * @param requestCode
//     * @param resultCode
//     * @param data
//     */
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
//        if(result != null) { // check that something got returned
//            if(result.getContents() == null) { // check that data was scanned successfully
//                Toast.makeText(this, "Cancelled", Toast.LENGTH_LONG).show();
//                finish(); // go back to previous activity if nothing is scanned
//            } else {
//                // pass the scanned text back to the experiment
//                Intent i = new Intent();
//                i.putExtra("scannedText", result.getContents());
//                setResult(RESULT_OK, i);
//                finish();
//            }
//        } else {
//            super.onActivityResult(requestCode, resultCode, data);
//        }
//    }
}