package defaultPackage;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import dominio.ArchivoXLS;
import dominio.Empresa;
import dominio.indicadores.ArchivoIndicadores;
import dominio.indicadores.Indicador;
import dominio.metodologias.*;
import excepciones.NoExisteCuentaError;

public class MetodologiaTest {
	
	private ArrayList<Indicador> indicadores;
	private ArrayList<Empresa> empresasParaIndicadores;
	private ArrayList<Empresa> empresasParaComparacionConMetodologias;
	
	@Before
	public void setUp() {
		ArchivoXLS archivoEjemploIndicadores = new ArchivoXLS("src/test/resources/EjemploIndicadores.xls");
		ArchivoXLS archivoEjemploMetodologias = new ArchivoXLS("src/test/resources/EjemploMetodologias.xls");
		ArchivoIndicadores archivoIndicadores = ArchivoIndicadores.getInstance();
		archivoIndicadores.cambiarPath("src/main/resources/Indicadores.txt");
		archivoEjemploIndicadores.leerEmpresas();
		archivoEjemploMetodologias.leerEmpresas();
		archivoIndicadores.leerIndicadores();
		indicadores = archivoIndicadores.getIndicadores();
		empresasParaIndicadores = archivoEjemploIndicadores.getEmpresas();
		empresasParaComparacionConMetodologias = archivoEjemploMetodologias.getEmpresas();
		//Ver formas de testear métodos que usan fecha actual (QUIZAS CONVENGA USAR INYECCION DE DEPENDENCIAS) (!!!)
	}
	
	@Test
	public void miEmpresaEsMasAntiguaQueEmpresaReLoca() {
		Empresa miEmpresa = empresasParaIndicadores.get(0);
		Empresa empresaReLoca = empresasParaIndicadores.get(2);
		assertTrue(new CondicionPrioritaria(new OperandoCondicion(new Ultimo(), new Antiguedad(), 1), new Mayor()).esMejorQue(miEmpresa, empresaReLoca));
	}
	
	//FALTA TEST DE CondPriConsistencia
	
	@Test
	public void miEmpresaCumpleCondTaxAntiguedadMenorA10() {
		Empresa miEmpresa = empresasParaIndicadores.get(0);
		assertTrue(new CondicionTaxativa(new OperandoCondicion(new Ultimo(), new Antiguedad(), 1), new Menor(), 10).laCumple(miEmpresa));
	}
	
	@Test
	public void empresaReLocaNoCumpleCondTaxAntiguedadMayorA3() {
		Empresa empresaReLoca = empresasParaIndicadores.get(2);
		assertFalse(new CondicionTaxativa(new OperandoCondicion(new Ultimo(), new Antiguedad(), 1), new Mayor(), 3).laCumple(empresaReLoca));
	}
	
	@Test
	public void empresaReLocaCumpleCondTaxAntiguedadIgualA1(){
		Empresa miEmpresa = empresasParaIndicadores.get(2);
		assertTrue(new CondicionTaxativa(new OperandoCondicion(new Ultimo(), new Antiguedad(), 1), new Igual(), 1).laCumple(miEmpresa));
	}
	
	@Test
	public void empresaReLocaNOCumpleCondTaxIndicadorINDICADORDOSMayorA360000() {
		Empresa empresaReLoca = empresasParaIndicadores.get(2);
		Indicador indicadorDos = indicadores.get(1);
		OperandoCondicion operando = new OperandoCondicion(new Ultimo(),indicadorDos,1);
		CondicionTaxativa cond = new CondicionTaxativa(operando, new Mayor(), 360000);
		assertFalse(cond.laCumple(empresaReLoca));
	}
	
	@Test
	public void empresaReLocaNOCumpleCondTaxIndicadorINDICADORDOSMenorA200000(){
		Empresa empresaReLoca = empresasParaIndicadores.get(2);
		Indicador indicadorDos = indicadores.get(1);
		OperandoCondicion operando = new OperandoCondicion(new Ultimo(),indicadorDos,1);
		CondicionTaxativa cond = new CondicionTaxativa(operando, new Menor(), 200000);
		assertFalse(cond.laCumple(empresaReLoca));
	}
	
