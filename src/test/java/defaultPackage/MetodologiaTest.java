package defaultPackage;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import org.junit.Before;
import org.junit.Test;

import dominio.ArchivoXLS;
import dominio.Empresa;
import dominio.indicadores.ArchivoIndicadores;
import dominio.indicadores.Indicador;
import dominio.metodologias.CondPriAntiguedad;
import dominio.metodologias.CondPriConsistencia;
import dominio.metodologias.CondPriIndicador;
import dominio.metodologias.CondTaxAntiguedad;
import dominio.metodologias.CondTaxIndicador;
import dominio.metodologias.Mayor;
import dominio.metodologias.Menor;

public class MetodologiaTest {
	
	private ArrayList<Indicador> indicadores;
	private ArrayList<Empresa> empresasParaIndicadores;
	
	@Before
	public void setUp() {
		ArchivoXLS archivoEjemploIndicadores = new ArchivoXLS("src/test/resources/EjemploIndicadores.xls");
		ArchivoIndicadores archivoIndicadores = ArchivoIndicadores.getInstance();
		archivoIndicadores.cambiarPath("src/main/resources/Indicadores.txt");
		archivoEjemploIndicadores.leerEmpresas();
		archivoIndicadores.leerIndicadores();
		indicadores = archivoIndicadores.getIndicadores();
		empresasParaIndicadores = archivoEjemploIndicadores.getEmpresas();
		//Ver formas de testear métodos que usan fecha actual (QUIZÁS CONVENGA USAR INYECCIÓN DE DEPENDENCIAS) (!!!)
	}
	
	@Test
	public void miEmpresaEsMasAntiguaQueEmpresaReLoca() {
		Empresa miEmpresa = empresasParaIndicadores.get(0);
		Empresa empresaReLoca = empresasParaIndicadores.get(2);
		assertTrue(new CondPriAntiguedad(new Mayor()).esMejorQue(miEmpresa, empresaReLoca));
	}
	
	/*@Test
	public void elIndicadorDosDeMiEmpresaEsMayorQueElDeEmpresaReLoca() { NO ENCUENTRA CUENTAS
		Empresa miEmpresa = empresasParaIndicadores.get(0);
		Empresa empresaReLoca = empresasParaIndicadores.get(2);
		Indicador indicadorDos = indicadores.get(1);
		assertTrue(new CondPriIndicador(indicadorDos, new Mayor()).esMejorQue(miEmpresa, empresaReLoca));
	}*/
	
	//FALTA TEST DE CondPriConsistencia
	
	@Test
	public void miEmpresaCumpleCondTaxAntiguedadMenorA10() {
		Empresa miEmpresa = empresasParaIndicadores.get(0);
		assertTrue(new CondTaxAntiguedad(new Menor(), 10).laCumple(miEmpresa));
	}
	
	@Test
	public void empresaReLocaNoCumpleCondTaxAntiguedadMayorA3() {
		Empresa empresaReLoca = empresasParaIndicadores.get(2);
		assertFalse(new CondTaxAntiguedad(new Mayor(), 3).laCumple(empresaReLoca));
	}
	
	@Test
	public void empresaReLocaCumpleCondTaxIndicadorINDICADORDOSMayorA360000() {
		Empresa empresaReLoca = empresasParaIndicadores.get(2);
		Indicador indicadorDos = indicadores.get(1);
		assertTrue(new CondTaxIndicador(indicadorDos, new Mayor(), 360000, 0).laCumple(empresaReLoca));//HAY QUE CAMBIAR EL TEMA DE LOS RANGOS!!
	}

}
