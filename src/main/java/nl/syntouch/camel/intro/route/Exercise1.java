package nl.syntouch.camel.intro.route;

import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.springframework.stereotype.Component;

@Component
public class Exercise1 extends RouteBuilder {
    @Override
    public void configure() throws Exception {

        /* Excercise 1.1
           Voeg een choice toe aan de route, als de body 'chunk norris' bevat log dit als LoggingLevel.WARN
           Gebruik hier voor de simple expression language, zie https://camel.apache.org/simple.html
         */

        /* Exercise 1.2
           Verander het woord "Chuck Norris" naar ${sys.user.name}
         */

        from("timer:myTimer?repeatCount=1")
                .routeId("exercise1")
                .to("http4:api.icndb.com/jokes/random")
                .unmarshal().json(JsonLibrary.Jackson)
                .transform(simple("${body[value][joke]}"))
                .log(LoggingLevel.INFO, "${body}")
        ;
    }
}
