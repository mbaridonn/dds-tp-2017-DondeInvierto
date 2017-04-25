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
            String excelPath = "src/archivo/LibroPrueba.xls";
            FileInputStream fileInputStream = new FileInputStream(new File(excelPath));

            HSSFWorkbook workbook = new HSSFWorkbook(fileInputStream);

            //Aca podriamos hacer que busque la pagina por el nombre de la Empresa.
            HSSFSheet sheet = workbook.getSheetAt(0);

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
            ie.printStackTrace();
            throw new RuntimeException("hola"); // Devolver una lista vacia o tirar un error? Quitar obviamente esto
        }
	}
}
