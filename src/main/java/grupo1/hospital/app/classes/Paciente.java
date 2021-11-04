package grupo1.hospital.app.classes;

import java.time.LocalDate;

import javafx.beans.property.SimpleIntegerProperty;

public class Paciente extends Pessoa {
	
	private SimpleIntegerProperty id;
	private SimpleIntegerProperty gravidade;

	public Paciente(String nome, String cpf, Integer rg, String telefone, LocalDate dtNascimento, 
			String sexo, Integer idPessoa, Integer gravidade) {
		super(nome, cpf, rg, telefone, dtNascimento, sexo, idPessoa);
		this.gravidade = new SimpleIntegerProperty(gravidade);
	}

	/* Getters */
	public Integer getId() {
		return this.id.get();
	}
	
	public Integer getGravidade() {
		return this.gravidade.get();
	}

	/* Setters */
	public void setId(Integer id) {
		this.id = new SimpleIntegerProperty(id);
	}
	
	public void setGravidade(Integer gravidade) {
		this.gravidade = new SimpleIntegerProperty(gravidade);
	}
	
	/*Property */
	public SimpleIntegerProperty idProperty() {
		return this.id;
	}
	
	public SimpleIntegerProperty gravidadeProperty() {
		return this.gravidade;
	}
	
	/* Overrides */
	@Override
	public String toString() {
		return "Nome: "+this.getNome()+" CPF: "+this.getCpf()+" RG: "+this.getRg()+" Telefone: "+this.getTelefone()+" Gravidade: "+this.getGravidade();
	}
}
