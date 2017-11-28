package server;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;
import org.uqbarproject.jpa.java8.extras.transaction.TransactionalOps;

import dominio.empresas.Empresa;
import dominio.empresas.LectorArchivos;
import dominio.empresas.RepositorioEmpresas;

public class CargarEmpresas implements WithGlobalEntityManager, TransactionalOps {

	public static void main(String[] args) {
		new CargarEmpresas().cargarArchivos();
	}

	private void cargarArchivos() {
		List<String> archivosALeer = this.getNombresDeArchivosALeer();
		archivosALeer.forEach(archivo -> this.cargarArchivo(archivo));
	}
	
	private void cargarArchivo(String nombreArchivo) {
		String rutaArchivo = raizRutaALeer() + nombreArchivo;
		List<Empresa> empresas = new LectorArchivos(rutaArchivo).getEmpresas();
		withTransaction(() -> new RepositorioEmpresas().agregarMultiplesEmpresas(empresas));
		moverALeidos(nombreArchivo);
	}
	
	private void moverALeidos(String nombreArchivo) {
		try {
			Files.move(Paths.get(raizRutaALeer() + nombreArchivo), Paths.get(raizRutaLeidos()+nombreArchivo), StandardCopyOption.REPLACE_EXISTING);
		} catch (IOException e) {
			throw new NoSePudoMoverElArchivoError("Error al cargar el archivo " + nombreArchivo);
		}
	}
	
	private List<String> getNombresDeArchivosALeer() {
		try {
			Stream<Path> paths = Files.walk(Paths.get("src/main/resources/ArchivosEmpresas/A Leer")).filter(Files::isRegularFile);
			return paths.map(p -> p.getFileName().toString()).collect(Collectors.toList());
		} catch (IOException e1) {
			throw new NoSePudoObtenerElNombreDeLosArchivosError(e1.getMessage());
		}
	}
	
	private String raizRutaALeer() {
		return "src/main/resources/ArchivosEmpresas/A Leer/";
	}
	
	private String raizRutaLeidos() {
		return "src/main/resources/ArchivosEmpresas/Leidos/";
	}

}

class NoSePudoObtenerElNombreDeLosArchivosError extends RuntimeException{public NoSePudoObtenerElNombreDeLosArchivosError(String e){super(e);}}
class NoSePudoMoverElArchivoError extends RuntimeException {public NoSePudoMoverElArchivoError(String e){super(e);}}
