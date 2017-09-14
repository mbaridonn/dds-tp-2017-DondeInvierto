package dominio.metodologias;

import java.util.stream.IntStream;

public enum OperacionAgregacion {
	
	Mediana{
		public int aplicarA(IntStream valores) {
			int[] vals = valores.sorted().toArray();
			int middle = (int) (vals.length / 2);
			if (vals.length%2 == 1) {
				return vals[middle];
			} else {
				return (vals[middle-1] + vals[middle]) / 2;
			}
		}
		public String toString(){
			return "Mediana";
		}
	},
	
	Promedio{
		public int aplicarA(IntStream valores) {
			return (int) valores.average().orElseThrow(() -> new NoSePudoRealizarOperacionDeAgregacionError("No se pudo obtener promedio."));
		}
		public String toString() {
			return "Promedio";
		}
	},
	
	Sumatoria{
		public int aplicarA(IntStream valores) {
			return valores.sum();
		}
		public String toString() {
			return "Sumatoria";
		}
	},
	
	Ultimo{
		public int aplicarA(IntStream valores) {
			int vals[] = valores.toArray();
			return vals[vals.length-1];
		}
		public String toString() {
			return "Ultimo";
		}
	},
	
	Variacion{
		public int aplicarA(IntStream valores) {
			int variacionAcumulada = 0, i=0;
			int vals[] = valores.toArray();
			while(i<vals.length-1){
				variacionAcumulada += Math.abs(vals[i] - vals[i+1]);
				i++;
			}
			return variacionAcumulada;
		}
		public String toString() {
			return "Variacion";
		}
	};
	
	public abstract int aplicarA(IntStream valores);
	public abstract String toString();

}

class NoSePudoRealizarOperacionDeAgregacionError extends RuntimeException{NoSePudoRealizarOperacionDeAgregacionError(String e){super(e);}}
