package dominio;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.Writer;
import java.util.ArrayList;

public class ArchivoIndicadores {
	String path = "src/main/resources/Indicadores.txt";

	public void escribirIndicador(String indicador){//Precondición, indicador ya validado
		Writer writer;
		try {
			writer = new BufferedWriter(new FileWriter(path,true));//El true es para que lo abra en append
			writer.write(indicador+"\n");
			writer.close();
		} catch (IOException e) {
			throw new NoSePudoEscribirArchivoError("No se pudo escribir el archivo.");
		}
	}
	
	public ArrayList<Indicador> leerIndicadores(){
		ArrayList<Indicador> indicadores = new ArrayList<Indicador>();
		Reader reader;
		String indicador = "";
		char caracter = 0;
		try {
			reader = new BufferedReader(new InputStreamReader(new FileInputStream(path), "utf-8"));
			while(true){
				while((caracter=(char) reader.read()) != '\n'){
					if (caracter == (char) -1){//Se llegó al EOF
						reader.close();
						return indicadores;
					}
					indicador+=caracter;
				}
				indicadores.add(new Indicador(indicador));
				indicador = "";
			}
		} catch (IOException e) {
			throw new NoSePudoLeerArchivoError("No se pudo leer el archivo.");
		}
	}

}

class NoSePudoEscribirArchivoError extends RuntimeException{NoSePudoEscribirArchivoError(String e){super(e);}}