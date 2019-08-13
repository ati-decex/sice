package br.mil.eb.decex.ati.modelo.jaas;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import br.mil.eb.decex.ati.enumerado.TipoAcesso;
import br.mil.eb.decex.ati.modelo.BaseModel;

/**
 * Perfis do usuário JAAS. ADMINISTRADOR e/ou USUARIO
 * 
 * @author William <b>Moreira</b> de Pinho - 1° Ten QCO
 * @version 1.0
 */
@Entity
@NamedQueries({
	@NamedQuery(name="userRoles.poruserName",
		query="SELECT q FROM UserRoles q WHERE q.users.name = :users")
})
@Table(name="user_roles")
public class UserRoles extends BaseModel<Long> implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@ManyToOne
	@JoinColumn(name="user_name")	
	private Users users;
	
	@Id
	@Column(name="role_name")
	@Enumerated(EnumType.STRING)
	private TipoAcesso roleName;

	
	/**
	* Usuário JAAS
	* @return usuario JAAS
	*/
	public Users getUsers() {
		return users;
	}
	public void setUsers(Users users) {
		this.users = users;
	}

	/**
	 * Tipo de perfil. USUARIO e/ou ADMINISTRADOR
	 * @return perfil do usuário
	 */
	public TipoAcesso getRoleName() {
		return roleName;
	}
	public void setRoleName(TipoAcesso roleName) {
		this.roleName = roleName;
	}
	
	/**
	 * Propriedade não utilizada
	 */
	@Override
	public Long getId() {
		return null;
	}
	
	public UserRoles() {
		
	}
	
	public UserRoles(Users users, TipoAcesso roleName) {
		this.users = users;
		this.roleName = roleName;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((roleName == null) ? 0 : roleName.hashCode());
		result = prime * result + ((users == null) ? 0 : users.hashCode());
		return result;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof UserRoles))
			return false;
		UserRoles other = (UserRoles) obj;
		if (getRoleName() != other.getRoleName())
			return false;
		if (getUsers() == null) {
			if (other.getUsers() != null)
				return false;
		} else if (!getUsers().equals(other.getUsers()))
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return "UserRoles [users=" + users + ", roleName=" + roleName + "]";
	}
}