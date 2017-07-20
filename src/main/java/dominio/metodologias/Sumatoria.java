package dominio.metodologias;

import java.util.stream.IntStream;

public class Sumatoria implements OperacionAgregacion{

	@Override
	public int aplicarA(IntStream valores) {
		return valores.sum();
	}
}