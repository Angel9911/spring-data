package app.services.Impl;

import app.entities.Shampoo;
import app.entities.Size;
import app.repositories.ShampooRepository;
import app.services.ShampooService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

@Service
public class ShampooServiceImpl implements ShampooService {
    private final ShampooRepository shampooRepository;

    @Autowired
    public ShampooServiceImpl(ShampooRepository shampooRepository) {
        this.shampooRepository = shampooRepository;
    }

    @Override
    public List<Shampoo> selectBySize(Size size) {
        return this.shampooRepository.findBySizeOrderByPriceAsc(size);
    }

    @Override
    public List<Shampoo> selectBySizeOrLabelTitle(Size size, String title) {
        return this.shampooRepository.findBySizeOrLabelTitleOrderByPriceAsc(size,title);
    }

    @Override
    public List<Shampoo> selectByExpensiveThanPrice(BigDecimal price) {
        return this.shampooRepository.findByPriceIsGreaterThanOrderByPriceDesc(price);
    }

    @Override
    public int selectCountPriceLowerThan(BigDecimal price) {
        return this.shampooRepository.countByPriceLessThan(price);
    }

    @Override
    public List<Shampoo> selectByIngredientsNames(Set<String> names) {
        return this.shampooRepository.findByIngredientsNames(names);
    }

    @Override
    public List<Shampoo> selectByIngredientCountLessThan(int count) {
        return this.shampooRepository.findByIngredientsCount(count);
    }
}
