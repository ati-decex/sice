package br.mil.eb.decex.ati.enumerado;

/**
 * Enumerador do gral de escolaridade para os Alunos Civis
 *  
 * @author Jonathan Philipe Amaral <b>Crespo</b> 3º SGT STT
 * @version 1.0
 */
public enum TipoEscolaridade {

	POSDOUTORADO("Pós Doutorado"),
	MESTRE("Mestre"),
	DOUTORADO("Doutorado"),
	POSGRADUACAO("Pós Graduação Latu Sensu");
	
	String value;
	
	private TipoEscolaridade(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	
}
