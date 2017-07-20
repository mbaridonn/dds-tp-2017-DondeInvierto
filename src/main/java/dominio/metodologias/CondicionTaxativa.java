package dominio.metodologias;

import dominio.Empresa;

public interface CondicionTaxativa {
	public abstract boolean laCumple(Empresa empresa);
}
