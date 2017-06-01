package defaultPackage;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;

/*import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;*/
import org.junit.Before;
import org.junit.Test;

import dominio.*;

public class EmpresaTest {

	Empresa empresa;
	ArrayList<Cuenta> cuentas;
	ArrayList<Empresa> empresasLibroDos;
	ArrayList<Indicador> indicadores;
	ArrayList<Empresa> empresasParaIndicadores;
	ArchivoIndicadores archivoIndicadores = ArchivoIndicadores.getInstance();

	@Before
	public void setUp() {
		ArchivoXLS archivoLibroUno = new ArchivoXLS("src/test/resources/LibroPrueba.xls");
		ArchivoXLS archivoLibroDos = new ArchivoXLS("src/test/resources/LibroPruebaEmpresas.xls");
		ArchivoXLS archivoEjemploIndicadores = new ArchivoXLS("src/test/resources/EjemploIndicadores.xls");
		ArchivoIndicadores archivoIndicadores = ArchivoIndicadores.getInstance();
		archivoIndicadores.cambiarPath("src/test/resources/indicadoresPredefinidos.txt");
		archivoLibroUno.leerEmpresas();
		archivoLibroDos.leerEmpresas();
		archivoEjemploIndicadores.leerEmpresas();
		archivoIndicadores.leerIndicadores();
		indicadores = archivoIndicadores.getIndicadores();
		empresasLibroDos = archivoLibroDos.getEmpresas();
		empresa = archivoLibroUno.getEmpresas().get(0);
		empresasParaIndicadores = archivoEjemploIndicadores.getEmpresas();
		cuentas = empresa.getCuentas();
	}

	/*
	 * @Test public void ejemploTest(){ assertEquals(expected, actual);
	 * assertFalse(algoBool); assertTrue(algoBool); }
	 */

	@Test
	public void elLectorXLSPuedeLeerUnArchivoConExtensionXLS() {
		ArchivoXLS archivo = new ArchivoXLS("src/test/resources/LibroPruebaEmpresas.xls");
		assertTrue(archivo.puedeLeerArchivo());
	}

	@Test
	public void elLectorCSVPuedeLeerUnArchivoConExtensionCSV() {
		ArchivoCSV archivo = new ArchivoCSV("src/test/resources/LibroPruebaEmpresas.csv");
		assertTrue(archivo.puedeLeerArchivo());
	}

	@Test
	public void elAnioDeLaUltimaCuentaEsDeLaPrimeraEmpresa2014() {
		assertEquals(cuentas.get(cuentas.size() - 1).getAnio(), "2014");
	}

	@Test
	public void elValorDeLaCuentaFDSDel2017Es158960() {
		assertEquals(empresa.getValorCuenta("FDS", "2017"), 158960);
	}

	@Test
	public void cargaCuentasCorrectamente() {
		ArrayList<Cuenta> cuentasEsperadas = new ArrayList<Cuenta>() {
			{
				add(new Cuenta("2017", "EBITDA", 35000));
				add(new Cuenta("2017", "FDS", 158960));
				add(new Cuenta("2016", "FDS", 144000));
				add(new Cuenta("2015", "EBITDA", 120000));
				add(new Cuenta("2015", "Free Clash Flow", 150000));
				add(new Cuenta("2014", "EBITDA", 260000));
				add(new Cuenta("2014", "FDS", 360000));
			}
		};
		assertTrue(this.sonLasMismasCuentas(cuentasEsperadas, cuentas));
	}

	@Test
	public void ambosLectoresDevuelvenLoMismo() {
		ArchivoXLS archivoXLS = new ArchivoXLS("src/test/resources/LibroPruebaEmpresas.xls");
		ArchivoCSV archivoCSV = new ArchivoCSV("src/test/resources/LibroPruebaEmpresas.csv");
		archivoXLS.leerEmpresas();
		archivoCSV.leerEmpresas();
		assertTrue(this.tienenLasMismasEmpresas(archivoXLS.getEmpresas(), archivoCSV.getEmpresas()));
	}

	@Test
	public void hayTresEmpresasCargadas() {
		assertEquals(3, empresasLibroDos.size());
	}

	@Test
	public void elNombreDeLaSegundaEmpresaEsEmpresaLoca() {
		assertEquals("EmpresaLoca", empresasLibroDos.get(1).getNombre());
	}

	@Test
	public void laPrimerEmpresaTieneSieteCuentas() {
		assertEquals(7, empresasLibroDos.get(0).cantidadDeCuentasQuePosee());
	}

