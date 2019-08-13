package br.mil.eb.decex.ati.validador;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

/**
 * Tratamento para validação de range de datas
 * @author retirado de artigo da web
 */
@FacesValidator("dateRangeValidator")
public class DateRangeValidator implements Validator {

    @Override
    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        if (value == null) {
            return;
        }
         
        //Leave the null handling of startDate to required="true"
        Object startDateValue = component.getAttributes().get("dataInicial");
        if (startDateValue==null) {
            return;
        }
         
        SimpleDateFormat ft = new SimpleDateFormat("dd/MM/yyyy");
                        
        Date startDate = (Date)startDateValue;
        
        try {
        	startDate = ft.parse(ft.format(startDate));			
		} catch (ParseException e) {
			throw new RuntimeException(e);			
		}
        
        Date endDate = (Date)value;

        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "A data inicial não pode ser maior que a data final", null);
        
        if (endDate.before(startDate)) {
            throw new ValidatorException(message);
        }
    }			
}