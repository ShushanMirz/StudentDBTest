package org.example;

public class Waves {

    private String name;
    private String department;
    private String start;

    public Waves(String name, String department, String start) {
        super();
        this.name = name;
        this.department = department;
        this.start = start;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    @Override
    public String toString() {
        return "Waves{" +
                "name='" + name + '\'' +
                ", department='" + department + '\'' +
                ", start='" + start + '\'' +
                '}';
    }
}