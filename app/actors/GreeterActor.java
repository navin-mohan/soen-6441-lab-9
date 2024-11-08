package actors;

import akka.actor.AbstractActor;
import services.GreeterAPI;

import javax.inject.Inject;

public class GreeterActor extends AbstractActor {

    GreeterAPI greeterAPI;

    @Inject
    public GreeterActor(GreeterAPI greeterAPI) {
        this.greeterAPI = greeterAPI;
    }

    public static class SayHello {
        public final String name;
        public SayHello(String name) {
            this.name = name;
        }
    }

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(SayHello.class, this::handleSayHello)
                .build();
    }

    private void handleSayHello(SayHello message) {
        getSender().tell(greeterAPI.sayHello(message.name), getSelf());
    }
}
