package com.example.eventservice.model.dto;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

public class OrganizerDto implements BaseDto {
    @NotEmpty(message = "An organizer name can't be empty.")
    @Length(min = 3, max = 20, message = "An organizer name length has to be from 3 to 20 symbols.")
    @Pattern(regexp = "\\w*", message = "An organizer name has to contain only letters.")
    private String organizerName;
    @NotEmpty(message = "Email can't be empty.")
    @Email(message = "Email has to be like xxx@xxx.xxx.")
    private String organizerEmail;
    @NotEmpty(message = "A telephone number can't be empty.")
    @Pattern(regexp = "\\+\\d{12}", message = "A telephone number has to include plus(+) and 12 digits.")
    private String organizerTelephoneNumber;

    public OrganizerDto() {
    }

    public OrganizerDto(String organizerName, String organizerEmail, String organizerTelephoneNumber) {
        this.organizerName = organizerName;
        this.organizerEmail = organizerEmail;
        this.organizerTelephoneNumber = organizerTelephoneNumber;
    }

    public String getOrganizerName() {
        return organizerName;
    }

    public void setOrganizerName(String organizerName) {
        this.organizerName = organizerName;
    }

    public String getOrganizerEmail() {
        return organizerEmail;
    }

    public void setOrganizerEmail(String organizerEmail) {
        this.organizerEmail = organizerEmail;
    }

    public String getOrganizerTelephoneNumber() {
        return organizerTelephoneNumber;
    }

    public void setOrganizerTelephoneNumber(String organizerTelephoneNumber) {
        this.organizerTelephoneNumber = organizerTelephoneNumber;
    }

    @Override
    public String toString() {
        return new StringBuffer("OrganizerDto{")
                .append("name='")
                .append(organizerName)
                .append("', email='")
                .append(organizerEmail)
                .append("', organizerTelephoneNumber='")
                .append(organizerTelephoneNumber)
                .append("'}")
                .toString();
    }
}