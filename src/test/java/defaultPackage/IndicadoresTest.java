package defaultPackage;

import static org.junit.Assert.*;

import java.time.Year;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;
import org.uqbarproject.jpa.java8.extras.test.AbstractPersistenceTest;
import org.uqbarproject.jpa.java8.extras.transaction.TransactionalOps;

import dominio.RepositorioUsuarios;
import dominio.Usuario;
import dominio.empresas.ArchivoXLS;
import dominio.empresas.Empresa;
import dominio.indicadores.RepositorioIndicadores;
import dominio.parser.ParserIndicadores;
import dominio.indicadores.Indicador;
import excepciones.EntidadExistenteError;
import excepciones.IndicadorExistenteError;

public class IndicadoresTest extends AbstractPersistenceTest implements WithGlobalEntityManager, TransactionalOps{

	List<Indicador> indicadores = new ArrayList<Indicador>();
	ArrayList<Empresa> empresasParaIndicadores;
	RepositorioIndicadores archivoIndicadores = new RepositorioIndicadores();
	Usuario usuario = new Usuario("admin","admin");
	@Before
	public void setUp() {
		withTransaction(() -> {
			archivoIndicadores.agregarMultiplesIndicadores(Arrays.asList(new String[] { 
					"INGRESONETO = netooperacionescontinuas + netooperacionesdiscontinuas",
					"INDICADORDOS = cuentarara + fds",
					"INDICADORTRES = INGRESONETO * 10 + ebitda",
					"A = 5 / 3", "PRUEBA = ebitda + 5" }));
		});
		indicadores.addAll(archivoIndicadores.obtenerTodos());
		usuario.setIndicadoresCreados(indicadores);
		Usuario.instance = usuario;
		System.out.println("HOLAA");
		System.out.println("HOLAA");
		ArchivoXLS archivoEjemploIndicadores = new ArchivoXLS("src/test/resources/EjemploIndicadores.xls");
		archivoEjemploIndicadores.leerEmpresas();		
		empresasParaIndicadores = archivoEjemploIndicadores.getEmpresas();
	}
	
	@Test
	public void elArchivoIndicadoresLeeCorrectamente() {
		Set<Indicador> indicadoresActuales = new HashSet<Indicador>(indicadores);
		Set<Indicador> indicadoresEsperados = new HashSet<Indicador>(Arrays.asList(new Indicador[] {
			new Indicador("INDICADORDOS"),
			new Indicador("A"),
			new Indicador("INGRESONETO"),
			new Indicador("PRUEBA"),
			new Indicador("INDICADORTRES")}));		
		boolean todosLosIndicadoresSonLosEsperados = indicadoresActuales.equals(indicadoresEsperados);
		assertTrue(todosLosIndicadoresSonLosEsperados);
	}

	@Test
	public void elArchivoIndicadoresLee5Renglones() {
		assertEquals(5, indicadores.size());
	}

	@Test
	public void elIndicadorIngresoNetoSeAplicaCorrectamenteALasEmpresas() {
		int resultadosEsperados[] = {7000, 3000, 11000};
		Indicador ingresoNeto = archivoIndicadores.buscarIndicador("ingresoNeto");
		int resultados[] = this.resultadosLuegoDeAplicarIndicadorAEmpresas(ingresoNeto);
		assertTrue(Arrays.equals(resultadosEsperados, resultados));
	}
	
	@Test
	public void unIndicadorCompuestoPorIndicadorCuentaYNumeroSeAplicaCorrectamente(){
		Indicador indicadorTres = archivoIndicadores.buscarIndicador("indicadorTres");
		int resultadosEsperados[] = {190000,330000,260000};
		int resultados[] = this.resultadosLuegoDeAplicarIndicadorAEmpresas(indicadorTres);
		assertTrue(Arrays.equals(resultadosEsperados, resultados));
	}
	
	@Test(expected = IndicadorExistenteError.class)
	public void siGuardoDosVecesElMismoIndicadorFalla() {
		usuario.crearIndicador("INGRESONETO = ebitda + 2");
		usuario.crearIndicador("INGRESONETO = ebitda + 2");
	}
	
	@Test
	public void elIndicadorDosEsInaplicableAEmpresaLocaEn2016PorInexistenciaDeCuenta(){
		Empresa empresaLoca = empresasParaIndicadores.get(1);
		Indicador indicadorDos = archivoIndicadores.buscarIndicador("indicadorDos");
		assertFalse(indicadorDos.esAplicableA(empresaLoca, obtenerAnio(2016)));
	}
	
	@Test
	public void soloDosIndicadoresSonAplicablesAEmpresaLocaEn2014(){
		Empresa empresaLoca = empresasParaIndicadores.get(1);
		int cantidadIndicadores = cantidadIndicadoresAplicablesSegunAnio(empresaLoca, obtenerAnio(2014));
		assertEquals(4,cantidadIndicadores);
	}
	
	@Test
	public void laCantidadDeIndicadoresAplicablesAEmpresaReLocaSonCinco(){
		Empresa empresaReLoca = empresasParaIndicadores.get(2);
		int cantidadIndicadores = cantidadIndicadoresAplicablesA(empresaReLoca);
		assertEquals(5,cantidadIndicadores);
	}
	
	@Test
	public void laCantidadDeIndicadoresAplicablesAEmpresaReLocaEn2016SonCuatro(){
		Empresa empresaReLoca = empresasParaIndicadores.get(2);
		empresaReLoca.resultadosParaEstosIndicadores(archivoIndicadores.todosLosIndicadoresAplicablesA(empresaReLoca)).size();
		int cantidadIndicadores = cantidadIndicadoresAplicablesSegunAnio(empresaReLoca, obtenerAnio(2016));
		assertEquals(4,cantidadIndicadores);
	}

	/* ------------------------------- METODOS AUXILIARES  ------------------------------- */
	
	private int cantidadIndicadoresAplicablesA(Empresa empresa) {
		System.out.println(archivoIndicadores.todosLosIndicadoresAplicablesA(empresa));
		Set<Indicador> indicadAplicables = new HashSet<Indicador>(archivoIndicadores.todosLosIndicadoresAplicablesA(empresa));
		return indicadAplicables.size();
	}
	
	private int cantidadIndicadoresAplicablesSegunAnio(Empresa empresa, Year anio) {
		return archivoIndicadores.indicadoresAplicablesA(empresa, anio).size();
	}
	
	private int[] resultadosLuegoDeAplicarIndicadorAEmpresas(Indicador ind){
		int resultados[] = new int[3];
		Empresa miEmpresa = empresasParaIndicadores.get(0);
		Empresa EmpresaLoca = empresasParaIndicadores.get(1);
		Empresa EmpresaReLoca = empresasParaIndicadores.get(2);
		System.out.println(ind);
		System.out.println(miEmpresa);
		System.out.println(Year.of(2015));
		resultados[0] = ind.evaluarEn(miEmpresa, Year.of(2015));
		resultados[1] = ind.evaluarEn(EmpresaLoca, Year.of(2014));
		resultados[2] = ind.evaluarEn(EmpresaReLoca, Year.of(2016));
		
		return resultados;
	}
	
	private Year obtenerAnio(int anio) {
		return Year.of(anio);
	}

}

