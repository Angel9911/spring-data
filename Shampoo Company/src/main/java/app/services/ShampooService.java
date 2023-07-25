package app.services;

import app.entities.Shampoo;
import app.entities.Size;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

public interface ShampooService {

    List<Shampoo> selectBySize(Size size);
    List<Shampoo> selectBySizeOrLabelTitle(Size size, String title);
    List<Shampoo> selectByExpensiveThanPrice(BigDecimal price);
    int  selectCountPriceLowerThan(BigDecimal price);
    List<Shampoo> selectByIngredientsNames(Set<String> names);
    List<Shampoo> selectByIngredientCountLessThan(int count);
}
