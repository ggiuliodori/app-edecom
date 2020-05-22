package edecom.edecom.estavezsale;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Ailu on 12/3/2018.
 */

public class ScanAlcoholemia extends Activity{

    MediaPlayer mediaPlayer;

    private static final int ACTIVITY_RESULT_QR_DRDROID = 0;
    SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
    SimpleDateFormat formatParaArchivo = new SimpleDateFormat("dd/MM/yyyy");

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

    //Dominio
    private EditText etDominio;

    //Nuevo escaneo
    private Button btnNuevoEscaneo;

    //Guardar datos
    private Button btnGuardar;

    //Layout principal
    private LinearLayout layoutPrincipal;

    //SD card
    private boolean sdDisponible;
    private boolean sdAccesoEscritura;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_alcoholemia);

        cargarReferencias();
        lecturaEscrituraSD();
        escanear();
    }

    // Comprueba si existe SD y si puedo escribir o no
    private void lecturaEscrituraSD() {

        String estado = Environment.getExternalStorageState();

        if (estado.equals(Environment.MEDIA_MOUNTED)) {
            sdDisponible = true;
            sdAccesoEscritura = true;
        } else if (estado.equals(Environment.MEDIA_MOUNTED_READ_ONLY)) {
            sdDisponible = true;
            sdAccesoEscritura = false;
        } else {
            sdDisponible = false;
            sdAccesoEscritura = false;
        }
    }

    @Override
    /**
     * Reads data scanned by user and returned by QR Droid
     */
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (ACTIVITY_RESULT_QR_DRDROID == requestCode && null != data && data.getExtras() != null) {
            //Read result from QR Droid (it's stored in la.droid.qr.result)
            String result = data.getExtras().getString(Services.RESULT);

     //       layoutPrincipal.setVisibility(View.VISIBLE);
            obtenerDatosQRLicPciaCba(result);
        }else{
            this.finish();
        }
    }

    private boolean patenteValida(String patente) {
        Pattern p = Pattern.compile("([a-zA-Z]{2}[0-9]{3}[a-zA-Z]{2})|([a-zA-Z]{3}[0-9]{3})");
        Matcher m = p.matcher(patente);

        return m.matches();
    }

    // Carga referencias y setea OnClicks.
    private void cargarReferencias() {
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
        etDominio = (EditText) findViewById(R.id.et_dominio);


        layoutPrincipal = (LinearLayout) findViewById(R.id.layout_principal1);
      //  layoutPrincipal.setVisibility(View.VISIBLE);

        //Creo 2 botones: Nuevo escaneo y Cargar
        btnNuevoEscaneo = (Button) findViewById(R.id.nuevo_escaneo1);
        btnNuevoEscaneo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mediaPlayer.start();
                escanear();
                limpiarCampos();
        //        layoutPrincipal.setVisibility(View.INVISIBLE);
            }
        });

        btnGuardar = (Button) findViewById(R.id.btn_guardar);
        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mediaPlayer.start();
                guardarDatos();
            }
        });

    }

    //Transformaa una fecha en formato String a Date.
    public Date parseFecha(String fecha) {
        SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
        Date fechaDate = null;
        try {
            fechaDate = formato.parse(fecha);
        } catch (ParseException ex) {
            System.out.println(ex);
            Toast.makeText(this, "Error parseando fecha", Toast.LENGTH_SHORT).show();
        }
        return fechaDate;
    }

    /*Toma los datos obtenidos tras leer el código QR y los inserta en un archivo .CSV.
      Si el archivo no existe, entonces lo crea con nombre igual a la fecha del día corriente.
     */
    private void guardarDatos() {
        if (patenteValida(etDominio.getText().toString())) {
            File ruta_sd = Environment.getExternalStorageDirectory();

            File f = new File(ruta_sd.getAbsolutePath(), "Registro Alcoholemia/" + format.format(new Date()) + ".csv");

            if (!f.exists()) {
                if (sdAccesoEscritura && sdDisponible) {
                    try {
                        OutputStreamWriter fout =
                                new OutputStreamWriter(
                                        new FileOutputStream(f, true));

                        fout.append("Nombre; Direccion; DNI; F. Nac.; Nacionalidad; Sexo; F. Otorg.; " +
                                "F. Vto.; Clase lic.; Cod. seguridad; Dominio\n");


                        fout.close();
                        Toast.makeText(this, "Datos guardados correctamente", Toast.LENGTH_LONG).show();
                    } catch (Exception ex) {
                        ex.printStackTrace();
                        Log.e("Ficheros", "Error al escribir fichero en la tarjeta SD");
                        Toast.makeText(this, "Error al escribir fichero en la tarjeta SD", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            try {

                Date fNac = null;
                Date fOtorg = null;
                Date fVto = null;


                fNac = parseFecha(tvFNac.getText().toString());

                fOtorg = parseFecha(tvFOtorg.getText().toString());

                fVto = parseFecha(tvFVto.getText().toString());


                String licencia = tvNombre.getText().toString() + ";" +
                        tvDireccion.getText().toString() + ";" +
                        tvDNI.getText().toString() + ";" +
                        formatParaArchivo.format(fNac) + ";" +
                        tvNac.getText().toString() + ";" +
                        tvSexo.getText().toString() + ";" +
                        formatParaArchivo.format(fOtorg) + ";" +
                        formatParaArchivo.format(fVto) + ";" +
                        tvClaseLic.getText().toString() + ";" +
                        tvCodSeg.getText().toString() + ";" +
                        etDominio.getText().toString().toUpperCase();

                OutputStreamWriter fout =
                        new OutputStreamWriter(
                                new FileOutputStream(f, true));

                fout.append(licencia + "\n");

                fout.close();
                Toast.makeText(this, "Datos guardados correctamente", Toast.LENGTH_LONG).show();

            } catch (Exception ex) {
                ex.printStackTrace();
                Log.e("Ficheros", "Error al escribir fichero en la tarjeta SD");
                Toast.makeText(this, "Error al escribir fichero en la tarjeta SD", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "Formato de patente inválido.", Toast.LENGTH_SHORT).show();
        }
    }

    // Comienza un nuevo escaneo
    private void escanear() {

        //Create a new Intent to send to QR Droid
        Intent qrDroid = new Intent(Services.SCAN); //Set action "la.droid.qr.scan"

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

    //Borra el contenido de todos los campos.
    private void limpiarCampos() {
        tvCodSeg.setText("");
        tvFOtorg.setText("");
        tvFVto.setText("");
        tvClaseLic.setText("");
        tvNac.setText("");
        tvDNI.setText("");
        tvSexo.setText("");
        tvFNac.setText("");
        tvNombre.setText("");
        tvDireccion.setText("");
        etDominio.setText("");
    }

    //Obtiene los datos desde el String devuelto por el escaneo del código QR.
    private void obtenerDatosQRLicPciaCba(String QRString) {

        tvCodSeg.setText(QRString.substring(1, 10));
        tvFOtorg.setText(obtenerFecha(QRString.substring(10, 18)));
        tvFVto.setText(obtenerFecha(QRString.substring(18, 26)));
        tvClaseLic.setText(QRString.substring(26, 29));
        tvNac.setText(QRString.substring(29, 33));
        tvDNI.setText(QRString.substring(56, 64));
        tvSexo.setText(QRString.substring(72, 73));
        tvFNac.setText(obtenerFecha(QRString.substring(73, 81)));
        tvNombre.setText(obtenerDatoLimpio(QRString, 81, 136));
        tvDireccion.setText(obtenerDatoLimpio(QRString, 136, 175));
    }

    //Elimina espacios en blanco inecesarios en un String. Deja solo los necesarios
    private String obtenerDatoLimpio(String cadena, int inicioDato, int finDato) {
        String datoLimpio = "";
        int posCorriente = inicioDato;
        int posFinNombre = finDato;
        char cc = ' ';

        while (posCorriente < posFinNombre) {
            while ((cc = cadena.charAt(posCorriente)) != ' ' &&
                    (cc = cadena.charAt(posCorriente)) != '-') {
                datoLimpio += cc;
                posCorriente++;
            }
            while (cadena.charAt(posCorriente) == ' ' ||
                    cadena.charAt(posCorriente) == '-') {
                posCorriente++;
            }
            if (posCorriente < posFinNombre) {
                datoLimpio += " ";
            }

        }

        return datoLimpio;
    }

    // Toma una cadena en formato AAAAMMDD representando una fecha y la devuelve en formato DD/MM/AAAA
    private String obtenerFecha(String fecha) {

        String ano = fecha.substring(0, 4);
        String mes = fecha.substring(4, 6);
        String dia = fecha.substring(6, 8);

        return dia + "/" + mes + "/" + ano;
    }
}
