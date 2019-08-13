package br.mil.eb.decex.ati.util;

import java.io.Serializable;
import java.util.Date;
import javax.inject.Inject;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;
import br.mil.eb.decex.ati.jpa.Repository;
import br.mil.eb.decex.ati.modelo.Auditoria;
import br.mil.eb.decex.ati.modelo.Efetivo;
import br.mil.eb.decex.ati.modelo.Usuario;
import br.mil.eb.decex.ati.seguranca.Autenticado;
import br.mil.eb.decex.ati.servico.BaseServiceLocal;

/**
 * Salva o tipo de registro realizado sobre uma obrigação para 
 * posterior auditagem
 * 
 * @author Jonathan Philipe Amaral <b>Crespo</b> - 3º SGT
 * @version 1.0
 * @see br.mil.eb.decex.ati.model.Auditoria
 * @see AuditoriaVO
 */
@Auditable
@Interceptor
public class AuditoriaListener implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	@Autenticado
	private Usuario usuario;
	
	@Inject
	private Repository repository;
	
	@Inject
	private BaseServiceLocal serviceLocal;	
	
	/**
	 * Registra uma nova auditorua
	 * @param auditoriaVO value object para auditoria
	 */
	@AroundInvoke
	public Object onRegistrarAuditoria(InvocationContext context) throws Exception {
		
		Auditoria auditoria = new Auditoria();

		Efetivo efetivo = (Efetivo)context.getContextData().get("efetivo");
		
		if (efetivo == null) {
			efetivo = (Efetivo) context.getParameters()[0];
			context.getContextData().put("efetivo", efetivo);									
		}
		
		auditoria.setData(new Date());	
		auditoria.setPostoGraduacao(efetivo.getLinhaConfiguracao().getPostoGraduacao());
		auditoria.setLinha(efetivo.getLinhaConfiguracao().getLinha());
		auditoria.setTipo(efetivo.getLinhaConfiguracao().getTipo());
		auditoria.setQuantidade(efetivo.getQuantidade());
		auditoria.setOrganizacaoId(usuario.getOrganizacaoMilitar().getId());
		auditoria.setUsuarioId(usuario.getId());			
		auditoria.setMetodo(context.getMethod().getName());
		
		
		serviceLocal.execute(() -> repository.adicionar(auditoria));		
				
		return context.proceed();		
	}
}