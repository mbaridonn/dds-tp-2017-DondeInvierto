package dominio.metodologias;

import java.util.Calendar;
import java.util.stream.IntStream;

import dominio.Empresa;
import dominio.indicadores.Indicador;

public class CondTaxConsistencia implements CondicionTaxativa{//CAPAZ CONVENDRÍA SEPARAR LA TOLERANCIA DE LA TENDENCIA
	private Tendencia tendencia; //Crec/Decr/Const
	private Indicador indicador;
	private int tolerancia;//Variación máxima aceptada entre un año y otro
	private int aniosAEvaluar;
	
	public CondTaxConsistencia(Tendencia tendencia, Indicador indicador, int tolerancia, int aniosAEvaluar) {
		this.tendencia = tendencia;
		this.indicador = indicador;
		this.tolerancia = tolerancia;
		this.aniosAEvaluar = aniosAEvaluar;
	}
	
	@Override
	public boolean laCumple(Empresa empresa) {//REPITE LÓGICA CON CondTaxAgregacion
		int anioActual = Calendar.getInstance().get(Calendar.YEAR);
		IntStream periodoAEvaluar = IntStream.range(anioActual - aniosAEvaluar, anioActual);
		IntStream indicesEnPeriodo = periodoAEvaluar.map(anio -> indicador.evaluarEn(empresa, String.valueOf(anio)));
		tendencia.setTolerancia(tolerancia);
		return tendencia.laCumplen(indicesEnPeriodo);
	}
}
