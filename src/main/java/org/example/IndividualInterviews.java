package org.example;


public class IndividualInterviews {

    private String date;
    private String note;
    private String learningPace;
    private String issues;
    private String student;


    public IndividualInterviews(String date, String note, String learningPace, String issues, String student) {
        super();
        this.date = date;
        this.note = note;
        this.learningPace = learningPace;
        this.issues = issues;
        this.student = student;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getLearningPace() {
        return learningPace;
    }

    public void setLearningPace(String learningPace) {
        this.learningPace = learningPace;
    }

    public String getIssues() {
        return issues;
    }

    public void setIssues(String issues) {
        this.issues = issues;
    }

    public String getStudent() {
        return student;
    }

    public void setStudent(String student) {
        this.student = student;
    }


    @Override
    public String toString() {
        return "IndividualInterviews{" +
                "date='" + date + '\'' +
                ", note='" + note + '\'' +
                ", learningPace='" + learningPace + '\'' +
                ", issues='" + issues + '\'' +
                ", student='" + student + '\'' +
                '}';
    }
}
