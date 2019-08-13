package br.mil.eb.decex.ati.modelo;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 * Tela para cadastramento dos tipos de efetivos. Classe para base
 * de montagem do wizard do sistema, uma vez que as telas são específicas 
 * por Organização militar
 * 
 * @author Fernando <b>Nunes</b> de Almeida - Ten
 * @version 1.0
 */
@Entity
@NamedQueries({
    @NamedQuery(name="ComboTela.porOmETela",
                query="SELECT c FROM ComboTela c WHERE c.organizacaoUsuario = :omUsuario AND c.tela = :tela ORDER BY c.organizacaoVinculadaParaTela.sigla"),
    @NamedQuery(name="ComboTela.porOm",
    			query="SELECT c FROM ComboTela c WHERE c.organizacaoUsuario = :omUsuario ORDER BY c.organizacaoVinculadaParaTela.ordem")
}) 
@Table(name="combotela", uniqueConstraints={@UniqueConstraint(columnNames={"tela_id","organizacao_usuario_id","organizacao_vinculada_para_tela_id"})})
public class ComboTela extends BaseModel<Long> implements Serializable {
	
	private static final long serialVersionUID = 1L;	
	
	@Id
	@SequenceGenerator(name="COMBOTELA_ID_GENERATOR", sequenceName="COMBOTELA_ID_SEQ", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="COMBOTELA_ID_GENERATOR")		
	private Long id;		
	
	@ManyToOne(optional=false, fetch=FetchType.EAGER)
	@JoinColumn(name="tela_id")
	private Tela tela;
	
	@ManyToOne(optional=false, fetch=FetchType.EAGER)
	@JoinColumn(name="organizacao_usuario_id")
	private OrganizacaoMilitar organizacaoUsuario;
	
	@ManyToOne(optional=false, fetch=FetchType.EAGER)
	@JoinColumn(name="organizacao_vinculada_para_tela_id")
	private OrganizacaoMilitar organizacaoVinculadaParaTela;

	@Override
	public Long getId() {
		return null;
	}
	
	/**
	 * Guarda as informações da organização militar do usuário
	 * @return organizacaoUsuario
	 */
	public OrganizacaoMilitar getOrganizacaoUsuario() {
		return organizacaoUsuario;
	}
	public void setOrganizacaoUsuario(OrganizacaoMilitar organizacaoUsuario) {
		this.organizacaoUsuario = organizacaoUsuario;
	}

	/**
	 * Guarda as informações da organização militar vinculada a OM do usuário
	 * @return organizacaoVinculadaParaTela
	 */
	public OrganizacaoMilitar getOrganizacaoVinculadaParaTela() {
		return organizacaoVinculadaParaTela;
	}
	public void setOrganizacaoVinculadaParaTela(OrganizacaoMilitar organizacaoVinculadaParaTela) {
		this.organizacaoVinculadaParaTela = organizacaoVinculadaParaTela;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((tela == null) ? 0 : tela.hashCode());
		result = prime * result + ((getOrganizacaoUsuario() == null) ? 0 : getOrganizacaoUsuario().hashCode());
		result = prime * result + ((getOrganizacaoVinculadaParaTela() == null) ? 0 : getOrganizacaoVinculadaParaTela().hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ComboTela other = (ComboTela) obj;
		if (tela == null) {
			if (other.tela != null)
				return false;
		} else if (!tela.equals(other.tela))
			return false;
		if (getOrganizacaoUsuario() != other.getOrganizacaoUsuario())
			return false;
		if (getOrganizacaoVinculadaParaTela() != other.getOrganizacaoVinculadaParaTela())
			return false;
		return true;
	}

	@Override
	public String toString() {
		return tela.getNome().toString()+this.getOrganizacaoUsuario().getSigla();
	}
			
}