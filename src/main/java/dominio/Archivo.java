package dominio;

import java.util.ArrayList;

public interface Archivo {
	public ArrayList<Empresa> leerEmpresas();
	public boolean puedeLeerArchivo();
}
