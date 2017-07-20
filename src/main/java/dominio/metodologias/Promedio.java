package dominio.metodologias;

import java.util.stream.IntStream;

public class Promedio implements OperacionAgregacion{

	@Override
	public int aplicarA(IntStream valores) {
		return (int) valores.average().orElseThrow(() -> new NoSePudoRealizarOperacionDeAgregacionError("No se pudo obtener promedio."));
	}
}

class NoSePudoRealizarOperacionDeAgregacionError extends RuntimeException{NoSePudoRealizarOperacionDeAgregacionError(String e){super(e);}}