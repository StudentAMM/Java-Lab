package com.company.Cats;


public class Cat{
    private String name; // имя
    private String breed; // порода

    public Cat(){}

    public Cat(String n, String b) {
        this.breed = b;
        this.name = n;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBreed() {
        return breed;
    }

    public void setBreed(String breed) {
        this.breed = breed;
    }

}
