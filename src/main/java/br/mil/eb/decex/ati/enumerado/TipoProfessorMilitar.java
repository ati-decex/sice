package br.mil.eb.decex.ati.enumerado;

/**
 * Tipificação dos Tipos de professores militar
 * 
 * @author Jonathan Philipe Amaral <b>Crespo</b> - 3º SGT
 * @version 1.0
 */
public enum TipoProfessorMilitar {

	PROFESSOR_EM_COMISSAO("PROFESSOR EM COMISSÃO"),
	PROFESSOR_PERMANENTE("PROFESSOR PERMANENTE"),
	
	DSA("DSA"),
	QCO("QCO"),
	TEMPORARIO("TEMPORÁRIO"),
	PTTC("PTTC"),
	SITUACAO_ESPECIFICA_PRACA("SITUAÇÃO ESPECÍFICA PRAÇA");
	
	String value;
	
	private TipoProfessorMilitar(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
}
