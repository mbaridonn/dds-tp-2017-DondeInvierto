package dominio;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;


public class ArchivoXLS implements Archivo{
	
	private String ruta;
	
	private FileInputStream fileInputStream;
	private HSSFWorkbook workbook;
	
	public ArchivoXLS(String ruta){
		this.ruta = ruta;
	}
	
	public boolean puedeLeerArchivo(){
		String extension = "";//Tiene sentido abstraer la obtención de la extensión en una superclase para evitar repetir lógica con ArchivoCSV?
		int i = ruta.lastIndexOf('.');
		if (i > 0) {
		    extension = ruta.substring(i+1);
		}
		return extension.equals("xls");
	}
	
	public ArrayList<Empresa> leerEmpresas(){
		this.abrirArchivo();
		ArrayList<Empresa> empresas = new ArrayList<Empresa>();
        workbook.forEach(hoja -> empresas.add(this.empresaDeHoja((HSSFSheet) hoja)));
        this.cerrarArchivo();
        return empresas;
	}
	
	public void abrirArchivo(){
		try{
			fileInputStream = new FileInputStream(new File(ruta));
			workbook = new HSSFWorkbook(fileInputStream);
		}catch(IOException ie){
            throw new NoSePudoLeerArchivoError("No se pudo leer el archivo.");
        }
	}
	
	private Empresa empresaDeHoja(HSSFSheet hoja){
		String nombre = hoja.getSheetName();
		ArrayList<Cuenta> cuentas = this.leerCuentasDeHoja(hoja);
		return new Empresa(nombre,cuentas);
	}
	
	private ArrayList<Cuenta> leerCuentasDeHoja(HSSFSheet hoja){
		String anio, tipoCuenta;
		int valor;
		ArrayList<Cuenta> cuentas = new ArrayList<Cuenta>();
		Iterator<Row> rowIterator = hoja.iterator();

        while (rowIterator.hasNext()){ //Va por filas
            Row row = rowIterator.next();
            Iterator<Cell> cellIterator = row.cellIterator();//Falta abstracción leerCuenta() !!!
            
            Cell cell = cellIterator.next();
            
            anio = String.valueOf((int)cell.getNumericCellValue());
            cell = cellIterator.next();
            
            tipoCuenta = cell.getStringCellValue();
            cell = cellIterator.next();
            
            valor = (int) cell.getNumericCellValue();
            
            Cuenta cuenta = new Cuenta(anio,tipoCuenta,valor);
            cuentas.add(cuenta); 
        }
        
        return cuentas;
	}
	
	private void cerrarArchivo(){
		try {
			workbook.close();
		} catch (IOException e) {
			throw new NoSePudoCerrarArchivoError("No se pudo cerrar el archivo.");
		}
	}
}

class NoSePudoLeerArchivoError extends RuntimeException{ public NoSePudoLeerArchivoError(String s){ super(s);}}
class NoSePudoCerrarArchivoError extends RuntimeException{ public NoSePudoCerrarArchivoError(String s){ super(s);}}