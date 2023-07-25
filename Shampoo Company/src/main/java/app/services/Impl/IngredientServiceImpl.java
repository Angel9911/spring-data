package app.services.Impl;

import app.entities.Ingredient;
import app.repositories.IngredientRepository;
import app.services.IngredientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
public class IngredientServiceImpl implements IngredientService {
    private final IngredientRepository ingredientRepository;

    @Autowired
    public IngredientServiceImpl(IngredientRepository ingredientRepository) {
        this.ingredientRepository = ingredientRepository;
    }

    @Override
    public List<Ingredient> selectIngredientsStartWith(String start) {
        return this.ingredientRepository.findByNameStartingWith(start);
    }

    @Override
    public List<Ingredient> selectIngredientsByNames(List<String> ingredientsNames) {
        return this.ingredientRepository.findByNameInOrderByPriceAsc(ingredientsNames);
    }

    @Override
    public int deleteByName(String name) {
        return this.ingredientRepository.deleteByName(name);
    }

    @Transactional
    @Override
    public int increasePriceByPercentage(double percent) {
        BigDecimal actualPercent = BigDecimal.valueOf(percent);
        return this.ingredientRepository.increasePriceByPercentage(actualPercent);
    }
}
