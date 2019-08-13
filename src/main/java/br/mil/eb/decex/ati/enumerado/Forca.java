package br.mil.eb.decex.ati.enumerado;

/**
 * Enumerador das forças armadas para alunos de nações amigas
 *  
 * @author Luiz Augusto Lourenço do <b>Amparo</b>3º SGT QCO
 * @version 1.0
 */
public enum Forca {

	MARINHA("Marinha"),
	FORCAAEREA("Força Aérea"),
	FORCAAUXILIAR("Polícia Militar"),
	BOMBEIROS("Bombeiros");
	
	String value;
	
	private Forca(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	
}
