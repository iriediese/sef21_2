import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletException;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.Date;

import org.apache.maven.shared.invoker.*;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.AbstractHandler;

import org.eclipse.jgit.api.CloneCommand;
import org.eclipse.jgit.api.Git;
import org.json.JSONObject;


/**
 * Continuous Integration Server which handles the cloning, building,
 *  test running and user notification tasks, similar to other CI servers
 *  like Travis and Jenkins.
 *
 * A POST request on the provided URL in the webhook is initiated when
 *  the event which triggers the webhook occurs (in this case the event
 *  is a commit on any branch and by any user on the repository).
 *
 *  For further API documentation of Hetty-related classes see the Jetty documentation.
 *
 *  @author Alexander E, Ioana C, Joaquin B Q, Johan H, Theodor M
 *  @since 2021-02-08
 */
public class Main extends AbstractHandler{
    /**
     * Stores the repository details.
     * @see RepositoryDetails
     */
    public RepositoryDetails repositoryDetails = new RepositoryDetails();

    /**
     * Handles incoming requests to the Continuous Integration Server, mainly triggered by
     * the associated webhook. When the subscribed event in the webhook occurs (a commit),
     * a POST request will be triggered by the webhook.
     *
     * Based on the associated repository data provided in the request, this method initiates
     * the tasks related to the CI server: clone repository branch, build and run tests.
     *
     * @param target the target name.
     * @param baseRequest the base request. When request was handled the handled status is set to true.
     * @param request the request that the method handles.
     * @param response the server response for the handled request. The returned status will be set to 200.
     * @throws IOException if the BufferedReader fails to read the content of the request.
     */
    public void handle(String target,
                       Request baseRequest,
                       HttpServletRequest request,
                       HttpServletResponse response)
            throws IOException
    {
        response.setContentType("text/html;charset=utf-8");
        response.setStatus(HttpServletResponse.SC_OK);
        baseRequest.setHandled(true);


        System.out.println(target);

        BufferedReader reader = request.getReader();
        String row, text = "";
        while((row = reader.readLine()) != null){
            text += row;
        }
        reader.close();


        System.out.println(text);
        JSONObject jason = new JSONObject(text);
        System.out.println(jason);
        repositoryDetails.setUrl(jason.getJSONObject("repository").getString("clone_url"));
        repositoryDetails.setBranch(jason.getString("ref"));
        repositoryDetails.setPusher_email(jason.getJSONObject("pusher").getString("email"));
        repositoryDetails.setName(jason.getJSONObject("repository").getString("full_name"));

        cloneRepo("https://github.com/iriediese/sef21_2.git", "development", "clone_test");
        build("clone_test");
        repositoryDetails.setDate(new Date().toString());
        test("test", "clone_test");
        removeDirectory(new File("clone_test"));

        response.getWriter().println("CI job done");
    }

    /**
     * Removes the directory and all its files. If the directory is not empty, all its files
     * will be deleted before the deleting the directory.
     *
     * @param directory a file object which contains the path to a directory which will be deleted.
     *                  If the directory is not empty, its content will be removed prior to
     *                  deleting the directory itself.
     * @return true if and only if the directory and all its files are successfully deleted;
     *         false otherwise
     */
    boolean removeDirectory(File directory) {
        File[] files = directory.listFiles();
        if (files != null) {
            for (File file : files) {
                removeDirectory(file);
            }
        }
        return directory.delete();
    }

    /**
     * Executes the test running task on the provided repository.
     * Since the method uses the maven invoker the files that are considered for execution follow the
     * standard naming conventions. Namely, all files that contain the keyword "Test" in their name and
     * contain valid test methods definitions and that are correctly annotated as defined in JUnit4 documentation.
     *
     * If the build of the project fails, the test task will not be ran.
     * The function outputs the execution details on the standard output.
     *
     * @param phase String object containing the goal required by the InvocationRequest
     *              which will be executed. To invoke a test running task the value
     *              should be equal to "test".
     * @param directory the relative path to a directory which contains a pom.xml file.
     *                  This value is required by the invoker to run the test task.
     * @return the exit code (status) of the test task.
     *         If the execution succeeds the default return value is 0;
     *         otherwise the associated exit code is returned.
     */
    private int test(String phase, String directory) {
        InvocationRequest req = new DefaultInvocationRequest();
        req.setPomFile (new File(directory + "/" + "pom.xml"));
        req.setGoals (Collections.singletonList( phase ));
        Invoker invoker = new DefaultInvoker();
        invoker.setMavenHome(new File("apache-maven-3.6.3"));
        InvocationResult res;
        try {
            res = invoker.execute(req);
            if (res.getExitCode() != 0) {
                return res.getExitCode();
            }

        } catch (MavenInvocationException e) {
            System.err.println("Integration Test Failed");
        }
        return 0;
    }
    /**
     * Clones a Git repository available at the uri provided as a parameter into the given directory
     * and sets the current branch. The method uses the jgit API to set the destination directory
     * and the current branch and to invoke the clone command.
     *
     * @param uri the web address to the repository to be cloned. This corresponds to the specific uri
     *            required by the "git clone" command.
     * @param branchName the name of the branch to be cloned. This corresponds to the specific name
     *                   required by the "--branch" argument.
     * @param directory the location(path to a directory) where the clone will be stored.
     */
    public void cloneRepo(String uri, String branchName, String directory){

        CloneCommand cmd = Git.cloneRepository();
        cmd.setDirectory( new File(directory));
        cmd.setURI(uri);
        if (branchName != null)
            cmd.setBranch(branchName);

        try {
            Git git = cmd.call();
            git.close();
        } catch ( Exception e) {
           e.printStackTrace();
        }
    }

    /**
     * Executes the build task on the provided repository.
     * The method uses maven invoker to build the project based on the pom.xml file included in the
     * root directory of the project.
     *
     * @param directory path to the root directory of the repository that is being build.
     *                  The directory should contain a pom.xml file which is required for the build.
     * @return the exit code (status) of the build task.
     *         If the execution succeeds the default return value is 0.
     *         otherwise it throws an IllegalStateException.
     * @throws IllegalStateException if the build fails.
     */
    public static int build(String directory){
        InvocationRequest request = new DefaultInvocationRequest();
        request.setPomFile (new File(directory + "/" + "pom.xml"));
        request.setGoals (Collections.singletonList( "install" ));
        Invoker invoker = new DefaultInvoker();
        invoker.setMavenHome(new File("apache-maven-3.6.3"));
        InvocationResult result;
        try {
            result = invoker.execute(request);
            if(result.getExitCode() != 0){
                throw new IllegalStateException("Build failed");
            }
            return result.getExitCode();
        } catch (MavenInvocationException e) {
            System.out.println("Build failed");
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * Starts the Continuous Integration Server and waits for requests.
     * @param args command line arguments.
     * @throws Exception
     */
    public static void main(String[] args) throws Exception
    {

        Server server = new Server(8080);
        server.setHandler(new Main());
        server.start();
        server.join();

    }
}