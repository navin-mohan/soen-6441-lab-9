package services;

public class GreeterAPIImpl implements GreeterAPI {
    @Override
    public String sayHello(String name) {
        return "Hello " + name + "!";
    }
}
