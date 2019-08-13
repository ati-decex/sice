package br.mil.eb.decex.ati.conversor;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import br.mil.eb.decex.ati.enumerado.Linha;

/**
 * Conversor padrão para Linha
 * @author Jonathan Philipe Amaral <b>Crespo</b> - 3° SGT
 * @version 1.0
 */
@FacesConverter(value="linhaConverter")
public class LinhaConverter implements Converter {

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {

		if(value != null && !value.isEmpty()) 
			return (Linha) component.getAttributes().get(value);

		return null;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		
		if(value instanceof Linha){
			Linha linha = (Linha) value;
			if(linha != null && linha instanceof Linha 
					&& linha.getValue() != null) {
				component.getAttributes().put(linha.getValue().toString(), linha);
				return linha.getValue().toString();
			}
		}
		
		return "";
		
	}

}
