package grupo1.hospital.app.classes;

import java.time.LocalDate;
import java.time.LocalTime;

public class Agenda {
	private Integer id;
	private LocalDate dtAgenda;
	private LocalTime horario;
	private Paciente paciente;

	public Agenda(LocalDate dtAgenda, LocalTime horario, Paciente paciente) {
		this.dtAgenda = dtAgenda;
		this.horario = horario;
		this.paciente = paciente;
	}

	/* Getters */
	public Integer getId() {
		return this.id;
	}
	
	public LocalDate getDtAgenda() {
		return this.dtAgenda;
	}
	
	public LocalTime getHorario() {
		return this.horario;
	}
	
	public Paciente getPaciente() {
		return this.paciente;
	}

	/* Setters */
	public void setId(Integer id) {
		this.id = id;
	}
	
	public void setDtAgenda(LocalDate dtAgenda) {
		this.dtAgenda = dtAgenda;
	}
	
	public void setHorario(LocalTime horario) {
		this.horario = horario;
	}
	
	public void setPaciente(Paciente paciente) {
		this.paciente = paciente;
	}
}
