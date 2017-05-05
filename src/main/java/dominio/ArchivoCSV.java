package dominio;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import com.opencsv.CSVReader;

public class ArchivoCSV extends ArchivoEmpresas implements Archivo {

	private CSVReader reader;

	public ArchivoCSV(String ruta) {
		super(ruta);
	}

	@Override
	protected String extensionQueLee() {
		return "csv";
	}

	public ArrayList<Empresa> leerEmpresas() {// Falta abstraer y manejar errores correctamente
		abrirArchivo();
		ArrayList<Empresa> empresas = new ArrayList<Empresa>();
		String[] linea;
		String nombreEmpresaActual = "";
		String nombreEmpresaProxLinea = "";
		ArrayList<Cuenta> cuentas = new ArrayList<Cuenta>();
		String anio, tipoCuenta;
		int valor;
		try {
			while ((linea = reader.readNext()) != null) {
				nombreEmpresaProxLinea = linea[3];
				if ((!nombreEmpresaActual.equals(nombreEmpresaProxLinea))) {
					if (!cuentas.isEmpty()) {
						empresas.add(new Empresa(nombreEmpresaActual, (ArrayList<Cuenta>) cuentas.clone()));//Fix temporal
						cuentas.clear();
					}
					nombreEmpresaActual = nombreEmpresaProxLinea;
				}
				anio = linea[0];
				tipoCuenta = linea[1];
				valor = Integer.parseInt(linea[2]);
				Cuenta cuenta = new Cuenta(anio, tipoCuenta, valor);
				cuentas.add(cuenta);
			}
			empresas.add(new Empresa(nombreEmpresaActual, cuentas));// Guarda la última empresa
		} catch (NumberFormatException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		cerrarArchivo();
		return empresas;
	}

	public void abrirArchivo() {
		try {
			reader = new CSVReader(new FileReader(ruta), ';');
		} catch (FileNotFoundException e) {
			throw new NoSePudoLeerArchivoError("No se pudo leer el archivo.");
		}
	}

	private void cerrarArchivo() {
		try {
			reader.close();
		} catch (IOException e) {
			throw new NoSePudoCerrarArchivoError("No se pudo cerrar el archivo.");
		}
	}
}
