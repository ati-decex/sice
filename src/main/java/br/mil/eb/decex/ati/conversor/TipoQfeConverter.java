package br.mil.eb.decex.ati.conversor;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import br.mil.eb.decex.ati.modelo.TipoQFE;


@FacesConverter(value="tipoQfeConverter")
public class TipoQfeConverter implements Converter {

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {

		if(value != null && !value.isEmpty()) 
			return (TipoQFE) component.getAttributes().get(value);

		return null;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		
		if(value instanceof TipoQFE){
			TipoQFE tipo = (TipoQFE) value;
			if(tipo != null && tipo instanceof TipoQFE && tipo.getDescr_tipo_qfe() != null) {
				component.getAttributes().put(tipo.getDescr_tipo_qfe(), tipo);
				return tipo.getDescr_tipo_qfe();
			}
		}
		
		return "";		
	}
}