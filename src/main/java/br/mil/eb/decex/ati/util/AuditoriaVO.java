package br.mil.eb.decex.ati.util;

import java.io.Serializable;
import br.mil.eb.decex.ati.enumerado.TipoOperacao;

/**
 * Value Object com objetos necessários para registro de uma auditoria
 * 
 * @author Jonathan Philipe Amaral <b>Crespo</b> - 3º SGT
 * @version 1.0
 */
public class AuditoriaVO implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long efetivoId;
	private TipoOperacao tipoOperacao;
	private Long organizacaoId;
	private Long usuarioId;	
	
	public AuditoriaVO() {
		
	}

	public AuditoriaVO(Long efetivoId, TipoOperacao tipoOperacao, Long organizacaoId, Long usuarioId) {
		this.efetivoId = efetivoId;
		this.tipoOperacao = tipoOperacao;
		this.organizacaoId = organizacaoId;
		this.usuarioId = usuarioId;		
	}

	/**
	 * Identificador da obrigação auditada
	 * @return id da obrigação auditada
	 */
	public Long getEfetivoId() {
		return efetivoId;
	}
	public void setEfetivoId(Long efetivoId) {
		this.efetivoId = efetivoId;
	}

	/**
	 * Tipo da operação auditada
	 * @return tipo da operação auditada
	 * @see br.mil.eb.decex.ati.enumerated.TipoOperacao
	 */
	public TipoOperacao getTipoOperacao() {
		return tipoOperacao;
	}
	public void setTipoOperacao(TipoOperacao tipoOperacao) {
		this.tipoOperacao = tipoOperacao;
	}

	/**
	 * Identificador da assessoria usuário que 
	 * operou sobre a obrigação
	 * @return id da assessoria
	 */	
	public Long getOrganizacaoId() {
		return organizacaoId;
	}
	public void setOrganizacaoId(Long organizacaoId) {
		this.organizacaoId = organizacaoId;
	}		
	
	/**
	 * Identificador do usuário que 
	 * operou sobre a obrigação
	 * @return id do usuário
	 */	
	public Long getUsuarioId() {
		return usuarioId;
	}
	public void setUsuarioId(Long usuarioId) {
		this.usuarioId = usuarioId;
	}			
}