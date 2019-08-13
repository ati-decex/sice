package br.mil.eb.decex.ati.controlador;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import br.mil.eb.decex.ati.enumerado.Serie;
import br.mil.eb.decex.ati.modelo.AlunosColegioMilitar;
import br.mil.eb.decex.ati.modelo.OrganizacaoMilitar;
import br.mil.eb.decex.ati.modelo.Periodo;
import br.mil.eb.decex.ati.modelo.Usuario;
import br.mil.eb.decex.ati.seguranca.Autenticado;
import br.mil.eb.decex.ati.seguranca.PeriodoSelecionado;

/**
 * Realiza o cadastro dos alunos dos colegios militares</p>
 * 
 * @author Luiz Augusto Lourenço do <b>Amparo</b> 3º SGT
 * @version 1.0
 */
@ViewScoped
@Named("alunosColegioMilitar")
public class AlunosColegiosMilitaresController extends BaseController {

	private static final long serialVersionUID = 1L;
	
	@Inject
    @Autenticado
    private Usuario usuario;
	
	@Inject
	@PeriodoSelecionado
	private Periodo periodo;
	
	private List<OrganizacaoMilitar> organizacoesMilitares;
	private List<AlunosColegioMilitar> alunosColegiosMilitares;
	private List<AlunosColegioMilitar> alunos;
	
	private AlunosColegioMilitar alunosColegioMilitar;
	private boolean habilitaBotao;
	private boolean desabilitarCampos;
	private boolean desabilitarBotaoSalvar;
	private Integer valorFinal;
	private String msgErroCampo;
	
	//controla a liberação dos campos
	private boolean habilitaAlunoMascFem;
	private boolean habilitaAlunoMil;
	
	private static final String SUCESSO = "sucesso_na_operacao";
	private static final String FALHA = "operacao_nao_realizada";

	/**
	 * Exibe a mensagem de validação dos campos
	 * @return String msgErroCampo
	 */
	public String getMsgErroCampo() {
		return msgErroCampo;
	}
	public void setMsgErroCampo(String msgErroCampo) {
		this.msgErroCampo = msgErroCampo;
	}
	
