package br.mil.eb.decex.ati.enumerado;

/**
 * Linhas de aplicação das Armas, Quadros e Serviços
 *  
 * @author William <b>Moreira</b> de Pinho - 1º Ten QCO
 * @version 1.0
 */
public enum Linha {

	BEL("Bélica"),
	COMPL("Complementar"),
	SAUDE("Saúde"),
	LCeT("Ciência e Tecnologia"),
	QCM("Capelão Militar"),
	COMB("Combatente"),
	LPR("Praças"),
	DEFAULT("Nenhum");
	
	private String value;
	
	private Linha(String value) {
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