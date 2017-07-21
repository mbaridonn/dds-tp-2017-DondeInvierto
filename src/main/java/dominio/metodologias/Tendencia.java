package dominio.metodologias;

import java.util.stream.IntStream;

public abstract class Tendencia {
	protected int tolerancia = 0;
	
	public boolean laCumplen(IntStream nums){
		return this.cumplenTolerancia(nums) && this.cumplenTendencia(nums);
	}
	
	public abstract boolean cumplenTendencia(IntStream nums);
	
	private boolean cumplenTolerancia(IntStream nums){
		int[] arrayNums = nums.toArray();
		boolean estaEnRango = true;
		for(int i=0; estaEnRango && i<arrayNums.length; i++){
			estaEnRango = Math.abs(arrayNums[i] - arrayNums[i+1]) < tolerancia;
		}
		return estaEnRango;
	}
	
	public void setTolerancia(int tolerancia) {
		this.tolerancia = tolerancia;
	}
}
