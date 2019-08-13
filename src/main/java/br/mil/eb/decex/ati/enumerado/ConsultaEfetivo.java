package br.mil.eb.decex.ati.enumerado;

/**
 * Lista de Carreira
 *  
 * @author Jonathan Philipe Amaral <b>Crespo</b>3º SGT STT
 * @version 1.0
 */
public enum ConsultaEfetivo {
	
	EXISTENTE("Efetivo Existente"),
	ALUNOSCOLEGIOMILITAR("Alunos Colégio Militar"),
	ENSINOADISTANCIA("Alunos de Educação a Distância"),
	PROFESSORESCIVIS("Professores Civis"),
	INSTRUTORESMONITORES("Instrutores e Monitores"),
	PROFESSORESMILITARES("Professores Militares"),
	ALUNOSOMVINCULADAS("Alunos de Om Vinculadas"),
	ALUNOSNACOESAMIGAS("Alunos de Nações Amigas"),
	ALUNOSMILITARES("Alunos Militares"),
	ALUNOSOUTRASFORCAS("Alunos de Outras Forças"),
	ALUNOSFUNDACAOOSORIO("Alunos da Fundação Osorio");
	
	String value;
	
	private ConsultaEfetivo(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}

}
