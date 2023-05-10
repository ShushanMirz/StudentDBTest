package org.example;

import java.util.List;

public class EducationInformationRequest {
    private List<EducationInformation> educationInformation;

    public EducationInformationRequest(List<EducationInformation> educationInformation) {
        this.educationInformation = educationInformation;
    }

    public List<EducationInformation> getEducationInformation() {
        return educationInformation;
    }

    public void setEducationInformation(List<EducationInformation> educationInformation) {
        this.educationInformation = educationInformation;
    }

    public static class EducationInformation {
        private String institutionName;
        private String programName;
        private String startDate;
        private String endDate;

        public EducationInformation(String institutionName, String programName, String startDate, String endDate) {
            this.institutionName = institutionName;
            this.programName = programName;
            this.startDate = startDate;
            this.endDate = endDate;
        }

        public String getInstitutionName() {
            return institutionName;
        }

        public void setInstitutionName(String institutionName) {
            this.institutionName = institutionName;
        }

        public String getProgramName() {
            return programName;
        }

        public void setProgramName(String programName) {
            this.programName = programName;
        }

        public String getStartDate() {
            return startDate;
        }

        public void setStartDate(String startDate) {
            this.startDate = startDate;
        }

        public String getEndDate() {
            return endDate;
        }

        public void setEndDate(String endDate) {
            this.endDate = endDate;
        }
    }

}