	@Test
	public void empresaReLocaCumpleCondTaxIndicadorINDICADORDOSIgualA30000(){
		Empresa empresaReLoca = empresasParaIndicadores.get(2);
		Indicador indicadorDos = indicadores.get(1);
		OperandoCondicion operando = new OperandoCondicion(new Ultimo(),indicadorDos,1);
		CondicionTaxativa cond = new CondicionTaxativa(operando, new Igual(), 300000);
		assertTrue(cond.laCumple(empresaReLoca));
	}
	
	@Test
	public void miEmpresaNOCumpleCondTaxIndicadorINGRESONETOMayorA20000EnUltimoAnio() {
		Empresa miEmpresa = empresasParaIndicadores.get(0);
		Indicador ingresoNeto = indicadores.get(0);
		CondicionTaxativa cond = new CondicionTaxativa(new OperandoCondicion(new Ultimo(),ingresoNeto,1), new Mayor(), 20000);
		assertFalse(cond.laCumple(miEmpresa));
	}
	
	@Test
	public void miEmpresaNOCumpleCondTaxIndicadorINGRESONETOMenorA8000EnUltimoAnio(){
		Empresa miEmpresa = empresasParaIndicadores.get(0);
		Indicador ingresoNeto = indicadores.get(0);
		CondicionTaxativa cond = new CondicionTaxativa(new OperandoCondicion(new Ultimo(),ingresoNeto,1), new Menor(), 8000);
		assertFalse(cond.laCumple(miEmpresa));
	}
	
	@Test
	public void miEmpresaCumpleCondTaxIndicadorINGRESONETOIgualA17000EnUltimoAnio(){
		Empresa miEmpresa = empresasParaIndicadores.get(0);
		Indicador ingresoNeto = indicadores.get(0);
		CondicionTaxativa cond = new CondicionTaxativa(new OperandoCondicion(new Ultimo(),ingresoNeto,1), new Igual(), 17000);
		assertTrue(cond.laCumple(miEmpresa));
	}
	
	@Test
	public void miEmpresaNOCumpleCondTaxIndicadorINGRESONETOConSumatoriaMayorA30000EnUltimosDosAnios() {
		Empresa miEmpresa = empresasParaIndicadores.get(0);
		Indicador ingresoNeto = indicadores.get(0);
		CondicionTaxativa cond = new CondicionTaxativa(new OperandoCondicion(new Sumatoria(),ingresoNeto,2), new Mayor(), 30000);
		assertFalse(cond.laCumple(miEmpresa));
	}
	
	@Test
	public void miEmpresaNOCumpleCondTaxIndicadorINGRESONETOConSumatoriaMenorA15000EnUltimosDosAnios(){
		Empresa miEmpresa = empresasParaIndicadores.get(0);
		Indicador ingresoNeto = indicadores.get(0);
		CondicionTaxativa cond = new CondicionTaxativa(new OperandoCondicion(new Sumatoria(),ingresoNeto,2), new Menor(), 15000);
		assertFalse(cond.laCumple(miEmpresa));
	}
	
	@Test
	public void miEmpresaCumpleCondTaxIndicadorINGRESONETOConSumatoriaIgualA28000EnUltimosDosAnios(){
		Empresa miEmpresa = empresasParaIndicadores.get(0);
		Indicador ingresoNeto = indicadores.get(0);
		CondicionTaxativa cond = new CondicionTaxativa(new OperandoCondicion(new Sumatoria(),ingresoNeto,2), new Igual(), 28000);
		assertTrue(cond.laCumple(miEmpresa));
	}
	
	@Test
	public void miEmpresaNOCumpleCondTaxIndicadorINGRESONETOConPromedioMayorA15000EnUltimosDosAnios() {
		Empresa miEmpresa = empresasParaIndicadores.get(0);
		Indicador ingresoNeto = indicadores.get(0);
		CondicionTaxativa cond = new CondicionTaxativa(new OperandoCondicion(new Promedio(),ingresoNeto,2), new Mayor(), 15000);
		assertFalse(cond.laCumple(miEmpresa));
	}
	
	@Test
	public void miEmpresaNOCumpleCondTaxIndicadorINGRESONETOConPromedioMenorA10000EnUltimosDosAnios(){
		Empresa miEmpresa = empresasParaIndicadores.get(0);
		Indicador ingresoNeto = indicadores.get(0);
		CondicionTaxativa cond = new CondicionTaxativa(new OperandoCondicion(new Promedio(),ingresoNeto,2), new Menor(), 10000);
		assertFalse(cond.laCumple(miEmpresa));
	}
	
