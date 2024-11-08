package controllers;

import actors.GreeterActor;
import akka.actor.ActorRef;
import com.google.inject.name.Named;
import play.mvc.*;
import scala.jdk.javaapi.FutureConverters;
import services.GreeterAPI;
import services.GreeterAPIImpl;
import static akka.pattern.Patterns.ask;

import javax.inject.Inject;
import java.util.concurrent.CompletionStage;

/**
 * This controller contains an action to handle HTTP requests
 * to the application's home page.
 */
public class HomeController extends Controller {

    private final ActorRef greeterActor;

    @Inject
    public HomeController(@Named("greeter-actor") ActorRef greeterActor) {
        this.greeterActor = greeterActor;
    }

    /**
     * An action that renders an HTML page with a welcome message.
     * The configuration in the <code>routes</code> file means that
     * this method will be called when the application receives a
     * <code>GET</code> request with a path of <code>/</code>.
     */
    public CompletionStage<Result> index() {
        return FutureConverters.asJava(
                ask(greeterActor, new GreeterActor.SayHello("Play Framework"), 1000))
                .thenApply(response -> ok(response.toString()))
                .exceptionally(throwable -> internalServerError("Timed out!"));
    }

}
