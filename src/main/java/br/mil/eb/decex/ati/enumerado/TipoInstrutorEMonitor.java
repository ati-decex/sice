package br.mil.eb.decex.ati.enumerado;

/**
 * Tipo de instrutor para cadastramento de efetivo de instrutores e monitores 
 *  
 * @author Luiz Augusto Lourenço do <b>Amparo</b>3º SGT QCO
 * @version 1.0
 */
public enum TipoInstrutorEMonitor {

	MONITOR("Monitor"),
	INSTRUTOR("Instrutor");
	
	String value;
	
	private TipoInstrutorEMonitor(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	
}
