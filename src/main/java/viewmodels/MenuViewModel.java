package viewmodels;

import java.util.ArrayList;
import org.uqbar.commons.utils.Dependencies;
import org.uqbar.commons.utils.Observable;

import dominio.empresas.ArchivoEmpresas;
import dominio.empresas.Empresa;
import dominio.empresas.LectorArchivos;
import dominio.empresas.RepositorioEmpresas;
import excepciones.NoSePudoLeerEseTipoDeArchivoError;

@Observable
public class MenuViewModel {
	private String resultadoOperacion = "";
	private String ruta = "";
	
	@Dependencies({ "ruta" })// EN ESTE MOMENTO, SI ARRANCA LA BD SIN EMPRESAS Y DESPUÉS SE AGREGAN, EL BOTÓN SIGUE BLOQUEADO (!!)
	public boolean getHayCuentas(){
		if(ruta != "") {
			return true;
		}
		return (new RepositorioEmpresas().hayEmpresas());
	}
	
	public void cargarArchivo(){
		try{
			ArrayList<Empresa> empresas;
			LectorArchivos lectorArchivos = new LectorArchivos(ruta);
			ArchivoEmpresas archivo = lectorArchivos.obtenerLectorApropiado();
			archivo.leerEmpresas();
			empresas = archivo.getEmpresas();
			new RepositorioEmpresas().agregarEmpresas(empresas);
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

}
