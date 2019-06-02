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
       Voeg de functionaliteit voor een addJoke en een  toe
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
                    .to("direct:getJokes");

        from("direct:getJokes")
                .routeId("getJokes")
                .setBody(constant("select * from jokes"))
                .to("jdbc:dataSource")
                .end();
    }
}
