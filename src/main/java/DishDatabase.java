import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DishDatabase {
    private static Map<Integer, List<String>> all_soups = new HashMap<>();
    private static Map<Integer, List<String>> all_mainDishes = new HashMap<>();
    private static List<String> soupName = new ArrayList<>();
    private static List<String> soupPath = new ArrayList<>();
    private static List<String> mainDishName = new ArrayList<>();
    private static List<String> mainDishPath = new ArrayList<>();

    private static List<String> soupList0 = new ArrayList<>();
    private static List<String> soupList1 = new ArrayList<>();
    private static List<String> soupList2 = new ArrayList<>();
    private static List<String> soupList3 = new ArrayList<>();
    private static List<String> mainDishList0 = new ArrayList<>();
    private static List<String> mainDishList1 = new ArrayList<>();
    private static List<String> mainDishList2 = new ArrayList<>();


    public static void createAllSoups(){
        soupName.add(0,"Pomidorowa"); soupPath.add(0,"images/soups/pomidorowa.jpg");
        soupName.add(1,"Barszcz"); soupPath.add(1,"images/soups/barszcz.jpg");
        soupName.add(2,"Pieczarkowa"); soupPath.add(2,"images/soups/pieczarkowa.jpg");
        soupName.add(3,"Koperkowa"); soupPath.add(2,"images/soups/koperkowa.jpg");
        addToDishDatabase(0, soupList0, soupName, soupPath, all_soups);
        addToDishDatabase(1, soupList1, soupName, soupPath, all_soups);
        addToDishDatabase(2, soupList2, soupName, soupPath, all_soups);
        addToDishDatabase(3, soupList3, soupName, soupPath, all_soups);
    }

    public static void addToDishDatabase(int dishNumber, List<String> listNumber, List<String> dishName, List<String> dishPath, Map<Integer, List<String>> allSoupOrMainDish){
        listNumber.add(dishName.get(dishNumber));
        listNumber.add(dishPath.get(dishNumber));
        allSoupOrMainDish.put(dishNumber,listNumber);
    }

    public static Map<Integer, List<String>> getAll_soups() {
        createAllSoups();
        if (soupName.size() == all_soups.size() && soupPath.size() == all_soups.size()){
            return all_soups;
        }
        else {
            System.out.println("Incorrectly entered data");
            return null;
        }
    }

    public static void createAllMainDishes(){
        mainDishName.add(0,"Spaghetti"); mainDishPath.add(0,"images/main_dishes/spaghetti.jpg");
        mainDishName.add(1,"Ryba"); mainDishPath.add(1,"images/main_dishes/ryba.jpg");
        mainDishName.add(2,"Schabowy"); mainDishPath.add(2,"images/main_dishes/schabowy.jpg");
        addToDishDatabase(0, mainDishList0, mainDishName, mainDishPath, all_mainDishes);
        addToDishDatabase(1, mainDishList1, mainDishName, mainDishPath, all_mainDishes);
        addToDishDatabase(2, mainDishList2, mainDishName, mainDishPath, all_mainDishes);
    }

    public static Map<Integer, List<String>> getAll_mainDishes() {
        createAllMainDishes();
        if (mainDishName.size() == all_mainDishes.size() && mainDishPath.size() == all_mainDishes.size()){
            return all_mainDishes;
        }
        else {
            System.out.println("Incorrectly entered data");
            return null;
        }
    }
}
