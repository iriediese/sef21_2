/**
 * Repository details class that stores the details for the current repository.
 * Stored details: url, date, branch, pusher email and name.
 *
 * @author Alexander E, Ioana C, Joaquin B Q, Johan H, Theodor M
 * @since 2021-02-08
 */
public class RepositoryDetails {
    private String url;
    private String date;
    private String branch;
    private String pusher_email;
    private String name;

    /**
     * Class constructor.
     */
    public RepositoryDetails() {
    }

    /**
     * Gets the url.
     *
     * @return a String representing the value of the url
     */
    public String getUrl() {
        return url;
    }

    /**
     * Sets the url of the repository.
     *
     * @param url  the url of the repository
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * Gets the date.
     *
     * @return a String representing the value of date
     */
    public String getDate() {
        return date;
    }

    /**
     * Sets the date of the repository.
     *
     * @param date  the date of the repository
     */
    public void setDate(String date) {
        this.date = date;
    }

    /**
     * Gets the branch.
     *
     * @return a String representing the value of branch
     */
    public String getBranch() {
        return branch;
    }

    /**
     * Sets the branch of the repository.
     *
     * @param branch  the branch of the repository
     */
    public void setBranch(String branch) {
        this.branch = branch;
    }

    /**
     * Gets the pusher_email
     *
     * @return a String representing the value of pusher_email
     */
    public String getPusher_email() {
        return pusher_email;
    }

    /**
     * Sets the pusher_email of the repository.
     *
     * @param pusher_email  the pusher_email of the repository
     */
    public void setPusher_email(String pusher_email) {
        this.pusher_email = pusher_email;
    }

    /**
     * Gets the name
     *
     * @return a String representing the value of name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the repository.
     *
     * @param name  the name of the repository
     */
    public void setName(String name) {
        this.name = name;
    }
}
