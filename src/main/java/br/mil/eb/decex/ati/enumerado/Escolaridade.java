package br.mil.eb.decex.ati.enumerado;

/**
 * Enumerador do gral de escolaridade para os Professores Civis
 *  
 * @author Luiz Augusto Lourenço do <b>Amparo</b>3º SGT QCO
 * @version 1.0
 */
public enum Escolaridade {


	DOUTOR("Doutor"),
	MESTRE("Mestre"),
	POSGRADUACAO("Pós Graduação Latu Sensu"),
	GRADUACAO("Graduação");
	
	String value;
	
	private Escolaridade(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	
}
