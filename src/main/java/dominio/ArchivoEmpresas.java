package dominio;

public abstract class ArchivoEmpresas {
	String ruta;
	public ArchivoEmpresas(String ruta){
		this.ruta = ruta;
	}
	public boolean puedeLeerArchivo(){
		String extension = "";
		int i = ruta.lastIndexOf('.');
		if (i > 0) {
		    extension = ruta.substring(i+1);
		}
		return extension.equals(this.extensionQueLee());
	}
	
	protected abstract String extensionQueLee();
}
