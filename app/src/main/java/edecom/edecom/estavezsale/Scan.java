package edecom.edecom.estavezsale;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by Ailu on 12/3/2018.
 */

public class Scan extends Activity {

    MediaPlayer mediaPlayer;
    private static final int ACTIVITY_RESULT_QR_DRDROID = 0;

    //Datos personales
    private TextView tvNombre;
    private TextView tvDireccion;
    private TextView tvDNI;
    private TextView tvFNac;
    private TextView tvNac;
    private TextView tvSexo;

    //Licencia
    private TextView tvFOtorg;
    private TextView tvFVto;
    private TextView tvClaseLic;
    private TextView tvCodSeg;

    //Nuevo escaneo
    private Button btnNuevoEscaneo;

    //Layout principal
    private LinearLayout layoutPrincipal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data);

        escanear();
        cargarReferencias();
    }

    @Override
    /**
     * Reads data scanned by user and returned by QR Droid
     */
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if( ACTIVITY_RESULT_QR_DRDROID==requestCode && null!=data && data.getExtras()!=null ) {
            //Read result from QR Droid (it's stored in la.droid.qr.result)
            String result = data.getExtras().getString(Services.RESULT);

        //    layoutPrincipal.setVisibility(View.VISIBLE);

            obtenerDatosQRLicPciaCba(result);
        }else{
            this.finish();
        }
    }

    private void cargarReferencias () {
        mediaPlayer = MediaPlayer.create(this,R.raw.click);

        tvNombre = (TextView) findViewById(R.id.tv_nombre2);
        tvDireccion = (TextView) findViewById(R.id.tv_direccion2);
        tvDNI = (TextView) findViewById(R.id.tv_dni2);
        tvFNac = (TextView) findViewById(R.id.tv_f_nac2);
        tvNac = (TextView) findViewById(R.id.tv_nac2);
        tvSexo = (TextView) findViewById(R.id.tv_sexo2);
        tvFOtorg = (TextView) findViewById(R.id.tv_fecha_desde2);
        tvFVto = (TextView) findViewById(R.id.tv_fecha_hasta2);
        tvClaseLic = (TextView) findViewById(R.id.tv_clase_lic2);
        tvCodSeg = (TextView) findViewById(R.id.tv_cod_seg2);

        layoutPrincipal = (LinearLayout) findViewById(R.id.layout_principal2);
    //    layoutPrincipal.setVisibility(View.VISIBLE);

        //Agrego botón para nuevo escaneo
        btnNuevoEscaneo = (Button) findViewById(R.id.nuevo_escaneo1);
        btnNuevoEscaneo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mediaPlayer.start();
                escanear();
     //           layoutPrincipal.setVisibility(View.INVISIBLE);
            }
        });
    }

    private void escanear() {
        //Create a new Intent to send to QR Droid
        Intent qrDroid = new Intent( Services.SCAN ); //Set action "la.droid.qr.scan"

        //Send intent and wait result
        try {
            startActivityForResult(qrDroid, ACTIVITY_RESULT_QR_DRDROID);
        } catch (ActivityNotFoundException activity) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Para utilizar esta aplicación debe tener instalado previamente QR Droid")
                    .setTitle("Aplicación requerida")
                    .setCancelable(false)
                    .setNeutralButton("Aceptar",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                    System.exit(0);
                                }
                            });
            AlertDialog alert = builder.create();
            alert.show();
        }
    }

    private void obtenerDatosQRLicPciaCba(String QRString) {

        tvCodSeg.setText(QRString.substring(1,10));
        tvFOtorg.setText(obtenerFecha(QRString.substring(10,18)));
        tvFVto.setText(obtenerFecha(QRString.substring(18,26)));
        tvClaseLic.setText(QRString.substring(26,29));
        tvNac.setText(QRString.substring(29,33));
        tvDNI.setText(QRString.substring(56,64));
        tvSexo.setText(QRString.substring(72,73));
        tvFNac.setText(obtenerFecha(QRString.substring(73,81)));
        tvNombre.setText(obtenerDatoLimpio(QRString,81,136));
        tvDireccion.setText(obtenerDatoLimpio(QRString,136,175));
    }

    private String obtenerDatoLimpio(String cadena, int inicioDato, int finDato) {
        String datoLimpio = "";
        int posCorriente = inicioDato;
        int posFinNombre = finDato;
        char cc = ' ';

        while (posCorriente<posFinNombre) {
            while ((cc = cadena.charAt(posCorriente)) != ' ' &&
                    (cc = cadena.charAt(posCorriente)) != '-') {
                datoLimpio += cc;
                posCorriente++;
            }
            while (cadena.charAt(posCorriente) == ' ' ||
                    cadena.charAt(posCorriente) == '-') {
                posCorriente++;
            }
            if (posCorriente<posFinNombre) {
                //datoLimpio += "\n";
                datoLimpio += " ";
            }

        }

        return datoLimpio;
    }

    // Toma una cadena en formato AAAAMMDD representando una fecha y la devuelve en formato DD/MM/AAAA
    private String obtenerFecha(String fecha) {

        String ano = fecha.substring(0,4);
        String mes = fecha.substring(4,6);
        String dia = fecha.substring(6,8);

        return dia+"/"+mes+"/"+ano;
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        //Nothing
    }

}
