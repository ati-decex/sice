package br.mil.eb.decex.ati.seguranca;

import java.io.IOException;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import br.mil.eb.decex.ati.modelo.Usuario;
import br.mil.eb.decex.ati.jpa.Repository;

/**
 * Após a autenticação via JAAS, recupera o registro do usuário na base e 
 * adiciona a sessão do sistema
 * 
 * @author William Moreira de Pinho - 1º Ten QCO
 * @version 1.0
 */
@WebFilter(urlPatterns="/restrito/*")
public class LoginFilter implements Filter {

	@Inject
	@Autenticado
	private Event<Usuario> loginEventSrc;

	@Inject
	@Autenticado	
	private Usuario usuario;
	
	@Inject
	private Repository repository;
	
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
	}

	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
			throws IOException, ServletException {
		
		HttpServletRequest request = (HttpServletRequest) servletRequest;
		
		String userName = request.getRemoteUser();
		
		/*
		 * O escopo de aplicação mantem a instancia do usuário no método produtor, 
		 * aqui verificamos se trata-se de um novo login, na condição abaixo. Se for 
		 * o caso de novo login, mata o usuário e reexecuta a query
		 */
		if(usuario != null && !usuario.getIdentidade().equals(userName))
			usuario = null;
		
		usuario = (usuario == null ? repository.encontrar(Usuario.class, "identidade", userName).get(0) : usuario);
						
		loginEventSrc.fire(usuario);
		
		filterChain.doFilter(servletRequest, servletResponse);				
	}

	@Override
	public void destroy() {
	}
}