	@Test
	public void miEmpresaCumpleCondTaxIndicadorINGRESONETOConPromedioIgualA14000EnUltimosDosAnios(){
		Empresa miEmpresa = empresasParaIndicadores.get(0);
		Indicador ingresoNeto = indicadores.get(0);
		CondicionTaxativa cond = new CondicionTaxativa(new OperandoCondicion(new Promedio(),ingresoNeto,2), new Igual(), 14000);
		assertTrue(cond.laCumple(miEmpresa));
	}
	
	@Test
	public void miEmpresaNOCumpleCondTaxIndicadorINGRESONETOConPromedioMayorA10000EnUltimosCuatroAnios() {
		Empresa miEmpresa = empresasParaIndicadores.get(0);
		Indicador ingresoNeto = indicadores.get(0);
		CondicionTaxativa cond = new CondicionTaxativa(new OperandoCondicion(new Promedio(),ingresoNeto,4), new Mayor(), 10000);
		assertFalse(cond.laCumple(miEmpresa));
	}
	
	@Test
	public void miEmpresaNOCumpleCondTaxIndicadorINGRESONETOConPromedioMenorA8000EnUltimosCuatroAnios(){
		Empresa miEmpresa = empresasParaIndicadores.get(0);
		Indicador ingresoNeto = indicadores.get(0);
		CondicionTaxativa cond = new CondicionTaxativa(new OperandoCondicion(new Promedio(),ingresoNeto,4), new Menor(), 8000);
		assertFalse(cond.laCumple(miEmpresa));
	}
	
	@Test
	public void miEmpresaCumpleCondTaxIndicadorINGRESONETOConPromedioIgualA9500EnUltimosCuatroAnios(){
		Empresa miEmpresa = empresasParaIndicadores.get(0);
		Indicador ingresoNeto = indicadores.get(0);
		CondicionTaxativa cond = new CondicionTaxativa(new OperandoCondicion(new Promedio(),ingresoNeto,4), new Igual(), 9500);
		assertTrue(cond.laCumple(miEmpresa));
	}
	
	@Test
	public void miEmpresaNOCumpleCondTaxIndicadorINGRESONETOConMedianaMayorA15000EnUltimosTresAnios() { // Mediana con n impar
		Empresa miEmpresa = empresasParaIndicadores.get(0);
		Indicador ingresoNeto = indicadores.get(0);
		CondicionTaxativa cond = new CondicionTaxativa(new OperandoCondicion(new Mediana(),ingresoNeto,3), new Mayor(), 15000);
		assertFalse(cond.laCumple(miEmpresa));
	}
	
	@Test
	public void miEmpresaNOCumpleCondTaxIndicadorINGRESONETOConMedianaMenorA10000EnUltimosTresAnios(){
		Empresa miEmpresa = empresasParaIndicadores.get(0);
		Indicador ingresoNeto = indicadores.get(0);
		CondicionTaxativa cond = new CondicionTaxativa(new OperandoCondicion(new Mediana(),ingresoNeto,3), new Menor(), 10000);
		assertFalse(cond.laCumple(miEmpresa));
	}
	
	@Test
	public void miEmpresaCumpleCondTaxIndicadorINGRESONETOConMedianaIgualA11000EnUltimosTresAnios(){
		Empresa miEmpresa = empresasParaIndicadores.get(0);
		Indicador ingresoNeto = indicadores.get(0);
		CondicionTaxativa cond = new CondicionTaxativa(new OperandoCondicion(new Mediana(),ingresoNeto,3), new Igual(), 11000);
		assertTrue(cond.laCumple(miEmpresa));
	}
	
	@Test
	public void miEmpresaNOCumpleCondTaxIndicadorINGRESONETOConMedianaMayorA10000EnUltimosCuatroAnios() { // Mediana con n impar
		Empresa miEmpresa = empresasParaIndicadores.get(0);
		Indicador ingresoNeto = indicadores.get(0);
		CondicionTaxativa cond = new CondicionTaxativa(new OperandoCondicion(new Mediana(),ingresoNeto,4), new Mayor(), 10000);
		assertFalse(cond.laCumple(miEmpresa));
	}
	
