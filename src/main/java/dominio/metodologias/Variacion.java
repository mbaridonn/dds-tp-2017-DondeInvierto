package dominio.metodologias;

import java.util.stream.IntStream;

public class Variacion implements OperacionAgregacion{

	@Override
	public int aplicarA(IntStream valores) {
		int variacionAcumulada = 0, i=0;
		int vals[] = valores.toArray();
		while(i<vals.length-1){
			variacionAcumulada += Math.abs(vals[i] - vals[i+1]);
			i++;
		}
		return variacionAcumulada;
	}
}
