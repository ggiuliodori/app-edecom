package edecom.edecom.estavezsale;

import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;


public class CondicionVehiculo extends AppCompatActivity {
    MediaPlayer mediaPlayer;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String estado = recibirEstado();
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);

        mediaPlayer = MediaPlayer.create(this,R.raw.click);

        if (estado.equals("No existe")) {
            setContentView(R.layout.inexistentevehiculo_layout);
            Button nvaBusqueda;
            nvaBusqueda = findViewById(R.id.buscar);
            nvaBusqueda.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mediaPlayer.start();
                    Intent i = new Intent(CondicionVehiculo.this, TaxiRemis.class);
                    startActivity(i);
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    finish();
                }
            });
        } else {
            String[] arregloDatos = recibirDatos();
            TextView tv1, tv2, tv3, tv4, tv5, tv6, tv7;
            setContentView(R.layout.vehiculo_layout);
            RelativeLayout layout =(RelativeLayout)findViewById(R.id.estadovehiculo);
            if (estado.equals("En regla")){
                layout.setBackgroundResource(R.mipmap.enregla);
            }
            else{
                layout.setBackgroundResource(R.mipmap.infractor);
            }
            tv1 = (TextView) findViewById(R.id.dato1);
            tv2 = (TextView) findViewById(R.id.dato2);
            tv3 = (TextView) findViewById(R.id.dato3);
            tv4 = (TextView) findViewById(R.id.dato4);
            tv5 = (TextView) findViewById(R.id.dato5);
            tv6 = (TextView) findViewById(R.id.dato6);
            tv7 = (TextView) findViewById(R.id.dato7);
            tv1.setText(arregloDatos[0]);
            tv2.setText(arregloDatos[1]);
            tv3.setText(arregloDatos[2]);
            tv4.setText(arregloDatos[3]);
            tv5.setText(arregloDatos[4]);
            tv6.setText(arregloDatos[5]);
            tv7.setText(arregloDatos[6]);
            if (arregloDatos[4].equals("EN REGLA")){
                tv5.setTextColor(Color.rgb(0,200,0));
            }
            else{
                tv5.setTextColor(Color.rgb(255,0,0));
            }

            if (arregloDatos[5].equals("EN REGLA")){
                tv6.setTextColor(Color.rgb(0,200,0));
            }
            else{
                tv6.setTextColor(Color.rgb(255,0,0));
            }
            if (arregloDatos[6].equals("EN REGLA")){
                tv7.setTextColor(Color.rgb(0,200,0));
            }
            else{
                tv7.setTextColor(Color.rgb(255,0,0));
            }
            Button nvaBusqueda;
            nvaBusqueda = findViewById(R.id.buscar);
            nvaBusqueda.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mediaPlayer.start();
                    Intent i = new Intent(CondicionVehiculo.this, TaxiRemis.class);
                    startActivity(i);
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    finish();
                }
            });
        }

    }

    public String recibirEstado() {
        Bundle extra = getIntent().getExtras();
        String estado = extra.getString("estado");
        return estado;
    }

    public String[] recibirDatos() {
        Bundle extra = getIntent().getExtras();
        String[] arreglo = extra.getStringArray("arreglo");
        return arreglo;
    }
}
