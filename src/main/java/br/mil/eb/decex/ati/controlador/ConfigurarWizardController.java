package br.mil.eb.decex.ati.controlador;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import br.mil.eb.decex.ati.jpa.OrganizacaoMilitarRepository;
import br.mil.eb.decex.ati.modelo.OrganizacaoMilitar;
import br.mil.eb.decex.ati.modelo.Tela;

/**
 * Realiza a configuração wizard das telas de cadastro das OMs</p>
 * 
 * @author Jonathan Philipe Amaral <b>Crespo</b> 3º SGT
 * @version 1.0
 */
@ViewScoped
@Named("configurarWizard")
public class ConfigurarWizardController extends BaseController {

	private static final long serialVersionUID = 1L;
	
	private List<OrganizacaoMilitar> superiores;
    private List<OrganizacaoMilitar> organizacoesMilitares;	
	private List<Tela> telas;
    private List<Tela> telasSelecionadas;    
	
	private OrganizacaoMilitar organizacaoMilitar;
	private OrganizacaoMilitar superiorSelected;	
	
	@Inject
	private OrganizacaoMilitarRepository omRepository;
		
	private static final String SUCESSO = "sucesso_na_operacao";
	private static final String FALHA = "operacao_nao_realizada";

	/**
	 * Lista de OrganizacaoMilitar para diretorias
	 * @return listaDiretorias
	 */
	public List<OrganizacaoMilitar> getSuperiores() {
		return superiores;
	}
			
	/**
	 * Carrega as Organizações Militares Subordinadas
	 * @return listaSubordinadas
	 */
	public List<OrganizacaoMilitar> getOrganizacoesMilitares() {
		return organizacoesMilitares;		
	}
	
	/**
	 * Guarda a Organização Militar selecionada
	 * @return omSelecionada
	 */
	public OrganizacaoMilitar getSuperiorSelected() {
		return superiorSelected;
	}
	public void setSuperiorSelected(OrganizacaoMilitar superiorSelected) {
		this.superiorSelected = superiorSelected;
	}	
	
	/**
	 * Guarda a Organização Militar Subordinada selecionada
	 * @return omSubordinadaSelecionada
	 */
	public OrganizacaoMilitar getOrganizacaoMilitar() {
		return organizacaoMilitar;
	}
	public void setOrganizacaoMilitar(OrganizacaoMilitar organizacaoMilitar) {
		this.organizacaoMilitar = organizacaoMilitar;
	}
		
	/**
	 * Telas disponíveis para cadastramento do efetivo
	 * @see br.mil.eb.decex.ati.modelo.Tela
	 * @return telas disponíveis para cadastramento do efetivo
	 */
	public List<Tela> getTelas() {
		return telas;
	}

	/**
	 * Lista de telas selecionadas pelo usuário	
	 * @see br.mil.eb.decex.ati.modelo.Tela
	 * @return telasSelecionadas Tela
	 */
	public List<Tela> getTelasSelecionadas() {
		return telasSelecionadas;
	}
	public void setTelasSelecionadas(List<Tela> telasSelecionadas) {
		this.telasSelecionadas = telasSelecionadas;
	}
	
	private void removeTela(List<Tela> relatoriosDisponiveis, String nomeTela) {
		Tela tela = new Tela();
		tela.setNome(nomeTela);
		if (relatoriosDisponiveis.contains(tela)) {
			relatoriosDisponiveis.remove(tela);
		}
	}
	
	

	@PostConstruct
	public void init() {
		organizacoesMilitares = new ArrayList<OrganizacaoMilitar>();
		telasSelecionadas = new ArrayList<Tela>();
		organizacaoMilitar = new OrganizacaoMilitar();
		superiores = omRepository.encontrarSuperiores();
		telas = repository.encontrar(Tela.class);
		removeTela(telas, "RESUMO_GERAL");
		
	}
	
	/**
	 * Carrega as telas da Organização Militar logada
	 */
	public void recuperarTelasDaOMLogada() {
		telasSelecionadas.clear();
		
		organizacaoMilitar = omRepository.encontrarComAsTelas(organizacaoMilitar.getId());
		
		if(organizacaoMilitar.getTelas() != null)
			organizacaoMilitar.getTelas().forEach(t -> telasSelecionadas.add(t));
	}
	
	/**
	 * Lista apenas as organizações com o superior informado	
	 */
	public void listarSubordinados() {
		telasSelecionadas.clear();
		
		organizacoesMilitares = omRepository.encontrarSubordinados(superiorSelected);		
		
		//adiciona o superior OM na posição 0
		organizacoesMilitares.add(0, repository.encontrar(OrganizacaoMilitar.class, "sigla", superiorSelected + " OM").get(0));
	}	
						
	/**
	 * Salva a seleção de {@link Tela} em {@link OrganizacaoMilitar}
	 */
	public void salvar() {
		organizacaoMilitar.setTelas(telasSelecionadas);		
		super.executar(() -> {repository.adicionarOuMudar(organizacaoMilitar);}, SUCESSO, FALHA);
    }	
}