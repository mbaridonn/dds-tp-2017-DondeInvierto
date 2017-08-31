package defaultPackage;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
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
import dominio.indicadores.ArchivoIndicadores;
import dominio.indicadores.Indicador;

public class IndicadoresTest {

	ArrayList<Indicador> indicadores;
	ArrayList<Empresa> empresasParaIndicadores;
	ArchivoIndicadores archivoIndicadores = ArchivoIndicadores.getInstance();

	@Before
	public void setUp() {
		ArchivoXLS archivoEjemploIndicadores = new ArchivoXLS("src/test/resources/EjemploIndicadores.xls");
		ArchivoIndicadores archivoIndicadores = ArchivoIndicadores.getInstance();
		archivoIndicadores.cambiarPath("src/main/resources/Indicadores.txt");
		archivoEjemploIndicadores.leerEmpresas();
		archivoIndicadores.leerIndicadores();
		indicadores = archivoIndicadores.getIndicadores();
		empresasParaIndicadores = archivoEjemploIndicadores.getEmpresas();

	}
	

	@Test
	public void elArchivoIndicadoresLeeCorrectamente() {
		ArrayList<Indicador> indicadoresEsperados = new ArrayList<Indicador>() {
			{
				add(new Indicador("INGRESONETO"));
				add(new Indicador("INDICADORDOS"));
				add(new Indicador("INDICADORTRES"));
				add(new Indicador("A"));
				add(new Indicador("PRUEBA"));
			}
		};
		assertTrue(this.sonLosMismosIndicadores(indicadoresEsperados, indicadores));
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
		indicadores = archivoIndicadores.getIndicadores();
		Indicador indicadorTres = indicadores.get(2);
		int resultadosEsperados[] = {190000,330000,260000};
		int resultados[] = this.resultadosLuegoDeAplicarIndicadorAEmpresas(indicadorTres);
		assertTrue(Arrays.equals(resultadosEsperados, resultados));
	}
	
	
	@Test
	public void noSePuedeEscribirUnIndicadorConElMismoNombreQueOtro() {
		try {
			archivoIndicadores.escribirIndicador("INGRESONETO = ebitda + 2");
			assertTrue(false);
		} catch (RuntimeException e) {
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
	
	@Test
	public void elArchivoIndicadoresNoDuplicaLaCantidadSiLeeDosVeces(){
		archivoIndicadores.leerIndicadores();
		int cantidadOriginal = archivoIndicadores.getIndicadores().size();
		archivoIndicadores.leerIndicadores();
		assertEquals(cantidadOriginal,archivoIndicadores.getIndicadores().size());
	}
	
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
	
	/*@Test
	public void elArchivoIndicadoresEliminaCorrectamente() {
		archivoIndicadores.escribirIndicador("HOLA = algo");
		archivoIndicadores.leerIndicadores();
		int cantidadAntesDeBorrar = archivoIndicadores.getIndicadores().size();
		archivoIndicadores.borrarIndicador("HOLA");
		archivoIndicadores.leerIndicadores();
		assertEquals(cantidadAntesDeBorrar - 1, archivoIndicadores.getIndicadores().size());
	}*/

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

	private boolean sonLosMismosIndicadores(ArrayList<Indicador> unosIndicadores, ArrayList<Indicador> otrosIndicadores) {
		for (int i = 0; i < otrosIndicadores.size(); i++) {
			if (!this.sonLosMismosIndicadores(unosIndicadores.get(i), otrosIndicadores.get(i))) {
				return false;
			}
		}
		return true && unosIndicadores.size() == otrosIndicadores.size();
	}

	private boolean sonLosMismosIndicadores(Indicador unIndicador, Indicador otroIndicador) {
		return unIndicador.getNombre().equals(otroIndicador.getNombre());
	}


}

