package ru.vyazankin.builder;

public class PersonBuilder {
    protected String firstName;
    protected String lastName;
    protected String middleName;
    protected String country;
    protected String address;
    protected String phone;
    protected int age;
    protected String gender;

    public PersonBuilder addFirstName(String firstName){
        this.firstName = firstName;
        return this;
    }

    public PersonBuilder addLastName(String lastName){
        this.lastName = lastName;
        return this;
    }

    public PersonBuilder addMiddleName(String middleName){
        this.middleName = middleName;
        return this;
    }

    public PersonBuilder addCountry(String country){
        this.country = country;
        return this;
    }

    public PersonBuilder addAddress(String address){
        this.address = address;
        return this;
    }

    public PersonBuilder addPhone(String phone){
        this.phone = phone;
        return this;
    }

    public PersonBuilder addAge(int age){
        this.age = age;
        return this;
    }

    public PersonBuilder addGender(String gender){
        this.gender = gender;
        return this;
    }

    public Person build(){
        Person person = new Person();
        person.setFirstName(firstName);
        person.setLastName(lastName);
        person.setMiddleName(middleName);
        person.setCountry(country);
        person.setAddress(address);
        person.setPhone(phone);
        person.setAge(age);
        person.setGender(gender);
        return person;
    }


}
