package dominio;

import java.util.ArrayList;

public interface Archivo {
	public void leerEmpresas();
	public boolean puedeLeerArchivo();
	public ArrayList<Empresa> getEmpresas();
}
