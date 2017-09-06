package viewmodels;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import org.uqbar.commons.utils.Observable;
import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;

import dominio.indicadores.RepositorioIndicadores;
import dominio.parser.ParserIndicadores;
import excepciones.IndicadorExistenteError;
import excepciones.ParserError;

@Observable
public class CargarIndicadoresViewModel implements WithGlobalEntityManager{

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
			ParserIndicadores.parse(indicador);//Se usa sólo para validar. Si lanza excepción, va al catch
			EntityManager entityManager = this.entityManager();
			EntityTransaction tx = entityManager.getTransaction();
			tx.begin();
			RepositorioIndicadores.getInstance().guardarIndicador(indicador);
			tx.commit();
			resultadoOperacion = "Indicador guardado";
		} catch (ParserError | IndicadorExistenteError e) {
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