package org.example;
import java.util.List;

public class Badges {

    private String name;
    private String course;
    private List<String> prerequisites;


    public Badges(String name, String course, List<String> prerequisites) {
        super();
        this.name = name;
        this.course = course;
        this.prerequisites = prerequisites;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public List<String> getPrerequisites() {
        return prerequisites;
    }

    public void setPrerequisites(List<String> prerequisites) {
        this.prerequisites = prerequisites;
    }

}
