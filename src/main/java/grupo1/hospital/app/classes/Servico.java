package grupo1.hospital.app.classes;


public class Servico {
	private Integer id;
	private String descricao;
	private Medico medico;
	private Paciente paciente;
	private Enfermeiro enfermeiro;

	public Servico(String descricao, Medico medico, Paciente paciente, Enfermeiro enfermeiro){
		this.descricao = descricao;
		this.setMedico(medico);
		this.setPaciente(paciente);
		this.setEnfermeiro(enfermeiro);
	}

	/* Getters */
	public Integer getId() {
		return this.id;
	}
	
	public String getDescricao() {
		return this.descricao;
	}

	/* Setters */
	public void setId(Integer id) {
		this.id = id;
	}
	
	public void setDescricao(String descricao) {
		this.descricao = descricao;
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
}
