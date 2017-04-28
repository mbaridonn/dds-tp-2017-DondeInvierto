package defaultPackage;

import static org.junit.Assert.assertEquals;
/*import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;*/
import org.junit.Before;
import org.junit.Test;

import dominio.*;

public class EmpresaTest{
	
	Empresa empresa;
	String resultadoReal = "";
	
	@Before
    public void setUp(){
		ArchivoXLS archivo = new ArchivoXLS("src/archivo/LibroPrueba.xls");
		Lista<Empresa> empresas = archivo.leerEmpresas();
		empresa = empresas.head();
    }

	@Test
    public void elAnioDeLaUltimaCuentaEs2014(){
    	assertEquals(empresa.getCuentas().last().getAnio(), "2014");
    }
	
    @Test
    public void cargaCuentasCorrectamente(){
    	String resultadoEsperado = "Anio: 2017 Tipo Cuenta: EBITDA Valor: 35000\nAnio: 2017 Tipo Cuenta: FDS Valor: 158960\nAnio: 2016 Tipo Cuenta: FDS Valor: 144000\nAnio: 2015 Tipo Cuenta: EBITDA Valor: 120000\nAnio: 2015 Tipo Cuenta: Free Clash Flow Valor: 150000\nAnio: 2014 Tipo Cuenta: EBITDA Valor: 260000\nAnio: 2014 Tipo Cuenta: FDS Valor: 360000\n";
		empresa.getCuentas().forEach(cuenta -> resultadoReal += ("Anio: " + cuenta.getAnio() +" Tipo Cuenta: " + cuenta.getTipoCuenta() + " Valor: " + cuenta.getValor() + "\n"));
		assertEquals(resultadoEsperado, resultadoReal);
    }

    /*@Test
    public void ejemploTest(){
        assertEquals(expected, actual);
        assertFalse(algoBool);
        assertTrue(algoBool);
    }*/
    
    @Test
    public void hayTresEmpresasCargadas(){
    	ArchivoXLS archivo = new ArchivoXLS("src/archivo/LibroPruebaEmpresas.xls");
    	Lista<Empresa> empresas = archivo.leerEmpresas();
		assertEquals(3, empresas.size());
    }
    
    @Test
    public void elNombreDeLaSegundaEmpresaEsEmpresaLoca(){
    	ArchivoXLS archivo = new ArchivoXLS("src/archivo/LibroPruebaEmpresas.xls");
    	Lista<Empresa> empresas = archivo.leerEmpresas();
		assertEquals("EmpresaLoca", empresas.get(1).getNombre());
    }
    
    @Test
    public void laPrimerEmpresaTieneSieteCuentas(){
    	ArchivoXLS archivo = new ArchivoXLS("src/archivo/LibroPruebaEmpresas.xls");
    	Lista<Empresa> empresas = archivo.leerEmpresas();
		assertEquals(7, empresas.head().cantidadDeCuentasQuePosee());
    }

}

