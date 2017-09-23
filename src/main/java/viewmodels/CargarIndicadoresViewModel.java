package viewmodels;

import org.uqbar.commons.utils.Observable;
import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;
import org.uqbarproject.jpa.java8.extras.transaction.TransactionalOps;

import dominio.indicadores.RepositorioIndicadores;
import excepciones.EntidadExistenteError;
import excepciones.ParserError;

@Observable
public class CargarIndicadoresViewModel implements WithGlobalEntityManager, TransactionalOps {

	private String indicador;
	private String resultadoOperacion;

	public void cargarIndicador() {
		try {
			RepositorioIndicadores.getInstance().agregarIndicador(indicador);
			resultadoOperacion = "Indicador cargado";
		} catch (ParserError e) {
			resultadoOperacion = e.getMessage();
		}
	}

	public void guardarIndicador() {
		try {
			withTransaction(() -> RepositorioIndicadores.getInstance().guardarIndicador(indicador));
			resultadoOperacion = "Indicador guardado";
		} catch (ParserError | EntidadExistenteError e) {
			resultadoOperacion = e.getMessage();
		}
	}

	public String getIndicador() {
		return indicador;
	}

	public void setIndicador(String indicador) {
		this.indicador = indicador;
	}
	
	public String getResultadoOperacion() {
		return resultadoOperacion;
	}
	
	public void setResultadoOperacion(String resultadoOperacion) {
		this.resultadoOperacion = resultadoOperacion;
	}

}