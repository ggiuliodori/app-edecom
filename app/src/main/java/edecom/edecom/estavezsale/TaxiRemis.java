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


public class TaxiRemis extends Activity {
    private ItemArrayAdapterVehiculo itemArrayAdapterVehiculo;
    Button buscar;
    EditText et1;
    MediaPlayer mediaPlayer;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.taxiremis_layout);
        itemArrayAdapterVehiculo = new ItemArrayAdapterVehiculo(getApplicationContext(), R.layout.itemvehiculo_layout);

        mediaPlayer = MediaPlayer.create(this,R.raw.click);

        File ruta_sd = Environment.getExternalStorageDirectory();

        File file = new File(ruta_sd.getAbsolutePath(), "Download/InspServPublicos.csv");
        CsvToList csvFile = new CsvToList(file);

        final List<String[]> scoreList = csvFile.read();
        for(String[] scoreData:scoreList ) {
            itemArrayAdapterVehiculo.add(scoreData);
        }
        et1 = findViewById(R.id.buscador);
        buscar = findViewById(R.id.buscar);
        buscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mediaPlayer.start();
                if (patenteValida(et1.getText().toString())) {
                    String dato = et1.getText().toString();
                    String condicion = itemArrayAdapterVehiculo.enReglaVehiculo(dato);
                    String[] arreglo = itemArrayAdapterVehiculo.arrayParaMostrar(dato);
                    Intent i = new Intent(TaxiRemis.this, CondicionVehiculo.class);
                    i.putExtra("estado", condicion);
                    i.putExtra("arreglo", arreglo);
                    startActivity(i);
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    finish();

                }
                else {
                    Toast.makeText(TaxiRemis.this, "Datos Incorrectos", Toast.LENGTH_LONG).show();
                    et1.setText("");
                }
            }
        });

    }

    private boolean patenteValida(String patente) {
        Pattern p = Pattern.compile("([a-zA-Z]{2}[0-9]{3}[a-zA-Z]{2})|([a-zA-Z]{3}[0-9]{3})|[0-9]{3}|[0-9]{4}");
        Matcher m = p.matcher(patente);

        return m.matches();
    }
}