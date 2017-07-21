package dominio.metodologias;

import java.util.stream.IntStream;

public class TendDecreciente extends Tendencia{

	@Override
	public boolean cumplenTendencia(IntStream nums) {//REPITE LOGICA CON CRECIENTE
		int[] arrayNums = nums.toArray();
		boolean cumple = true;
		for(int i=0; cumple && i<arrayNums.length; i++){
			cumple = arrayNums[i] < arrayNums[i+1];
		}
		return cumple;
	}

}