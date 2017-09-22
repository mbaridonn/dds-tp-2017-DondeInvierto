package defaultPackage;

import static org.junit.Assert.*;

import java.time.Year;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/*import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;*/
import org.junit.Before;
import org.junit.Test;

import dominio.empresas.ArchivoXLS;
import dominio.empresas.Cuenta;
import dominio.empresas.Empresa;
import dominio.indicadores.RepositorioIndicadores;
import dominio.indicadores.Indicador;
import excepciones.IndicadorExistenteError;
import excepciones.MetodologiaExistenteError;

public class IndicadoresTest {

	List<Indicador> indicadores = new ArrayList<Indicador>();
	ArrayList<Empresa> empresasParaIndicadores;
	RepositorioIndicadores archivoIndicadores;

	@Before
	public void setUp() {
		RepositorioIndicadores.setIndicadoresPredefinidos(new HashSet(Arrays.asList(new String[] { 
				"INGRESONETO = netooperacionescontinuas + netooperacionesdiscontinuas",
				"INDICADORDOS = cuentarara + fds",
				"INDICADORTRES = INGRESONETO * 10 + ebitda",
				"A = 5 / 3", "PRUEBA = ebitda + 5" })));
		archivoIndicadores = RepositorioIndicadores.getInstance();
		ArchivoXLS archivoEjemploIndicadores = new ArchivoXLS("src/test/resources/EjemploIndicadores.xls");
		RepositorioIndicadores archivoIndicadores = RepositorioIndicadores.getInstance();
		archivoEjemploIndicadores.leerEmpresas();
		indicadores.addAll(archivoIndicadores.getIndicadores());
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
		Indicador ingresoNeto = this.getIndicadorLlamado("ingresoNeto");
		int resultados[] = this.resultadosLuegoDeAplicarIndicadorAEmpresas(ingresoNeto);
		assertTrue(Arrays.equals(resultadosEsperados, resultados));
	}
	
	@Test
	public void unIndicadorCompuestoPorIndicadorCuentaYNumeroSeAplicaCorrectamente(){
		Indicador indicadorTres = this.getIndicadorLlamado("indicadorTres");
		int resultadosEsperados[] = {190000,330000,260000};
		int resultados[] = this.resultadosLuegoDeAplicarIndicadorAEmpresas(indicadorTres);
		assertTrue(Arrays.equals(resultadosEsperados, resultados));
	}
	
	@Test(expected = IndicadorExistenteError.class)
	public void siGuardoDosVecesElMismoIndicadorFalla() {
		archivoIndicadores.guardarIndicador("INGRESONETO = ebitda + 2");
		archivoIndicadores.guardarIndicador("INGRESONETO = ebitda + 2");
	}
	
	@Test
	public void elIndicadorDosEsInaplicableAEmpresaLocaEn2016PorInexistenciaDeCuenta(){
		Empresa empresaLoca = empresasParaIndicadores.get(1);
		Indicador indicadorDos = this.getIndicadorLlamado("indicadorDos");
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
		empresaReLoca.resultadosIndicadoresTotales(archivoIndicadores.todosLosIndicadoresAplicablesA(empresaReLoca)).size();
		int cantidadIndicadores = cantidadIndicadoresAplicablesSegunAnio(empresaReLoca, obtenerAnio(2016));
		assertEquals(4,cantidadIndicadores);
	}
	
	@Test
	public void seMuestranCuentasEIndicadoresParaEmpresaReLoca(){
		Empresa empresaReLoca = empresasParaIndicadores.get(0);
		Set<Indicador> indicadoresAplicables = archivoIndicadores.todosLosIndicadoresAplicablesA(empresaReLoca);
		this.mostrarCuentas(empresaReLoca.getCuentas());
		this.mostrarCuentas(empresaReLoca.resultadosIndicadoresTotales(indicadoresAplicables));
		assertTrue(true);
	}

	/* ------------------------------- METODOS AUXILIARES  ------------------------------- */
	
	private int cantidadIndicadoresAplicablesA(Empresa empresa) {
		return archivoIndicadores.todosLosIndicadoresAplicablesA(empresa).size();
	}
	
	private int cantidadIndicadoresAplicablesSegunAnio(Empresa empresa, Year anio) {
		return archivoIndicadores.indicadoresAplicablesA(empresa, anio).size();
	}
	
	private void mostrarCuentas(List<Cuenta> cuentas){
		cuentas.forEach(cuenta -> cuenta.mostrarDatos());
	}
	
	private int[] resultadosLuegoDeAplicarIndicadorAEmpresas(Indicador ind){
		int resultados[] = new int[3];
		Empresa miEmpresa = empresasParaIndicadores.get(0);
		Empresa EmpresaLoca = empresasParaIndicadores.get(1);
		Empresa EmpresaReLoca = empresasParaIndicadores.get(2);
		resultados[0] = ind.evaluarEn(miEmpresa, Year.of(2015));
		resultados[1] = ind.evaluarEn(EmpresaLoca, Year.of(2014));
		resultados[2] = ind.evaluarEn(EmpresaReLoca, Year.of(2016));
		
		return resultados;
	}
	
	private Indicador getIndicadorLlamado(String nombreIndicador){
		return indicadores.stream().filter(ind -> ind.getNombre().equalsIgnoreCase(nombreIndicador)).findFirst().orElseThrow(() -> new NoSePudoObtenerIndicadorError("No se pudo obtener un indicador con ese nombre."));
	}
	
	private Year obtenerAnio(int anio) {
		return Year.of(anio);
	}

}

class NoSePudoObtenerIndicadorError extends RuntimeException{public NoSePudoObtenerIndicadorError(String e){super(e);}}

