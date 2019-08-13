package br.mil.eb.decex.ati.enumerado;

/**
 * Enumerador de ano para clasificar o periodo dos alunos militares OMDS
 * 
 * @author Luiz Augusto Lourenço do <b>Amparo</b> - 3º SGT
 * @version 1.0
 */
public enum Ano {
	
	PRIMEIRO("1º ANO"),
	SEGUNDO("2º ANO"),
	TERCEIRO("3º ANO"),
	QUARTO("4º ANO");
	
	String value;
	
	private Ano(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
}

