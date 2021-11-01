package grupo1.hospital.app.classes;

import java.time.LocalDate;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

public class Pessoa {
	/* Attributes */
	private SimpleStringProperty nome;
	private SimpleStringProperty cpf;
	private SimpleIntegerProperty rg;
	private SimpleStringProperty telefone;
	private SimpleIntegerProperty id;
	private ObjectProperty<LocalDate> dtNascimento;
	private SimpleStringProperty sexo;
	
	public Pessoa (String nome, String cpf, Integer rg, String telefone, LocalDate dtNascimento, String sexo, Integer id) {
		this.nome = new SimpleStringProperty(nome);
		this.cpf= new SimpleStringProperty(cpf);
		this.rg = new SimpleIntegerProperty(rg);
		this.telefone = new SimpleStringProperty(telefone);
		this.dtNascimento = new SimpleObjectProperty< LocalDate >(dtNascimento);
		this.sexo = new SimpleStringProperty(sexo);
		this.id = new SimpleIntegerProperty(id);
	}

	/* Getters */
	public String getNome() {
		return this.nome.get();
	}

	public String getCpf() {
		return this.cpf.get();
	}

	public Integer getRg() {
		return this.rg.get();
	}
	
	public String getTelefone() {
		return this.telefone.get();
	}
	
	public LocalDate getDtNascimento() {
		return this.dtNascimento.get();
	}
	
	public Integer getIdPessoa() {
		return this.id.get();
	}
	
	public String getSexo() {
		return this.sexo.get();
	}

	/* Setters */
	public void setNome(String nome) {
		this.nome = new SimpleStringProperty(nome);
	}
	
	public void setIdPessoa(Integer id) {
		this.id = new SimpleIntegerProperty(id);
	}

	public void setCpf(String cpf) {
		this.cpf= new SimpleStringProperty(cpf);
	}

	public void setRg(Integer rg) {
		this.rg = new SimpleIntegerProperty(rg);
	}
	
	public void setTelefone(String telefone) {
		this.telefone = new SimpleStringProperty(telefone);
	}
	
	public void setDtNascimento(LocalDate dtNascimento) {
		this.dtNascimento = new SimpleObjectProperty< LocalDate >(dtNascimento);
	}

	public void setSexo(String sexo) {
		this.sexo = new SimpleStringProperty(sexo);
	}

	/* PROPERTY */
	public SimpleStringProperty nomeProperty() {
		return this.nome;
	}

	public SimpleStringProperty cpfProperty() {
		return this.cpf;
	}

	public SimpleIntegerProperty rgProperty() {
		return this.rg;
	}
	
	public SimpleStringProperty telefoneProperty() {
		return this.telefone;
	}
	
	public ObjectProperty<LocalDate> dtNascimentoProperty() {
		return this.dtNascimento;
	}
	
	public SimpleIntegerProperty idPessoaProperty() {
		return this.id;
	}

	@Override
	public String toString() {
		return this.getNome()+", "+this.getCpf()+", "+this.getSexo()+", "+this.getIdPessoa()
			+", "+this.getRg()+", "+this.getDtNascimento()+";";
	}
}