	@Test
	public void elArchivoXLSNoSobreCargaEmpresasAnteVariasEjecuciones() {
		ArchivoXLS archivo = new ArchivoXLS("src/test/resources/LibroPruebaEmpresas.xls");
		archivo.leerEmpresas();
		archivo.leerEmpresas();
		assertEquals(3, archivo.getEmpresas().size());
	}

	@Test
	public void elArchivoCSVNoSobreCargaEmpresasAnteVariasEjecuciones() {
		ArchivoCSV archivo = new ArchivoCSV("src/test/resources/LibroPruebaEmpresas.csv");
		archivo.leerEmpresas();
		archivo.leerEmpresas();
		assertEquals(3, archivo.getEmpresas().size());
	}

	@Test
	public void elArchivoIndicadoresLeeCorrectamente() {
		ArrayList<Indicador> indicadoresEsperados = new ArrayList<Indicador>() {
			{
				add(new Indicador("INGRESONETO"));
				add(new Indicador("INDICADORDOS"));
				add(new Indicador("INDICADORTRES"));
			}
		};
		assertTrue(this.sonLosMismosIndicadores(indicadoresEsperados, indicadores));
	}

	@Test
	public void elArchivoIndicadoresLee3Renglones() {
		assertEquals(3, indicadores.size());
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
		assertEquals(2,archivoIndicadores.indicadoresAplicablesA(empresaLoca, "2014").size());
	}
	
	@Test
	public void elArchivoIndicadoresNoDuplicaLaCantidadSiLeeDosVeces(){
		archivoIndicadores.leerIndicadores();
		int cantidadOriginal = archivoIndicadores.getIndicadores().size();
		archivoIndicadores.leerIndicadores();
		assertEquals(cantidadOriginal,archivoIndicadores.getIndicadores().size());
	}
	
	@Test
	public void laCantidadDeIndicadoresAplicablesAEmpresaReLocaSonTres(){
		Empresa empresaReLoca = empresasParaIndicadores.get(2);
		assertEquals(3,archivoIndicadores.todosLosIndicadoresAplicablesA(empresaReLoca).size());
	}
	
	@Test
	public void laCantidadDeIndicadoresAplicablesAEmpresaReLocaEn2016SonDos(){
		Empresa empresaReLoca = empresasParaIndicadores.get(2);
		empresaReLoca.resultadosIndicadoresTotales(archivoIndicadores.todosLosIndicadoresAplicablesA(empresaReLoca)).size();
		assertEquals(2,archivoIndicadores.indicadoresAplicablesA(empresaReLoca, "2016").size());
	}
	
	@Test
	public void elArchivoIndicadoresEliminaCorrectamente() {
		archivoIndicadores.escribirIndicador("HOLA = algo");
		archivoIndicadores.leerIndicadores();
		int cantidadAntesDeBorrar = archivoIndicadores.getIndicadores().size();
		archivoIndicadores.borrarIndicador("HOLA");
		archivoIndicadores.leerIndicadores();
		assertEquals(cantidadAntesDeBorrar - 1, archivoIndicadores.getIndicadores().size());
	}

	/* ------------------------------- METODOS AUXILIARES  ------------------------------- */
	
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

	private boolean tienenLasMismasEmpresas(ArrayList<Empresa> primerListaEmpresas,
			ArrayList<Empresa> segundaListaEmpresas) {
		for (int i = 0; i < primerListaEmpresas.size(); i++) {
			if (!this.sonLasMismasEmpresas(primerListaEmpresas.get(i), segundaListaEmpresas.get(i))) {
				return false;
			}
		}
		return true;
	}

	private boolean sonLasMismasEmpresas(Empresa unaEmpresa, Empresa otraEmpresa) {
		return unaEmpresa.seLlama(otraEmpresa.getNombre())
				&& this.sonLasMismasCuentas(unaEmpresa.getCuentas(), otraEmpresa.getCuentas());
	}

	private boolean sonLasMismasCuentas(ArrayList<Cuenta> cuentasEsperadas, ArrayList<Cuenta> cuentas) {
		boolean resultado = true;
		for (int i = 0; i < cuentasEsperadas.size(); i++) {
			resultado = resultado && this.cuentasSonIguales(cuentas.get(i), cuentasEsperadas.get(i));
		}
		return resultado && cuentasEsperadas.size() == cuentas.size();
	}

	private boolean cuentasSonIguales(Cuenta cuenta, Cuenta cuentaEsperada) {
		return cuenta.getAnio().equals(cuentaEsperada.getAnio())
				&& cuenta.getTipoCuenta().equals(cuentaEsperada.getTipoCuenta())
				&& cuenta.getValor() == cuentaEsperada.getValor();
	}

	private boolean sonLosMismosIndicadores(ArrayList<Indicador> unosIndicadores,
			ArrayList<Indicador> otrosIndicadores) {
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
