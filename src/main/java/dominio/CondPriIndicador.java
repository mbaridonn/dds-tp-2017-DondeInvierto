package dominio;

import java.util.Calendar;

public class CondPriIndicador implements CondicionPrioritaria{
	private Indicador indicador;
	private OperacionRelacional operacionRelacional;
	
	public CondPriIndicador(Indicador indicador, OperacionRelacional operacionRelacional) {
		this.indicador = indicador;
		this.operacionRelacional = operacionRelacional;
	}

	@Override
	public boolean esMejorQue(Empresa empresa1, Empresa empresa2) {
		int anioActual = Calendar.getInstance().get(Calendar.YEAR);
		return operacionRelacional.aplicarA(indicador.evaluarEn(empresa1, String.valueOf(anioActual)), 
				indicador.evaluarEn(empresa2, String.valueOf(anioActual)));
	}

}