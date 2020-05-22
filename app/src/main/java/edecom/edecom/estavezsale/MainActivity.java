package edecom.edecom.estavezsale;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity {
    MediaPlayer mediaPlayer;
    Button butryt, butchof,btnalcoholemia,btnconductor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mediaPlayer = MediaPlayer.create(this,R.raw.click);

        butryt = findViewById(R.id.btnryt);
        butryt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mediaPlayer.start();
                Intent i = new Intent(MainActivity.this, TaxiRemis.class);
                startActivity(i);
            }
        });

        butchof = findViewById(R.id.btnchofer);
        butchof.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mediaPlayer.start();
                Intent i = new Intent(MainActivity.this, Chofer.class);
                startActivity(i);
            }
        });

        btnalcoholemia = findViewById(R.id.btnalcoholemia);
        btnalcoholemia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mediaPlayer.start();
                Intent i = new Intent(MainActivity.this, ScanAlcoholemia.class);
                startActivity(i);
            }
        });

        btnconductor = findViewById(R.id.btnconductor);
        btnconductor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mediaPlayer.start();
                Intent i = new Intent(MainActivity.this, Scan.class);
                startActivity(i);
            }
        });

    }
}

