package edecom.edecom.estavezsale;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Chofer extends Activity {
    private ItemArrayAdapterChofer itemArrayAdapterChofer;
    Button buscar;
    EditText et1;
    MediaPlayer mediaPlayer;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chofer_layout);
        itemArrayAdapterChofer = new ItemArrayAdapterChofer(getApplicationContext(), R.layout.itemchofer_layout);

        mediaPlayer = MediaPlayer.create(this,R.raw.click);


        File ruta_sd = Environment.getExternalStorageDirectory();

        File file = new File(ruta_sd.getAbsolutePath(), "Download/InspServPublicosChof.csv");
        CsvToList csvFile = new CsvToList(file);


        final List<String[]> scoreList = csvFile.read();
        for(String[] scoreData:scoreList ) {
            itemArrayAdapterChofer.add(scoreData);
        }
        et1 = (EditText)findViewById(R.id.buscadorChof);
        buscar = findViewById(R.id.buscarChof);
        buscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mediaPlayer.start();
                if (dniValida(et1.getText().toString())) {
                    String dato = et1.getText().toString();
                    String condicion = itemArrayAdapterChofer.enReglaChofer(dato);
                    String[] arreglo = itemArrayAdapterChofer.arrayParaMostrar(dato);
                    Intent i = new Intent(Chofer.this, CondicionChofer.class);
                    i.putExtra("estado", condicion);
                    i.putExtra("arreglo", arreglo);
                    startActivity(i);
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    finish();
                }
                else {
                    Toast.makeText(Chofer.this, "D.N.I. Incorrecto", Toast.LENGTH_LONG).show();
                    et1.setText("");
                }
            }
        });
    }

    private boolean dniValida(String patente) {
        Pattern p = Pattern.compile("([0-9]{6})|([0-9]{7})|([0-9]{8})");
        Matcher m = p.matcher(patente);

        return m.matches();
    }
}