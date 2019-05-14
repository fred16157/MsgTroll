package com.example.troll;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        PropertyReader pr = new PropertyReader(this);
        EditText textField = (EditText)findViewById(R.id.textField);
        textField.setText(pr.getLastText());
        EditText phoneNumbersField = (EditText)findViewById(R.id.phoneNumbersField);
        phoneNumbersField.setText(pr.getLastNumbers());
    }

    public void btnSendClicked(View v)
    {
        int permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS);

        if(permissionCheck== PackageManager.PERMISSION_DENIED){
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.SEND_SMS},
                    1);
        }

        EditText textField = (EditText)findViewById(R.id.textField);
        EditText phoneNumbersField = (EditText)findViewById(R.id.phoneNumbersField);
        Switch locationSwitch = (Switch)findViewById(R.id.sendPositionSwitch);
        String textToSend = textField.getText().toString();
        if(locationSwitch.isChecked())
        {
            permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);

            if(permissionCheck== PackageManager.PERMISSION_DENIED){
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        1);
            }
            LocationManager lm = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
            Location location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);

            textToSend += "\n위도: " + location.getLongitude() + "\n" +
                    "경도: " + location.getLatitude() + "\n" +
                    "고도: " + location.getAltitude() + "\n" +
                    "정확도: " + location.getAccuracy();
        }
        for(String phoneNumber : phoneNumbersField.getText().toString().split("\n"))
        {
            SmsManager sm = SmsManager.getDefault();
            sm.sendTextMessage(phoneNumber, null, textToSend, null, null);
        }
        PropertyReader pr = new PropertyReader(this);
        pr.writeProps(textToSend, phoneNumbersField.getText().toString());
    }
}
