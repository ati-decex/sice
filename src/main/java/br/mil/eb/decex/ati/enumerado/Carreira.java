package br.mil.eb.decex.ati.enumerado;

/**
 * Lista de Carreira
 *  
 * @author Luiz Augusto Lourenço do <b>Amparo</b>3º SGT QCO
 * @version 1.0
 */
public enum Carreira {

	MAGISTERIOSUPERIOR("Magistério Superior"),
	EBTT("EBTT");
	
	String value;
	
	private Carreira(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}

}
