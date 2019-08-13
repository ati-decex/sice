package br.mil.eb.decex.ati.enumerado;

/**
 * Lista das séries da funcaçao Osorio
 *  
 * @author Luiz Augusto Lourenço do <b>Amparo</b>3º SGT QCO
 * @version 1.0
 */
public enum SerieFundacaoOsorio {

	ANO1F("1° ANO ENSINO FUNDAMENTAL"),
	ANO2F("2° ANO ENSINO FUNDAMENTAL"),
	ANO3F("3° ANO ENSINO FUNDAMENTAL"),
	ANO4F("4° ANO ENSINO FUNDAMENTAL"),
	ANO5F("5° ANO ENSINO FUNDAMENTAL"),
	ANO6F("6° ANO ENSINO FUNDAMENTAL"),
	ANO7F("7° ANO ENSINO FUNDAMENTAL"),
	ANO8F("8° ANO ENSINO FUNDAMENTAL"),
	ANO9F("9° ANO ENSINO FUNDAMENTAL"),
	ANO1M("1° ANO ENSINO MÉDIO"),
	ANO2M("2° ANO ENSINO MÉDIO"),
	ANO3M("3° ANO ENSINO MÉDIO");
	
	String value;
	
	private SerieFundacaoOsorio(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
}

