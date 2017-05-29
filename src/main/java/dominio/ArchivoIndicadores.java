package dominio;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.util.ArrayList;

public class ArchivoIndicadores {
	private String path = "src/main/resources/Indicadores.txt";
	private ArrayList<Indicador> indicadores;
	
	private BufferedReader reader;
	private Writer writer;
	
	public ArchivoIndicadores(String path){
		this.path = path;
		indicadores = new ArrayList<Indicador>();
	}
	
	public ArchivoIndicadores(){};

	public void escribirIndicador(String indicador){//Precondición, indicador ya validado
		this.abrirEnModoEscritura();
		try {
			writer.write(indicador+"\n");
		} catch (IOException e) {
			throw new NoSePudoEscribirArchivoError("No se pudo escribir el archivo.");
		}
		this.cerrarModoEscritura();
	}
	
	public void leerIndicadores(){
		String indicador = "";
		this.abrirEnModoLectura();
		while((indicador = this.leerUnIndicador())!=null){
			indicadores.add(new Indicador(indicador));
		}
		this.cerrarModoLectura();
	}
	
	public void borrarIndicador(String nombreIndicador){
		this.leerIndicadores();
		Indicador indicador = this.buscarIndicador(nombreIndicador);
		indicadores.remove(indicador);
		this.actualizarArchivoIndicadores();
	}
	
	private Indicador buscarIndicador(String nombreIndicador){
		return indicadores.stream().filter(ind -> ind.seLlama(nombreIndicador)).findFirst().orElseThrow(() -> new ElIndicadorPedidoNoExisteError("El indicador no existe."));
	}
	
	private void actualizarArchivoIndicadores(){
		this.limpiarArchivo();
		indicadores.forEach(ind -> ind.registrarseEn(this));
	}
	
	private void limpiarArchivo(){
		try {
			new FileWriter(path).close();
		} catch (IOException e) {
			throw new NoSePudoEscribirArchivoError("Error al sobreescribir el archivo Indicadores.");
		}
	}
	
	private String leerUnIndicador(){
		try {
			return reader.readLine();
		} catch (IOException e) {
			throw new NoSePudoLeerArchivoError("No se pudo leer el archivo.");
		}
	}
	private void abrirEnModoLectura(){
		try {
			indicadores.clear();
			reader = new BufferedReader(new InputStreamReader(new FileInputStream(path), "utf-8"));
		} catch (UnsupportedEncodingException e) {
			throw new NoSeSoportaElEncodingError("No se soporta el encoding del archivo de Indicadores.");
		} catch (FileNotFoundException e) {
			throw new NoSeEncontroElArchivoError("No se pudo encontrar el archivo de Indicadores.");
		}
	}
	
	private void abrirEnModoEscritura(){
		try {
			writer = new BufferedWriter(new FileWriter(path,true));//El true es para que lo abra en append
		} catch (IOException e) {
			throw new NoSePudoAbrirElArchivoError("Error al abrir el archivo de Indicadores.");
		}
	}
	
	private void cerrarModoEscritura(){
		try {
			writer.close();
		} catch (IOException e) {
			this.errorAlCerrarArchivo();
		}
	}
	
	private void cerrarModoLectura(){
		try {
			reader.close();
		} catch (IOException e) {
			this.errorAlCerrarArchivo();
		}
	}

	private void errorAlCerrarArchivo(){
		throw new NoSePudoCerrarElArchivoError("Error al cerrar el archivo de Indicadores.");
	}
	
	public ArrayList<Indicador> getIndicadores() {
		return indicadores;
	}
	
}

class ElIndicadorPedidoNoExisteError extends RuntimeException{ElIndicadorPedidoNoExisteError(String e){super(e);}}
class NoSePudoAbrirElArchivoError extends RuntimeException{NoSePudoAbrirElArchivoError(String e){super(e);}}
class NoSePudoCerrarElArchivoError extends RuntimeException{NoSePudoCerrarElArchivoError(String e){super(e);}}
class NoSeEncontroElArchivoError extends RuntimeException{NoSeEncontroElArchivoError(String e){super(e);}}
class NoSeSoportaElEncodingError extends RuntimeException{NoSeSoportaElEncodingError(String e){super(e);}}
class NoSePudoEscribirArchivoError extends RuntimeException{NoSePudoEscribirArchivoError(String e){super(e);}}