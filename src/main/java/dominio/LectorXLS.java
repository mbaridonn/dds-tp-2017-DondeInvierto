package dominio;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Iterator;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;


public class LectorXLS {
	
	public static String ruta;
	
	public static Lista<Cuenta> leerCuentasDe(String nombreEmpresa){
		String anio, tipoCuenta;
		int valor;
		Lista<Cuenta> cuentasEmpresa = new Lista<Cuenta>();
		
		try{
            String excelPath = ruta;
            FileInputStream fileInputStream = new FileInputStream(new File(excelPath));

            HSSFWorkbook workbook = new HSSFWorkbook(fileInputStream);

            HSSFSheet sheet = workbook.getSheet(nombreEmpresa);
            

            // Iterate through each rows
            Iterator<Row> rowIterator = sheet.iterator();

            while (rowIterator.hasNext()){ //Va por filas
                Row row = rowIterator.next();
                
                Iterator<Cell> cellIterator = row.cellIterator();
                
                Cell cell = cellIterator.next();
                
                anio = String.valueOf((int)cell.getNumericCellValue());
                cell = cellIterator.next();
                
                tipoCuenta = cell.getStringCellValue();
                cell = cellIterator.next();
                
                valor = (int) cell.getNumericCellValue();
                
                Cuenta cuenta = new Cuenta(anio,tipoCuenta,valor);
                cuentasEmpresa.add(cuenta); 
            }
            workbook.close();
            return cuentasEmpresa;

        }catch (IOException ie){
            throw new NoSePudoLeerArchivoError("No se pudo leer el archivo");
        }
	}
}

class NoSePudoLeerArchivoError extends RuntimeException{ public NoSePudoLeerArchivoError(String s){ super(s);}}