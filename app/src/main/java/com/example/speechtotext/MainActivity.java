package com.example.speechtotext;

import android.Manifest;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Environment;
import android.speech.RecognizerIntent;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.speechtotext.R;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    TextView voiceInput,sp;
    Calendar calendar;
    SimpleDateFormat simpleDateFormat;
    String timesysa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView(R.layout.activity_main );
        voiceInput=findViewById( R.id.inputvoice);
        sp=findViewById( R.id.btnspeak );
        sp.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText( MainActivity.this, "Clicked", Toast.LENGTH_SHORT ).show();
                boolhalkehalke();
            }
        } );
    }

    private void boolhalkehalke() {
        Intent intent= new Intent( RecognizerIntent.ACTION_RECOGNIZE_SPEECH );
        intent.putExtra( RecognizerIntent.EXTRA_LANGUAGE_MODEL,RecognizerIntent.LANGUAGE_MODEL_FREE_FORM );
        intent.putExtra( RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra( RecognizerIntent.EXTRA_PROMPT,"Hi Speck something");
        try {
            startActivityForResult( intent, 20 );
        }catch (ActivityNotFoundException a)
        {

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult( requestCode, resultCode, data );
        switch (requestCode) {
            case 20:
                if (resultCode == RESULT_OK && null != data) {
                    ArrayList <String> result=data.getStringArrayListExtra( RecognizerIntent.EXTRA_RESULTS );
                    voiceInput.setText(result.get(0));
                    writedatainfile(voiceInput.getText().toString());
                }
                break;
        }
    }

    private void writedatainfile(String toString) {
        calendar = Calendar.getInstance();
        simpleDateFormat= new SimpleDateFormat( "dd-MM-yyyy HH:mm:ss" );
        timesysa= simpleDateFormat.format(calendar.getTime());
        timesysa="ExternalData"+timesysa+" .txt";
        ActivityCompat.requestPermissions( this,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},2);
        File folder= Environment.getExternalStoragePublicDirectory( Environment.DIRECTORY_DOWNLOADS );
        File myFile =new File( folder,timesysa );
        writeData(myFile,toString  );
 
    }

    private void writeData(File myFile, String result) {
        FileOutputStream fileOutputStream= null;
        try {
            fileOutputStream=new FileOutputStream( myFile );
            try {
                fileOutputStream.write( result.getBytes() );
                Toast.makeText( this,"Done" +myFile.getAbsolutePath(),Toast.LENGTH_SHORT).show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }
}


/*
<uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE"/>
<uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE"/>

*/





