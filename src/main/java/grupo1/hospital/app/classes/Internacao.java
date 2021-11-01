package grupo1.hospital.app.classes;

import java.time.LocalTime;

public class Internacao {
	private Integer id;
	private LocalTime horario;
	private String quarto;
	private Paciente paciente;
	private Pessoa acompanhante;
	
	public Internacao(LocalTime horario, String quarto, Paciente paciente, Pessoa acompanhante) {
		this.horario = horario;
		this.quarto = quarto;
		this.paciente = paciente;
		this.acompanhante = acompanhante;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public LocalTime getHorario() {
		return horario;
	}

	public void setHorario(LocalTime horario) {
		this.horario = horario;
	}

	public String getQuarto() {
		return quarto;
	}

	public void setQuarto(String quarto) {
		this.quarto = quarto;
	}

	public Paciente getPaciente() {
		return paciente;
	}

	public void setPaciente(Paciente paciente) {
		this.paciente = paciente;
	}

	public Pessoa getAcompanhante() {
		return acompanhante;
	}

	public void setAcompanhante(Pessoa acompanhante) {
		this.acompanhante = acompanhante;
	}
}
