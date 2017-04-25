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
	
	public static Lista<Cuenta> leer(){
		String anio;
		String tipoCuenta;
		int valor;
		Lista<Cuenta> cuentasEmpresa = new Lista<Cuenta>();
		
		try{
            String excelPath = "C:\\Users\\marti\\Desktop\\LibroPrueba.xls";
            FileInputStream fileInputStream = new FileInputStream(new File(excelPath));

            // Create Workbook instance holding .xls file
            HSSFWorkbook workbook = new HSSFWorkbook(fileInputStream);

            // Get the first worksheet. Aca podriamos hacer que busque la pagina por el nombre de la Empresa.
            HSSFSheet sheet = workbook.getSheetAt(0);

            // Iterate through each rows
            Iterator<Row> rowIterator = sheet.iterator();

            while (rowIterator.hasNext()){ //Va por filas
                // Get Each Row
                Row row = rowIterator.next();

                // Iterating through Each column of Each Row
                Iterator<Cell> cellIterator = row.cellIterator();
                
                Cell cell = cellIterator.next();
                
                anio = String.valueOf(cell.getNumericCellValue()); //Ver como quitar el .0 a los Strings
                cell = cellIterator.next();
                
                tipoCuenta = cell.getStringCellValue();
                cell = cellIterator.next();
                
                valor = (int) cell.getNumericCellValue();
                
                
                Cuenta cuenta = new Cuenta(anio,tipoCuenta,valor);
                
                cuentasEmpresa.add(cuenta);
                
                
            }
            
            return cuentasEmpresa;

        }catch (IOException ie){
            ie.printStackTrace();
            throw new RuntimeException("hola"); // Devolver una lista vacia o tirar un error? Quitar obviamente esto
        }
	}
}
