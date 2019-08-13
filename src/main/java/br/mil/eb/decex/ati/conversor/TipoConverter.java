package br.mil.eb.decex.ati.conversor;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import br.mil.eb.decex.ati.enumerado.Tipo;

/**
 * Conversor padrão para Tipo
 * @author Jonathan Philipe Amaral <b>Crespo</b> - 3° SGT
 * @version 1.0
 */
@FacesConverter(value="tipoConverter")
public class TipoConverter implements Converter {

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {

		if(value != null && !value.isEmpty()) 
			return (Tipo) component.getAttributes().get(value);

		return null;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		
		if(value instanceof Tipo){
			Tipo tipo = (Tipo) value;
			if(tipo != null && tipo instanceof Tipo 
					&& tipo.getValue() != null) {
				component.getAttributes().put(tipo.getValue().toString(), tipo);
				return tipo.getValue().toString();
			}
		}
		
		return "";
		
	}

}
