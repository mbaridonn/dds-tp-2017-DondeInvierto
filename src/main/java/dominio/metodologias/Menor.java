package dominio.metodologias;

public class Menor implements OperacionRelacional{

	@Override
	public boolean aplicarA(int num1, int num2) {
		return num1 < num2;
	}

	@Override
	public String toString(){
		return "<";
	}
}
