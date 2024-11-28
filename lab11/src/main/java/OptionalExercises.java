import java.util.Optional;
import java.util.Properties;

public class OptionalExercises {

    // Q2.1: Combining two Optionals
    public static Optional<Insurance> nullSafeFindCheapestInsurance(Optional<Person> person, Optional<Car> car) {
        return person.flatMap(p -> car.map(c -> findCheapestInsurance(p, c)));
    }

    private static Insurance findCheapestInsurance(Person person, Car car) {
        // Mock implementation of finding the cheapest insurance
        return new Insurance("Cheapest Insurance");
    }

    // Q2.2: Filtering an Optional
    public static String getCarInsuranceName(Optional<Person> person, int minAge) {
        return person.filter(p -> p.getAge() >= minAge)
                .flatMap(Person::getCar)
                .flatMap(Car::getInsurance)
                .map(Insurance::getName)
                .orElse("Unknown");
    }

    // Q2.3: Reading a "duration" from a property using an Optional
    public static int readDuration(Properties props, String name) {
        return Optional.ofNullable(props.getProperty(name))
                .flatMap(OptionalExercises::stringToInt)
                .filter(i -> i > 0)
                .orElse(0);
    }

    public static Optional<Integer> stringToInt(String s) {
        try {
            return Optional.of(Integer.parseInt(s));
        } catch (NumberFormatException e) {
            return Optional.empty();
        }
    }

    // Main method to demonstrate the exercises
    public static void main(String[] args) {
        // Q2.1: Combining two Optionals
        Optional<Person> person = Optional.of(new Person("John", 30));
        Optional<Car> car = Optional.of(new Car("Tesla", new Insurance("Tesla Insurance")));
        Optional<Insurance> insurance = nullSafeFindCheapestInsurance(person, car);
        System.out.println("Cheapest insurance: " + insurance.map(Insurance::getName).orElse("None"));

        // Q2.2: Filtering an Optional
        String insuranceName = getCarInsuranceName(person, 25);
        System.out.println("Car insurance name (age >= 25): " + insuranceName);

        // Q2.3: Reading a "duration" from a property
        Properties props = new Properties();
        props.setProperty("a", "5");
        props.setProperty("b", "true");
        props.setProperty("c", "-3");
        System.out.println("Duration for 'a': " + readDuration(props, "a")); // 5
        System.out.println("Duration for 'b': " + readDuration(props, "b")); // 0
        System.out.println("Duration for 'c': " + readDuration(props, "c")); // 0
        System.out.println("Duration for 'd': " + readDuration(props, "d")); // 0
    }
}

// Mock classes for Person, Car, and Insurance
class Person {
    private String name;
    private int age;
    private Car car;

    public Person(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public int getAge() {
        return age;
    }

    public Optional<Car> getCar() {
        return Optional.ofNullable(car);
    }
}

class Car {
    private String model;
    private Insurance insurance;

    public Car(String model, Insurance insurance) {
        this.model = model;
        this.insurance = insurance;
    }

    public Optional<Insurance> getInsurance() {
        return Optional.ofNullable(insurance);
    }
}

class Insurance {
    private String name;

    public Insurance(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}