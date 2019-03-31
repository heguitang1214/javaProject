package designPatterns.flyquantity.fly;

/**
 * 外部对象管理器
 */
public class PlantManager {

    private int length = 10000000;
    private int[] xArray = new int[length], yArray = new int[length],
            AgeArray = new int[length], typeArray = new int[length];

    private PlantFactory mPlantFactory;

    public PlantManager() {

        mPlantFactory = new PlantFactory();
        for (int i = 0; i < length; i++) {

            xArray[i] = (int) (Math.random() * length);
            yArray[i] = (int) (Math.random() * length);
            AgeArray[i] = (int) (Math.random() * length) % 5;
            typeArray[i] = (int) (Math.random() * length) % 2;
        }
    }

    public void displayTrees() {
        for (int i = 0; i < length; i++) {
            mPlantFactory.getPlant(typeArray[i]).display(xArray[i], yArray[i], AgeArray[i]);
        }
    }
}
