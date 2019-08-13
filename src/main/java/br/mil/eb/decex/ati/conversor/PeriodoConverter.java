package br.mil.eb.decex.ati.conversor;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import br.mil.eb.decex.ati.modelo.Periodo;

/**
 * Conversor padrão para Período
 * @author William <b>Moreira</b> de Pinho - 1º TEN QCO
 * @version 1.0
 */
@FacesConverter(value="periodoConverter")
public class PeriodoConverter implements Converter {

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {

		if(value != null && !value.isEmpty()) 
			return (Periodo) component.getAttributes().get(value);

		return null;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		
		if(value instanceof Periodo){
			Periodo periodo = (Periodo) value;
			if(periodo != null && periodo instanceof Periodo 
					&& periodo.getId() != null) {
				component.getAttributes().put(periodo.getId().toString(), periodo);
				return periodo.getId().toString();
			}
		}
		
		return "";		
	}
}