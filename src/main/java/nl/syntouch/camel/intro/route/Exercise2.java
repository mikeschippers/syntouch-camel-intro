package nl.syntouch.camel.intro.route;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class Exercise2 extends RouteBuilder {

    @Override
    public void configure() throws Exception {

        /* Exercise 2.1
           Split het input bestand aan de hand van een line break (\n). Schrijf het resultaat weg naar de done folder.
           Gebruik hier voor de fileName output-${property.CamelSplitIndex}.txt
         */

        from("file:src/main/resources/exercise1/inbox?fileName=input.txt&noop=true")
                .routeId("exercise2")
                .log("TODO");


    }
}
