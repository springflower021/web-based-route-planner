package frontend;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;

public class RootHandler implements HttpHandler {

        @Override

        public void handle(HttpExchange he) throws IOException {

            /*
           String response = "<h1>Server start success if you see this message</h1>" + "<h1>Port: " + Main.port + "</h1>";
            he.sendResponseHeaders(200, response.length());
            OutputStream os = he.getResponseBody();
            os.write(response.getBytes());
            os.close();*/


            File file = new File("C:\\Users\\paula\\IdeaProjects\\new\\web-based-route-planner\\index.html");
            he.sendResponseHeaders(200, file.length());
            try (OutputStream outputStream = he.getResponseBody()) {
                Files.copy(file.toPath(), outputStream);
            }


        }

}
