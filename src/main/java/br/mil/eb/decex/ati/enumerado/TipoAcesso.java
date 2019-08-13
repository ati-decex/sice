package br.mil.eb.decex.ati.enumerado;

/**
 * Enumerado para definição dos perfis do usuário
 * 
 * @author William Moreira de Pinho - 1º Ten QCO
 * @version 1.0
 */
public enum TipoAcesso {
	USUARIO("USUARIO"),
	ADMINISTRADOR("ADMINISTRADOR"),
	DIRETORIA("DIRETORIA"),
	AUDITORIA("AUDITORIA");
	
	String value;
	
	private TipoAcesso(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
}