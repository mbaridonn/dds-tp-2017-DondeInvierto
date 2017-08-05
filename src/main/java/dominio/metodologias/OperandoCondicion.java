package dominio.metodologias;

import java.time.LocalDate;
import java.util.stream.IntStream;

import dominio.Empresa;

public class OperandoCondicion {
	private OperacionAgregacion operacionAgregacion;
	private EvaluableEnCondicion indicadorOAntiguedad;
	private int aniosAEvaluar;
	
	public OperandoCondicion(OperacionAgregacion operacionAgregacion, EvaluableEnCondicion indicadorOAntiguedad, int aniosAEvaluar) {
		this.operacionAgregacion = operacionAgregacion;
		this.indicadorOAntiguedad = indicadorOAntiguedad;
		this.aniosAEvaluar = aniosAEvaluar - 1; //Si aniosAEvaluar es 1, el intervalo tiene que ser (X,X), no (X-1,X)
	}
	
	public int valorPara(Empresa empresa){
		int anioActual = LocalDate.now().getYear();
		IntStream periodoAEvaluar = IntStream.rangeClosed(anioActual - aniosAEvaluar, anioActual);
		IntStream indicesEnPeriodo = periodoAEvaluar.map(anio -> indicadorOAntiguedad.evaluarEn(empresa, String.valueOf(anio)));
		return operacionAgregacion.aplicarA(indicesEnPeriodo);
	}
}