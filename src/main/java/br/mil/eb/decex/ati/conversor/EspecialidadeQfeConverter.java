package br.mil.eb.decex.ati.conversor;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import br.mil.eb.decex.ati.modelo.EspecialidadeQFE;

@FacesConverter(value="especialidadeQfeConverter")
public class EspecialidadeQfeConverter implements Converter {

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {

		if(value != null && !value.isEmpty()) 
			return (EspecialidadeQFE) component.getAttributes().get(value);

		return null;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		
		if(value instanceof EspecialidadeQFE){
			EspecialidadeQFE especialidade = (EspecialidadeQFE) value;
			if(especialidade != null && especialidade instanceof EspecialidadeQFE && especialidade.getDescr_especialidade_qfe() != null) {
				component.getAttributes().put(especialidade.getDescr_especialidade_qfe(), especialidade);
				return especialidade.getDescr_especialidade_qfe();
			}
		}
		
		return "";		
	}
}