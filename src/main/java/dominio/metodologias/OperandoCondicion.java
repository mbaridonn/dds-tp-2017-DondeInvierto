package dominio.metodologias;

import java.time.LocalDate;
import java.time.Year;
import java.util.stream.IntStream;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import dominio.empresas.Empresa;
import excepciones.AntiguedadMenorACeroError;
import excepciones.NoExisteCuentaError;

@Entity
@Table(name = "operandos_condicion")
public class OperandoCondicion {
	
	@Id 
	@GeneratedValue
	private Long id;
	
	@Enumerated
	private OperacionAgregacion operacionAgregacion;
	@ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
	private Cuantificador indicadorOAntiguedad;
	private int aniosAEvaluar;
	
	private OperandoCondicion() {} //Necesario para persistir la clase
	
	public OperandoCondicion(OperacionAgregacion operacionAgregacion, Cuantificador indicadorOAntiguedad, int aniosAEvaluar) {
		this.operacionAgregacion = operacionAgregacion;
		this.indicadorOAntiguedad = indicadorOAntiguedad;
		this.aniosAEvaluar = aniosAEvaluar - 1; //Si aniosAEvaluar es 1, el intervalo tiene que ser (X,X), no (X-1,X)
		if (aniosAEvaluar < 0){
			throw new AniosAEvaluarMenorAUnoError("La condición se tiene que evaluar al menos en un año");
		}
	}
	
	public boolean sePuedeEvaluarPara(Empresa empresa){
		try{
			this.valorPara(empresa);
			return true;
		} catch (NoExisteCuentaError | AntiguedadMenorACeroError e) {
			return false;
		}
	}
	
	public int valorPara(Empresa empresa){
		int anioActual = LocalDate.now().getYear();
		IntStream periodoAEvaluar = IntStream.rangeClosed(anioActual - aniosAEvaluar, anioActual);
		IntStream indicesEnPeriodo = periodoAEvaluar.map(anio -> indicadorOAntiguedad.evaluarEn(empresa, Year.of(anio)));
		return operacionAgregacion.aplicarA(indicesEnPeriodo);
	}
}

class AniosAEvaluarMenorAUnoError extends RuntimeException{AniosAEvaluarMenorAUnoError(String e){super(e);}}