	/**
	 * habilita a função de abilitar o botão a partir do prencimento do formulario, ou se ja estiver informação cadastrada.
	 * @return HabilitarBotao
	 */
	public boolean isHabilitaBotao() {
		return habilitaBotao;
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
	 * Lista de AlunoColegioMilitar usada para armazenar os valores do banco de dados
	 * @return lista de AlunoColegioMilitar
	 */
	public List<AlunosColegioMilitar> getAlunosColegiosMilitares() {
		return alunosColegiosMilitares;
	}
	public void setAlunosColegiosMilitares(List<AlunosColegioMilitar> alunosColegiosMilitares) {
		this.alunosColegiosMilitares = alunosColegiosMilitares;
	}
	
	/**
	 * Armazena as informações de AlunoColegioMilitar
	 * @return AlunoColegioMilitar
	 */
	public AlunosColegioMilitar getAlunosColegioMilitar() {
		return alunosColegioMilitar;
	}
	public void setAlunosColegioMilitar(AlunosColegioMilitar alunosColegioMilitar) {
		this.alunosColegioMilitar = alunosColegioMilitar;
	}
	
	/**
	 * Carrega a lista Serie com valores do Enum Serie
	 * @see br.mil.eb.decex.ati.enumerado.Serie
	 * @return Serie
	 */
	public List<Serie> getSerie() {
		return Arrays.asList(Serie.values());
	}
	
	@PostConstruct
	public void init() {
		alunosColegioMilitar = new AlunosColegioMilitar();
		
		organizacoesMilitares = new ArrayList<OrganizacaoMilitar>();
	
		alunosColegioMilitar.setPeriodo(periodo);
		
		organizacoesMilitares.add(usuario.getOrganizacaoMilitar());
		
		//habilita o botão avançar
		habilitaBotao=true;
		//habilita os campos após o total de alunos serem definidos
		setDesabilitarCampos(true);
		//habilita o botão salvar após a validação dos campos
		setDesabilitarBotaoSalvar(true);
		//controla a validação do botão salvar
		habilitaAlunoMascFem=true;
		habilitaAlunoMil=true;
		
		preencherListaDeAlunosMilitaresPorPeriodo();
		if (alunos.size()>0){
			habilitaBotao=false;
		}
		limpar();
	}
	
	private void preencherListaDeAlunosMilitaresPorPeriodo() {
		alunos = repository.encontrarComQueryNomeada(AlunosColegioMilitar.class,"alunosColegioMilitar.listarPorPeriodo",
				new Object[]{"periodo", periodo},
				new Object[]{"organizacao", usuario.getOrganizacaoMilitar()});
		
	}
	
	/**
	 * Verifica se os campos Processo Seletivo e R69 estão preenchidos
	 * @param AlunosColegioMilitar aluno
	 */
	public void liberarCampos(AlunosColegioMilitar aluno) {
		msgErroCampo = new String();
		desabilitarCampos = true;
		valorFinal = alunosColegioMilitar.getProcessoSeletivo() + alunosColegioMilitar.getR69();
		
		if ((aluno.getProcessoSeletivo() > 0)&& (aluno.getR69() > 0)){
			desabilitarCampos = false;
			desabilitarBotaoSalvar = false;
//			validarCamposAlunoMascFem(aluno);
		}
		
//		setHabilitarCampos(aluno.getProcessoSeletivo() > 0 ? (aluno.getR69() > 0 ? false : true) : true);
		
		
		//validarCamposAlunoMascFem();
		//validarCamposAlunoMil();
		
		//setMsgErroCampo("A soma dos alunos deve ser igual a " + valorFinal);
		
		/*if ((aluno.getProcessoSeletivo() <= 0) || (aluno.getR69() <= 0))
			setMsgErroCampo("");*/
	}
	
	/**
	 * Verifica se os campos masculino e feminino são menores que a soma de {@link #verificarCampos(AlunosColegioMilitar)}
	 */
	public void validarCamposAlunoMascFem(AlunosColegioMilitar aluno) {
		msgErroCampo = new String();
		Integer alunoMascFem = aluno.getMasculino() + aluno.getFeminino();
		habilitaAlunoMascFem = valorFinal == alunoMascFem ? false : true;

		if (habilitaAlunoMascFem) 
			setMsgErroCampo("A soma de Feminino e Masculino deve ser igual a " + (aluno.getProcessoSeletivo()+aluno.getR69()));
		validarCamposGeral();
	}
	
	/**
	 * Verifica se os campos civis, eb, forcas e forcasAuxiliares são menores que a soma de {@link #verificarCampos(AlunosColegioMilitar)}
	 */
	public void validarCamposAlunoMil() {
		msgErroCampo = new String();
		
		Integer alunoMil = alunosColegioMilitar.getCivis() + alunosColegioMilitar.getEb() + alunosColegioMilitar.getForcas() + alunosColegioMilitar.getForcasAuxiliares()
				+ alunosColegioMilitar.getNAFeminino() + alunosColegioMilitar.getNAMasculino();
		
		habilitaAlunoMil = valorFinal == alunoMil ? false : true;
		
		if (habilitaAlunoMil) 
			setMsgErroCampo("A soma de Civis, Mil do EB, Mil de Outras Forças, Mil das Forças Aux e Nações Amigas(Masculino e Feminino) deve ser igual a " + valorFinal);
		
		validarCamposGeral();
	}
	
	/**
	 * Libera o botão salvar caso as 2 outras validações sejam false 
	 */
	public void validarCamposGeral() {
		if ((this.getAlunosColegioMilitar().getProcessoSeletivo().intValue() <= 0) || (this.getAlunosColegioMilitar().getR69().intValue() <= 0)){
			desabilitarBotaoSalvar = true;
			//limpar();
			
		}else{
			//setHabilitarBotaoSalvar(habilitaAlunoMascFem ? true : (habilitaAlunoMil ? true : false));
			desabilitarBotaoSalvar = true;
		}
			
	}

	/**
	 * Limpa Id, Masculino e Feminino de professorMilitar
	 */
	public void limpar() {
		alunosColegioMilitar.setId(null);
		alunosColegioMilitar.setMasculino(0);
		alunosColegioMilitar.setFeminino(0);
		alunosColegioMilitar.setProcessoSeletivo(0);
		alunosColegioMilitar.setR69(0);
		alunosColegioMilitar.setCivis(0);
		alunosColegioMilitar.setForcas(0);
		alunosColegioMilitar.setForcasAuxiliares(0);
		alunosColegioMilitar.setNEMasculino(0);
		alunosColegioMilitar.setNEFeminino(0);
		alunosColegioMilitar.setNAMasculino(0);
		alunosColegioMilitar.setNAFeminino(0);
		alunosColegioMilitar.setEb(0);
	}
	
	/**
	 * Verifica se já existe um valor de alunosColegiosMilitares no banco de dados
	 */
	
	public void carregarRegistro(AlunosColegioMilitar alunos){
		limpar();
		alunosColegiosMilitares = repository.encontrarComQueryNomeada(AlunosColegioMilitar.class, "alunosColegioMilitar.listarPorOMEPeriodo",
				new Object[]{"periodo", periodo},
				new Object[]{"organizacao", alunos.getOrganizacaoMilitar()},
				new Object[]{"serie",alunos.getSerie()});
		setAlunosColegioMilitar(alunosColegiosMilitares.get(0));
		desabilitarCampos = false;
		desabilitarBotaoSalvar = false;
		
	}
	
	public void excluir(AlunosColegioMilitar alunos) {
		super.executar(() -> {repository.apagar(AlunosColegioMilitar.class, alunos.getId()); init();}, SUCESSO, FALHA);
	}
	
	public void verificarRegistro() {
		limpar();
		
		alunosColegiosMilitares = repository.encontrarComQueryNomeada(AlunosColegioMilitar.class, "alunosColegioMilitar.listarPorOMEPeriodo",
				new Object[]{"periodo", periodo},
				new Object[]{"organizacao", alunosColegioMilitar.getOrganizacaoMilitar()},
				new Object[]{"serie",alunosColegioMilitar.getSerie()});
		
		if (alunosColegiosMilitares.size() > 0) {
			setAlunosColegioMilitar(alunosColegiosMilitares.get(0));
			habilitaBotao=false;
			setDesabilitarCampos(false);
			setDesabilitarBotaoSalvar(false);
			habilitaAlunoMascFem=false;
			habilitaAlunoMil=false;
		}else{
			habilitaBotao=true;
			setDesabilitarCampos(true);
			setDesabilitarBotaoSalvar(true);
			habilitaAlunoMascFem=true;
			habilitaAlunoMil=true;
			
		}
					
	}
	
	/**
	 * Salva um novo cadastro de AlunosColegiosMmilitares no Banco de Dados
	 */
	public void salvarOuAtualizar() {
		if (validarSomas(alunosColegioMilitar)){
			super.executar(() -> {repository.adicionarOuMudar(alunosColegioMilitar); init();}, SUCESSO, FALHA);
			habilitaBotao=false;
		}
	}
	
	private boolean validarSomas(AlunosColegioMilitar al) {
		boolean result = true;
		
		msgErroCampo = "";
		
		int somaProcessoSeletivoMaisR69 = al.getProcessoSeletivo()+al.getR69();
		int somaMasculinoMaisFeminino = al.getMasculino()+al.getFeminino();
		int somaEBCivisEForcas = al.getEb()+al.getCivis()+al.getForcas()+al.getForcasAuxiliares();
		int somaNacoesAmigas = al.getNAMasculino()+al.getNAFeminino();
		
		somaEBCivisEForcas+=somaNacoesAmigas;
		
		if (somaProcessoSeletivoMaisR69!=somaMasculinoMaisFeminino){
			msgErroCampo = "A soma de Masculino mais Feminino deve ser igual a : " + somaProcessoSeletivoMaisR69;
			result = false;
		} else if (somaProcessoSeletivoMaisR69!=somaEBCivisEForcas){
			msgErroCampo = "A soma de Civis, Mil do EB, Mil de Outras Forças, Mil das Forças Aux e Nações Amigas(Masculino e Feminino) deve ser igual a : " + somaProcessoSeletivoMaisR69;
			result = false;
		}
		
		return result;
	}
	
	public List<AlunosColegioMilitar> getAlunos() {
		return alunos;
	}
	public void setAlunos(List<AlunosColegioMilitar> alunos) {
		this.alunos = alunos;
	}
	
	public boolean isDesabilitarCampos() {
		return desabilitarCampos;
	}
	public void setDesabilitarCampos(boolean desabilitarCampos) {
		this.desabilitarCampos = desabilitarCampos;
	}
	
	public boolean isDesabilitarBotaoSalvar() {
		return desabilitarBotaoSalvar;
	}
	public void setDesabilitarBotaoSalvar(boolean desabilitarBotaoSalvar) {
		this.desabilitarBotaoSalvar = desabilitarBotaoSalvar;
	}
}
