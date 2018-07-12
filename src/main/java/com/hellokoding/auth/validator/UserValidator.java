package com.hellokoding.auth.validator;

import com.hellokoding.auth.model.User;
import com.hellokoding.auth.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class UserValidator implements Validator {
    @Autowired
    private UserService userService;
    private String UN="username";
    private String PASS="password";
    private String NOTEMPTY="NotEmpty";
    @Override
    public boolean supports(Class<?> aClass) {
        return User.class.equals(aClass);
    }

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
