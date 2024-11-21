package controllers;

import actors.UserActor;
import akka.actor.ActorSystem;
import akka.stream.Materializer;
import com.google.inject.Inject;

import play.libs.streams.ActorFlow;
import play.mvc.*;

import actors.TimeActor;

/**
 * This controller contains an action to handle HTTP requests
 * to the application's home page.
 */
public class HomeController extends Controller {

    private final ActorSystem actorSystem;
    private final Materializer materializer;
    @Inject
    public HomeController(ActorSystem system, Materializer materializer) {
        system.actorOf(TimeActor.getProps(), "timeActor");
        this.actorSystem = system;
        this.materializer = materializer;

    }


    /**
     * An action that renders an HTML page with a welcome message.
     * The configuration in the <code>routes</code> file means that
     * this method will be called when the application receives a
     * <code>GET</code> request with a path of <code>/</code>.
     */
    public Result index() {
        return ok(views.html.index.render());
    }
    public WebSocket ws() {
        return WebSocket.Json.accept(
                request -> ActorFlow.actorRef(UserActor::props, this.actorSystem, this.materializer));
    }

}
