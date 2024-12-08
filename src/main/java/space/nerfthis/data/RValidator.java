package space.nerfthis.data;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;
import javax.faces.validator.FacesValidator;

@FacesValidator("rValidator")
public class RValidator implements Validator<Double> {

    @Override
    public void validate(FacesContext context, UIComponent component, Double value) throws ValidatorException {
        if (value == null || value < 1 || value > 4) {
            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Invalid R value", "R must be between 1 and 4."));
        }
    }
}
