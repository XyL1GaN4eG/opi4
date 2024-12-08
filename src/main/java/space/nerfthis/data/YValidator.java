package space.nerfthis.data;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;
import javax.faces.validator.FacesValidator;

@FacesValidator("yValidator")
public class YValidator implements Validator<Double> {

    @Override
    public void validate(FacesContext context, UIComponent component, Double value) throws ValidatorException {
        if (value == null || value < -5 || value > 3) {
            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Invalid Y value", "Y must be between -5 and 3."));
        }
    }
}
