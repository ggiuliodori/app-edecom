package edecom.edecom.estavezsale;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Ailu on 10/3/2018.
 */

public class ItemArrayAdapterChofer extends ArrayAdapter<String[]> {
    public List<String[]> scoreList = new ArrayList<String[]>();

    static class ItemViewHolder {
        TextView name,score,score1,score2,score3,score4;

    }

    public ItemArrayAdapterChofer(Context context, int textViewResourceId) {
        super(context, textViewResourceId);
    }

    @Override
    public void add(String[] object) {
        scoreList.add(object);
        super.add(object);
    }

    @Override
    public int getCount() {
        return this.scoreList.size();
    }

    @Override
    public String[] getItem(int index) {
        return this.scoreList.get(index);
    }

    public String enReglaChofer(String x) {
        x = x.toUpperCase();
        for (String[] fila : scoreList) {
            if (Arrays.asList(fila).contains(x) && x.length()>0) {
                for (int posicion = 0; posicion < fila.length; posicion++) {
                    if ((fila[posicion].equals(x))) {
                        if ((fila[2].equals("EN REGLA")) && (fila[3].equals("EN REGLA")) && (fila[4].equals("EN REGLA")))
                            return "En regla";
                        else
                            return "Infractor";
                    }
                }
            }
        }
        return "No existe";
    }

    public String[] arrayParaMostrar (String x) {
        for (String[] arreglo : scoreList) {
            if (Arrays.asList(arreglo).contains(x)) {
                return arreglo;
            }
        }
        return null;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        ItemArrayAdapterVehiculo.ItemViewHolder viewHolder;
        if (row == null) {
            LayoutInflater inflater = (LayoutInflater) this.getContext().
                    getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(R.layout.itemchofer_layout, parent, false);
            viewHolder = new ItemArrayAdapterVehiculo.ItemViewHolder();
            viewHolder.name = (TextView) row.findViewById(R.id.dni);
            viewHolder.score = (TextView) row.findViewById(R.id.nombre);
            viewHolder.score1 = (TextView) row.findViewById(R.id.vtoLicencia);
            viewHolder.score2 = (TextView) row.findViewById(R.id.vtoAntecedente);
            viewHolder.score3 = (TextView) row.findViewById(R.id.vtoLibreta);
            row.setTag(viewHolder);
        } else {
            viewHolder = (ItemArrayAdapterVehiculo.ItemViewHolder)row.getTag();
        }
        String[] stat = getItem(position);
        viewHolder.name.setText(stat[0]);
        viewHolder.score.setText(stat[1]);
        viewHolder.score1.setText(stat[2]);
        viewHolder.score2.setText(stat[3]);
        viewHolder.score3.setText(stat[4]);
        return row;
    }
}
