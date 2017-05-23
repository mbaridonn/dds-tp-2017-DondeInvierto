package dominio;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import com.opencsv.CSVReader;

public class ArchivoCSV extends ArchivoEmpresas implements Archivo {

	private CSVReader reader;
	private String linea[];

	public ArchivoCSV(String ruta) {
		super(ruta);
	}

	@Override
	protected String extensionQueLee() {
		return "csv";
	}
	
	@Override
	protected void leerRegistros(){
		while (this.hayMasCuentas()){
			this.asignarCuentaAEmpresa(this.leerCuenta(linea),this.nombreEmpresa(linea));
		}
	}
	
	private boolean hayMasCuentas(){
		try {
			linea = reader.readNext();
		} catch (IOException e) {
			throw new NoSePudoLeerArchivoError("No se pudo leer el archivo.");
		}
		return linea != null;
	}
	
	private Cuenta leerCuenta(String linea[]){
		return new Cuenta(linea[0],linea[1],Integer.parseInt(linea[2]));
	}
	
	private String nombreEmpresa(String linea[]){
		return linea[3];
	}
	
	private void asignarCuentaAEmpresa(Cuenta cuenta,String nombreEmpresa){
		this.obtenerEmpresaLlamada(nombreEmpresa).registrarCuenta(cuenta);
	}
	
	private Empresa obtenerEmpresaLlamada(String nombreEmpresa){
		if(this.empresaNoCreada(nombreEmpresa)){
			Empresa empresa = new Empresa(nombreEmpresa, new ArrayList<Cuenta>());
			empresas.add(empresa);
			return empresa;
		}
		return empresas.stream().filter(empr->empr.seLlama(nombreEmpresa)).findFirst().orElseThrow(() -> new NoSePudoObtenerLaEmpresaError("Error al buscar la empresa."));
	}
	
	private boolean empresaNoCreada(String nombreEmpresa){
		return (!empresas.stream().anyMatch(empr -> empr.seLlama(nombreEmpresa)));
	}

	@Override
	protected void prepararLector(){
		try {
			reader = new CSVReader(new FileReader(ruta), ';');
			linea = null;
		} catch (FileNotFoundException e) {
			throw new NoSePudoLeerArchivoError("No se pudo leer el archivo.");
		}
	}

	@Override
	protected void cerrarArchivo() {
		try {
			reader.close();
		} catch (IOException e) {
			throw new NoSePudoCerrarArchivoError("No se pudo cerrar el archivo.");
		}
	}
}

class NoSePudoObtenerLaEmpresaError extends RuntimeException{NoSePudoObtenerLaEmpresaError(String e){super(e);}}
