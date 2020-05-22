package edecom.edecom.estavezsale;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ailu on 4/3/2018.
 */


public class CsvToList {
    private final File archivo;

    public CsvToList(File archivo){
        this.archivo = archivo;
    }

    public List<String[]> read(){
        List<String[]> resultList = new ArrayList<String[]>();
        try(BufferedReader br = new BufferedReader(new FileReader(archivo))) {
            String line = "";
            while ((line = br.readLine()) != null) {
                resultList.add(line.split(";"));
            }
        } catch (FileNotFoundException e) {
            //Some error logging
        } catch (IOException e) {
            e.printStackTrace();
        }
        return resultList;
    }

    public String mostrar(){
        return archivo.toString();
    }
}