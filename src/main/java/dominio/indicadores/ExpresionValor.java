package dominio.indicadores;

import java.time.Year;

import dominio.empresas.Empresa;

public class ExpresionValor implements Expresion{
	
	private int valor;
	
	public ExpresionValor(int valor) {
		this.valor = valor;
	}

	public int evaluarEn(Empresa empresa, Year anio) {
		return valor;
	}
	
}
