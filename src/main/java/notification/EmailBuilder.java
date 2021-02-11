package notification;

import repository.RepositoryDetails;

/**
 * Email builder for creating the HTML body of the notification email
 * sent to the pusher of a commit or to another team member.
 *
 * The email has the following structure (as constructed by the builder):
 * Build status
 * Date
 * Repository name
 * URL
 * Branch
 * Commit by
 *
 * @author Alexander E, Ioana C, Joaquin B Q, Johan H, Theodor M
 * @since 2021-02-08
 */
public class EmailBuilder {
    private final String successfulBuild =
            "<ul style=\"color:green;" +
                    "list-style-type: disc;" +
                    "   font-size: 400%;" +
                    "   padding-right: 3px;\">" +
                    "    <li><span style=\"color: black;font-size: 41% ;vertical-align: middle;\">" +
                    "Build completed successfully" +
                    "</span></li>" +
                    "</ul> " +
                    "<ul style=\"list-style-type: none; margin-top:-60px;\">";
    private final String unsuccessfulBuild =
            "<ul style=\"color:red;" +
                    "list-style-type: disc;" +
                    "   font-size: 400%;" +
                    "   padding-right: 3px;\">" +
                    "    <li><span style=\"color: black; font-size: 41% ;vertical-align: middle;\">" +
                    "Build failed</span></li>" +
                    "</ul>" +
                    "<ul style=\"list-style-type: none; margin-top:-60px;\">";
    private final String element =
            "<li><span style=\"color: black; font-size: 120%\">";

    private final String endLi =
            "</span></li>";

    private String content = "";

    /**
     * Class constructor specifying the status of the build and test run and the associated repository details.
     *
     * @param status the status of the build and test run on the specified repository.
     * @param repositoryDetails the specified repository details.
     *
     * @see RepositoryDetails
     */
    public EmailBuilder(Boolean status, RepositoryDetails repositoryDetails){
        if (status){
            this.content = successfulBuild;
        } else {
            this.content = unsuccessfulBuild;
        }
        buildContent(repositoryDetails);
    }

    /**
     * Builds the content of the notification email for the repository build and test run.
     *
     * Note:
     * Due to private access this method will not be included in the generated JavaDoc HTML files.
     *
     * @param repositoryDetails the specified repository details.
     */
    private void buildContent(RepositoryDetails repositoryDetails){
        this.content = this.content + element+ "Date: </span> <span style=\"color: black; font-size: 110%;\">" + repositoryDetails.getDate() + endLi;
        this.content = this.content + element+ "Repository name: </span> <span style=\"color: black; font-size: 110%;\">" + repositoryDetails.getName() + endLi;
        this.content = this.content + element+ "URL: </span> <span style=\"color: black; font-size: 110%;\">" + repositoryDetails.getUrl() + endLi;
        this.content = this.content + element+ "Branch: </span> <span style=\"color: black; font-size: 110%;\">" + repositoryDetails.getBranch() + endLi;
        this.content = this.content + element+ "Commit by: </span> <span style=\"color: black; font-size: 110%;\">" + repositoryDetails.getPusherEmail() + endLi;
    }

    /**
     * Gets the content of the email
     *
     * @return a String representing the content(body) of the email
     */
    public String getContent(){
        return this.content;
    }
}
