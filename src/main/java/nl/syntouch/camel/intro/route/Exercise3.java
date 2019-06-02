package nl.syntouch.camel.intro.route;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.rest.RestBindingMode;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;

@Component
public class Exercise3 extends RouteBuilder {

    /* Exercise 3.1
       H2 Console: http://localhost:8080/h2-console/ JDBC-URL: jdbc:h2:mem:test
       Swagger URL: http://localhost:8080/api-doc

       Maak een route die de regels uit het input.txt bestand in de database zet.
    */

    /* Exercise 3.2
       Voeg de functionaliteit voor een addJoke en een deleteJoke
       Hint: gebruik de useHeadersAsParameters bij het JDBC component
     */

    @Override
    public void configure() throws Exception {

        restConfiguration()
                .component("servlet")
                .enableCORS(true)
                .bindingMode(RestBindingMode.json)
                .contextPath("/")
                .apiContextPath("/api-doc")
                    .apiProperty("api.title", "Jokes REST API")
                    .apiProperty("api.version", "1.0")
                    .apiProperty("cors", "true");

        rest()
                .produces("application/json")
                .consumes("application/json")
                .get("/jokes")
                    .to("direct:getJokes")
                .post("/jokes")
                    .to("direct:addJoke")
                .delete("/jokes/{id}")
                    .to("direct:deleteJoke");

        from("direct:getJokes")
                .routeId("getJokes")
                .setBody(constant("select * from jokes"))
                .to("jdbc:dataSource")
                .end();

        from("direct:addJoke")
                .routeId("addJoke")
                .setHeader("joke", simple("${body[joke]}"))
                .setBody(constant("insert into jokes (joke) values (:?joke)"))
                .to("jdbc:dataSource?useHeadersAsParameters=true")
                .end();

        from("direct:deleteJoke")
                .routeId("deleteJoke")
                .setBody(constant("delete from jokes where id=(:?id)"))
                .to("jdbc:dataSource?useHeadersAsParameters=true");

        from("file:src/main/resources/exercise1/inbox?fileName=input.txt&noop=true")
                .routeId("loadJokes")
                .split().tokenize("\\n")
                .setHeader("joke", simple("${body}"))
                .setBody(constant("insert into jokes (joke) values (:?joke)"))
                .to("jdbc:dataSource?useHeadersAsParameters=true");
    }
}