	@Test
	public void miEmpresaNOCumpleCondTaxIndicadorINGRESONETOConMedianaMenorA7000EnUltimosCuatroAnios(){
		Empresa miEmpresa = empresasParaIndicadores.get(0);
		Indicador ingresoNeto = indicadores.get(0);
		CondicionTaxativa cond = new CondicionTaxativa(new OperandoCondicion(new Mediana(),ingresoNeto,4), new Menor(), 7000);
		assertFalse(cond.laCumple(miEmpresa));
	}
	
	@Test
	public void miEmpresaCumpleCondTaxIndicadorINGRESONETOConMedianaIgualA9000EnUltimosCuatroAnios(){
		Empresa miEmpresa = empresasParaIndicadores.get(0);
		Indicador ingresoNeto = indicadores.get(0);
		CondicionTaxativa cond = new CondicionTaxativa(new OperandoCondicion(new Mediana(),ingresoNeto,4), new Igual(), 9000);
		assertTrue(cond.laCumple(miEmpresa));
	}
	
	@Test
	public void miEmpresaNOCumpleCondTaxIndicadorINGRESONETOConVariacionMayorA15000EnUltimosCuatroAnios() { // Mediana con n impar
		Empresa miEmpresa = empresasParaIndicadores.get(0);
		Indicador ingresoNeto = indicadores.get(0);
		CondicionTaxativa cond = new CondicionTaxativa(new OperandoCondicion(new Variacion(),ingresoNeto,4), new Mayor(), 15000);
		assertFalse(cond.laCumple(miEmpresa));
	}
	
	@Test
	public void miEmpresaNOCumpleCondTaxIndicadorINGRESONETOConVariacionMenorA13000EnUltimosCuatroAnios(){
		Empresa miEmpresa = empresasParaIndicadores.get(0);
		Indicador ingresoNeto = indicadores.get(0);
		CondicionTaxativa cond = new CondicionTaxativa(new OperandoCondicion(new Variacion(),ingresoNeto,4), new Menor(), 13000);
		assertFalse(cond.laCumple(miEmpresa));
	}
	
	@Test
	public void miEmpresaCumpleCondTaxIndicadorINGRESONETOConVariacionIgualA14000EnUltimosCuatroAnios(){
		Empresa miEmpresa = empresasParaIndicadores.get(0);
		Indicador ingresoNeto = indicadores.get(0);
		CondicionTaxativa cond = new CondicionTaxativa(new OperandoCondicion(new Variacion(),ingresoNeto,4), new Igual(), 14000);
		assertTrue(cond.laCumple(miEmpresa));
	}
	
	@Test
	public void empresaReLocaLanzaErrorAlQuererCumplirCondTaxIndicadorINGRESONETOConVariacionIgualA14000EnUltimosTresAniosPorFaltaDeCuentas(){
		Empresa empresaReLoca = empresasParaIndicadores.get(2);
		Indicador ingresoNeto = indicadores.get(0);
		boolean huboError = false;
		try{
			CondicionTaxativa cond = new CondicionTaxativa(new OperandoCondicion(new Variacion(),ingresoNeto,3), new Igual(), 14000);
			cond.laCumple(empresaReLoca);
		}catch(NoExisteCuentaError e){
			huboError = true;
		}
		assertTrue(huboError);
	}
	
	@Test
	public void miEmpresaLanzaErrorAlQuererCumplirCondTaxIndicadorINGRESONETOConSumatoriaIgualA14000EnUltimosCincoAniosPorFaltaDeCuentas(){
		Empresa miEmpresa = empresasParaIndicadores.get(0);
		Indicador ingresoNeto = indicadores.get(0);
		boolean huboError = false;
		try{
			CondicionTaxativa cond = new CondicionTaxativa(new OperandoCondicion(new Variacion(),ingresoNeto,5), new Igual(), 50000);
			cond.laCumple(miEmpresa);
		}catch(RuntimeException e){
			huboError = true;
		}
		assertTrue(huboError);
	}
	
