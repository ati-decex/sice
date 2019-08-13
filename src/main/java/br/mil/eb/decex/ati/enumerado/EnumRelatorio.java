package br.mil.eb.decex.ati.enumerado;

/**
 * Enumerado para identificação das OMS cadastradas 
 * 
 * @author Luiz Augusto Lourenço do Amparo <b>Amparo</b> - 3º SGT
 * @version 1.0
 */
public enum EnumRelatorio {
	
	RELATORIO_GERAL_ESPECIFICO_OM("Efetivo Previsto e Existente"),
	RELATORIO_INSTRUTORES_E_MONITORES("Intrutores e Monitores"),
	RELATORIO_ALUNOS_MILITARES("Alunos Militares"),
	RELATORIO_MILITARES_DE_NACOES_AMIGAS("Militares de Nações Amigas"),
	RELATORIO_ALUNOS_DE_ENSINO_A_DISTANCIA("Alunos de Ensino a Distância"),
	RELATORIO_ALUNOS_DE_OM_VINCULADAS("Alunos de OM Vinculadas"),
	RELATORIO_PROFESSORES_MILITARES("Professores Militares"),
	RELATORIO_PROFESSORES_CIVIS("Professores Civis"),
	RELATORIO_ALUNOS_DE_COLEGIOS_MILITARES("Alunos de Colégios Militares"),
	RESUMO_GERAL("Resumo Geral"),
	AUDITORIA("Auditoria");
	
	String value;
	
	private EnumRelatorio(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
}
