package br.mil.eb.decex.ati.controlador;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.mil.eb.decex.ati.enumerado.Ano;
import br.mil.eb.decex.ati.enumerado.TipoAlunosMilitaresOMDS;
import br.mil.eb.decex.ati.modelo.AlunosMilitaresOMDS;
import br.mil.eb.decex.ati.modelo.OrganizacaoMilitar;
import br.mil.eb.decex.ati.modelo.Periodo;
import br.mil.eb.decex.ati.modelo.Usuario;
import br.mil.eb.decex.ati.seguranca.Autenticado;
import br.mil.eb.decex.ati.seguranca.PeriodoSelecionado;

/**
 * Realiza o cadastro das OMs Vinculadas de cada OM</p>
 * 
 * @author Jonathan Philipe Amaral <b>Crespo</b> 3º SGT
 * @version 1.0
 */
@ViewScoped
@Named("alunosOmds")
public class AlunosMilitaresOmdsControlle extends BaseController {

	private static final long serialVersionUID = 1L;
	
	@Inject
    @Autenticado
    private Usuario usuario;
	
	@Inject
	@PeriodoSelecionado
	private Periodo periodo; 
	
	private List<OrganizacaoMilitar> organizacoesMilitares;
	private List<AlunosMilitaresOMDS> listaAlunoOmds;
	private List<AlunosMilitaresOMDS> listaAlunos;
	private List<Ano> ano;
	
	private OrganizacaoMilitar subordinado;
	private Ano anoSelecionado;
	private AlunosMilitaresOMDS alunosMilitarPraca;
	private AlunosMilitaresOMDS alunosMilitarOficial;
	
	private Boolean habilitaBotao = true;
	private Boolean habilitaAno = false;
	
	private static final String SUCESSO = "sucesso_na_operacao";
	private static final String FALHA = "operacao_nao_realizada";
	
	/**
	 * Controla a exibição do Ano
	 * @return Boolean
	 */
	public Boolean getHabilitaAno() {
		return habilitaAno;
	}
	
	/**
	 * Guarda os registros de Ano
	 * @return Ano
	 */
	public Ano getAnoSelecionado() {
		return anoSelecionado;
	}
	public void setAnoSelecionado(Ano anoSelecionado) {
		this.anoSelecionado = anoSelecionado;
	}

	/**
	 * Guarda os registros de OMDS 
	 * @return listaAlunos List<AlunosMilitaresOMDS>
	 */
	public List<AlunosMilitaresOMDS> getListaAlunos() {
		return listaAlunos;
	}
	public void setListaAlunos(List<AlunosMilitaresOMDS> listaAlunos) {
		this.listaAlunos = listaAlunos;
	}
	
	/**
	 * Armazena as informações de AlunosMilitaresOMDS do tipo Praça
	 * @return AlunosMilitaresOMDS
	 */
	public AlunosMilitaresOMDS getAlunosMilitarPraca() {
		return alunosMilitarPraca;
	}
	public void setAlunosMilitarPraca(AlunosMilitaresOMDS alunosMilitarPraca) {
		this.alunosMilitarPraca = alunosMilitarPraca;
	}
	
	/**
	 * Armazena as informações de AlunosMilitaresOMDS do tipo oficial
	 * @return AlunosMilitaresOMDS
	 */
	public AlunosMilitaresOMDS getAlunosMilitarOficial() {
		return alunosMilitarOficial;
	}
	public void setAlunosMilitarOficial(AlunosMilitaresOMDS alunosMilitarOficial) {
		this.alunosMilitarOficial = alunosMilitarOficial;
	}
	
	/**
	 * Carrega a lista de Organizações Militares Subordinadas 
	 * @return lista de Organizações Militares
	 */
	public List<OrganizacaoMilitar> getOrganizacoesMilitares() {
		return organizacoesMilitares;
	}
	public void setOrganizacoesMilitares(List<OrganizacaoMilitar> organizacoesMilitares) {
		this.organizacoesMilitares = organizacoesMilitares;
	}
	
	/**
	 * carrega a lista de AlunosMilitaresOMDS usada para armazenar os valores do banco de dados
	 * @return lista de AlunosMilitaresOMDS
	 */
	public List<AlunosMilitaresOMDS> getListaAlunoOmds() {
		return listaAlunoOmds;
	}
	public void setListaAlunoOmds(List<AlunosMilitaresOMDS> listaAlunoOmds) {
		this.listaAlunoOmds = listaAlunoOmds;
	}
	
