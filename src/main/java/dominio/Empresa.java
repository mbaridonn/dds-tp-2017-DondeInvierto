package dominio;

import java.util.ArrayList;

public class Empresa {
	private String nombre;
	private ArrayList<Cuenta> cuentas;
	
	public Empresa(String nombre,ArrayList<Cuenta> cuentas){
		this.nombre = nombre;
		this.cuentas = cuentas;
	}
	
	public int cantidadDeCuentasQuePosee(){
		return cuentas.size();
	}
	
	public boolean seLlama(String nombre){
		return this.nombre.equals(nombre);
	}
	
	public void registrarCuenta(Cuenta cuenta){
		cuentas.add(cuenta);
	}
	
	public String getNombre(){
		return nombre;
	}

	public ArrayList<Cuenta> getCuentas() {
		return cuentas;
	}
	
	public int getValorCuenta(String tipoCuenta, String anio){
		Cuenta cuentaBuscada = cuentas.stream().filter(cuenta -> cuenta.esDeTipo(tipoCuenta) && cuenta.esDeAnio(anio)).findFirst().orElseThrow(() -> new NoExisteCuentaError("No se pudo encontrar una cuenta en ese año para esta empresa."));
		//El findFirst podría enmascarar el caso erróneo en el que haya dos cuentas del mismo tipo con valores distintos en el mismo año
		return cuentaBuscada.getValor();
	}
	
	@Override
	public String toString(){ //Es necesario para que el Selector muestre solo el nombre de la empresa
		return nombre;
	}
	
}

class NoExisteCuentaError extends RuntimeException{NoExisteCuentaError(String e){super(e);}}
