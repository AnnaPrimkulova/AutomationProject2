package june8;

import com.github.javafaker.Faker;

public class faker {
    public static void main(String[] args) {

        Faker faker = new Faker();

        String fN = faker.name().firstName();
        System.out.println(fN);

    }
}
