package br.mil.eb.decex.ati.conversor;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import br.mil.eb.decex.ati.enumerado.TipoAcesso;

/**
 * Conversor padrão para Tipo Acesso
 * @author Jonathan Philipe Amaral <b>Crespo</b> - 3° SGT
 * @version 1.0
 */
@FacesConverter(value="tipoAcessoConverter")
public class TipoAcessoConverter implements Converter {

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {

		if(value != null && !value.isEmpty()) 
			return (TipoAcesso) component.getAttributes().get(value);

		return null;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		
		if(value instanceof TipoAcesso){
			TipoAcesso tipo = (TipoAcesso) value;
			if(tipo != null && tipo instanceof TipoAcesso 
					&& tipo.getValue() != null) {
				component.getAttributes().put(tipo.getValue().toString(), tipo);
				return tipo.getValue().toString();
			}
		}
		
		return "";
	}

}
