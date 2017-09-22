package dominio.indicadores;

import java.time.Year;

import javax.persistence.Entity;

import dominio.empresas.Empresa;

@Entity
public class ExpresionValor extends Expresion{
	
	private int valor;
	
	private ExpresionValor(){} //Necesario para persistir la clase
	
	public ExpresionValor(int valor) {
		this.valor = valor;
	}

	@Override
	public int evaluarEn(Empresa empresa, Year anio) {
		return valor;
	}
	
}
