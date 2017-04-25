package dominio;

public class TestRustico{

	public static void main(String[] args){
		Empresa empresa = new Empresa("miEmpresa");
		empresa.cargarCuentas();
		
		empresa.cuentas.forEach(cuenta -> System.out.println("Anio: " + cuenta.getAnio() +" Tipo Cuenta: " + cuenta.getTipoCuenta() + " Valor: " + cuenta.getValor() + "\n"));
	}

}
