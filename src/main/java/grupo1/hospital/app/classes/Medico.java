package grupo1.hospital.app.classes;

import java.time.LocalDate;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Medico extends Pessoa {

	private SimpleIntegerProperty id;
	private SimpleStringProperty especialidade;

	public Medico(String nome, String cpf, Integer rg, String telefone, LocalDate dtNascimento, 
			String sexo, Integer idPessoa, String especialidade) {
		super(nome, cpf, rg, telefone, dtNascimento, sexo, idPessoa);
		this.especialidade = new SimpleStringProperty(especialidade);
	}

	/* Getters */
	public Integer getId() {
		return this.id.get();
	}
	
	public String getEspecialidade() {
		return this.especialidade.get();
	}

	/* Setters */
	public void setId(Integer id) {
		this.id = new SimpleIntegerProperty(id);
	}
	
	public void setEspecialidade(String especialidade) {
		this.especialidade = new SimpleStringProperty(especialidade);
	}
	
	/* properties */
	
	public SimpleIntegerProperty idProperty() {
		return this.id;
	}
	
	public SimpleStringProperty especialidadeProperty() {
		return this.especialidade;
	}
	
	@Override
	public String toString() {
		return super.toString()+", "+this.getId()+", "+this.getEspecialidade();
	}
}