	@Test
	public void miEmpresaEsMejorQueEmpresaReLocaConINGRESONETOEnUltimoAnio(){
		Empresa miEmpresa = empresasParaIndicadores.get(0);
		Empresa empresaReLoca = empresasParaIndicadores.get(2);
		Indicador ingresoNeto = indicadores.get(0);
		CondicionPrioritaria cond = new CondicionPrioritaria(new OperandoCondicion(new Ultimo(),ingresoNeto,1), new Mayor());
		assertTrue(cond.esMejorQue(miEmpresa, empresaReLoca));
	}
	
	@Test
	public void empresaReLocaEsMejorQuemiEmpresaConINDICADORDOSConSumatoriaEnUltimosDosAnios(){
		Empresa miEmpresa = empresasParaComparacionConMetodologias.get(0);
		Empresa empresaReLoca = empresasParaComparacionConMetodologias.get(2);
		Indicador indicadorDos = indicadores.get(1);
		CondicionPrioritaria cond = new CondicionPrioritaria(new OperandoCondicion(new Sumatoria(),indicadorDos,1), new Mayor());
		assertTrue(cond.esMejorQue(empresaReLoca, miEmpresa));
	}
	
	@Test
	public void empresaReLocaEsMejorQuemiEmpresaConINDICADORDOSConPromedioEnUltimosDosAnios(){
		Empresa miEmpresa = empresasParaComparacionConMetodologias.get(0);
		Empresa empresaReLoca = empresasParaComparacionConMetodologias.get(2);
		Indicador indicadorDos = indicadores.get(1);
		CondicionPrioritaria cond = new CondicionPrioritaria(new OperandoCondicion(new Promedio(),indicadorDos,1), new Mayor());
		assertTrue(cond.esMejorQue(empresaReLoca, miEmpresa));
	}
	
	@Test
	public void empresaReLocaEsMejorQuemiEmpresaYmiEmpresaEsMejorQueEmpresaLocaConINDICADORDOSConPromedioEnUltimosDosAnios(){
		Empresa miEmpresa = empresasParaComparacionConMetodologias.get(0);
		Empresa empresaReLoca = empresasParaComparacionConMetodologias.get(2);
		Empresa empresaLoca = empresasParaComparacionConMetodologias.get(1); 
		Indicador indicadorDos = indicadores.get(1);
		CondicionPrioritaria cond = new CondicionPrioritaria(new OperandoCondicion(new Promedio(),indicadorDos,1), new Mayor());
		assertTrue(cond.esMejorQue(empresaReLoca, miEmpresa) && cond.esMejorQue(miEmpresa, empresaLoca));
	}
	
	@Test
	public void miEmpresaEsMejorQueEmpresaReLocaYEmpresaReLocaEsMejorQueEmpresaLocaConINGRESONETOConSumatoriaEnUltimosDosAnios(){
		Empresa miEmpresa = empresasParaComparacionConMetodologias.get(0);
		Empresa empresaReLoca = empresasParaComparacionConMetodologias.get(2);
		Empresa empresaLoca = empresasParaComparacionConMetodologias.get(1); 
		Indicador ingresoNeto = indicadores.get(0);
		CondicionPrioritaria cond = new CondicionPrioritaria(new OperandoCondicion(new Sumatoria(),ingresoNeto,1), new Mayor());
		assertTrue(cond.esMejorQue(miEmpresa, empresaReLoca) && cond.esMejorQue(empresaReLoca, empresaLoca));
	}
	
	@Test
	public void miEmpresaEsMejorQueEmpresaReLocaYEmpresaReLocaEsMejorQueEmpresaLocaConINGRESONETOConVariacionEnUltimosDosAnios(){
		Empresa miEmpresa = empresasParaComparacionConMetodologias.get(0);
		Empresa empresaReLoca = empresasParaComparacionConMetodologias.get(2);
		Empresa empresaLoca = empresasParaComparacionConMetodologias.get(1); 
		Indicador ingresoNeto = indicadores.get(0);
		CondicionPrioritaria cond = new CondicionPrioritaria(new OperandoCondicion(new Variacion(),ingresoNeto,2), new Mayor());
		assertTrue(cond.esMejorQue(miEmpresa, empresaReLoca) && cond.esMejorQue(empresaReLoca, empresaLoca));
	}
	
