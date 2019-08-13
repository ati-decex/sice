package br.mil.eb.decex.ati.enumerado;

/**
 * Tipo de militar para Alunos militares de outras forças 
 *  
 * @author Luiz Augusto Lourenço do <b>Amparo</b>3º SGT
 * @version 1.0
 */
public enum TipoAlunosMilitarOutrasForcas {

	OFICIAL("Oficial"),
	PRACA("Praça");
	
	String value;
	
	private TipoAlunosMilitarOutrasForcas(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	
}
