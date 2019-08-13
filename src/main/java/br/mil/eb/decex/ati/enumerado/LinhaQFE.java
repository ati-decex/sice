package br.mil.eb.decex.ati.enumerado;

/**
 * Linhas de aplicação das Armas, Quadros e Serviços
 *  
 * @author William <b>Moreira</b> de Pinho - 1º Ten QCO
 * @version 1.0
 */
public enum LinhaQFE {

	BEL("Bélica");
	
	private String value;
	
	private LinhaQFE(String value) {
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