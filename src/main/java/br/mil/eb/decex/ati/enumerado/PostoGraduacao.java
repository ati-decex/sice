package br.mil.eb.decex.ati.enumerado;

/**
 * Enumerado dos Postos e Graduações das 
 * Forças Armadas
 * 
 * @author Luiz Augusto Lourenço do Amparo <b>Amparo</b> 3 º SGT
 * @version 1.0
 */
public enum PostoGraduacao {
	
	GEN_EX("Gen Ex"),
	GEN_DIV("Gen Div"),
	GEN_BDA("Gen Bda"),
	CEL("Cel"),
	TC("Ten Cel"),
	MAJ("Maj"),
	CAP("Cap"),
	PRI_TEN("1º Ten"),
	SEG_TEN("2º Ten"),
	ASP("Asp Of"),
	ST("S Ten"),
	PRI_SGT("1º Sgt"),
	SEG_SGT("2º Sgt"),
	TER_SGT("3º Sgt"),
	CB("Cabo"),
	SD("Soldado"),
	TM("TM"),
	T1("T1"),
	T2("T2"),
	PTTC("PTTC"),
	SV_CV("Servidor Civil"),
	PRF_CIV("Professor Civil"),
	ALUNO("Aluno");
	
	
	
	private String value;
	
	private PostoGraduacao(String value) {
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