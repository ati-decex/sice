package br.mil.eb.decex.ati.enumerado;

/**
 * Enumerado dos Postos e Graduações das 
 * Forças Armadas
 * 
 * @author William <b>Moreira</b> de Pinho - 1º Ten QCO
 * @version 1.0
 */
public enum PostoGraduacaoEspecialidade {

	OFI("Oficial"),
	PRA("Praça");
	
	
	private String value;
	
	private PostoGraduacaoEspecialidade(String value) {
		this.value = value;
	}
	
	public int getKey(){
		return ordinal();
	}

	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
}