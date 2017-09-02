package dominio.indicadores;

import javax.persistence.Entity;

import dominio.empresas.Empresa;

@Entity
public class ExpresionValor extends Expresion{
	
	private int valor;
	
	public ExpresionValor(int valor) {
		this.valor = valor;
	}

	@Override
	public int evaluarEn(Empresa empresa, String anio) {
		return valor;
	}
	
}
