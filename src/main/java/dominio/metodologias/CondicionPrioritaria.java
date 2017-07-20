package dominio.metodologias;

import dominio.Empresa;

public interface CondicionPrioritaria {
	public abstract boolean esMejorQue(Empresa empresa1, Empresa empresa2);
}
