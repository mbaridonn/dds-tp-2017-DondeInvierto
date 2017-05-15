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
	ArrayList<Empresa> empresasLibroDos;
	
	@Before
    public void setUp(){
		ArchivoXLS archivoLibroUno = new ArchivoXLS("src/test/resources/LibroPrueba.xls");
		ArchivoXLS archivoLibroDos = new ArchivoXLS("src/test/resources/LibroPruebaEmpresas.xls");
		archivoLibroUno.leerEmpresas();
		archivoLibroDos.leerEmpresas();
    	empresasLibroDos = archivoLibroDos.getEmpresas();
    	empresa = archivoLibroUno.getEmpresas().get(0);
		cuentas = empresa.getCuentas();
    }
	
	/*@Test
    public void ejemploTest(){
        assertEquals(expected, actual);
        assertFalse(algoBool);
        assertTrue(algoBool);
    }*/

	@Test
    public void elLectorXLSPuedeLeerUnArchivoConExtensionXLS(){
		ArchivoXLS archivo = new ArchivoXLS("src/test/resources/LibroPruebaEmpresas.xls");
		assertTrue(archivo.puedeLeerArchivo());
    }
	
	@Test
    public void elLectorCSVPuedeLeerUnArchivoConExtensionCSV(){
		ArchivoCSV archivo = new ArchivoCSV("src/test/resources/LibroPruebaEmpresas.csv");
		assertTrue(archivo.puedeLeerArchivo());
    }
	
	@Test
    public void elAnioDeLaUltimaCuentaEsDeLaPrimeraEmpresa2014(){
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
		assertTrue(this.sonLasMismasCuentas(cuentasEsperadas,cuentas));
    }
    
    @Test
    public void ambosLectoresDevuelvenLoMismo(){
    	ArchivoXLS archivoXLS = new ArchivoXLS("src/test/resources/LibroPruebaEmpresas.xls");
    	ArchivoCSV archivoCSV = new ArchivoCSV("src/test/resources/LibroPruebaEmpresas.csv");
    	archivoXLS.leerEmpresas();
    	archivoCSV.leerEmpresas();
		assertTrue(this.tienenLasMismasEmpresas(archivoXLS.getEmpresas(),archivoCSV.getEmpresas()));
    }
    
    @Test
    public void hayTresEmpresasCargadas(){
		assertEquals(3, empresasLibroDos.size());
    }
    
    @Test
    public void elNombreDeLaSegundaEmpresaEsEmpresaLoca(){
		assertEquals("EmpresaLoca", empresasLibroDos.get(1).getNombre());
    }
    
    @Test
    public void laPrimerEmpresaTieneSieteCuentas(){
		assertEquals(7, empresasLibroDos.get(0).cantidadDeCuentasQuePosee());
    }
    
    @Test
    public void elArchivoXLSNoSobreCargaEmpresasAnteVariasEjecuciones(){
    	ArchivoXLS archivo = new ArchivoXLS("src/test/resources/LibroPruebaEmpresas.xls");
    	archivo.leerEmpresas();
    	archivo.leerEmpresas();
    	assertEquals(archivo.getEmpresas().size(),3);
    }
    
    @Test
    public void elArchivoCSVNoSobreCargaEmpresasAnteVariasEjecuciones(){
    	ArchivoCSV archivo = new ArchivoCSV("src/test/resources/LibroPruebaEmpresas.csv");
    	archivo.leerEmpresas();
    	archivo.leerEmpresas();
    	assertEquals(archivo.getEmpresas().size(),3);
    }
    
    
    /* ------------------------------- METODOS AUXILIARES ------------------------------- */
    
    private boolean tienenLasMismasEmpresas(ArrayList<Empresa> primerListaEmpresas, ArrayList<Empresa> segundaListaEmpresas){
    	for(int i=0; i < primerListaEmpresas.size() ; i++){
    		if(!this.sonLasMismasEmpresas(primerListaEmpresas.get(i),segundaListaEmpresas.get(i))){
    			return false;
    		}
    	}
    	return true;
    }
    
    private boolean sonLasMismasEmpresas(Empresa unaEmpresa, Empresa otraEmpresa){
    	return unaEmpresa.seLlama(otraEmpresa.getNombre()) && this.sonLasMismasCuentas(unaEmpresa.getCuentas(), otraEmpresa.getCuentas());
    }
    
    private boolean sonLasMismasCuentas(ArrayList<Cuenta> cuentasEsperadas, ArrayList<Cuenta> cuentas){    	
    	boolean resultado=true;
    	for(int i=0;i<cuentasEsperadas.size();i++){
    		resultado = resultado && this.cuentasSonIguales(cuentas.get(i), cuentasEsperadas.get(i));
    	}
    	return resultado && cuentasEsperadas.size()==cuentas.size();
    }

    private boolean cuentasSonIguales(Cuenta cuenta, Cuenta cuentaEsperada){
    	return cuenta.getAnio().equals(cuentaEsperada.getAnio()) && cuenta.getTipoCuenta().equals(cuentaEsperada.getTipoCuenta()) && cuenta.getValor()==cuentaEsperada.getValor();
    }

}