	@Test
	public void miEmpresaEsMejorQueEmpresaReLocaYEmpresaReLocaEsMejorQueEmpresaLocaConINGRESONETOConVariacionConsiderandoLaMenorEnUltimosDosAnios(){
		Empresa miEmpresa = empresasParaComparacionConMetodologias.get(0);
		Empresa empresaReLoca = empresasParaComparacionConMetodologias.get(2);
		Empresa empresaLoca = empresasParaComparacionConMetodologias.get(1); 
		Indicador ingresoNeto = indicadores.get(0);
		CondicionPrioritaria cond = new CondicionPrioritaria(new OperandoCondicion(new Variacion(),ingresoNeto,2), new Menor());
		assertTrue(cond.esMejorQue(empresaLoca, empresaReLoca) && cond.esMejorQue(empresaReLoca, miEmpresa));
	}
	
	@Test
	public void hayErrorAlQuererCompararMiEmpresaConEmpresaLocaPorINGRESONETOConSumatoriaEnUltimosTresAniosPorFaltaDeCuentas(){
		Empresa miEmpresa = empresasParaComparacionConMetodologias.get(0);
		Empresa empresaLoca = empresasParaComparacionConMetodologias.get(1);
		Indicador ingresoNeto = indicadores.get(0);
		boolean huboError = false;
		try{
			CondicionPrioritaria cond = new CondicionPrioritaria(new OperandoCondicion(new Sumatoria(),ingresoNeto,3), new Mayor());
			cond.esMejorQue(miEmpresa, empresaLoca);
		}catch(NoExisteCuentaError e){
			huboError = true;
		}
		assertTrue(huboError);
	}
	
	@Test
	public void soloDosEmpresasCumplenINGRESONETOConPromedioMayorA10000EnUltimos2Anios(){
		Metodologia metodologia = new Metodologia("Una Metodologia");
		Indicador ingresoNeto = indicadores.get(0);
		CondicionTaxativa cond = new CondicionTaxativa(new OperandoCondicion(new Promedio(),ingresoNeto,2), new Mayor(), 10000);
		metodologia.agregarCondicionTaxativa(cond);
		assertEquals(2,metodologia.evaluarPara(empresasParaComparacionConMetodologias).size());
	}
	
	@Test
	public void soloMiEmpresaYEmpresaReLocaCumplenINGRESONETOConPromedioMayorA10000EnUltimos2Anios(){
		Empresa miEmpresa = empresasParaComparacionConMetodologias.get(0);
		Empresa empresaReLoca = empresasParaComparacionConMetodologias.get(2);
		Metodologia metodologia = new Metodologia("Una Metodologia");
		Indicador ingresoNeto = indicadores.get(0);
		CondicionTaxativa cond = new CondicionTaxativa(new OperandoCondicion(new Promedio(),ingresoNeto,2), new Mayor(), 10000);
		metodologia.agregarCondicionTaxativa(cond);
		assertTrue(metodologia.evaluarPara(empresasParaComparacionConMetodologias).stream().anyMatch(emp -> emp == miEmpresa) &&  metodologia.evaluarPara(empresasParaComparacionConMetodologias).stream().anyMatch(emp -> emp == empresaReLoca));
	}
	
	@Test
	public void lasEmpresasQueCumplenINGRESONETOConPromedioMayorA10000EnUltimos2AniosSonOrdenadasCorrectamente(){
		Empresa miEmpresa = empresasParaComparacionConMetodologias.get(0);
		Empresa empresaReLoca = empresasParaComparacionConMetodologias.get(2);
		Metodologia metodologia = new Metodologia("Una Metodologia");
		Indicador ingresoNeto = indicadores.get(0);
		CondicionTaxativa cond = new CondicionTaxativa(new OperandoCondicion(new Promedio(),ingresoNeto,2), new Mayor(), 10000);
		metodologia.agregarCondicionTaxativa(cond);
		assertTrue(metodologia.evaluarPara(empresasParaComparacionConMetodologias).get(0)==miEmpresa && metodologia.evaluarPara(empresasParaComparacionConMetodologias).get(1)==empresaReLoca);
	}
	
	@Test
	public void soloUnaEmpresaNoCumpleINGRESONETOConPromedioMayorA10000EnUltimos2Anios(){
		Metodologia metodologia = new Metodologia("Una Metodologia");
		Indicador ingresoNeto = indicadores.get(0);
		CondicionTaxativa cond = new CondicionTaxativa(new OperandoCondicion(new Promedio(),ingresoNeto,2), new Mayor(), 10000);
		metodologia.agregarCondicionTaxativa(cond);
		assertEquals(1,metodologia.empresasQueNoCumplenTaxativas(empresasParaComparacionConMetodologias).size());
	}
	
