package dominio.indicadores;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import org.uqbar.commons.utils.Observable;

import dominio.Empresa;
import dominio.parser.ParserIndicadores;
import excepciones.NoSePudoLeerArchivoError;

@Observable
public class ArchivoIndicadores {
	private String path = "src/main/resources/Indicadores.txt"; // Está mal que
																// esté
																// hardcodeado?
	private ArrayList<Indicador> indicadores = new ArrayList<Indicador>();

	private BufferedReader reader;
	private Writer writer;
	 
	public void cambiarPath(String nuevoPath) {// Necesario para los tests
		path = nuevoPath;
	}

	private ArchivoIndicadores() {}// Para que no se pueda instanciar la clase desde afuera

	private static ArchivoIndicadores singleton = new ArchivoIndicadores();

	public static ArchivoIndicadores getInstance() {
		return singleton;
	}

	public void cargarIndicador(Indicador indicador) {
		indicadores.add(indicador);
	}
	
	public Set<Indicador> todosLosIndicadoresAplicablesA(Empresa empresa){
		Set<Indicador> indicadoresAplicables = new HashSet<Indicador>();
		Set<String> aniosDeCuentas = empresa.aniosDeLosQueTieneCuentas();
		aniosDeCuentas.forEach(anio -> indicadoresAplicables.addAll(this.indicadoresAplicablesA(empresa, anio)));
		return indicadoresAplicables;
	}
	
	public ArrayList<Indicador> indicadoresAplicablesA(Empresa empresa, String anio){
		ArrayList<Indicador> indicadoresAplicables = new ArrayList<Indicador>();
		this.leerSiNoSeCargaronLosIndicadores();
		indicadores.stream().filter(ind -> ind.esAplicableA(empresa, anio)).forEach(ind -> indicadoresAplicables.add(ind));
		return indicadoresAplicables;
	}

	public void escribirIndicador(String indicador) {
		this.abrirEnModoEscritura();
		try {
			this.verificarExistencia(indicador);
			writer.write(indicador + "\n");
		} catch (IOException e) {
			throw new NoSePudoEscribirArchivoError("No se pudo escribir el archivo.");
		}
		this.cerrarModoEscritura();
	}

	public void leerIndicadores() {
		String indicadorStr = "";
		indicadores = new ArrayList<Indicador>();
		this.abrirEnModoLectura();
		while ((indicadorStr = this.leerUnIndicador()) != null) {
			Indicador nuevoIndicador = ParserIndicadores.parse(indicadorStr);
			this.cargarIndicador(nuevoIndicador);
		}
		this.cerrarModoLectura();
	}

	public void borrarIndicador(String nombreIndicador) {
		this.leerSiNoSeCargaronLosIndicadores();
		try {
			Indicador indicador = this.buscarIndicador(nombreIndicador);
			indicadores.remove(indicador);
			this.actualizarArchivoIndicadores();
		} catch (NoExisteIndicadorError e) {
			return;
		}
	}

	private void verificarExistencia(String indicador) {
		String indicadorStr;
		this.abrirEnModoLectura();
		while ((indicadorStr = this.leerUnIndicador()) != null) {
			if (this.seLlamanIgual(indicador, indicadorStr)) {
				throw new IndicadorExistenteError("Ya existe un indicador con ese nombre.");
			}
		}
		this.cerrarModoLectura();
	}

	private boolean seLlamanIgual(String indicador, String otroIndicador) {
		String nombrePrimerIndicador = this.obtenerNombre(indicador);
		String nombreSegundoIndicador = this.obtenerNombre(otroIndicador);
		return nombrePrimerIndicador.equalsIgnoreCase(nombreSegundoIndicador);
	}

	private String obtenerNombre(String indicador) {
		String nombre = "";
		int i = indicador.indexOf('=');
		if (i > 0) {
			nombre = indicador.substring(0, i).trim();
		}
		return nombre;
	}

	public Indicador buscarIndicador(String nombreIndicador) {
		return indicadores.stream().filter(ind -> ind.seLlama(nombreIndicador)).findFirst()
				.orElseThrow(() -> new NoExisteIndicadorError("No se pudo encontrar una indicador con ese nombre."));
	}

	private void actualizarArchivoIndicadores() {
		this.limpiarArchivo();
		this.registrarNuevamenteLosIndicadores();
	}
	
	private void registrarNuevamenteLosIndicadores(){
		for(int i = 0; i < indicadores.size(); i++){
			indicadores.get(i).registrarseEn(this);
		}
	}

	private void limpiarArchivo() {
		try {
			new FileWriter(path).close();
		} catch (IOException e) {
			throw new NoSePudoEscribirArchivoError("Error al sobreescribir el archivo Indicadores.");
		}
	}

	private String leerUnIndicador() {
		try {
			return reader.readLine();
		} catch (IOException e) {
			throw new NoSePudoLeerArchivoError("No se pudo leer el archivo.");
		}
	}
	
	private void leerSiNoSeCargaronLosIndicadores(){
		if(indicadores.isEmpty()){
			this.leerIndicadores();
		}
	}
	

	private void abrirEnModoLectura() {
		try {
			reader = new BufferedReader(new InputStreamReader(new FileInputStream(path), "utf-8"));
		} catch (UnsupportedEncodingException e) {
			throw new NoSeSoportaElEncodingError("No se soporta el encoding del archivo de Indicadores.");
		} catch (FileNotFoundException e) {
			throw new NoSeEncontroElArchivoError("No se pudo encontrar el archivo de Indicadores.");
		}
	}

	private void abrirEnModoEscritura() {
		try {
			writer = new BufferedWriter(new FileWriter(path, true));
		} catch (IOException e) {
			throw new NoSePudoAbrirElArchivoError("Error al abrir el archivo de Indicadores.");
		}
	}

	private void cerrarModoEscritura() {
		try {
			writer.close();
		} catch (IOException e) {
			this.errorAlCerrarArchivo();
		}
	}

	private void cerrarModoLectura() {
		try {
			reader.close();
		} catch (IOException e) {
			this.errorAlCerrarArchivo();
		}
	}

	private void errorAlCerrarArchivo() {
		throw new NoSePudoCerrarElArchivoError("Error al cerrar el archivo de Indicadores.");
	}

	public ArrayList<Indicador> getIndicadores() {
		return indicadores;
	}

}

class IndicadorExistenteError extends RuntimeException {
	IndicadorExistenteError(String e) {
		super(e);
	}
}

class NoExisteIndicadorError extends RuntimeException {
	NoExisteIndicadorError(String e) {
		super(e);
	}
}

class NoSePudoAbrirElArchivoError extends RuntimeException {
	NoSePudoAbrirElArchivoError(String e) {
		super(e);
	}
}

class NoSePudoCerrarElArchivoError extends RuntimeException {
	NoSePudoCerrarElArchivoError(String e) {
		super(e);
	}
}

class NoSeEncontroElArchivoError extends RuntimeException {
	NoSeEncontroElArchivoError(String e) {
		super(e);
	}
}

class NoSeSoportaElEncodingError extends RuntimeException {
	NoSeSoportaElEncodingError(String e) {
		super(e);
	}
}

class NoSePudoEscribirArchivoError extends RuntimeException {
	NoSePudoEscribirArchivoError(String e) {
		super(e);
	}
}