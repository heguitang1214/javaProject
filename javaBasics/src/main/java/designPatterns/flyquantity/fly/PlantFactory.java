package designPatterns.flyquantity.fly;

import java.util.HashMap;

public class PlantFactory {

    private HashMap<Integer, Plant> plantMap = new HashMap<Integer, Plant>();

    public PlantFactory() {

    }

    public Plant getPlant(int type) {

        if (!plantMap.containsKey(type)) {
            switch (type) {
                case 0:
                    plantMap.put(0, new Tree());
                    break;
                case 1:
                    plantMap.put(1, new Grass());
                    break;
            }
        }

        return plantMap.get(type);
    }
}
