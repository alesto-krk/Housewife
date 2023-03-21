import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class DishDatabaseTest {

    //index is not missed (testowanie wyjatkow?)
    //dish path exists

    @Test
    void soupDatabaseShouldntBeEmpty(){
        Map<Integer, List<String>> soupMap;
        soupMap = DishDatabase.getAll_soups();
        System.out.println(soupMap.size());
        assertNotNull(soupMap);
        assertFalse(soupMap.isEmpty());
        assertNotEquals(0, soupMap.size());
    }

    @DisplayName("Main dish database is not empty")
    @Test
    void mainDishDatabaseShouldntBeEmpty(){
        Map<Integer, List<String>> mainDishMap;
        mainDishMap = DishDatabase.getAll_mainDishes();
        System.out.println(mainDishMap.size());
        System.out.println(DishDatabase.class.isAnonymousClass());

        assertNotEquals(0, mainDishMap.size());
    }


    @Test
    void directoryForSoupImagesShouldBeCorrect(){
        DishDatabase.createAllSoups();
        String correctDirectoryForSoup = "images/soups/";
        for(int i=0; i<DishDatabase.getSoupPath().size(); i++){
            String enteredDirectory = DishDatabase.getSoupPath().get(i).substring(0,13);
            //System.out.println(enteredDirectory);
            assertEquals(correctDirectoryForSoup, enteredDirectory);
        }


    }
}

