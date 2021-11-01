package grupo1.hospital.app.classes;

import java.time.LocalDate;

public class Enfermeiro extends Pessoa {

	private Integer id;
	private LocalDate dtAdmissao;

	public Enfermeiro(String nome, String cpf, Integer rg, String telefone, LocalDate dtNascimento, 
			String sexo, Integer idPessoa, LocalDate dtAdmissao) {
		super(nome, cpf, rg, telefone, dtNascimento, sexo, idPessoa);
		this.dtAdmissao = dtAdmissao;
	}

	/* Getters */
	public Integer getId() {
		return this.id;
	}
	
	public LocalDate getDtAdmissao() {
		return this.dtAdmissao;
	}

	/* Setters */
	public void setId(Integer id) {
		this.id = id;
	}
	
	public void setdtAdmissao(LocalDate dtAdmissao) {
		this.dtAdmissao = dtAdmissao;
	}
	
	/* Overrides */
	@Override
	public String toString() {
		return "Nome: "+this.getNome()+" CPF: "+this.getCpf()+" RG: "+this.getRg()+" Telefone: "+this.getTelefone()+" dtAdmissao: "+this.getDtAdmissao();
	}
}
