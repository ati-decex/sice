package br.mil.eb.decex.ati.conversor;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import br.mil.eb.decex.ati.modelo.QCP;

/**
 * Conversor padrão para DocumentoReferencia
 * @author Luiz Augusto Lourenço do <b>Amparo</b>3º SGT QCO
 * @version 1.0
 */
@FacesConverter(value="qcpConverter")
public class QCPConverter implements Converter {

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {

		if(value != null && !value.isEmpty()) 
			return (QCP) component.getAttributes().get(value);

		return null;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		
		if(value instanceof QCP){
			QCP documento = (QCP) value;
			if(documento != null && documento instanceof QCP 
					&& documento.getDataAlteracao() != null) {
				component.getAttributes().put(documento.toString(), documento);
				return documento.toString();
			}
		}
		
		return "";		
	}
}
