package br.mil.eb.decex.ati.enumerado;

/**
 * Tipo de militar para Alunos militares de outras forças 
 *  
 * @author Jonathan Philipe Amaral <b>Crespo</b>3º SGT
 * @version 1.0
 */
public enum TipoAlunoOmVinculada {
	
	OFICIAL("Oficial"),
	PRACA("Praça");
	
	String value;
	
	private TipoAlunoOmVinculada(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}

}
