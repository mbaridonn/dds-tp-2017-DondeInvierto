package defaultPackage;

import static org.junit.Assert.*;

import java.util.ArrayList;

/*import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;*/
import org.junit.Before;
import org.junit.Test;

import dominio.*;

public class EmpresaTest{
	
	Empresa empresa;
	ArrayList<Cuenta> cuentas;
	
	@Before
    public void setUp(){
		ArchivoXLS archivo = new ArchivoXLS("src/test/resources/LibroPrueba.xls");
		ArrayList<Empresa> empresas = archivo.leerEmpresas();
		empresa = empresas.get(0);
		cuentas = empresa.getCuentas();
    }

	@Test
    public void elAnioDeLaUltimaCuentaEs2014(){
    	assertEquals(cuentas.get(cuentas.size()-1).getAnio(), "2014");
    }
	
    @Test
    public void cargaCuentasCorrectamente(){
    	ArrayList<Cuenta> cuentasEsperadas = new ArrayList<Cuenta> () {{
    		add(new Cuenta("2017","EBITDA",35000));
    		add(new Cuenta("2017","FDS",158960));
    		add(new Cuenta("2016","FDS",144000));
    		add(new Cuenta("2015","EBITDA",120000));
    		add(new Cuenta("2015","Free Clash Flow",150000));
    		add(new Cuenta("2014","EBITDA",260000));
    		add(new Cuenta("2014","FDS",360000));
    	}};
		assertTrue(this.sonIguales(cuentasEsperadas,cuentas));
    }
    
    private boolean sonIguales(ArrayList<Cuenta> cuentasEsperadas, ArrayList<Cuenta> cuentas){    	
    	boolean resultado=true;
    	for(int i=0;i<cuentasEsperadas.size();i++){
    		resultado = resultado && this.cuentasSonIguales(cuentas.get(i), cuentasEsperadas.get(i));
    	}
    	return resultado && cuentasEsperadas.size()==cuentas.size();
    }

    private boolean cuentasSonIguales(Cuenta cuenta, Cuenta cuentaEsperada){
    	return cuenta.getAnio().equals(cuentaEsperada.getAnio()) && cuenta.getTipoCuenta().equals(cuentaEsperada.getTipoCuenta()) && cuenta.getValor()==cuentaEsperada.getValor();
    }
    /*@Test
    public void ejemploTest(){
        assertEquals(expected, actual);
        assertFalse(algoBool);
        assertTrue(algoBool);
    }*/
    
    @Test
    public void hayTresEmpresasCargadas(){
    	ArchivoXLS archivo = new ArchivoXLS("src/test/resources/LibroPruebaEmpresas.xls");
    	ArrayList<Empresa> empresas = archivo.leerEmpresas();
		assertEquals(3, empresas.size());
    }
    
    @Test
    public void elNombreDeLaSegundaEmpresaEsEmpresaLoca(){
    	ArchivoXLS archivo = new ArchivoXLS("src/test/resources/LibroPruebaEmpresas.xls");
    	ArrayList<Empresa> empresas = archivo.leerEmpresas();
		assertEquals("EmpresaLoca", empresas.get(1).getNombre());
    }
    
    @Test
    public void laPrimerEmpresaTieneSieteCuentas(){
    	ArchivoXLS archivo = new ArchivoXLS("src/test/resources/LibroPruebaEmpresas.xls");
    	ArrayList<Empresa> empresas = archivo.leerEmpresas();
		assertEquals(7, empresas.get(0).cantidadDeCuentasQuePosee());
    }

}

