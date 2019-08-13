package br.mil.eb.decex.ati.enumerado;

/**
 * Tipificação do Sexo
 * 
 * @author William <b>Moreira</b> de Pinho - 1º Ten QCO
 * @version 1.0
 */
public enum Sexo {

	MASCULINO("MASCULINO"),
	FEMININO("FEMININO");
	
	String value;
	
	private Sexo(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}	
	
}