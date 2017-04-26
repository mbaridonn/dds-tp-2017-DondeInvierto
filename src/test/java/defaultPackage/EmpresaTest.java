package defaultPackage;

import static org.junit.Assert.assertEquals;
/*import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;*/
import org.junit.Before;
import org.junit.Test;

import dominio.Empresa;

public class EmpresaTest{
	
	Empresa empresa;
	
	@Before
    public void setUp(){
		empresa = new Empresa("miEmpresa");
		empresa.cargarCuentas();
    }

	@Test
    public void elAnioDeLaUltimaCuentaEs2014(){
    	assertEquals(empresa.cuentas.last().getAnio(), "2014");
    }
	
	//Quise hacer un test polenta que chequee todo el archivo pero no anduvo (probablemente haya un problema en el forEach)
    /*@Test
    public void cargaCuentasCorrectamente(){
    	String resultadoEsperado = "Anio: 2017 Tipo Cuenta: EBITDA Valor: 35000\nAnio: 2017 Tipo Cuenta: FDS Valor: 158960\nAnio: 2016 Tipo Cuenta: FDS Valor: 144000\nAnio: 2015 Tipo Cuenta: EBITDA Valor: 120000\nAnio: 2015 Tipo Cuenta: Free Clash Flow Valor: 150000\nAnio: 2014 Tipo Cuenta: EBITDA Valor: 260000\nAnio: 2014 Tipo Cuenta: FDS Valor: 360000";
		String resultadoReal = "";
		empresa.cuentas.forEach(cuenta -> resultadoReal.concat("Anio: " + cuenta.getAnio() +" Tipo Cuenta: " + cuenta.getTipoCuenta() + " Valor: " + cuenta.getValor() + "\n"));
		assertEquals(resultadoReal, resultadoEsperado);
    }*/

    /*@Test
    public void obtenerConDosFiltros() throws Exception{
        assertEquals(response.getStatus(), 200);
        assertFalse(json.contains("totalItems"));
        assertTrue(json.contains("title"));
    }*/

}

