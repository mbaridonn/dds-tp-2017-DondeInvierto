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
	public String toString(){ //Es necesario para que el Selector muestre solo el nombre de la Empresa
		return nombre;
	}
	
	public ArrayList<Cuenta> obtenerCuentasSegunTipoCuenta(String tipoCuenta){
		ArrayList<Cuenta> cuentasFiltradas = new ArrayList<Cuenta>();
		//Cuenta cuenta = cuentas.stream().filter(unaCuenta -> unaCuenta.esDeTipo(tipoCuenta)).findFirst().orElseThrow(() -> new NoExisteCuentaError("No se pudo encontrar una cuenta en ese año para esta empresa."));
		//cuentasFiltradas.add(cuenta);
		for(Cuenta cuenta: cuentas){ //no es la mejor opcion pero no se me ocurrio como se puede hacer con un forEach
			if(cuenta.esDeTipo(tipoCuenta))
				cuentasFiltradas.add(cuenta);
		}
		
		return cuentasFiltradas;
	}
	
	public void aparearListasSegun(String tipoCuenta1, String tipoCuenta2){
		//Por ahora esta pensado para que funcione con el indicador NetoTotal = NetoOperacionesContinuas + NetoOperacionesDiscontinuas
		ArrayList<String> listaAnios = new ArrayList<String>();
		int valor1,valor2, neto;
		
		obtenerCuentasSegunTipoCuenta(tipoCuenta1).forEach(unaCuenta -> listaAnios.add(unaCuenta.getAnio()));
		
		for(int i = 0; i < listaAnios.size(); i++){ //for ASQUEROSO, si se les ocurre algo mejor buenisimo
			String anio = listaAnios.get(i);
			valor1 = this.getValorCuenta(tipoCuenta1, anio);
			valor2 = this.getValorCuenta(tipoCuenta2, anio);
			neto = valor1 + valor2;
			
			cuentas.add(new Cuenta(anio,"IngresoNeto",neto));
		}

	}
}

class NoExisteCuentaError extends RuntimeException{NoExisteCuentaError(String e){super(e);}}