	@Test
	public void soloEmpresaLocaNOCumpleINGRESONETOConPromedioMayorA10000EnUltimos2Anios(){
		Empresa empresaLoca = empresasParaComparacionConMetodologias.get(1);
		Metodologia metodologia = new Metodologia("Una Metodologia");
		Indicador ingresoNeto = indicadores.get(0);
		CondicionTaxativa cond = new CondicionTaxativa(new OperandoCondicion(new Promedio(),ingresoNeto,2), new Mayor(), 10000);
		metodologia.agregarCondicionTaxativa(cond);
		assertTrue(metodologia.empresasQueNoCumplenTaxativas(empresasParaComparacionConMetodologias).get(0)==empresaLoca);
	}
	
	@Test
	public void soloUnaEmpresaCumpleINGRESONETOConSumatoriaMayorA20000EnUltimos3Anios(){
		Metodologia metodologia = new Metodologia("Una Metodologia");
		Indicador ingresoNeto = indicadores.get(0);
		CondicionTaxativa cond = new CondicionTaxativa(new OperandoCondicion(new Sumatoria(),ingresoNeto,3), new Mayor(), 20000);
		metodologia.agregarCondicionTaxativa(cond);
		assertEquals(1,metodologia.evaluarPara(empresasParaComparacionConMetodologias).size());
	}
	
	@Test
	public void soloMiEmpresaCumpleINGRESONETOConSumatoriaMayorA20000EnUltimos3Anios(){
		Empresa miEmpresa = empresasParaComparacionConMetodologias.get(0);
		Metodologia metodologia = new Metodologia("Una Metodologia");
		Indicador ingresoNeto = indicadores.get(0);
		CondicionTaxativa cond = new CondicionTaxativa(new OperandoCondicion(new Promedio(),ingresoNeto,3), new Mayor(), 10000);
		metodologia.agregarCondicionTaxativa(cond);
		assertTrue(metodologia.evaluarPara(empresasParaComparacionConMetodologias).stream().anyMatch(emp -> emp == miEmpresa));
	}
	
	@Test
	public void ningunaEmpresaNOCumpleINGRESONETOConSumatoriaMayorA20000EnUltimos3Anios(){
		Metodologia metodologia = new Metodologia("Una Metodologia");
		Indicador ingresoNeto = indicadores.get(0);
		CondicionTaxativa cond = new CondicionTaxativa(new OperandoCondicion(new Sumatoria(),ingresoNeto,3), new Mayor(), 20000);
		metodologia.agregarCondicionTaxativa(cond);
		assertEquals(0,metodologia.empresasQueNoCumplenTaxativas(empresasParaComparacionConMetodologias).size());
	}
	
	@Test
	public void soloDosEmpresasNOtienenDatosSuficientesParaINGRESONETOConSumatoriaMayorA20000EnUltimos3Anios(){
		Metodologia metodologia = new Metodologia("Una Metodologia");
		Indicador ingresoNeto = indicadores.get(0);
		CondicionTaxativa cond = new CondicionTaxativa(new OperandoCondicion(new Sumatoria(),ingresoNeto,3), new Mayor(), 20000);
		metodologia.agregarCondicionTaxativa(cond);
		assertEquals(2,metodologia.empresasConDatosFaltantes(empresasParaComparacionConMetodologias).size());
	}
	
	@Test
	public void soloEmpresaLocaYEmpresaReLocaNOtienenDatosSuficientesParaINGRESONETOConSumatoriaMayorA20000EnUltimos3Anios(){
		Empresa empresaLoca = empresasParaComparacionConMetodologias.get(1);
		Empresa empresaReLoca = empresasParaComparacionConMetodologias.get(2);
		Metodologia metodologia = new Metodologia("Una Metodologia");
		Indicador ingresoNeto = indicadores.get(0);
		CondicionTaxativa cond = new CondicionTaxativa(new OperandoCondicion(new Sumatoria(),ingresoNeto,3), new Mayor(), 20000);
		metodologia.agregarCondicionTaxativa(cond);
		assertTrue(metodologia.empresasConDatosFaltantes(empresasParaComparacionConMetodologias).stream().anyMatch(emp -> emp == empresaLoca) &&  metodologia.empresasConDatosFaltantes(empresasParaComparacionConMetodologias).stream().anyMatch(emp -> emp == empresaReLoca));
	}
	
