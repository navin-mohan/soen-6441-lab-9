package actors;

import akka.actor.AbstractActorWithTimers;
import akka.actor.ActorRef;
import akka.actor.Props;
import scala.concurrent.duration.Duration;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class TimeActor extends AbstractActorWithTimers {

    private final List<ActorRef> userActorList = new ArrayList<>();

    public static Props getProps() {
        return Props.create(TimeActor.class);
    }
    private static final class Tick {
    }

    static final class RegisterMsg {
    }

    @Override
    public void preStart() {
        getTimers().startPeriodicTimer(
                "Timer",
                new Tick(),
                Duration.create(5, TimeUnit.SECONDS));
    }

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(Tick.class, message -> notifyClients())
                .match(RegisterMsg.class, message -> {
                    userActorList.add(getSender());
                })
                .build();
    }

    private void notifyClients() {
        System.out.println("TimeActor: notifyClients: " + userActorList.size() );
        UserActor.TimeMessage tMsg = new UserActor.TimeMessage(LocalDateTime.now().toString());
        userActorList.forEach(ar -> ar.tell(tMsg, self()));
    }
}
