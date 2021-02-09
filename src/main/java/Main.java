import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletException;

import java.io.File;
import java.io.IOException;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.AbstractHandler;

import org.eclipse.jgit.api.CloneCommand;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.errors.TransportException;
import sun.rmi.transport.Transport;

/**
 Skeleton of a ContinuousIntegrationServer which acts as webhook
 See the Jetty documentation for API documentation of those classes.
 */
public class Main extends AbstractHandler
{
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

        // here you do all the continuous integration tasks
        // for example
        // 1st clone your repository
        // 2nd compile the code

        response.getWriter().println("CI job done");
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

    public void build(){
        /**
         * write the function that builds
         * right
         * here
         */
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