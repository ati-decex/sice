package br.mil.eb.decex.ati.enumerado;

/**
 * Lista das séries dos colegios militares
 *  
 * @author Luiz Augusto Lourenço do <b>Amparo</b>3º SGT QCO
 * @version 1.0
 */
public enum Serie {
	
	
	ANO6EF("6° ANO EF"),
	ANO7EF("7° ANO EF"),
	ANO8EF("8° ANO EF"),
	ANO9EF("9° ANO EF"),
	ANO1EM("1° ANO EM"),
	ANO2EM("2° ANO EM"),
	ANO3EM("3° ANO EM");
	
	String value;
	
	private Serie(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}

}
