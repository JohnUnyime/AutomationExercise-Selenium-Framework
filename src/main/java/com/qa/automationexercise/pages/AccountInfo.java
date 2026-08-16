package com.qa.automationexercise.pages;

public class AccountInfo {

    private final String title;
    private final String password;
    private final String day;
    private final String month;
    private final String year;
    private final boolean newsletter;
    private final boolean optin;
    private final String firstName;
    private final String lastName;
    private final String company;
    private final String address1;
    private final String address2;
    private final String country;
    private final String state;
    private final String city;
    private final String zipcode;
    private final String mobileNumber;

    public AccountInfo(String title, String password, String day, String month, String year,
                        boolean newsletter, boolean optin,
                        String firstName, String lastName, String company,
                        String address1, String address2, String country,
                        String state, String city, String zipcode, String mobileNumber) {
        this.title = title;
        this.password = password;
        this.day = day;
        this.month = month;
        this.year = year;
        this.newsletter = newsletter;
        this.optin = optin;
        this.firstName = firstName;
        this.lastName = lastName;
        this.company = company;
        this.address1 = address1;
        this.address2 = address2;
        this.country = country;
        this.state = state;
        this.city = city;
        this.zipcode = zipcode;
        this.mobileNumber = mobileNumber;
    }

    public String getTitle() { return title; }
    public String getPassword() { return password; }
    public String getDay() { return day; }
    public String getMonth() { return month; }
    public String getYear() { return year; }
    public boolean isNewsletter() { return newsletter; }
    public boolean isOptin() { return optin; }
    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
    public String getCompany() { return company; }
    public String getAddress1() { return address1; }
    public String getAddress2() { return address2; }
    public String getCountry() { return country; }
    public String getState() { return state; }
    public String getCity() { return city; }
    public String getZipcode() { return zipcode; }
    public String getMobileNumber() { return mobileNumber; }
}