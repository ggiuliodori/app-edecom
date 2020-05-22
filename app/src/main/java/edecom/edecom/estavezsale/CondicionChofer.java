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


public class CondicionChofer extends AppCompatActivity {
    MediaPlayer mediaPlayer;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String estado = recibirEstado();
        //ESTA LINEA SIRVE PARA SACAR EL TITULO
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        mediaPlayer = MediaPlayer.create(this,R.raw.click);



        if (estado.equals("No existe")) {
            setContentView(R.layout.inexistentechofer_layout);
            Button volver = findViewById(R.id.buscar);
            volver.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mediaPlayer.start();
                    Intent i = new Intent(CondicionChofer.this, Chofer.class);
                    startActivity(i);
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    finish();
                }
            });
        } else {
            String[] arregloDatos = recibirDatos();
            TextView tv1, tv2, tv3, tv4, tv5;
            setContentView(R.layout.estadochofer_layout);
            RelativeLayout layout =(RelativeLayout)findViewById(R.id.estadochofer);
            if (estado.equals("En regla")){
                layout.setBackgroundResource(R.mipmap.docenregla);
            }
            else{
                layout.setBackgroundResource(R.mipmap.docnoregla);
            }
            tv1 = (TextView) findViewById(R.id.dato1);
            tv2 = (TextView) findViewById(R.id.dato2);
            tv3 = (TextView) findViewById(R.id.dato3);
            tv4 = (TextView) findViewById(R.id.dato4);
            tv5 = (TextView) findViewById(R.id.dato5);

            tv1.setText(arregloDatos[0]);
            tv2.setText(arregloDatos[1]);
            tv3.setText(arregloDatos[2]);
            tv4.setText(arregloDatos[3]);
            tv5.setText(arregloDatos[4]);

            if (arregloDatos[2].equals("EN REGLA")){
                tv3.setTextColor(Color.rgb(0,200,0));
            }
            else{
                tv3.setTextColor(Color.rgb(255,0,0));
            }

            if (arregloDatos[3].equals("EN REGLA")){
                tv4.setTextColor(Color.rgb(0,200,0));
            }
            else{
                tv4.setTextColor(Color.rgb(255,0,0));
            }
            if (arregloDatos[4].equals("EN REGLA")){
                tv5.setTextColor(Color.rgb(0,200,0));
            }
            else{
                tv5.setTextColor(Color.rgb(255,0,0));
            }
            Button volver;
            volver = findViewById(R.id.buscar);
            volver.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mediaPlayer.start();
                    Intent i = new Intent(CondicionChofer.this, Chofer.class);
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
