package dominio.empresas;

import java.util.ArrayList;

import excepciones.NoSePudoLeerEseTipoDeArchivoError;

public class LectorArchivos {
	private ArrayList<ArchivoEmpresas> lectores = new ArrayList<ArchivoEmpresas>();//Se inicializa en el constructor
	
	public LectorArchivos(String ruta){
		lectores.add(new ArchivoXLS(ruta));
		lectores.add(new ArchivoCSV(ruta));
	}
	
	public ArchivoEmpresas obtenerLectorApropiado(){
		return lectores.stream().filter(lector -> lector.puedeLeerArchivo()).findFirst().orElseThrow(() -> new NoSePudoLeerEseTipoDeArchivoError("No se pudo leer el archivo."));
	}

}