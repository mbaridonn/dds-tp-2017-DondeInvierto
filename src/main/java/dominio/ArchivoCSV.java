package dominio;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

public class ArchivoCSV {
	
	private String ruta;
	
	public ArrayList<Cuenta> leerCuentasDeHoja(){
	
    BufferedReader br = null;
    String csvFile = ruta;
    String linea = "";
    String cvsSplitBy = ",";
    String[] fila;
	String anio, tipoCuenta;
	int valor;
	ArrayList<Cuenta> cuentas = new ArrayList<Cuenta>();

    try {
        br = new BufferedReader(new FileReader(csvFile));
        linea = br.readLine();
        while (linea != null) {
        fila = linea.split(cvsSplitBy);
        anio = fila[0];
        tipoCuenta = fila[1];
        valor = Integer.parseInt(fila[2]);
        //System.out.println("Año = " + anio + " , tipoCuenta = " + tipoCuenta + ", valor = " + valor);  // Sólo para ver si se van cargando bien los datos
        Cuenta cuenta = new Cuenta(anio,tipoCuenta,valor);
        cuentas.add(cuenta); 
        }

    } catch (Exception e) {
        e.printStackTrace();
    	}
    return cuentas;
	}
}
