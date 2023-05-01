package org.example;

public interface Endpoint {
    String All_Users=  "/users";
    String Single_User = "/users/{userId}";

    String All_Payments =  "/payments";
    String Single_Payment = "/payments/{paymentId}";

    String All_Students =  "/students";
    String Single_Student = "/students/{studentId}";

    String Login = "/auth/login";

    String Students_Pass_Data = "/students/{studentId}/passport-data";
    String Students_Emergency_Contact = "/students/{studentId}/emergency-contact";
    String Students_Work_Experience = "/students/{studentId}/work-experience";
    String Students_Admission_History = "/students/{studentId}/admission-history";
    String Students_Exam_History = "/students/{studentId}/exam-history";
    String Students_Education_Info = "/students/{studentId}/education-information";
    String Students_Comm_Logs = "/students/{studentId}/comm-logs";
    String Students_Image = "/students/{studentId}/image";
    String Students_Cv = "/students/{studentId}/cv";
    String Students_Current_Groups = "/students/{studentId}/current-groups";
    String Students_Previous_Groups = "/students/{studentId}/previous-groups";
    String Students_Badges = "/students/{studentId}/badges";
    String Students_Ta = "/students/{studentId}/ta";
    String Students_Indi_Interviews = "/students/{studentId}/indi-interviews";
    String Students_Skills = "/students/{studentId}/skills";
    String Students_Notes = "/students/{studentId}/notes";
    String Students_Payments = "/students/{studentId}/payments";
    String Students_Departments = "/students/{studentId}/departments";

    String All_Skills = "/skills";
    String Single_Skill = "/skills/{skillId}";

    String All_Departments = "/departments";
    String Single_Department = "/departments/{departmentId}";


    String All_Courses = "/courses";
    String Single_Course = "/courses/{courseId}";


    String All_Groups = "/groups";
    String Single_Group = "/groups/{groupId}";


    String All_Waves = "/waves";
    String Single_Waves = "/waves/{waveId}";



}
