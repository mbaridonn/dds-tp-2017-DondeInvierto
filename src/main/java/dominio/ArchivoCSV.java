package dominio;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import com.opencsv.CSVReader;

public class ArchivoCSV extends ArchivoEmpresas implements Archivo{


	private CSVReader reader;
	
	public ArchivoCSV(String ruta){
		super(ruta);
	}
	
	@Override
	protected String extensionQueLee() {
		return "csv";
	}

	public ArrayList<Empresa> leerEmpresas(){//Falta abstraer y manejar errores correctamente
			try {
				reader = new CSVReader(new FileReader(ruta),';');
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			ArrayList<Empresa> empresas = new ArrayList<Empresa>();
			String[] linea;
			String nombreEmpresaActual = "";
			String nombreEmpresaProxLinea = "";
			ArrayList<Cuenta> cuentas = new ArrayList<Cuenta>();
			String anio, tipoCuenta;
			int valor;
			try {
				while ((linea = reader.readNext()) != null) { // ACA IRIA EL WHILE ANTERIOR
					anio = linea[0];
					tipoCuenta = linea[1];
					valor = Integer.parseInt(linea[2]);
					Cuenta cuenta = new Cuenta(anio,tipoCuenta,valor);
					cuentas.add(cuenta);
					Empresa empresa = new Empresa(linea[3],cuentas);
					empresas.add(empresa);
				}
			} catch (NumberFormatException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			try {
				reader.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		return empresas;
	}

	/*
	 * public ArrayList<Cuenta> leerCuentasDeHoja(){
	 * 
	 * BufferedReader br = null; String csvFile = ruta; String linea = "";
	 * String cvsSplitBy = ","; String[] fila; String anio, tipoCuenta; int
	 * valor; ArrayList<Cuenta> cuentas = new ArrayList<Cuenta>();
	 * 
	 * try { br = new BufferedReader(new FileReader(csvFile)); linea =
	 * br.readLine(); while (linea != null) { fila = linea.split(cvsSplitBy);
	 * anio = fila[0]; tipoCuenta = fila[1]; valor = Integer.parseInt(fila[2]);
	 * //System.out.println("A�o = " + anio + " , tipoCuenta = " + tipoCuenta +
	 * ", valor = " + valor); // S�lo para ver si se van cargando bien los datos
	 * Cuenta cuenta = new Cuenta(anio,tipoCuenta,valor); cuentas.add(cuenta); }
	 * 
	 * } catch (Exception e) { e.printStackTrace(); } return cuentas; }
	 */
}
