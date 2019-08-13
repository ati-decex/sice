package br.mil.eb.decex.ati.controlador;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.mil.eb.decex.ati.enumerado.TipoAcesso;
import br.mil.eb.decex.ati.modelo.OrganizacaoMilitar;
import br.mil.eb.decex.ati.modelo.Periodo;
import br.mil.eb.decex.ati.modelo.RegistroAlteracaoEfetivoExistente;
import br.mil.eb.decex.ati.modelo.Usuario;
import br.mil.eb.decex.ati.modelo.jaas.UserRoles;
import br.mil.eb.decex.ati.seguranca.Autenticado;
import br.mil.eb.decex.ati.seguranca.PeriodoSelecionado;
import br.mil.eb.decex.ati.seguranca.UsuarioCorrente;

/**
 * Consulta dos registros de acesso ao sistema
 * </p>
 * 
 * @author Jonathan Philipe Amaral <b>Crespo</b> 3º SGT STT
 * @version 1.0
 */
@Named("registroAcesso")
@ViewScoped
public class RegistroAcessoController extends BaseController {

	private static final long serialVersionUID = 1L;

	@Inject
	@Autenticado
	private Usuario usuario;

	@Inject
	@PeriodoSelecionado
	private Periodo periodo;
	private List<Periodo> periodos;

	private List<RegistroAlteracaoEfetivoExistente> registroEfetivos;

	private List<OrganizacaoMilitar> organizacoesMilitares;
	
	private List<OrganizacaoMilitar> organizacoesMilitaresDiretoria;

	private boolean usuarioDiretoria() {
		for (UserRoles role : usuario.getUsers().getRoles()) {
			if (role.getRoleName().equals(TipoAcesso.DIRETORIA)){
				return true;
			}
		}
		return false;

	}
	

	@PostConstruct
	public void init() {
		
		periodos = new ArrayList<Periodo>();
		periodos = repository.encontrarComQueryNomeada(Periodo.class,"Periodo.ordemDesc");
//		periodos = repository.encontrar(Periodo.class);
		carregarLista();
	}

	public List<OrganizacaoMilitar> getOrganizacoesMilitares() {
		return organizacoesMilitares;
	}
	public void setOrganizacoesMilitares(List<OrganizacaoMilitar> organizacoesMilitares) {
		this.organizacoesMilitares = organizacoesMilitares;
	}

	public Periodo getPeriodo() {
		return periodo;
	}
	public void setPeriodo(Periodo periodo) {
		this.periodo = periodo;
	}
	
	public List<Periodo> getPeriodos() {
		return periodos;
	}
	public void setPeriodos(List<Periodo> periodos) {
		this.periodos = periodos;
	}
	
	public void carregarLista() {
		if (usuarioDiretoria()){
			organizacoesMilitares = repository.encontrarComQueryNomeada(OrganizacaoMilitar.class,
					"OrganizacaoMilitar.listarEstabelecimentosDeEnsinoPorDiretoria",
					 new Object[] { "diretoria", usuario.getOrganizacaoMilitar() });
		}
		else{
			organizacoesMilitares = repository.encontrarComQueryNomeada(OrganizacaoMilitar.class,
					"OrganizacaoMilitar.listarEstabelecimentosDeEnsino");
		}

		

		registroEfetivos = repository.encontrarComQueryNomeada(RegistroAlteracaoEfetivoExistente.class, "Pegunta.listarPorPeriodoOM",
				new Object[] { "periodo", getPeriodo() });

		for (OrganizacaoMilitar om : organizacoesMilitares) {
			for (int i = 0; i < registroEfetivos.size(); i++) {
				RegistroAlteracaoEfetivoExistente r = registroEfetivos.get(i);
				if (om.getSigla().toLowerCase().equals(r.getOrganizacaoMilitar().getSigla().toLowerCase())) {
					om.setRegistroAlteracao(
							(RegistroAlteracaoEfetivoExistente) RegistroAlteracaoEfetivoExistente.cloneSerializable(r));
					registroEfetivos.remove(i);
					i = 0;
				}
			}
		}
	}
}