	@Test
	public void soloDosEmpresasSeAplicaCorrectamenteUnaMetodologiaConCondTaxINGRESONETOConPromedioMayorA10000YConCondPriorINDICADORDOSConSumatoriaAmbosEnUltimosDosAnios(){
		Indicador ingresoNeto = indicadores.get(0);
		Indicador indicadorDos = indicadores.get(1);
		Metodologia metodologia = new Metodologia("Una Metodologia");
		CondicionTaxativa condTax = new CondicionTaxativa(new OperandoCondicion(new Promedio(),ingresoNeto,2), new Mayor(), 10000);
		CondicionPrioritaria condPrior = new CondicionPrioritaria(new OperandoCondicion(new Sumatoria(),indicadorDos,2), new Mayor());
		metodologia.agregarCondicionTaxativa(condTax);
		metodologia.agregarCondicionPrioritaria(condPrior);
		assertEquals(2,metodologia.evaluarPara(empresasParaComparacionConMetodologias).size());
	}
	
	@Test
	public void seAplicaCorrectamenteUnaMetodologiaConCondTaxINGRESONETOConPromedioMayorA10000YConCondPriorINDICADORDOSConSumatoriaAmbosEnUltimosDosAniosDevolviendoEnCorrectoOrdenAmiEmpresaYEmpresaReLoca(){
		Empresa miEmpresa = empresasParaComparacionConMetodologias.get(0);
		Empresa empresaReLoca = empresasParaComparacionConMetodologias.get(2);
		Indicador ingresoNeto = indicadores.get(0);
		Indicador indicadorDos = indicadores.get(1);
		Metodologia metodologia = new Metodologia("Una Metodologia");
		CondicionTaxativa condTax = new CondicionTaxativa(new OperandoCondicion(new Promedio(),ingresoNeto,2), new Mayor(), 10000);
		CondicionPrioritaria condPrior = new CondicionPrioritaria(new OperandoCondicion(new Variacion(),indicadorDos,2), new Mayor());
		metodologia.agregarCondicionTaxativa(condTax);
		metodologia.agregarCondicionPrioritaria(condPrior);
		assertTrue(metodologia.evaluarPara(empresasParaComparacionConMetodologias).get(0)==empresaReLoca && metodologia.evaluarPara(empresasParaComparacionConMetodologias).get(1)==miEmpresa);
	}
	
	@Test//VER POR QUE NO FILTRA empresaLoca SI LE PASO A LA METODOLOGIA empresasParaComparacionConMetodologias
	public void seAplicaCorrectamenteUnaMetodologiaConCondTaxPRUEBAConUltimoMayorA0YConCondPriorPRUEBAConUltimoAmbosEnUltimoAnioDevolviendoEnCorrectoOrdenAEmpresaReLocaYmiEmpresa(){
		Empresa miEmpresa = empresasParaComparacionConMetodologias.get(0);
		Empresa empresaReLoca = empresasParaComparacionConMetodologias.get(2);
		ArrayList<Empresa> empresasAEvaluar = new ArrayList<Empresa>(); // !
		empresasAEvaluar.add(miEmpresa);
		empresasAEvaluar.add(empresaReLoca);
		Indicador prueba = indicadores.get(4);
		Metodologia metodologia = new Metodologia("Una Metodologia");
		CondicionTaxativa condTax = new CondicionTaxativa(new OperandoCondicion(new Ultimo(),prueba,1), new Mayor(), 0);
		CondicionPrioritaria condPrior = new CondicionPrioritaria(new OperandoCondicion(new Ultimo(),prueba,1), new Mayor());
		metodologia.agregarCondicionTaxativa(condTax);
		metodologia.agregarCondicionPrioritaria(condPrior);
		assertTrue(metodologia.evaluarPara(empresasParaComparacionConMetodologias).get(0)==empresaReLoca && metodologia.evaluarPara(empresasParaComparacionConMetodologias).get(1)==miEmpresa);
	}

}
