package br.mil.eb.decex.ati.modelo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Transient;

import br.mil.eb.decex.ati.enumerado.PostoGraduacao;
import br.mil.eb.decex.ati.enumerado.TipoAcesso;
import br.mil.eb.decex.ati.modelo.jaas.Users;
import br.mil.eb.decex.ati.util.EncodingSHA256;

/**
 * Usuarios habilitados a entrar com registros no 
 * SICE.
 * 
 * @author William <b>Moreira</b> de Pinho - 1º Ten QCO
 * @version 1.0
 */
@Entity
@NamedQueries({
	@NamedQuery(name = "Usuario.ativos", query = "SELECT u FROM Usuario u WHERE u.liberado is FALSE order by u.postoGraduacao "),
	@NamedQuery(name = "Usuario.buscarUsuario", query = "SELECT u FROM Usuario u WHERE u.liberado is FALSE "),
	@NamedQuery(name = "Usuario.Keyword", query = "SELECT u FROM Usuario u WHERE ((lower(u.nome) like lower('%'||:keyword||'%')) OR (lower(u.nomeGuerra) like lower('%'||:keyword||'%')) OR (lower(u.identidade) like lower('%'||:keyword||'%')) OR (lower(u.organizacaoMilitar.sigla) like lower('%'||:keyword||'%'))) ")})            
	

public class Usuario extends BaseModel<Long> implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="USUARIO_ID_GENERATOR", sequenceName="USUARIO_ID_SEQ", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="USUARIO_ID_GENERATOR")	
	private Long id;
		
	@ManyToOne(optional=false, fetch=FetchType.EAGER)
	@JoinColumn(name="organizacao_id")
	private OrganizacaoMilitar organizacaoMilitar;
	
	@Transient
	private Users users;
	
	@Transient
	private List<TipoAcesso> listaTipoAcesso;
	
	@Column(unique=true)
	private String identidade;
	
	@Column
	private String nome;
	
	@Column
	private String nomeGuerra;
	
	@Enumerated(EnumType.STRING)
	private PostoGraduacao postoGraduacao;
			
	@Column
	private Boolean liberado;
	
	@Column
	private String email;
	
	@Column
	private String ritex;
	
	@Column
	private String tel_funcional;
	
	@Column
	private Boolean excluido;
	
	
	public Usuario(){
		liberado = Boolean.FALSE;
		excluido = Boolean.FALSE;
	}
	
	/**
	 * Identificador de tabela. Código sequencial
	 * @return chave primária
	 */	
	@Override
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * Organização militar do usuário	
	 * @return organização militar do usuário
	 */
	public OrganizacaoMilitar getOrganizacaoMilitar() {
		return organizacaoMilitar;
	}
	public void setOrganizacaoMilitar(OrganizacaoMilitar organizacaoMilitar) {
		this.organizacaoMilitar = organizacaoMilitar;
	}

	/**
	 * Identidade do militar
	 * @return identidade do militar 
	 */	
	public String getIdentidade() {
		return identidade;
	}
	public void setIdentidade(String identidade) {
		this.identidade = identidade;
	}

	/**
	 * Nome do militar
	 * @return nome do militar 
	 */
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}

	/**
	 * Nome de guerra do militar
	 * @return nome de guerra do militar
	 */
	public String getNomeGuerra() {
		return nomeGuerra;
	}
	public void setNomeGuerra(String nomeGuerra) {
		this.nomeGuerra = nomeGuerra;
	}

	/**
	 * Posto/Graduação do militar
	 * @return posto/graduação do militar
	 */
	public PostoGraduacao getPostoGraduacao() {
		return postoGraduacao;
	}
	public void setPostoGraduacao(PostoGraduacao postoGraduacao) {
		this.postoGraduacao = postoGraduacao;
	}
			
	/**
	 * Indica liberação para acesso ao sistema
	 * @return true-> Acesso liberado <br/>false-> Acesso negado
	 */
	public Boolean getLiberado() {
		return liberado;
	}
	public void setLiberado(Boolean liberado) {
		this.liberado = liberado;
	}	
	
	/**
	 * E-mail do militar
	 * @return email do militar
	 */
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	/**
	 * Ritex do militar
	 * @return ritex do militar
	 */
	public String getTel_funcional() {
		return tel_funcional;
	}
	public String getRitex() {
		return ritex;
	}
	
	/**
	 * Telefone funcional do militar
	 * @return tel_funcional do militar
	 */
	public void setRitex(String ritex) {
		this.ritex = ritex;
	}
	public void setTel_funcional(String tel_funcional) {
		this.tel_funcional = tel_funcional;
	}
	
	/**
	 * Lista com os perfis do usuário
	 */
	public Users getUsers() {
		return users;
	}
	public void setUsers(Users users) {
		this.users = users;
	}
	
	public Boolean getExcluido() {
		return excluido;
	}
	public void setExcluido(Boolean excluido) {
		this.excluido = excluido;
	}

	public List<TipoAcesso> getListaTipoAcesso() {
		if (listaTipoAcesso == null){
			listaTipoAcesso = new ArrayList<TipoAcesso>();
		}
		
		return listaTipoAcesso;
	}
	public void setListaTipoAcesso(List<TipoAcesso> listaTipoAcesso) {
		this.listaTipoAcesso = listaTipoAcesso;
	}

	/**
	 * Realiza parse para usuário JAAS. Na liberação do usuário 
	 * para acesso ao sistema, por convenção a senha será a identidade 
	 * criptografada
	 * 
	 * @return parse para usuário JAAS
	 */
	public Users parseUsers() {
		Users users = new Users();
		
		users.setName(identidade);
		users.addRole(TipoAcesso.USUARIO);
		users.setPass(EncodingSHA256.encodingBase64(identidade));
		
		return users;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((identidade == null) ? 0 : identidade.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Usuario))
			return false;
		Usuario other = (Usuario) obj;
		if (getIdentidade() == null) {
			if (other.getIdentidade() != null)
				return false;
		} else if (!getIdentidade().equals(other.getIdentidade()))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Usuario [identidade=" + identidade + ", nomeGuerra=" + nomeGuerra + email + ritex + tel_funcional +"]";
	}

}