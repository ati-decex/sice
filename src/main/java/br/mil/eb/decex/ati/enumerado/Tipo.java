package br.mil.eb.decex.ati.enumerado;

/**
 * Tipificação das Armas, Quadros e ServiçosQEMA
 * 
 * @author William <b>Moreira</b> de Pinho - 1º Ten QCO
 * @version 1.0
 */
public enum Tipo {

	QEMA("QEMA"),
	QSG("QSG"),
	QCO("QCO"),
	QAO("QAO"),
	CARR("Carreira"),
	QFE("QFE"),
	QE("QE"),
	LOF("Linha de Oficial"),
	LPR("Linha de Praça"),
	TMPR("Temporário"),
	EV("Efetivo Variável"),
	EP("Efetivo Profissional"),
	ET("Especialista Temporário"),
	NQ("Não Qualificado"),
	PTTC("PTTC"),
	SV_CV("Servidor Civil"),
	PRF_CIV("Professor Civil"),
	ALUNO("Aluno"),
	TAIF("Taifeiro"),
	DEFAULT("Nenhum");
	
	String value;
	
	private Tipo(String value) {
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