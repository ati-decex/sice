package br.mil.eb.decex.ati.conversor;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import br.mil.eb.decex.ati.modelo.Especialidade;;

/**
 * Conversor padrão para DocumentoReferencia
 * @author Luiz Augusto Lourenço do <b>Amparo</b>3º SGT QCO
 * @version 1.0
 */
@FacesConverter(value="especialidadeConverter")
public class EspecialidadeConverter implements Converter {

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String nomeEspecialidade) {

		if(nomeEspecialidade != null && !nomeEspecialidade.isEmpty()) 
			return (Especialidade) component.getAttributes().get(nomeEspecialidade);

		return null;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object nomeEspecialidade) {
		
		if(nomeEspecialidade instanceof Especialidade){
			Especialidade especialidade = (Especialidade) nomeEspecialidade;
			if(especialidade != null && especialidade instanceof Especialidade 
					&& especialidade.getNomeEspecialidade() != null) {
				component.getAttributes().put(especialidade.getNomeEspecialidade().toString(), especialidade);
				return especialidade.getNomeEspecialidade().toString();
			}
		}
		
		return "";
		
	}

}
