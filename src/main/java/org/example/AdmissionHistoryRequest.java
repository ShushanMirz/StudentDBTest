package org.example;

import java.util.List;

public class AdmissionHistoryRequest {
    private List<AdmissionRecord> admissionHistory;

    public AdmissionHistoryRequest(List<AdmissionRecord> admissionHistory) {
        this.admissionHistory = admissionHistory;
    }

    public List<AdmissionRecord> getAdmissionHistory() {
        return admissionHistory;
    }

    public void setAdmissionHistory(List<AdmissionRecord> admissionHistory) {
        this.admissionHistory = admissionHistory;
    }

    public static class AdmissionRecord {
        private String date;
        private String note;
        private String state;

        public AdmissionRecord(String date, String note, String state) {
            this.date = date;
            this.note = note;
            this.state = state;
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

        public String getState() {
            return state;
        }

        public void setState(String state) {
            this.state = state;
        }
    }
}

