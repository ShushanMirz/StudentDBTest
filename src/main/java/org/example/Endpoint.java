package org.example;

public interface Endpoint {
    String All_Users=  "/users";
    String Single_User = "/users/{id}";

    String All_Payments =  "/payments";
    String Single_Payment = "/payments/{id}";

    String All_Students =  "/students";
    String Single_Student = "/students/{id}";

    String Login = "/auth/login";

    String Students_Pass_Data = "/students/{id}/passport-data";
    String Students_Emergency_Contact = "/students/{id}/emergency-contact";
    String Students_Work_Experience = "/students/{id}/work-experience";
    String Students_Admission_History = "/students/{id}/admission-history";
    String Students_Exam_History = "/students/{id}/exam-history";
    String Students_Education_Info = "/students/{id}/education-information";
    String Students_Comm_Logs = "/students/{id}/comm-logs";
    String Students_Image = "/students/{id}/image";
    String Students_Cv = "/students/{id}/cv";
    String Students_Current_Groups = "/students/{id}/current-groups";
    String Students_Previous_Groups = "/students/{id}/previous-groups";
    String Students_Badges = "/students/{id}/badges";
    String Students_Ta = "/students/{id}/ta";
    String Students_Indi_Interviews = "/students/{id}/indi-interviews";
    String Students_Skills = "/students/{id}/skills";
    String Students_Notes = "/students/{id}/notes";
    String Students_Payments = "/students/{id}/payments";
    String Students_Departments = "/students/{id}/departments";
    String All_Skills = "/skills";
    String Single_Skill = "/skills/{id}";

    String All_Departments = "/departments";
    String Single_Department = "/departments/{id}";


    String All_Courses = "/courses";
    String Single_Course = "/courses/{id}";


    String All_Groups = "/groups";
    String Single_Group = "/groups/{id}";


    String All_Waves = "/waves";
    String Single_Wave = "/waves/{id}";

    String All_InDi_Interviews = "/individual-interviews";
    String Single_InDi_Interviews = "/individual-interviews/{id}";

    String All_Badges = "/badges";
    String Single_Badge = "/badges/{id}";

    String All_Exams = "/exams";
    String Single_Exam = "/exams/{id}";


    String All_Notes = "/notes";
    String Single_Note = "/notes/{id}";




}
