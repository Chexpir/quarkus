package io.quarkus.it.opentelemetry;

import java.util.concurrent.CompletionStage;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.eclipse.microprofile.reactive.messaging.Message;

@ApplicationScoped
public class TracedKafkaConsumer {

    @Inject
    TracedService tracedService;

    @Incoming("traces-in")
    CompletionStage<Void> consume(Message<String> msg) {
        tracedService.call();
        return msg.ack();
    }

}
