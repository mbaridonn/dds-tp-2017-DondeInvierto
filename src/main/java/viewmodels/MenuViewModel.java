package viewmodels;

import java.util.ArrayList;
import org.uqbar.commons.utils.Dependencies;
import org.uqbar.commons.utils.Observable;
import dominio.ArchivoEmpresas;
import dominio.Empresa;
import dominio.LectorArchivos;
import excepciones.NoSePudoLeerEseTipoDeArchivoError;

@Observable
public class MenuViewModel {
	private String resultadoOperacion = "";
	private String ruta = "";

	public void cargarArchivo(){
		try{
			ArrayList<Empresa> empresas;
			LectorArchivos lectorArchivos = new LectorArchivos(ruta);
			ArchivoEmpresas archivo = lectorArchivos.obtenerLectorApropiado();
			archivo.leerEmpresas();
			empresas = archivo.getEmpresas();
			RepositorioEmpresas.getInstance().setEmpresas(empresas);
			resultadoOperacion = "Archivo cargado";
		}
		catch(NoSePudoLeerEseTipoDeArchivoError e){
			this.ruta = "";
			resultadoOperacion = e.getMessage();
		}
	}
	
	public String getResultadoOperacion() {
		return resultadoOperacion;
	}
	
	public void setResultadoOperacion(String resultadoOperacion) {
		this.resultadoOperacion = resultadoOperacion;
	}

	public String getRuta() {
		return ruta;
	}

	public void setRuta(String ruta) {
		this.ruta = ruta;
		this.cargarArchivo();
	}

	@Dependencies({ "ruta" })
	public boolean getCargado() {
		return ruta.length() > 0;
	}
}
