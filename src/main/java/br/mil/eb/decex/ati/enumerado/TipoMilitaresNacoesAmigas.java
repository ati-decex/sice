package br.mil.eb.decex.ati.enumerado;

/**
 * Tipificação dos Tipos de militares das Nações amigas
 * 
 * @author Jonathan Philipe Amaral <b>Crespo</b> - 3º SGT
 * @version 1.0
 */
public enum TipoMilitaresNacoesAmigas {
	
	OFICIAL("OFICIAL"),
	PRACA("PRACA"),
	ALUNO_OF("ALUNO OFICIAL"),
	ALUNO_PR("ALUNO PRAÇA");
	
	String value;
	
	private TipoMilitaresNacoesAmigas(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
}
