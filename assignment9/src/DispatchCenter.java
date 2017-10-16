import java.util.*;

public class DispatchCenter {
    public static String[] AREA_NAMES = {"North", "South", "East", "West", "Downtown", "Airport"};

    public HashMap<Integer, Taxi> taxis;
    public HashMap<String, ArrayList<Taxi>> areas;

    private int[][] stats; // You'll need this for the last part of the assignment


    // Constructor
    public DispatchCenter() {
        //key is the plate number of the taxi, value is the taxi object
        taxis = new HashMap<Integer, Taxi>();
        areas = new HashMap<String, ArrayList<Taxi>>();

        Integer randomTaxiNum, randomAreaNum;

        for (int i = 0; i < 50; i++) {
            randomTaxiNum = randomRange(100, 999);
            randomAreaNum = randomRange(0, AREA_NAMES.length - 1);
            addTaxi(new Taxi(randomTaxiNum), AREA_NAMES[randomAreaNum]);
        }


        // You'll need this for the last part of the assignment
        stats = new int[AREA_NAMES.length][AREA_NAMES.length];
    }

    // Determine the travel times from one area to another
    public static int computeTravelTimeFrom(String pickup, String dropOff) {
        int[][] TimeArray = new int[][]{
                {10, 40, 20, 20, 20, 40},
                {40, 10, 20, 20, 20, 40},
                {20, 20, 10, 40, 20, 20},
                {20, 20, 40, 10, 20, 60},
                {20, 20, 20, 20, 10, 40},
                {40, 40, 20, 60, 40, 10}
        };
        int pickupCol = 0;
        int dropoffRow = 0;
        for (int x = 0; x < 6; x++) {
            if (AREA_NAMES[x].equals(pickup) && AREA_NAMES[x].equals(dropOff)) {
                pickupCol = x;
                dropoffRow = x;
                break;
            } else if ((AREA_NAMES[x].equals(dropOff))) {
                dropoffRow = x;
            } else if (AREA_NAMES[x].equals(pickup)) {
                pickupCol = x;
            }
        }

        return TimeArray[dropoffRow][pickupCol];
    }

    //returns a random integer between min and mix
    public static int randomRange(int min, int max) {
        int range = (max - min) + 1;
        return (int) (Math.random() * range) + min;
    }

    // You'll need this for the last part of the assignment
    public int[][] getStats() {
        return stats;
    }

    // Update the statistics for a taxi going from the pickup location to the dropoff location
    public void updateStats(String pickup, String dropOff) {

        int pickupCol = 0;
        int dropoffRow = 0;
        for (int x = 0; x < 6; x++) {
            if (AREA_NAMES[x].equals(pickup) && AREA_NAMES[x].equals(dropOff)) {
                pickupCol = x;
                dropoffRow = x;
                break;
            } else if ((AREA_NAMES[x].equals(dropOff))) {
                dropoffRow = x;
            } else if (AREA_NAMES[x].equals(pickup)) {
                pickupCol = x;
            }
        }

        getStats()[dropoffRow][pickupCol]++;

    }

    // Add a taxi to the hashmaps
    public void addTaxi(Taxi aTaxi, String area) {
        taxis.put(aTaxi.getPlateNumber(), aTaxi);

        if (!(areas.containsKey(area))) {
            areas.put(area, new ArrayList<>());
        }


        areas.get(area).add(aTaxi);


    }

    // Return a list of all available taxis within a certain area
    public ArrayList<Taxi> availableTaxisInArea(String s) {  ///CHANGED TO PUBLIC REVERT BACK TO PRIVATE
        ArrayList<Taxi> result = new ArrayList<Taxi>();

        for (Taxi t : areas.get(s))
            if (t.getAvailable())
                result.add(t);
        return result;
    }

    // Return a list of all busy taxis
    public ArrayList<Taxi> getBusyTaxis() {
        ArrayList<Taxi> result = new ArrayList<Taxi>();

        for (Taxi t : taxis.values()) {
            if (!(t.getAvailable())) {
                result.add(t);
            }
        }
        return result;
    }

    //Getter/Setter Methods
    public HashMap<String, ArrayList<Taxi>> getAreas() {
        return areas;
    }


    // Find a taxi to satisfy the given request
    public Taxi sendTaxiForRequest(ClientRequest request) {

        //Declare a Taxi Pointer,and Arraylist size of Dropoff area.
        Taxi availibleTaxi;

        //Check to see if there is a taxi in the pickup area
        if (availableTaxisInArea(request.getPickupLocation()).size() != 0) {
            availibleTaxi = availableTaxisInArea(request.getPickupLocation()).get(0);

            //Remove the taxi from pickuplocation, add it to the Dropofflocation, and set Availible taxi to false;

            areas.get(request.getPickupLocation()).remove(availibleTaxi);

            areas.get(request.getDropoffLocation()).add(areas.get(request.getDropoffLocation()).size(), availibleTaxi);
            availibleTaxi.setAvailable(false);

            availibleTaxi.setDestination(request.getDropoffLocation());
            availibleTaxi.setEstimatedTimeToDest(computeTravelTimeFrom(request.getPickupLocation(), request.getDropoffLocation()));

            //Update the stats for the pickup location and drop off location
            updateStats(request.getPickupLocation(), request.getDropoffLocation());
            return availibleTaxi;
        } else {
            //Iterate through all the areas in search of an availible taxi
            for (int i = 0; i < AREA_NAMES.length; i++) {
                if (availableTaxisInArea(AREA_NAMES[i]).size() != 0) {

                    // IF THE TOP MOST TAXI IS AVailible REMEmBER THAT
                    availibleTaxi = availableTaxisInArea(AREA_NAMES[i]).get(0);


                    areas.get(AREA_NAMES[i]).remove(availibleTaxi);
                    areas.get(request.getDropoffLocation()).add(areas.get(request.getDropoffLocation()).size(), availibleTaxi);
                    availibleTaxi.setAvailable(false);

                    //Travel time is Distance to drive to pick uplocation + distance fr
                    availibleTaxi.setDestination(request.getDropoffLocation());

                    //(Distance from external taxi initial area to Client's pickuplocation) + (Distance from pickup location to CLient Location);
                    availibleTaxi.setEstimatedTimeToDest((computeTravelTimeFrom(AREA_NAMES[i], request.getPickupLocation())) + computeTravelTimeFrom(request.getPickupLocation(), request.getDropoffLocation()));

                    //Update the stats
                    updateStats(request.getPickupLocation(), request.getDropoffLocation());
                    return availibleTaxi; ///THIS COULD NOT WORK IT REALLY DEPENDS ON IF THE TOP
                }
            }

        }


        return null;
    }


}