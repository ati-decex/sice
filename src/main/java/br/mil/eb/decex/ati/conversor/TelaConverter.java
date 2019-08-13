package br.mil.eb.decex.ati.conversor;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import br.mil.eb.decex.ati.modelo.Tela;

/**
 * Conversor padrão para Telas
 * @author William <b>Moreira</b> de Pinho - CAP
 * @version 1.0
 */
@FacesConverter(value="telaConverter")
public class TelaConverter implements Converter {

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {

		if(value != null && !value.isEmpty()) 
			return (Tela) component.getAttributes().get(value);

		return null;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		
		if(value instanceof Tela){
			Tela tela = (Tela) value;
			if(tela != null && tela instanceof Tela && tela.getNome() != null) {
				component.getAttributes().put(tela.getNome(), tela);
				return tela.getNome();
			}
		}
		
		return "";		
	}
	
	
}