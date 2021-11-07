package grupo1.hospital.app.classes;

import java.time.LocalDate;
import java.time.LocalTime;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

public class Agenda {
	private SimpleIntegerProperty id;
	private ObjectProperty<LocalDate> dtAgenda;
	private ObjectProperty<LocalTime> horario;
	private Paciente paciente;

	public Agenda(LocalDate dtAgenda, LocalTime horario, Paciente paciente) {
		this.dtAgenda = new SimpleObjectProperty<LocalDate>(dtAgenda);
		this.horario = new SimpleObjectProperty<LocalTime>(horario);
		this.paciente = paciente;
	}

	/* Getters */
	public Integer getId() {
		return this.id.get();
	}
	
	public LocalDate getDtAgenda() {
		return this.dtAgenda.get();
	}
	
	public LocalTime getHorario() {
		return this.horario.get();
	}
	
	public Paciente getPaciente() {
		return this.paciente;
	}
	
	public Integer idPaciente() {
		return this.paciente.getId();
	}

	/* Setters */
	public void setId(Integer id) {
		this.id = new SimpleIntegerProperty(id);
	}
	
	public void setDtAgenda(LocalDate dtAgenda) {
		this.dtAgenda = new SimpleObjectProperty<LocalDate>(dtAgenda);
	}
	
	public void setHorario(LocalTime horario) {
		this.horario = new SimpleObjectProperty<LocalTime>(horario);
	}
	
	public void setPaciente(Paciente paciente) {
		this.paciente = paciente;
	}
	
	/* property */
	public SimpleIntegerProperty idProperty() {
		return this.id;
	}
	
	public ObjectProperty<LocalDate> dtAgendaProperty() {
		return this.dtAgenda;
	}
	
	public ObjectProperty<LocalTime> horarioProperty() {
		return this.horario;
	}
	
	public SimpleStringProperty pacienteDadosPropery() {
		return new SimpleStringProperty(paciente.getNome());
	}
}
