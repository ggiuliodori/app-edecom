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
 * Created by Ailu on 27/2/2018.
 */

public class ItemArrayAdapterVehiculo extends ArrayAdapter<String[]> {
    public List<String[]> scoreList = new ArrayList<String[]>();

    static class ItemViewHolder {
        TextView name,score,score1,score2,score3,score4;

    }

    public ItemArrayAdapterVehiculo(Context context, int textViewResourceId) {
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

    public String enReglaVehiculo(String x) {
        x = x.toUpperCase();
        for (String[] fila : scoreList) {
            if (fila[0].equals(x) || fila[1].equals(x)) {
                for (int posicion = 0; posicion < fila.length; posicion++) {
                    if ((fila[posicion].equals(x))) {
                        if ((fila[4].equals("EN REGLA")) && (fila[5].equals("EN REGLA")) && (fila[6].equals("EN REGLA")))
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
        ItemViewHolder viewHolder;
        if (row == null) {
            LayoutInflater inflater = (LayoutInflater) this.getContext().
                    getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(R.layout.itemvehiculo_layout, parent, false);
            viewHolder = new ItemViewHolder();
            viewHolder.name = (TextView) row.findViewById(R.id.oblea);
            viewHolder.score = (TextView) row.findViewById(R.id.patente);
            viewHolder.score1 = (TextView) row.findViewById(R.id.marca);
            viewHolder.score2 = (TextView) row.findViewById(R.id.iMec);
            viewHolder.score3 = (TextView) row.findViewById(R.id.seg);
            viewHolder.score4 = (TextView) row.findViewById(R.id.iTV);
            row.setTag(viewHolder);
        } else {
            viewHolder = (ItemViewHolder)row.getTag();
        }
        String[] stat = getItem(position);
        viewHolder.name.setText(stat[0]);
        viewHolder.score.setText(stat[1]);
        viewHolder.score1.setText(stat[2]);
        viewHolder.score2.setText(stat[3]);
        viewHolder.score3.setText(stat[4]);
        viewHolder.score4.setText(stat[5]);
        return row;
    }

}
