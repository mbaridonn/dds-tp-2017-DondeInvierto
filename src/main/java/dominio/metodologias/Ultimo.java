package dominio.metodologias;

import java.util.stream.IntStream;

public class Ultimo implements OperacionAgregacion{

	@Override
	public int aplicarA(IntStream valores) {
		int vals[] = valores.toArray();
		return vals[vals.length-1];
	}
}
