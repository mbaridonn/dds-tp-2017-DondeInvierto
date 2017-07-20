package dominio.metodologias;

import java.util.Calendar;
import java.util.stream.IntStream;

import dominio.Empresa;
import dominio.indicadores.Indicador;

public class CondTaxAgregacion implements CondicionTaxativa{
	OperacionAgregacion operacionAgregacion; //Prod/Med/Suma
	private Indicador indicador;
	private OperacionRelacional operacionRelacional;
	private int valor;
	private int aniosAEvaluar;
	
	public CondTaxAgregacion(OperacionAgregacion operacionAgregacion, Indicador indicador, OperacionRelacional operacionRelacional, int valor, int aniosAEvaluar) {
		this.operacionAgregacion = operacionAgregacion;
		this.indicador = indicador;
		this.operacionRelacional = operacionRelacional;
		this.valor = valor;
		this.aniosAEvaluar = aniosAEvaluar;
	}
	
	@Override
	public boolean laCumple(Empresa empresa) {
		int anioActual = Calendar.getInstance().get(Calendar.YEAR);
		IntStream periodoAEvaluar = IntStream.range(anioActual - aniosAEvaluar, anioActual);
		IntStream indicesEnPeriodo = periodoAEvaluar.map(anio -> indicador.evaluarEn(empresa, String.valueOf(anio)));
		return operacionRelacional.aplicarA(operacionAgregacion.aplicarA(indicesEnPeriodo), valor);
	}
}