package org.example;

import java.util.List;

public class ExamHistoryRequest {
    private List<ExamResult> examHistory;

    public ExamHistoryRequest(List<ExamResult> examHistory) {
        this.examHistory = examHistory;
    }

    public List<ExamResult> getExamHistory() {
        return examHistory;
    }

    public void setExamHistory(List<ExamResult> examHistory) {
        this.examHistory = examHistory;
    }

    public static class ExamResult {
        private String exam;
        private String status;
        private String feedback;
        private int score;

        public ExamResult(String exam, String status, String feedback, int score) {
            this.exam = exam;
            this.status = status;
            this.feedback = feedback;
            this.score = score;
        }

        public String getExam() {
            return exam;
        }

        public void setExam(String exam) {
            this.exam = exam;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getFeedback() {
            return feedback;
        }

        public void setFeedback(String feedback) {
            this.feedback = feedback;
        }

        public int getScore() {
            return score;
        }

        public void setScore(int score) {
            this.score = score;
        }
    }
}
