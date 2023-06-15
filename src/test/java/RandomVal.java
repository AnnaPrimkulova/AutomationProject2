import java.util.Random;

public class RandomVal {


    private int ranNum3;
    private String [] firstName;
    private String [] lastName;
    private String [] streets;
    private String [] cities;
    private String [] states;
    private  int zip5digits;

    private long cardRandomGen;
     private Random random;



    public RandomVal() {

        firstName = new String[] {"John", "Mary", "James", "Sarah", "Alex"};
        lastName = new String[]{"Smith", "Johnson", "Brown", "Davis", "Miller"};
        states = new String[]{"Main Street", "Maple Avenue", "Oak Street", "Elm Drive", "Cedar Lane"};
        cities = new String[] {"New York", "Los Angeles", "Chicago", "Houston", "Phoenix"};
        states = new String[] {"New York", "California", "Illinois", "Texas", "Arizona"};
        streets = new String[]{
                "Oak Street", "Maple Avenue", "Cedar Lane", "Elm Drive", "Pine Road",
                "Willow Lane", "Birch Street", "Hickory Avenue", "Juniper Court", "Sycamore Lane"
        };

        random = new Random();

    }

    public String randomFirstAndLastName(){

        String fN = firstName [random.nextInt(firstName.length)];
        String lN = lastName [random.nextInt(lastName.length)];

        return fN + " " + lN;
    }

    public String randomCity(){
        String city = cities [random.nextInt(cities.length)];

        return city;
    }

    public String state (){

        String state = states [random.nextInt(states.length)];
        return state;
    }

    public  String streetN(){

        String street = streets [random.nextInt(streets.length)];
        int randomNum3 = random.nextInt(900) + 100;
        return randomNum3 + ", "+ street;

    }


    public int zip(){
        // Generate a random zip code within a specific range
        int minZipCode = 10000; // Minimum zip code
        int maxZipCode = 99999; // Maximum zip code
        this.zip5digits = random.nextInt(maxZipCode - minZipCode + 1) + minZipCode;

        return this.zip5digits;

    }
    public int random3DigNum(){

        this.ranNum3 = random.nextInt(100) + 1;

         
        return ranNum3;
    }



}
