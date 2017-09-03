package defaultPackage;

import static org.junit.Assert.*;

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

public class IndicadoresTest {

	List<Indicador> indicadores = new ArrayList<Indicador>();
	ArrayList<Empresa> empresasParaIndicadores;
	RepositorioIndicadores archivoIndicadores;

	@Before
	public void setUp() {
		RepositorioIndicadores.setIndicadoresPredefinidos(new HashSet(Arrays.asList(new String[] { "INGRESONETO = netooperacionescontinuas + netooperacionesdiscontinuas",
				"INDICADORDOS = cuentarara + fds", "INDICADORTRES = INGRESONETO * 10 + ebitda" , "A = 5 / 3", "PRUEBA = ebitda + 5" })));
		archivoIndicadores = RepositorioIndicadores.getInstance();
		ArchivoXLS archivoEjemploIndicadores = new ArchivoXLS("src/test/resources/EjemploIndicadores.xls");
		RepositorioIndicadores archivoIndicadores = RepositorioIndicadores.getInstance();
		archivoEjemploIndicadores.leerEmpresas();
		indicadores.addAll(archivoIndicadores.getIndicadores());
		empresasParaIndicadores = archivoEjemploIndicadores.getEmpresas();
	}
	

	@Test
	public void elArchivoIndicadoresLeeCorrectamente() {
		Set<Indicador> indicadoresEsperados = new HashSet<Indicador>(Arrays.asList(new Indicador[] {
			new Indicador("INGRESONETO"),
			new Indicador("INDICADORDOS"),
			new Indicador("INDICADORTRES"),
			new Indicador("A"),
			new Indicador("PRUEBA")}));
		assertTrue(indicadoresEsperados.equals(indicadores));
	}

	@Test
	public void elArchivoIndicadoresLee5Renglones() {
		assertEquals(5, indicadores.size());
	}

	@Test
	public void elIndicadorIngresoNetoSeAplicaCorrectamenteALasEmpresas() {
		int resultadosEsperados[] = { 7000, 3000, 11000};
		Indicador ingresoNeto = indicadores.get(0);
		int resultados[] = this.resultadosLuegoDeAplicarIndicadorAEmpresas(ingresoNeto);
		assertTrue(Arrays.equals(resultadosEsperados, resultados));
	}
	
	@Test
	public void unIndicadorCompuestoPorIndicadorCuentaYNumeroSeAplicaCorrectamente(){
		Indicador indicadorTres = indicadores.get(2);
		int resultadosEsperados[] = {190000,330000,260000};
		int resultados[] = this.resultadosLuegoDeAplicarIndicadorAEmpresas(indicadorTres);
		assertTrue(Arrays.equals(resultadosEsperados, resultados));
	}
	
	
	@Test
	public void noSePuedeGuardarUnIndicadorConElMismoNombreQueOtro() {
		try {
			archivoIndicadores.guardarIndicador("INGRESONETO = ebitda + 2");
			assertTrue(false);
		} catch (IndicadorExistenteError e) {
			assertTrue(true);
		}
	}
	
	@Test
	public void elINDICADORDOSEsInaplicableAEmpresaLocaEn2016PorInexistenciaDeCuenta(){
		Empresa EmpresaLoca = empresasParaIndicadores.get(1);
		Indicador indicadorDos = indicadores.get(1);
		assertTrue(!indicadorDos.esAplicableA(EmpresaLoca, "2016"));
	}
	
	@Test
	public void soloDosIndicadoresSonAplicablesAEmpresaLoca2014(){
		Empresa empresaLoca = empresasParaIndicadores.get(1);
		assertEquals(4,archivoIndicadores.indicadoresAplicablesA(empresaLoca, "2014").size());
	}
	
	/*@Test
	public void elArchivoIndicadoresNoDuplicaLaCantidadSiLeeDosVeces(){
		archivoIndicadores.leerIndicadores();
		int cantidadOriginal = archivoIndicadores.getIndicadores().size();
		archivoIndicadores.leerIndicadores();
		assertEquals(cantidadOriginal,archivoIndicadores.getIndicadores().size());
	}*/
	
	@Test
	public void laCantidadDeIndicadoresAplicablesAEmpresaReLocaSonCinco(){
		Empresa empresaReLoca = empresasParaIndicadores.get(2);
		assertEquals(5,archivoIndicadores.todosLosIndicadoresAplicablesA(empresaReLoca).size());
	}
	
	@Test
	public void laCantidadDeIndicadoresAplicablesAEmpresaReLocaEn2016SonCuatro(){
		Empresa empresaReLoca = empresasParaIndicadores.get(2);
		empresaReLoca.resultadosIndicadoresTotales(archivoIndicadores.todosLosIndicadoresAplicablesA(empresaReLoca)).size();
		assertEquals(4,archivoIndicadores.indicadoresAplicablesA(empresaReLoca, "2016").size());
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
	
	private void mostrarCuentas(List<Cuenta> cuentas){
		cuentas.forEach(cuenta -> cuenta.mostrarDatos());
	}
	
	private int[] resultadosLuegoDeAplicarIndicadorAEmpresas(Indicador ind){
		int resultados[] = new int[3];
		Empresa miEmpresa = empresasParaIndicadores.get(0);
		Empresa EmpresaLoca = empresasParaIndicadores.get(1);
		Empresa EmpresaReLoca = empresasParaIndicadores.get(2);
		resultados[0] = ind.evaluarEn(miEmpresa, "2015");
		resultados[1] = ind.evaluarEn(EmpresaLoca, "2014");
		resultados[2] = ind.evaluarEn(EmpresaReLoca, "2016");
		
		return resultados;
	}

	/*private boolean sonLosMismosIndicadores(Set<Indicador> unosIndicadores, Set<Indicador> otrosIndicadores) {
		for (int i = 0; i < otrosIndicadores.size(); i++) {
			if (!this.sonLosMismosIndicadores(unosIndicadores.get(i), otrosIndicadores.get(i))) {
				return false;
			}
		}
		return true && unosIndicadores.size() == otrosIndicadores.size();
	}*/

}

