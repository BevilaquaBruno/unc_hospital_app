package grupo1.hospital.app.classes;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Servico {
	private SimpleIntegerProperty id;
	private SimpleStringProperty descricao;
	private Medico medico;
	private Paciente paciente;
	private Enfermeiro enfermeiro;

	public Servico(String descricao, Medico medico, Paciente paciente, Enfermeiro enfermeiro){
		this.descricao = new SimpleStringProperty(descricao);
		this.setMedico(medico);
		this.setPaciente(paciente);
		this.setEnfermeiro(enfermeiro);
	}

	/* Getters */
	public Integer getId() {
		return this.id.get();
	}
	
	public String getDescricao() {
		return this.descricao.get();
	}

	/* Setters */
	public void setId(Integer id) {
		this.id = new SimpleIntegerProperty(id);
	}
	
	public void setDescricao(String descricao) {
		this.descricao = new SimpleStringProperty(descricao);
	}

	public Medico getMedico() {
		return medico;
	}

	public void setMedico(Medico medico) {
		this.medico = medico;
	}

	public Enfermeiro getEnfermeiro() {
		return enfermeiro;
	}

	public void setEnfermeiro(Enfermeiro enfermeiro) {
		this.enfermeiro = enfermeiro;
	}

	public Paciente getPaciente() {
		return paciente;
	}

	public void setPaciente(Paciente paciente) {
		this.paciente = paciente;
	}
	/* properties */
	public SimpleIntegerProperty idProperty() {
		return this.id;
	}
	
	public SimpleStringProperty descricaoProperty() {
		return this.descricao;
	}
}
