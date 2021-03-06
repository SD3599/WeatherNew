package com.WeatherApp.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.WeatherApp.model.User;
import com.WeatherApp.service.UserService;

/**This class creates a custom validation for the user credentials
 * 
 * @author Swapnika
 *
 */
@Component
public class UserValidator implements Validator {
    @Autowired
    private UserService userService;
    private String UN="username";
    private String PASS="password";
    private String NOTEMPTY="NotEmpty";
    /**
     * This method checks if the parameter class is equal to user entity
     */
    @Override
    public boolean supports(Class<?> aClass) {
        return User.class.equals(aClass);
    }

    /**
     * This method validates the user object for various fields based on the listed 
     * conditions and populates the errors if they exist
     * @param Object contains the user object
     * @param errors is the placeholder for errors
     */
    @Override
    public void validate(Object o, Errors errors) {
        User user = (User) o;

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, UN, NOTEMPTY);
        if (user.getUsername().length() < 6 || user.getUsername().length() > 32) {
            errors.rejectValue(UN, "Size.userForm.username");
        }
        if (userService.findByUsername(user.getUsername()) != null) {
            errors.rejectValue(UN, "Duplicate.userForm.username");
        }

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", NOTEMPTY);
        if (user.getPassword().length() < 8 || user.getPassword().length() > 32) {
            errors.rejectValue(PASS, "Size.userForm.password");
        }

        if (!user.getPasswordConfirm().equals(user.getPassword())) {
            errors.rejectValue(PASS, "Diff.userForm.passwordConfirm");
        }
        ValidationUtils.rejectIfEmpty(errors, "Location", NOTEMPTY);
        
    }
}
