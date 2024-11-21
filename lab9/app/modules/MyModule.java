package modules;

import actors.GreeterActor;
import com.google.inject.AbstractModule;
import play.libs.akka.AkkaGuiceSupport;
import services.GreeterAPI;
import services.GreeterAPIImpl;

public class MyModule extends AbstractModule implements AkkaGuiceSupport {
    @Override
    protected void configure() {
        bind(GreeterAPI.class).to(GreeterAPIImpl.class);
        bindActor(GreeterActor.class, "greeter-actor");
    }
}
