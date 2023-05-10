package org.example;

import java.util.List;

public class CommLogsRequest {
    private List<CommLog> commLogs;

    public CommLogsRequest(List<CommLog> commLogs) {
        this.commLogs = commLogs;
    }

    public List<CommLog> getCommLogs() {
        return commLogs;
    }

    public void setCommLogs(List<CommLog> commLogs) {
        this.commLogs = commLogs;
    }

    public static class CommLog {
        private String date;
        private String type;
        private String log;

        public CommLog(String date, String type, String log) {
            this.date = date;
            this.type = type;
            this.log = log;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getLog() {
            return log;
        }

        public void setLog(String log) {
            this.log = log;
        }
    }
}

