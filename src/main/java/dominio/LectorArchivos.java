package dominio;

import java.util.ArrayList;

public class LectorArchivos {
	private String ruta;
	private ArrayList<Archivo> lectores = new ArrayList<Archivo>();//Se inicializa en el constructor
	
	public LectorArchivos(String ruta){
		this.ruta = ruta;
		lectores.add(new ArchivoXLS(ruta));
		lectores.add(new ArchivoCSV(ruta));
	}
	
	public Archivo obtenerLectorApropiado(){
		return lectores.stream().filter(lector -> lector.puedeLeerArchivo()).findFirst().orElseThrow(() -> new NoSePudoLeerEseTipoDeArchivoError("No se pudo leer el archivo."));
	}

}

class NoSePudoLeerEseTipoDeArchivoError extends RuntimeException{ public NoSePudoLeerEseTipoDeArchivoError(String s){ super(s);}}