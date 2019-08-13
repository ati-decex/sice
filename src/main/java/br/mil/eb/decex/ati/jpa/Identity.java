package br.mil.eb.decex.ati.jpa;

import java.io.Serializable;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import br.mil.eb.decex.ati.enumerado.TipoAcesso;
import br.mil.eb.decex.ati.modelo.jaas.UserRoles;
import br.mil.eb.decex.ati.modelo.jaas.Users;

/**
 * Repositório para operações com objetos do JAAS. Users e UsersRoles
 * 
 * @author William <b>Moreira</b> de Pinho - 1° Ten QCO
 * @version 1.0
 */
public class Identity implements Serializable {

	private static final long serialVersionUID = 1L;

	@PersistenceContext(unitName="sicePU")
	private EntityManager entityManager;	
	
	/**
	 * Salva objetos Users JAAS
	 * @param users usuário JAAS
	 */
	public void persist(Users users) {
		entityManager.persist(users);
		entityManager.flush();		
	}
		
	/**
	 * Altera objetos Users JAAS
	 * @param users usuário JAAS
	 */	
	public void merge(Users users) {
		Users u = new Users();
		
		u.setName(users.getName());
		u.setPass(users.getPass());
		
		Users u_roles = find(users.getName());
		
		u_roles.getRoles().forEach(r -> u.addRole(r.getRoleName()));
		
		remove(users.getName());
		
		persist(u);
	}	
	
	/**
	 * Remove um usuário JAAS
	 * @param identidade identificador do usuário
	 */
	public void remove(String identidade) {

		Users users = find(identidade);
		
		if(users != null)
			entityManager.remove(users);
		else
			throw new RuntimeException();				
	}
	
	/**
	 * Encontra usuário JAAS
	 * @param identidade identificador
	 * @return Usuário JAAS
	 */
	public Users find(String identidade) {
		return entityManager.find(Users.class, identidade);
	}

	/**
	 * Adiciona uma nova role
	 * @param identidade identificação do usuário
	 * @param acesso tipo de role
	 */
	public void addRole(String identidade, TipoAcesso acesso) {
		
		UserRoles roles = new UserRoles();
		
		roles.setUsers(new Users(identidade));
		roles.setRoleName(acesso);
		
		entityManager.persist(roles);
	}
	
	/**
	 * Remove uma role
	 * @param identidade identificação do usuário
	 * @param acesso tipo de role
	 */	
	public void removeRole(String identidade, TipoAcesso acesso) {
		
		Query query = entityManager.createQuery(
				"DELETE FROM UserRoles r WHERE r.users.name = :identidade AND "
				+ "r.roleName = :role");
		
		query.setParameter("identidade", identidade)
			 .setParameter("role", acesso).executeUpdate();		
	}	
}