	/**
	 * Armazena as informações das subordinações de  organização militar  
	 * @return OrganizacaoMilitar
	 */
	public OrganizacaoMilitar getSubordinado() {
		return subordinado;
	}
	public void setSubordinado(OrganizacaoMilitar subordinado) {
		this.subordinado = subordinado;
	}
	
	/**
	 * habilita a função de abilitar o botão apartir do prencimento do formulario, ou se ja estiver informação cadastrada.
	 * @return HabilitarBotao
	 */
	public Boolean getHabilitaBotao() {
		return habilitaBotao;
	}
	public void setHabilitaBotao(Boolean habilitaBotao) {
		this.habilitaBotao = habilitaBotao;
	}
	
	/**
	 * Carrega a lista ano com valores do Enum Ano
	 * @see br.mil.eb.decex.ati.enumerado.Ano
	 * @return ano List<Ano>
	 */
	public List<Ano> getAno() {
		return ano;
	}
	
	@PostConstruct
	public void init() {
		ano = new ArrayList<Ano>();
		listaAlunoOmds = new ArrayList<AlunosMilitaresOMDS>();
		organizacoesMilitares = new ArrayList<OrganizacaoMilitar>();
		subordinado = new OrganizacaoMilitar();
		anoSelecionado = null;
		
		alunosMilitarPraca = new AlunosMilitaresOMDS();
		alunosMilitarOficial = new AlunosMilitaresOMDS();
		
		organizacoesMilitares.add(usuario.getOrganizacaoMilitar());
		
		alunosMilitarPraca.setPeriodo(periodo);
		alunosMilitarOficial.setPeriodo(periodo);

//	**  Necessário para a utilização da função listarAlunosOmds
//		listarAlunosOmds(organizacoesMilitares);
//
//		organizacoesMilitares = repository.encontrarComQueryNomeada(OrganizacaoMilitar.class, "OrganizacaoMilitar.porSuperior", 
//				new Object[]{"sigla", usuario.getOrganizacaoMilitar()});
		
		listaAlunoOmds = repository.encontrarComQueryNomeada(AlunosMilitaresOMDS.class, "alunosMilitaresOMDS.listarPorPeriodo",
				new Object[]{"periodo", periodo},
				new Object[]{"organizacao", usuario.getOrganizacaoMilitar()});
		
		if (listaAlunoOmds.size()>0) {
			habilitaBotao=false;
		}
		
		//Habilita o select Ano caso a OM logada seja ECEME ou AMAN
		if ((usuario.getOrganizacaoMilitar().toString().equals("ECEME")) || (usuario.getOrganizacaoMilitar().toString().equals("AMAN")))
			habilitaAno=true;
	}
	
	/**
	 * Carrega os Anos de OMDS
	 */
	public void carregarAno() {
		if (usuario.getOrganizacaoMilitar().toString().equals("AMAN")){
			ano.addAll(Arrays.asList(Ano.values()));
		}
		else if (usuario.getOrganizacaoMilitar().toString().equals("ECEME")){
			ano.addAll(Arrays.asList(Ano.values()));
			ano.remove(Ano.TERCEIRO);
			ano.remove(Ano.QUARTO);
		}
		else{
			carregarRegistroDoPrimeiroAno();
		}
	}
	
	/**
	 * Recupera os registros de acordo com a OM selecionada e seta em seu respectivo lugar
	 */
	public void carregarRegistro() {
		limpar();
		
		listaAlunos = repository.encontrarComQueryNomeada(AlunosMilitaresOMDS.class, "alunosMilitaresOMDS.listarPorOMEPeriodo",
				new Object[]{"periodo", periodo},
				new Object[]{"organizacao", subordinado},
				new Object[]{"ano", anoSelecionado});
		
		if(listaAlunos.size() > 0) {
			listaAlunos.forEach(efetivo ->{
				if (efetivo.getTipoAlunosMilitaresOMDS().equals(TipoAlunosMilitaresOMDS.OFICIAL))
					setAlunosMilitarOficial(efetivo);
				if(efetivo.getTipoAlunosMilitaresOMDS().equals(TipoAlunosMilitaresOMDS.PRACA))
					setAlunosMilitarPraca(efetivo);
			});
			habilitaBotao=false;
		}
		organizarValores();
	}
	
