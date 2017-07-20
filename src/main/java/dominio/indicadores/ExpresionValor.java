package dominio.indicadores;

import dominio.Empresa;

public class ExpresionValor implements Expresion{
	
	private int valor;
	
	public ExpresionValor(int valor) {
		this.valor = valor;
	}

	@Override
	public int evaluarEn(Empresa empresa, String anio) {
		return valor;
	}
	
}
