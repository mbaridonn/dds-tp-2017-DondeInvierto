package dominio;

import java.util.Calendar;
import org.apache.poi.ss.formula.eval.RelationalOperationEval;

public class CondPriIndicador implements CondicionPrioritaria{//FALTAN SETTERS/CONSTRUCTOR
	private Indicador indicador;
	private RelationalOperationEval operacionRelacional;//DEBERÍA PODER ELEGIR ENTRE > Y <. POR AHORA FUNCIONA SOLO CON >

	@Override
	public boolean esMejorQue(Empresa empresa1, Empresa empresa2) {
		int anioActual = Calendar.getInstance().get(Calendar.YEAR);
		return indicador.evaluarEn(empresa1, String.valueOf(anioActual)) > indicador.evaluarEn(empresa2, String.valueOf(anioActual));
	}
}