package defaultPackage;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.time.Year;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import org.junit.Before;
import org.junit.Test;
import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;
import org.uqbarproject.jpa.java8.extras.test.AbstractPersistenceTest;

import dominio.empresas.Cuenta;
import dominio.empresas.Empresa;
import dominio.empresas.RepositorioEmpresas;
import dominio.indicadores.Indicador;
import dominio.indicadores.RepositorioIndicadores;
import dominio.metodologias.CondicionPrioritaria;
import dominio.metodologias.CondicionTaxativa;
import dominio.metodologias.Metodologia;
import dominio.metodologias.OperacionAgregacion;
import dominio.metodologias.OperacionRelacional;
import dominio.metodologias.OperandoCondicion;
import dominio.metodologias.RepositorioMetodologias;
import dominio.parser.ParserIndicadores;
import excepciones.IndicadorExistenteError;

public class PersistenciaTest extends AbstractPersistenceTest implements WithGlobalEntityManager {

	private EntityManager entityManager;
	private EntityTransaction tx;
	private ArrayList<Indicador> indicadores;
	private RepositorioEmpresas repoEmpresas;
	private RepositorioIndicadores repoIndicadores;
	private RepositorioMetodologias repoMetodologias;
	private List<Cuenta> listaCuentasEjemplo1;
	private List<Cuenta> listaCuentasEjemplo2;

	@Before
	public void startTransaction() {
		repoEmpresas = new RepositorioEmpresas();
		repoIndicadores = new RepositorioIndicadores();
		repoMetodologias = new RepositorioMetodologias();
		indicadores = new ArrayList<Indicador>();
		RepositorioIndicadores archivoIndicadores = RepositorioIndicadores.getInstance();
		indicadores.addAll(archivoIndicadores.getIndicadores());
		listaCuentasEjemplo1 = new ArrayList<Cuenta>(
				Arrays.asList(new Cuenta(Year.of(2015), "EBITDA", 2000), new Cuenta(Year.of(2014), "FDS", 3000)));
		listaCuentasEjemplo2 = new ArrayList<Cuenta>(Arrays.asList(new Cuenta(Year.of(2013), "EBITDA", 6000),
				new Cuenta(Year.of(2010), "FDS", 8000), new Cuenta(Year.of(2014), "EBITDA", 8000)));
	}

	@Test
	public void alAgregarDosEmpresasAlRepositorioEmpresasEstasSePersistenEsaCantidad() {
		Empresa empresa1 = new Empresa("empresa1Test0", listaCuentasEjemplo1);
		Empresa empresa2 = new Empresa("empresa2Test0", listaCuentasEjemplo2);
		List<Empresa> listaEmpresas = new ArrayList<Empresa>(Arrays.asList(empresa1, empresa2));
		int cantidadAntesDeAgregar = repoEmpresas.getEmpresas().size();
		repoEmpresas.agregarEmpresas(listaEmpresas);
		assertEquals(cantidadAntesDeAgregar + 2, repoEmpresas.getEmpresas().size());
	}

	@Test
	public void alAgregarDosEmpresasAlRepositorioEmpresasEstasSePersistenCorrectamente() {
		Empresa empresa1 = new Empresa("empresa1Test1", listaCuentasEjemplo1);
		Empresa empresa2 = new Empresa("empresa2Test1", listaCuentasEjemplo2);
		List<Empresa> listaEmpresas = new ArrayList<Empresa>(Arrays.asList(empresa1, empresa2));
		repoEmpresas.agregarEmpresas(listaEmpresas);
		assertTrue(repoEmpresas.getEmpresas().containsAll(listaEmpresas));
	}

	@Test
	public void noSePersistenDosEmpresasConElMismoNombre() {
		Empresa empresa = new Empresa("empresa1Test2", listaCuentasEjemplo1);
		Empresa empresaCopia = new Empresa("empresa1Test2", listaCuentasEjemplo2);
		List<Empresa> listaEmpresas = new ArrayList<Empresa>(Arrays.asList(empresa, empresaCopia));
		repoEmpresas.agregarEmpresas(listaEmpresas);
		assertEquals(1,
				this.entityManager().createQuery("FROM Empresa where nombre = 'empresa1Test2'").getResultList().size());
	}

	@Test
	public void alAgregarDosIndicadoresAlRepositorioIndicadoresEstosSePersistenEsaCantidad() {
		List<String> listaIndicadores = new ArrayList<String>(Arrays.asList("INDICADORUNOTESTCERO = ebitda + fds - 2",
				"INDICADORDOSTESTCERO = ebitda * 2 + fds - 2500"));
		int cantidadAntesDeAgregar = repoIndicadores.getIndicadores().size();
		repoIndicadores.guardarIndicadores(listaIndicadores);
		assertEquals(cantidadAntesDeAgregar + 2, repoIndicadores.getIndicadores().size());
	}

