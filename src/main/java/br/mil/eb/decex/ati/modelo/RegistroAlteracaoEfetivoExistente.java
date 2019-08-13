package br.mil.eb.decex.ati.modelo;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
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

/**
 * Realiza a solicitação de acesso de usuariao para manupulação no sistema  </p>
 * 
 * @author Luiz Augusto Lourenço do Amparo <b>Amparo</b> 3º SGT
 * @version 1.0
 */
@Entity
@NamedQueries({
	@NamedQuery(name="Pegunta.listarPorPeriodo",
			query="SELECT e FROM RegistroAlteracaoEfetivoExistente e WHERE e.periodo = :periodo AND e.organizacaoMilitar = :organizacao"),
	@NamedQuery(name="Pegunta.listarPorPeriodoOM",
			query="SELECT e FROM RegistroAlteracaoEfetivoExistente e LEFT JOIN e.organizacaoMilitar om WHERE e.periodo = :periodo AND "
					+ "om.tipo = 'ESTABELECIMENTO_ENSINO' ORDER BY om.ordem")})

//SELECT DISTINCT(om.sigla), data, resposta FROM registroalteracaoefetivoexistente e RIGHT JOIN organizacaomilitar om on e.organizacao_id = om.id WHERE om.tipo = 'ESTABELECIMENTO_ENSINO' ORDER BY data DESC

//select om.sigla, data, resposta from organizacaomilitar om
//left join registroalteracaoefetivoexistente r on r.organizacao_id = om.id and r.periodo_id = 23
//where om.tipo = 'ESTABELECIMENTO_ENSINO'
//order by om.sigla,r.id

//select om.sigla, data, resposta from registroalteracaoefetivoexistente e
//left join organizacaomilitar om on e.organizacao_id = om.id and e.periodo_id = 23
//where om.tipo = 'ESTABELECIMENTO_ENSINO'
//order by om.sigla,e.id

//SELECT e FROM registroalteracaoefetivoexistente e RIGHT JOIN organizacaomilitar om WHERE e.periodo_id = 23 AND om.tipo = 'ESTABELECIMENTO_ENSINO' ORDER BY e.id, om.sigla

@Table(name="registroAlteracaoEfetivoExistente")
public class RegistroAlteracaoEfetivoExistente extends BaseModel<Long> implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="PERGUNTA_ID_GENERATOR", sequenceName="PERGUNTA_ID_SEQ", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="PERGUNTA_ID_GENERATOR")		
	private Long id;		

	@ManyToOne(optional=false, fetch=FetchType.EAGER)
	@JoinColumn(name="periodo_id")
	private Periodo periodo;
	
	@ManyToOne(optional=false, fetch=FetchType.EAGER)
	@JoinColumn(name="organizacao_id")
	private OrganizacaoMilitar organizacaoMilitar;
	
	@ManyToOne(optional=false, fetch=FetchType.EAGER)
	@JoinColumn(name="usuario_id")
	private Usuario usuario;
	
	@Column
	private Date data;
	
	@Column
	private Boolean resposta;
	
	public RegistroAlteracaoEfetivoExistente(){
	}

	/**
	 * Identificador de tabela. Código sequencial
	 * @return chave primária da obrigação
	 */		
	@Override
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	/**
	 * Resposta para o usuario se hove alteração no lançamento.
	 * @see br.mil.eb.decex.ati.modelo.RegistroAlteracaoEfetivoExistente
	 * @return resposta
	 */
	public Boolean getResposta() {
		return resposta;
	}
	public void setResposta(Boolean resposta) {
		this.resposta = resposta;
	}
	
	/**
	 * Período de lançamento do efetivo. Cada efetivo é lançado dentro de um período.
	 * @see br.mil.eb.decex.ati.modelo.Periodo
	 * @return período de lançamento
	 */
	public Periodo getPeriodo() {
		return periodo;
	}
	public void setPeriodo(Periodo periodo) {
		this.periodo = periodo;
	}

	/**
	 * usuario logado
	 * @return usuario
	 */
	public Usuario getUsuario() {
		return usuario;
	}
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	
	/**
	 * Organização militar que possui o efetivo informado
	 * @return Organização militar que possui o efetivo informado
	 */
	public OrganizacaoMilitar getOrganizacaoMilitar() {
		return organizacaoMilitar;
	}
	public void setOrganizacaoMilitar(OrganizacaoMilitar organizacaoMilitar) {
		this.organizacaoMilitar = organizacaoMilitar;
	}
	
	/**
	 * Data de gravação do registro
	 * @return data que informa a inclusão do registro
	 */
	public Date getData() {
		return data;
	}
	public void setData(Date data) {
		this.data = data;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((resposta == null) ? 0 : resposta.hashCode());
		result = prime * result + ((organizacaoMilitar == null) ? 0 : organizacaoMilitar.hashCode());
		result = prime * result + ((usuario == null) ? 0 : usuario.hashCode());
		return result;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (!(obj instanceof RegistroAlteracaoEfetivoExistente))
			return false;
		RegistroAlteracaoEfetivoExistente other = (RegistroAlteracaoEfetivoExistente) obj;
		if (getResposta() != other.getResposta())
			return false;
		if (getOrganizacaoMilitar() != other.getOrganizacaoMilitar())
			return false;
		if (getUsuario() != other.getUsuario())
			return false;
			return true;
	}

	@Override
	public String toString() {
		return "Resposta [organizacaoMilitar=" + organizacaoMilitar + ", resposta=" + resposta  + ", usuario=" + usuario  + "]";
	}
}
