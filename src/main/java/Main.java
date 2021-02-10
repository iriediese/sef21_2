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
 Skeleton of a ContinuousIntegrationServer which acts as webhook
 See the Jetty documentation for API documentation of those classes.
 */
public class Main extends AbstractHandler{
    private RepositoryDetails repositoryDetails = new RepositoryDetails();

    public void handle(String target,
                       Request baseRequest,
                       HttpServletRequest request,
                       HttpServletResponse response)
            throws IOException, ServletException
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

        // email, updated_at, repo name, sha

        cloneRepo("https://github.com/iriediese/sef21_2.git", "development", "clone_test");
        build("clone_test");
        repositoryDetails.setDate(new Date().toString());
        test("test", "clone_test");
        removeDirectory(new File("clone_test"));

        response.getWriter().println("CI job done");
    }

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
     *
     * @param phase
     * @param directory
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
     * Method that clones a Git repository into a given directory.
     * @param uri Addres to the repository to be cloned.
     * @param branchName Name of the branch to be cloned.
     * @param directory Location to store the repo.
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

    public static int build(String directory){

        /**
         * Method that uses maven to build a project (containing a pom.xml) in a given directory.
         * @param directory Location to store the repo.
         */

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

    // used to start the CI server in command line
    public static void main(String[] args) throws Exception
    {

        Server server = new Server(8080);
        server.setHandler(new Main());
        server.start();
        server.join();

    }
}