	@Test
	public void alAgregarDosIndicadoresAlRepositorioIndicadoresEstosSePersistenCorrectamente() {
		List<String> listaIndicadores = new ArrayList<String>(Arrays.asList("INDICADORUNOTESTUNO = ebitda + fds - 2",
				"INDICADORDOSTESTUNO = ebitda * 2 + fds - 2500"));
		List<Indicador> indicadores = crearIndicadoresAPartirDeSusExpresiones(listaIndicadores);
		repoIndicadores.guardarIndicadores(listaIndicadores);
		assertTrue(repoIndicadores.getIndicadores().containsAll(indicadores));
	}

	@Test
	public void noSePersistenDosIndicadoresConElMismoNombre() {
		List<String> listaIndicadores = new ArrayList<String>(Arrays.asList("INDICADORUNOTESTDOS = ebitda + fds - 2",
				"INDICADORUNOTESTDOS = ebitda * 2 + fds - 2500"));
		try {
			repoIndicadores.guardarIndicadores(listaIndicadores);
			assertTrue(false);
		} catch (IndicadorExistenteError e) {
			assertTrue(true);
		}
	}

	@Test
	public void alAgregarDosMetodologiasAlRepositorioMetodologiasEstosSePersistenEsaCantidad() {
		List<Metodologia> listaMetodologias = new ArrayList<Metodologia>(
				Arrays.asList(obtenerMetodologiaTipo1("MetodologiaTipo1TestCero"),
						obtenerMetodologiaTipo2("MetodologiaTipo2TestCero")));
		int cantidadAntesDeAgregar = repoMetodologias.getMetodologias().size();
		listaMetodologias.forEach(metodologia -> repoMetodologias.agregarMetodologia(metodologia));
		assertEquals(cantidadAntesDeAgregar + 2, repoMetodologias.getMetodologias().size());
	}

	@Test
	public void alAgregarDosMetodologiasAlRepositorioMetodologiasEstosSePersistenCorrectamente() {
		List<Metodologia> listaMetodologias = new ArrayList<Metodologia>(
				Arrays.asList(obtenerMetodologiaTipo1("MetodologiaTipo1TestUno"),
						obtenerMetodologiaTipo2("MetodologiaTipo2TestUno")));
		listaMetodologias.forEach(metodologia -> repoMetodologias.agregarMetodologia(metodologia));
		assertTrue(repoMetodologias.getMetodologias().containsAll(listaMetodologias));
	}

	/*
	 * @Test(expected=MetodologiaExistenteError.class) public void
	 * siPersistoDosVecesLaMismaMetodolgiaFalla(){
	 * repoMetodologias.agregarMetodologia(metodologiaDeWarren);
	 * repoMetodologias.agregarMetodologia(metodologiaDeWarren); }
	 */

	// ************************* METODOS AUXILIARES
	// *************************************//
	
	private List<Indicador> crearIndicadoresAPartirDeSusExpresiones(List<String> expresiones) {
		List<Indicador> indicadores = new ArrayList<Indicador>();
		expresiones.forEach(exp -> indicadores.add(ParserIndicadores.parse(exp)));
		return indicadores;
	}

	private Metodologia obtenerMetodologiaTipo1(String nombreMetodologia) {
		Indicador ingresoNeto = getIndicadorLlamado("ingresoNeto");
		Indicador indicadorDos = getIndicadorLlamado("indicadorDos");
		Metodologia metodologia = new Metodologia(nombreMetodologia);
		CondicionTaxativa condTax = new CondicionTaxativa(
				new OperandoCondicion(OperacionAgregacion.Promedio, ingresoNeto, 2), OperacionRelacional.Mayor, 10000);
		CondicionPrioritaria condPrior = new CondicionPrioritaria(
				new OperandoCondicion(OperacionAgregacion.Sumatoria, indicadorDos, 2), OperacionRelacional.Mayor);
		metodologia.agregarCondicionTaxativa(condTax);
		metodologia.agregarCondicionPrioritaria(condPrior);
		return metodologia;
	}

	private Metodologia obtenerMetodologiaTipo2(String nombreMetodologia) {
		Indicador prueba = getIndicadorLlamado("prueba");
		Metodologia metodologia = new Metodologia(nombreMetodologia);
		CondicionTaxativa condTax = new CondicionTaxativa(new OperandoCondicion(OperacionAgregacion.Ultimo, prueba, 1),
				OperacionRelacional.Mayor, 0);
		CondicionPrioritaria condPrior = new CondicionPrioritaria(
				new OperandoCondicion(OperacionAgregacion.Ultimo, prueba, 1), OperacionRelacional.Mayor);
		metodologia.agregarCondicionTaxativa(condTax);
		metodologia.agregarCondicionPrioritaria(condPrior);
		return metodologia;
	}

	private Indicador getIndicadorLlamado(String nombreIndicador) {
		return indicadores.stream().filter(ind -> ind.getNombre().equalsIgnoreCase(nombreIndicador)).findFirst()
				.orElseThrow(
						() -> new NoSePudoObtenerIndicadorError("No se pudo obtener un indicador con ese nombre."));
	}
}
