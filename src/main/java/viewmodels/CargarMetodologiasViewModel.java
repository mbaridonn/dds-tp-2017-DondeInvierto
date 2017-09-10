package viewmodels;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import org.uqbar.commons.utils.Dependencies;
import org.uqbar.commons.utils.Observable;
import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;

import dominio.indicadores.RepositorioIndicadores;
import dominio.indicadores.Indicador;
import dominio.metodologias.*;
import excepciones.MetodologiaExistenteError;
import excepciones.MetodologiaInvalidaError;

@Observable
public class CargarMetodologiasViewModel implements WithGlobalEntityManager{
	private String nombreMetodologia = "";
	private String resultadoOperacion;
	private MetodologiaBuilder metodologiaBuilder;

	private int aniosSeleccionados;

	private int valorSeleccionado;

	private Indicador indicadorSeleccionado;
	private static Set<Indicador> indicadores = RepositorioIndicadores.getInstance().getIndicadores();

	private OperacionAgregacion operacionAgregacionSeleccionada;
	private static ArrayList<OperacionAgregacion> operacionesAgregacion = new ArrayList<OperacionAgregacion>(
			Arrays.asList(OperacionAgregacion.Mediana, OperacionAgregacion.Promedio, OperacionAgregacion.Sumatoria,
					OperacionAgregacion.Ultimo, OperacionAgregacion.Variacion));

	private OperacionRelacional operacionRelacionalSeleccionada;
	private static ArrayList<OperacionRelacional> operacionesRelacionales = new ArrayList<OperacionRelacional>(
			Arrays.asList(OperacionRelacional.Mayor, OperacionRelacional.Menor, OperacionRelacional.Igual));

	private static CargarMetodologiasViewModel singleton = new CargarMetodologiasViewModel();

	public int getValorSeleccionado() {
		return valorSeleccionado;
	}

	public void setValorSeleccionado(int valorSeleccionado) {
		this.valorSeleccionado = valorSeleccionado;
	}

	public int getAniosSeleccionados() {
		return aniosSeleccionados;
	}

	public void setAniosSeleccionados(int aniosSeleccionados) {
		this.aniosSeleccionados = aniosSeleccionados;
	}

	public OperacionAgregacion getOperacionAgregacionSeleccionada() {
		return operacionAgregacionSeleccionada;
	}

	public void setOperacionAgregacionSeleccionada(OperacionAgregacion operacionAgregacionSeleccionada) {
		this.operacionAgregacionSeleccionada = operacionAgregacionSeleccionada;
	}

	public ArrayList<OperacionAgregacion> getOperacionesAgregacion() {
		return operacionesAgregacion;
	}

	public void setOperacionesAgregacion(ArrayList<OperacionAgregacion> operacionesAgregacion) {
		this.operacionesAgregacion = operacionesAgregacion;
	}

	public Indicador getIndicadorSeleccionado() {
		return indicadorSeleccionado;
	}

	public void setIndicadorSeleccionado(Indicador indicadorSeleccionado) {
		this.indicadorSeleccionado = indicadorSeleccionado;
	}

	public Set<Indicador> getIndicadores() {
		return indicadores;
	}

	public void setIndicadores(Set<Indicador> indicadores) {
		this.indicadores = indicadores;
	}

	public static CargarMetodologiasViewModel getInstance() {
		return singleton;
	}

	public OperacionRelacional getOperacionRelacionalSeleccionada() {
		return operacionRelacionalSeleccionada;
	}

	public void setOperacionRelacionalSeleccionada(OperacionRelacional operacionRelacional) {
		this.operacionRelacionalSeleccionada = operacionRelacional;
	}

	public void crearMetodologia() {
		metodologiaBuilder = new MetodologiaBuilder();
		metodologiaBuilder.crearMetodologia(nombreMetodologia);
		if (metodologiaBuilder.contieneMetodologiaValida()) {
			// Cargar la metodologÃ­a donde corresponda
			resultadoOperacion = "MetodologÃ­a creada";
			// ABRIR PANEL CONDICIONES. HAY QUE PASAR EL BUILDER DE UNA VISTA A
			// OTRA PARA CONTINUAR CON LA CREACIÃ“N DE LA METODOLOGÃ�A (!!)
		} else {
			resultadoOperacion = "Ingrese un nombre para la MetodologÃ­a";
			// Alternativamente, se podrÃ­a hacer que cada campo incorrecto
			// lance una excepciÃ³n por separado (de tipo
			// MetodologiaMalInicializadaError)
			// y catchearlas acÃ¡ y mostrar el mensaje de c/u (!)
		}
	}

	public String getNombreMetodologia() {
		return nombreMetodologia;
	}

	public void setNombreMetodologia(String nombreMetodologia) {
		this.nombreMetodologia = nombreMetodologia;
	}

	public String getResultadoOperacion() {
		return resultadoOperacion;
	}

	public void setResultadoOperacion(String resultadoOperacion) {
		this.resultadoOperacion = resultadoOperacion;
	}

	public ArrayList<OperacionRelacional> getOperacionesRelacionales() {
		return operacionesRelacionales;
	}

	public void agregarCondicionProritaria() {
		metodologiaBuilder = metodologiaBuilder.agregarCondicionPrioritaria(operacionAgregacionSeleccionada,
				indicadorSeleccionado, aniosSeleccionados, operacionRelacionalSeleccionada);
	}

	public void agregarCondicionTaxativa() {
		metodologiaBuilder = metodologiaBuilder.agregarCondicionTaxativa(operacionAgregacionSeleccionada,
				indicadorSeleccionado, aniosSeleccionados, operacionRelacionalSeleccionada, valorSeleccionado);
	}

	public void guardarMetodologia() {
		try {
			EntityManager entityManager = this.entityManager();
			EntityTransaction tx = entityManager.getTransaction();
			tx.begin();
			new RepositorioMetodologias().agregarMetodologia(metodologiaBuilder.buildMetodologia());
			tx.commit();
			resultadoOperacion = "Metodologia guardada";
		} catch (MetodologiaInvalidaError | MetodologiaExistenteError e) {
			resultadoOperacion = e.getMessage();
		}
	}

	@Dependencies({ "nombreMetodologia" })
	public boolean getCargado() {
		return nombreMetodologia.length() > 0;
	}

}
