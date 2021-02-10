public class HistoryEntry {
    public String commitIdentifier;
    public String date;
    public String buildLog;

    public HistoryEntry(String commitIdentifier, String date, String buildLog) {
        this.commitIdentifier = commitIdentifier;
        this.date = date;
        this.buildLog = buildLog;
    }

    public HistoryEntry() {
    }

    public String getCommitIdentifier() {
        return commitIdentifier;
    }

    public void setCommitIdentifier(String commitIdentifier) {
        this.commitIdentifier = commitIdentifier;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getBuildLog() {
        return buildLog;
    }

    public void setBuildLog(String buildLog) {
        this.buildLog = buildLog;
    }
}
