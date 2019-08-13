package br.mil.eb.decex.ati.conversor;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import br.mil.eb.decex.ati.modelo.Disciplina;

/**
 * Conversor padrão para Disciplina
 * @author Luiz Augusto Lourenço do  <b>Amparo</b> - 3° SGT
 * @version 1.0
 */
@FacesConverter(value="disciplinaConverter")
public class DisciplinaConverter implements Converter {

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {

		if(value != null && !value.isEmpty()) 
			return (Disciplina) component.getAttributes().get(value);

		return null;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		
		if(value instanceof Disciplina){
			Disciplina disciplina = (Disciplina) value;
			if(disciplina != null && disciplina instanceof Disciplina 
					&& disciplina.getNome() != null) {
				component.getAttributes().put(disciplina.getNome().toString(), disciplina);
				return disciplina.getNome().toString();
			}
		}
		
		return "";
		
	}

}