	/**
	 * Recupera os registros do primeiro Ano e seta em seu respectivo lugar
	 */
	public void carregarRegistroDoPrimeiroAno() {
		limpar();
		
		listaAlunos = repository.encontrarComQueryNomeada(AlunosMilitaresOMDS.class, "alunosMilitaresOMDS.listarPorOMEPeriodo",
				new Object[]{"periodo", periodo},
				new Object[]{"organizacao", subordinado},
				new Object[]{"ano", Ano.PRIMEIRO});
		
		if (listaAlunos.size() > 0) {
			listaAlunos.forEach(efetivo ->{
				if (efetivo.getTipoAlunosMilitaresOMDS().equals(TipoAlunosMilitaresOMDS.OFICIAL))
					setAlunosMilitarOficial(listaAlunos.get(0));
				if(efetivo.getTipoAlunosMilitaresOMDS().equals(TipoAlunosMilitaresOMDS.PRACA))
					setAlunosMilitarPraca(listaAlunos.get(0));;
			});
			habilitaBotao=false;
		}
		organizarValores();
	}
	
	/**
	 * Busca os registros de AlunoOmVinculada de cada Organização vinculada 
	 * @param List<OrganizacaoMilitar> organizacoes
	 */
//	public void listarAlunosOmds(List<OrganizacaoMilitar> organizacoes) {
//		if (!usuario.getOrganizacaoMilitar().getSigla().equals("DECEX"))
//			organizacoes.add(0, usuario.getOrganizacaoMilitar());
//		
//		organizacoes.forEach(listaOm->{
//			List<AlunosMilitaresOMDS> lista = repository.encontrarComQueryNomeada(AlunosMilitaresOMDS.class, "alunosMilitaresOMDS.listarPorPeriodo",
//					new Object[]{"periodo", periodo},
//					new Object[]{"organizacao", listaOm});
//			
//			if (lista.size() > 0)
//				listaAlunoOmds.addAll(lista);
//		});
//		
//		if (listaAlunoOmds.size() > 0)
//			habilitaBotao=false;
//	}
	
	/**
	 * Seta os valores da Organização Militar e o tipo(Intrutor, Aluno de intrutor, Monitor ou Aluno de Monitor)
	 */
	public void organizarValores() {
		alunosMilitarPraca.setOrganizacaoMilitar(subordinado);
		alunosMilitarPraca.setTipoAlunosMilitaresOMDS(TipoAlunosMilitaresOMDS.PRACA);
		alunosMilitarPraca.setAno(anoSelecionado);
		
		alunosMilitarOficial.setOrganizacaoMilitar(subordinado);
		alunosMilitarOficial.setTipoAlunosMilitaresOMDS(TipoAlunosMilitaresOMDS.OFICIAL);
		alunosMilitarOficial.setAno(anoSelecionado);
	}
	
	/**
	 * Limpa Id, Masculino e Feminino
	 */
	public void limpar() {
		alunosMilitarPraca.setId(null);
		alunosMilitarPraca.setMasculino(null);
		alunosMilitarPraca.setFeminino(null);
		
		alunosMilitarOficial.setId(null);
		alunosMilitarOficial.setMasculino(null);
		alunosMilitarOficial.setFeminino(null);
	}
	
	/**
	 * Edita as informações salvas, ja cadastrada no Banco de Dados pelo usuário.
	 * @param editarEfetivo
	 */
	public void editar(AlunosMilitaresOMDS editarEfetivo) {		
		salvarOuAtualizar(editarEfetivo);
	}
	
	/**
	 * Exclui a instancia de OmVinculada da base de dados de acordo com o id selecionado
	 * @param excluirEfetivo
	 */
	public void excluir(AlunosMilitaresOMDS excluirEfetivo) {
		super.executar(() -> {repository.apagar(AlunosMilitaresOMDS.class, excluirEfetivo.getId()); init();}, SUCESSO, FALHA);
	}
	
	/**
	 * Prepara o salvar ou atualizar
	 */
	public void prepararDados() {
		atribuiPrimeiroAnoCasoAnoEstejaNulo();
		
		salvarOuAtualizar(alunosMilitarPraca);
		salvarOuAtualizar(alunosMilitarOficial);
		
		init();
		habilitaBotao=false;
	}

	/**
	 * Salva um novo cadastro de alunos militares de outras forças no Banco de Dados
	 */
	public void salvarOuAtualizar(AlunosMilitaresOMDS AMOF) {
		super.executar(() -> {repository.adicionarOuMudar(AMOF);}, SUCESSO, FALHA);
	}
	
	private void atribuiPrimeiroAnoCasoAnoEstejaNulo() {
		if (alunosMilitarPraca.getAno()==null)
			alunosMilitarPraca.setAno(Ano.PRIMEIRO);
		
		if (alunosMilitarOficial.getAno()==null)
			alunosMilitarOficial.setAno(Ano.PRIMEIRO);
	}
}
