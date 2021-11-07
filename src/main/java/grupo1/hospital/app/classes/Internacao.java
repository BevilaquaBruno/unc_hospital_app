package grupo1.hospital.app.classes;

import java.time.LocalTime;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

public class Internacao {
	private SimpleIntegerProperty id;
	private ObjectProperty<LocalTime> horario;
	private SimpleStringProperty quarto;
	private Paciente paciente;
	private Pessoa acompanhante;
	
	public Internacao(LocalTime horario, String quarto, Paciente paciente, Pessoa acompanhante) {
		this.horario = new SimpleObjectProperty<LocalTime>(horario);
		this.quarto = new SimpleStringProperty(quarto);
		this.paciente = paciente;
		this.acompanhante = acompanhante;
	}

	public Integer getId() {
		return id.get();
	}

	public void setId(Integer id) {
		this.id = new SimpleIntegerProperty(id);
	}

	public LocalTime getHorario() {
		return horario.get();
	}

	public void setHorario(LocalTime horario) {
		this.horario = new SimpleObjectProperty<LocalTime>(horario);
	}

	public String getQuarto() {
		return quarto.get();
	}

	public void setQuarto(String quarto) {
		this.quarto = new SimpleStringProperty(quarto);
	}

	public Paciente getPaciente() {
		return this.paciente;
	}

	public void setPaciente(Paciente paciente) {
		this.paciente = paciente;
	}

	public Pessoa getAcompanhante() {
		return this.acompanhante;
	}

	public void setAcompanhante(Pessoa acompanhante) {
		this.acompanhante = acompanhante;
	}
	
	/* property */
	public SimpleIntegerProperty idProperty() {
		return this.id;
	}
	
	public SimpleStringProperty quartoProperty() {
		return this.quarto;
	}
	
	public ObjectProperty<LocalTime> horarioProperty() {
		return this.horario;
	}
}
