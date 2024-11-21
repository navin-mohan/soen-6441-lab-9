package actors;

import akka.actor.AbstractActor;
import akka.actor.Props;
import akka.actor.ActorRef;
import com.fasterxml.jackson.databind.node.ObjectNode;
import play.libs.Json;

public class UserActor extends AbstractActor {

    private final ActorRef ws;

    public UserActor(ActorRef ws) {
        this.ws = ws;
    }

    @Override
    public void preStart() {
        context().actorSelection("/user/timeActor/")
                .tell(new TimeActor.RegisterMsg(), self());
    }

    private void sendTime(TimeMessage msg) {
        final ObjectNode response = Json.newObject();
        response.put("time", msg.timeStr);
        ws.tell(response, self());
    }

    public static Props props(final ActorRef wsout) {
        return Props.create(UserActor.class, wsout);
    }

    public static final class TimeMessage {
        public final String timeStr;
        public TimeMessage(String message) {
            this.timeStr = message;
        }
    }
    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(TimeMessage.class, this::sendTime)
                .build();
    }
}
