package app.runners;

import app.entities.Size;
import app.services.IngredientService;
import app.services.ShampooService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;

@Controller
public class ConsoleRunner implements CommandLineRunner {
    private final ShampooService shampooService;
    private final IngredientService ingredientService;

    @Autowired
    public ConsoleRunner(ShampooService shampooService, IngredientService ingredientService) {
        this.shampooService = shampooService;
        this.ingredientService = ingredientService;
    }
    @Transactional
    @Override
    public void run(String... args) throws Exception {

        Scanner scanner = new Scanner(System.in);

        System.out.println("SELECT SHAMPOOS BY SIZE: ");
        this.shampooService.selectBySize(Size.MEDIUM)
                .forEach(System.out::println);

        System.out.println("SELECT SHAMPOOS  BY SIZE OR LABEL TITLE: ");
        this.shampooService.selectBySizeOrLabelTitle(Size.SMALL,"Repair & Nutrition")
                .forEach(System.out::println);

        System.out.println("SELECT SHAMPOOS BY PRICE MORE EXPENSIVE THAN CERTAIN PRICE: ");
        this.shampooService.selectByExpensiveThanPrice(BigDecimal.valueOf(5))
                .forEach(System.out::println);

        System.out.println("SELECT INGREDIENTS BY NAME STARTS WITH: ");
        this.ingredientService.selectIngredientsStartWith("M")
                .forEach(System.out::println);

        System.out.println("SELECT INGREDIENTS WHICH ARE CONTAINED CERTAIN NAMES: ");

        List<String> ingredientsNames = new ArrayList<>();
        ingredientsNames.add("Lavender");
        ingredientsNames.add("Herbs");
        ingredientsNames.add("Apple");

        this.ingredientService.selectIngredientsByNames(ingredientsNames)
                .forEach(System.out::println);

        System.out.println("SELECT COUNT BY PRICE LESS THAN: ");
        int count = this.shampooService.selectCountPriceLowerThan(BigDecimal.valueOf(8.5));
        System.out.println(count);

        System.out.println("SELECT SHAMPOOS BY SET OF INGREDIENTS: ");
        Set<String> ingredients = new HashSet<>();
        ingredients.add("Berry");
        ingredients.add("Mineral-Collagen");

        this.shampooService.selectByIngredientsNames(ingredients)
                .forEach(System.out::println);

        System.out.println("SELECT SHAMPOOS BY INGREDIENTS COUNT: ");
        this.shampooService.selectByIngredientCountLessThan(2)
                .forEach(System.out::println);

        System.out.println("DELETE INGREDIENT BY NAME");
       // int deleteRecords = this.ingredientService.deleteByName("netelle");
       // System.out.println(deleteRecords);

        System.out.println("UPDATE PRICE WITH PERCENTAGE");
        int updateRecords = this.ingredientService.increasePriceByPercentage(0.1);
        System.out.println("Updated records: " + updateRecords);
    }
    public void demo(){
        /*Size sizeName = Size.valueOf(scanner.nextLine());
        System.out.println(sizeName);
        shampooRepository.findBySizeOrderById(sizeName)
                .forEach(System.out::println);


        shampooRepository.findByBrandAndSize("Keratin Strong", Size.SMALL)
                .forEach(shampoo -> System.out.println("Filter by brand and size: " + shampoo.getId()));

        shampooRepository.findByBrand("Keratin Strong")
                .forEach(shampoo -> System.out.println("Filter by brand " + shampoo.getId())); */
    }

}
