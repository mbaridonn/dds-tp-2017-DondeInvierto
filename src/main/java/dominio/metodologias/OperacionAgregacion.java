package dominio.metodologias;

import java.util.stream.IntStream;

public interface OperacionAgregacion {
	public abstract int aplicarA(IntStream valores);
	public String toString();
}
