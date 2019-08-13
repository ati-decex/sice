package br.mil.eb.decex.ati.jpa;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import br.mil.eb.decex.ati.enumerado.TipoOrganizacaoMilitar;
import br.mil.eb.decex.ati.modelo.OrganizacaoMilitar;

/**
 * Repositório específico para operações de leitura para Organizações Militares
 * 
 * @author William <b>Moreira</b> de Pinho - CAP
 * @version 1.0
 */
public class OrganizacaoMilitarRepository implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private Repository repository;

	/**
	 * Lista organizações militares dos tipos Departamento e Diretoria
	 * 
	 * @return organizações militares ordenadas por id
	 */
	public List<OrganizacaoMilitar> encontrarSuperiores() {

		List<OrganizacaoMilitar> superiores = repository.encontrarComQueryNomeada(OrganizacaoMilitar.class,
				"OrganizacaoMilitar.superiores");
		superiores.sort((s1, s2) -> Long.compare(s1.getId(), s2.getId()));

		return superiores;
	}

	/**
	 * Lista as organizações militares subordinadas ao superior informado
	 * 
	 * @param superior
	 *            organização militar superior
	 * @return lista de organizações militares subordinadas sem ordenação
	 */
	public List<OrganizacaoMilitar> encontrarSubordinados(OrganizacaoMilitar superior) {

		List<OrganizacaoMilitar> organizacoesMilitares = new ArrayList<OrganizacaoMilitar>();

		// Lista todas as organizações militares para iterar e selecionar apenas
		// com o superior selecionado
		List<OrganizacaoMilitar> organizacoes = repository.encontrarComQueryNomeada(OrganizacaoMilitar.class,
				"OrganizacaoMilitar.comSuperior");

		// Seleciona apenas as organizações com o superior informado
		for (OrganizacaoMilitar om : organizacoes)
			if (om.getSuperiores().contains(superior) && !om.getTipo().equals(TipoOrganizacaoMilitar.VINCULADA))
				organizacoesMilitares.add(om);

		return organizacoesMilitares;
	}

	public List<OrganizacaoMilitar> encontrarEstabelecimentosEnsinoSubordinados(OrganizacaoMilitar superior) {
		return repository.encontrarComQueryNomeada(OrganizacaoMilitar.class,
				"OrganizacaoMilitar.EstabelecimentosDeEnsinoSubordinados", new Object[] { "om", superior });
	}

	/**
	 * Busca uma organizacao militar pela chave com as telas de efetivo
	 * carregadas
	 * 
	 * @param organizacaoMilitarID
	 *            chave da organização militar
	 * @return organização militar com as telas de efetivo carregadas
	 */
	public OrganizacaoMilitar encontrarComAsTelas(Long organizacaoMilitarID) {

		OrganizacaoMilitar om = null;

		try {

			om = repository.encontrarComQueryNomeada(OrganizacaoMilitar.class, "OrganizacaoMilitar.telas",	new Object[] { "id", organizacaoMilitarID }).get(0);

		} catch (IndexOutOfBoundsException e) {

			// Tratamento para retorno sem registros
			om = new OrganizacaoMilitar();

		}
		return om;
	}

	public List<OrganizacaoMilitar> encontrarOMPorSigla(String sigla) {

		return repository.encontrarComQueryNomeada(OrganizacaoMilitar.class, "OrganizacaoMilitar.porSigla",
				new Object[] { "sigla", "%"+sigla+"%" });

	}
}