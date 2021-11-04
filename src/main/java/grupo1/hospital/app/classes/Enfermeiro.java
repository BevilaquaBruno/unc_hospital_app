package grupo1.hospital.app.classes;

import java.time.LocalDate;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;

public class Enfermeiro extends Pessoa {

	private SimpleIntegerProperty id;
	private ObjectProperty<LocalDate> dtAdmissao;

	public Enfermeiro(String nome, String cpf, Integer rg, String telefone, LocalDate dtNascimento, 
			String sexo, Integer idPessoa, LocalDate dtAdmissao) {
		super(nome, cpf, rg, telefone, dtNascimento, sexo, idPessoa);
		this.dtAdmissao = new SimpleObjectProperty<LocalDate>(dtAdmissao);
	}

	/* Getters */
	public Integer getId() {
		return this.id.get();
	}
	
	public LocalDate getDtAdmissao() {
		return this.dtAdmissao.get();
	}

	/* Setters */
	public void setId(Integer id) {
		this.id = new SimpleIntegerProperty(id);
	}
	
	public void setdtAdmissao(LocalDate dtAdmissao) {
		this.dtAdmissao = new SimpleObjectProperty<LocalDate>(dtAdmissao);;
	}
	
	/* Properties */
	public SimpleIntegerProperty idProperty() {
		return this.id;
	}
	
	public ObjectProperty<LocalDate> dtAdmissaoProperty() {
		return this.dtAdmissao;
	}
	
	/* Overrides */
	@Override
	public String toString() {
		return "Nome: "+this.getNome()+" CPF: "+this.getCpf()+" RG: "+this.getRg()+" Telefone: "+this.getTelefone()+" dtAdmissao: "+this.getDtAdmissao();
	}
}
