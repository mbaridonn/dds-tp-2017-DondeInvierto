package dominio.metodologias;

import java.util.stream.IntStream;

public class Mediana implements OperacionAgregacion{

	@Override
	public int aplicarA(IntStream valores) {
		valores.sorted();
		int middle = (int) (valores.count() / 2);
		int[] vals = valores.toArray();
		if (valores.count()%2 == 1) {
			return vals[middle];
		} else {
			return (vals[middle-1] + vals[middle]) / 2;
		}
	}
	
	@Override
	public String toString() {
		return "Mediana";
	}
}