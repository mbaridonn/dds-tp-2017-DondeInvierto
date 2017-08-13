package dominio.metodologias;

import java.util.stream.IntStream;

public class Mediana implements OperacionAgregacion{

	@Override
	public int aplicarA(IntStream valores) {
		//valores.sorted();
		int[] vals = this.streamToArray(valores.sorted());
		int middle = (int) (vals.length / 2);
		if (vals.length%2 == 1) {
			return vals[middle];
		} else {
			return (vals[middle-1] + vals[middle]) / 2;
		}
	}
	
	private int[] streamToArray(IntStream valores){ // No se puede aplicar dos mensajes a un Intstream no se porque, por eso tuve que crear este metodo.
		return valores.toArray();
	}
}