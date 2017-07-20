package dominio.metodologias;

import java.util.Calendar;
import java.util.stream.IntStream;

import dominio.Empresa;
import dominio.indicadores.Indicador;

public class CondPriConsistencia implements CondicionPrioritaria{
	private Indicador indicador;
	private int aniosAEvaluar;
	
	public CondPriConsistencia(Indicador indicador, int aniosAEvaluar) {
		this.indicador = indicador;
		this.aniosAEvaluar = aniosAEvaluar;
	}

	@Override
	public boolean esMejorQue(Empresa empresa1, Empresa empresa2) {
		//Una empresa es consistentemente mejor que otra si la variación del indicador a lo largo de los años fue menor.
		int anioActual = Calendar.getInstance().get(Calendar.YEAR);
		int[] periodoAEvaluar = IntStream.range(anioActual - aniosAEvaluar, anioActual).toArray();
		int variacionEmpresa1, variacionEmpresa2;
		variacionEmpresa1 = this.calcularVariacion(empresa1, indicador, periodoAEvaluar);
		variacionEmpresa2 = this.calcularVariacion(empresa2, indicador, periodoAEvaluar);
		return variacionEmpresa1 < variacionEmpresa2;
	}
	
	private int calcularVariacion(Empresa empresa, Indicador indicador, int[] periodoAEvaluar){//PROCEDURAL AF
		int variacionAcumulada = 0, i=0;
		while(i<periodoAEvaluar.length-1){
			variacionAcumulada += Math.abs(indicador.evaluarEn(empresa, String.valueOf(periodoAEvaluar[i])) - 
					indicador.evaluarEn(empresa, String.valueOf(periodoAEvaluar[i+1])));
			i++;
		}
		return variacionAcumulada;
	}

}
