package dominio.empresas;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

import excepciones.NoSePudoCerrarArchivoError;
import excepciones.NoSePudoLeerArchivoError;


public class ArchivoXLS extends ArchivoEmpresas{

	private FileInputStream fileInputStream;
	private HSSFWorkbook workbook;
	
	public ArchivoXLS(String ruta) {
		super(ruta);
	}
	
	@Override
	protected String extensionQueLee() {
		return "xls";
	}
	
	public void leerRegistros(){
        workbook.forEach(hoja -> empresas.add(this.empresaDeHoja((HSSFSheet) hoja)));
	}
	
	@Override
	protected void prepararLector(){
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
		ArrayList<Cuenta> cuentas = new ArrayList<Cuenta>();
		Iterator<Row> rowIterator = hoja.iterator();
		rowIterator.next(); //permite saltearse la primer fila del excel (la primer fila contiene titulos, no datos)
        while (rowIterator.hasNext()){
            Row row = rowIterator.next();   
            cuentas.add(this.obtenerCuentaDeFila(row));
        }
        return cuentas;
	}
	
	private Cuenta obtenerCuentaDeFila(Row row){
		return new Cuenta(this.anioDeCuenta(row),this.tipoDeCuenta(row),this.valorDeCuenta(row)); 
	}
	
	private String anioDeCuenta(Row row){
		Cell cell = this.obtenerCeldaNumero(0,row);
		return String.valueOf((int)cell.getNumericCellValue());
	}
	
	private String tipoDeCuenta(Row row){
		Cell cell = this.obtenerCeldaNumero(1,row);
		return cell.getStringCellValue();
	}
	
	private int valorDeCuenta(Row row){
		Cell cell = this.obtenerCeldaNumero(2, row);
		return (int) cell.getNumericCellValue();
	}
	
	private Cell obtenerCeldaNumero(int numCelda, Row row){
		Iterator<Cell> cellIterator = row.cellIterator();
		Cell cell = null;
		for(int i=0; i <= numCelda;i++){
			cell = cellIterator.next();
		}
		return cell;
	}
	
	@Override
	protected void cerrarArchivo(){
		try {
			workbook.close();
		} catch (IOException e) {
			throw new NoSePudoCerrarArchivoError("No se pudo cerrar el archivo.");
		}
	}
}