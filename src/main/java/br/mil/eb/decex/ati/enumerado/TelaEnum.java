package br.mil.eb.decex.ati.enumerado;

/**
 * Linhas de aplicação das Armas, Quadros e Serviços
 *  
 * @author William <b>Moreira</b> de Pinho - 1º Ten QCO
 * @version 1.0
 */
public enum TelaEnum {

	EAD("Ensino a Distancia"),
	ALUNOSCOLEGIOMILITAR("Alunos Colegios Militar"),
	ALUNOSFUNDACAOOSORIO("Alunos Funcação Osorio"),
	EFETIVOEXISTENTE("Efetivo existente"),
	ALUNOSCIVIS("Alunos Civis"),
	ALUNOSMILITARES("Alunos Militares Outras Forças"),
	ALUNOSMILITARESOMDS("Alunos Militares"),
	PROFESSORCIVIL("Professor Civil"),
	PROFESSORMILITAR("Professor Militar"),
	INTRUTORMONITORES("Instrutor e Monitores");
	
	
	private String value;
	
	private TelaEnum(String value) {
		this.value = value;
	}
	
	public int getKey(){
		return ordinal();
	}
	
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
}