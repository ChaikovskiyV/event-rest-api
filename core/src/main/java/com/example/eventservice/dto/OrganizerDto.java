package com.example.eventservice.dto;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

public class OrganizerDto {
    @NotEmpty(message = "An organizer name can't be empty.")
    @Length(min = 3, max = 20, message = "An organizer name length has to be from 3 to 20 symbols.")
    @Pattern(regexp = "\\w*", message = "An organizer name has to contain only letters.")
    private String name;
    @NotEmpty(message = "Email can't be empty.")
    @Email(message = "Email has to be like xxx@xxx.xxx.")
    private String email;
    @NotEmpty(message = "A telephone number can't be empty.")
    @Pattern(regexp = "\\+\\d{12}", message = "A telephone number has to include plus(+) and 12 digits.")
    private String telephoneNumber;

    public OrganizerDto() {
    }

    public OrganizerDto(String name, String email, String telephoneNumber) {
        this.name = name;
        this.email = email;
        this.telephoneNumber = telephoneNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelephoneNumber() {
        return telephoneNumber;
    }

    public void setTelephoneNumber(String telephoneNumber) {
        this.telephoneNumber = telephoneNumber;
    }
}