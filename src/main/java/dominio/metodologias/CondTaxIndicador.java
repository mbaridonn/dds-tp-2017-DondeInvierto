package dominio.metodologias;

import java.util.Calendar;
import java.util.stream.IntStream;

import dominio.Empresa;
import dominio.indicadores.Indicador;

public class CondTaxIndicador implements CondicionTaxativa{
	private Indicador indicador;
	private OperacionRelacional operacionRelacional;
	private int valor;
	private int aniosAEvaluar;
	
	public CondTaxIndicador(Indicador indicador, OperacionRelacional operacionRelacional, int valor, int aniosAEvaluar) {
		this.indicador = indicador;
		this.operacionRelacional = operacionRelacional;
		this.valor = valor;
		this.aniosAEvaluar = aniosAEvaluar;
	}
	
	@Override
	public boolean laCumple(Empresa empresa) {
		int anioActual = Calendar.getInstance().get(Calendar.YEAR);
		return IntStream.rangeClosed(anioActual - aniosAEvaluar, anioActual)
				.allMatch(anio -> operacionRelacional.aplicarA(indicador.evaluarEn(empresa, String.valueOf(anio)), valor));
	}
}
