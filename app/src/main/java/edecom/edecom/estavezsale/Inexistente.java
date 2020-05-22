package edecom.edecom.estavezsale;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 * Created by Tecnica-Edecom on 08/03/2018.
 */

public class Inexistente extends Activity {

    Button volver;
    MediaPlayer mediaPlayer;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        mediaPlayer = MediaPlayer.create(this,R.raw.click);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.inexistentevehiculo_layout);
        volver = findViewById(R.id.buscar);
        volver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mediaPlayer.start();
                Intent i = new Intent(Inexistente.this, TaxiRemis.class);
                startActivity(i);
            }
        });
    }
}
