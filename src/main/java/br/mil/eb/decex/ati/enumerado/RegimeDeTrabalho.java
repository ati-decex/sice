package br.mil.eb.decex.ati.enumerado;

/**
 * Lista de Regime de trabalho para professores civis 
 *  
 * @author Luiz Augusto Lourenço do <b>Amparo</b>3º SGT QCO
 * @version 1.0
 */
public enum RegimeDeTrabalho {

	QUARENTAHCOMDEDICACAOEXCLUSIVA("40 horas com Dedicação exclusiva"),
	QUARENTAHSEMDEDICACAOEXCLUSIVA("40 horas sem Dedicação exclusiva"),
	VINTEH("20 horas");
	
	String value;
	
	private RegimeDeTrabalho(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}

}
