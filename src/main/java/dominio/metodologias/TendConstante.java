package dominio.metodologias;

import java.util.stream.IntStream;

public class TendConstante extends Tendencia{

	@Override
	public boolean cumplenTendencia(IntStream nums) {
		int puntoMedio = new Promedio().aplicarA(nums);
		int valorMinimo = puntoMedio - tolerancia;
		int valorMaximo = puntoMedio + tolerancia;
		return nums.allMatch(num -> valorMaximo > num && num < valorMinimo);
	}

}
