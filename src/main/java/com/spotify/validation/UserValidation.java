package com.spotify.validation;

import com.spotify.model.User;
import com.spotify.utilities.Constants;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.regex.Pattern;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserValidation {

    boolean isUserValid;
    String errorMessage;

    public boolean isValid(User user) {

        boolean isNameValid = isNameValid(user.getName());
        boolean isEmailValid = isEmailValid(user.getEmail());
        boolean isContactValid = isContactValid(user.getContactNo());

        if (!isNameValid || !isEmailValid || !isContactValid) {
            this.isUserValid = false;
            if (!isNameValid) {
                this.errorMessage = Constants.USER_NAME_INVALID;
            }
            else if (!isEmailValid) {
                this.errorMessage = Constants.USER_EMAIL_INVALID;
            }
            else if (!isContactValid) {
                this.errorMessage = Constants.USER_CONTACT_INVALID;
            }
        }
        return this.isUserValid;
    }

    public boolean isNameValid(String name) {
        if (name != null && !name.isEmpty()) {
            name = name.trim();
            if(name.length() >= 2 && isAllString(name)) {
                return true;
            }
        }
        return false;
    }


    public boolean isAllString(String string) {
        String stringPattern = "[a-zA-Z\\s]+";
        return Pattern.compile(stringPattern)
                .matcher(string)
                .matches();
    }


    /*
        It allows numeric values from 0 to 9
        Both uppercase and lowercase letters from a to z are allowed
        Allowed are underscore “_”, hyphen “-” and dot “.”
        Dot isn't allowed at the start and end of the local-part
        Consecutive dots aren't allowed
        For the local part, a maximum of 64 characters are allowed
     */
    public boolean isEmailValid(String emailAddress) {
        if(emailAddress != null && !emailAddress.isEmpty()) {
            String emailRegexPattern = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";
            return Pattern.compile(emailRegexPattern)
                    .matcher(emailAddress)
                    .matches();
        }
        return false;
    }


    /*
        (0/91): number starts with (0/91)
        [7-9]: starting of the number may contain a digit between 0 to 9
        [0-9]: then contains digits 0 to 9
    */
    public boolean isContactValid(String contactNumber) {
        if(contactNumber != null && !contactNumber.isEmpty()) {
            String contactRegexPattern = "(0/91)?[7-9][0-9]{9}";
            return Pattern.compile(contactRegexPattern)
                    .matcher(contactNumber)
                    .matches();
        }
        return false;
    }

}
