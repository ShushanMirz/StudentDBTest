package org.example;

public class Departments {

    private String name;
    private String head;


    public Departments(String name, String head) {
        super();
        this.name = name;
        this.head = head;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHead() {
        return head;
    }

    public void setHead(String head) {
        this.head = head;
    }

    @Override
    public String toString() {
        return "Departments{" +
                "name='" + name + '\'' +
                ", head='" + head + '\'' +
                '}';
    }
}