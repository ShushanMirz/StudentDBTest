package org.example;

import java.util.List;

public class WorkExperienceRequest {
    private List<WorkExperience> workExperience;

    public WorkExperienceRequest(List<WorkExperience> workExperience) {
        this.workExperience = workExperience;
    }

    public List<WorkExperience> getWorkExperience() {
        return workExperience;
    }

    public void setWorkExperience(List<WorkExperience> workExperience) {
        this.workExperience = workExperience;
    }

    public static class WorkExperience {
        private String companyName;
        private String positionTitle;
        private String startDate;
        private String endDate;

        public WorkExperience(String companyName, String positionTitle, String startDate, String endDate) {
            this.companyName = companyName;
            this.positionTitle = positionTitle;
            this.startDate = startDate;
            this.endDate = endDate;
        }

        public String getCompanyName() {
            return companyName;
        }

        public void setCompanyName(String companyName) {
            this.companyName = companyName;
        }

        public String getPositionTitle() {
            return positionTitle;
        }

        public void setPositionTitle(String positionTitle) {
            this.positionTitle = positionTitle;
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
