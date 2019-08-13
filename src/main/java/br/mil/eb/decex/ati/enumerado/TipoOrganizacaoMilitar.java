package br.mil.eb.decex.ati.enumerado;

/**
 * Enumerado para identificação das OMS cadastradas 
 * 
 * @author Luiz Augusto Lourenço do Amparo <b>Amparo</b> - 3º SGT
 * @version 1.0
 */
public enum TipoOrganizacaoMilitar{
	
	DEPARTAMENTO("Departamento"),
	DIRETORIA("Diretoria"),
	ESTABELECIMENTO_ENSINO("Estabelecimento de Ensino"),
	VINCULADA("Vinculada");
	
	String value;
	
	private TipoOrganizacaoMilitar(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
}