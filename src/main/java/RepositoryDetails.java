public class RepositoryDetails {
    private String url;
    private String date;
    private String branch;
    private String pusher_email;
    private String name;

    public RepositoryDetails() {
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public String getPusher_email() {
        return pusher_email;
    }

    public void setPusher_email(String pusher_email) {
        this.pusher_email = pusher_email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
