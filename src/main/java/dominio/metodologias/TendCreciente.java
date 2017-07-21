package dominio.metodologias;

import java.util.stream.IntStream;

public class TendCreciente extends Tendencia{

	@Override
	public boolean cumplenTendencia(IntStream nums) {//REPITE LOGICA CON DECRECIENTE
		int[] arrayNums = nums.toArray();
		boolean cumple = true;
		for(int i=0; cumple && i<arrayNums.length; i++){
			cumple = arrayNums[i] > arrayNums[i+1];
		}
		return cumple;
	}

}
