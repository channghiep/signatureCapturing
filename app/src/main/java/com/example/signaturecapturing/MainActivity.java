package com.example.signaturecapturing;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.github.gcacace.signaturepad.views.SignaturePad;

import java.io.ByteArrayOutputStream;

public class MainActivity extends AppCompatActivity {

    private SignaturePad mSignaturePad;
    private Button mClearButton;
    private Button mSaveButton;
    Bitmap SignatureBitmap;
    String encodedBitmap;
    Bitmap decodedBitmap;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        mClearButton = (Button) findViewById(R.id.clear_button);
        mSaveButton = (Button) findViewById(R.id.save_button);

        mSignaturePad = (SignaturePad) findViewById(R.id.signature_pad);
        mSignaturePad.setOnSignedListener(new SignaturePad.OnSignedListener() {

            @Override
            public void onStartSigning() {
                //Event triggered when the pad is touched


            }

            @Override
            public void onSigned() {
                //Event triggered when the pad is signed
                mSaveButton.setEnabled(true);
                mClearButton.setEnabled(true);
            }

            @Override
            public void onClear() {
                //Event triggered when the pad is cleared
                mSaveButton.setEnabled(false);
                mClearButton.setEnabled(false);
            }
        });

        mSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SignatureBitmap = mSignaturePad.getSignatureBitmap();
                bitmapToBase64();
                base64ToImage();
            }
        });
        mClearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSignaturePad.clear();
            }
        });

    }
    //convert bitmap to base64==============
    public void bitmapToBase64() {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        SignatureBitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream .toByteArray();
        encodedBitmap = Base64.encodeToString(byteArray, Base64.DEFAULT);
        Log.d("base64", Base64.encodeToString(byteArray, Base64.DEFAULT));
    }

    public void base64ToImage() {
        byte[] imageAsBytes = Base64.decode(encodedBitmap.getBytes(), Base64.DEFAULT);
        decodedBitmap = BitmapFactory.decodeByteArray(imageAsBytes, 0, imageAsBytes.length);

        ImageView pastedImg = (ImageView) findViewById(R.id.imageView);
        pastedImg.setImageBitmap(decodedBitmap);


    }

//========================


}
