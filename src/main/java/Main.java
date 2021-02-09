import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletException;

import java.io.File;
import java.io.IOException;
import java.util.Collections;

import org.apache.maven.shared.invoker.*;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.AbstractHandler;

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

    public void cloneRepo(){
        /**
         * write the funciton that clones
         * the
         * repository
         * right
         * here
         * please
         */
    }

    public static int build(){

        // Create build
        /**
         * write the function that builds
         * right
         * here
         */

        InvocationRequest request = new DefaultInvocationRequest();
        request.setPomFile (new File("pom.xml"));
        request.setGoals (Collections.singletonList( "install" ));
        Invoker invoker = new DefaultInvoker();
        invoker.setMavenHome(new File("/usr/"));
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
        if(build() == 0){
            System.out.println("compiles");
        }

        Server server = new Server(8080);
        server.setHandler(new Main());
        server.start();
        server.join();

    }
}