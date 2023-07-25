package app.repositories;

import app.entities.Ingredient;
import app.entities.Shampoo;
import app.entities.Size;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

@Repository
public interface ShampooRepository extends JpaRepository<Shampoo, Long> {

    List<Shampoo> findBySizeOrderByPriceAsc(Size size);
    List<Shampoo> findBySizeOrLabelTitleOrderByPriceAsc(Size size,String title);
    List<Shampoo> findByPriceIsGreaterThanOrderByPriceDesc(BigDecimal price);
    int countByPriceLessThan(BigDecimal price);
    // its a jpql query. You must not use join ON, because then we move to sql query.
    @Query("SELECT s FROM shampoos s JOIN s.ingredients as i WHERE i.name IN :ingredients")
    List<Shampoo> findByIngredientsNames(@Param("ingredients")Set<String> ingredients);

    @Query("select s from shampoos s where s.ingredients.size < :ingredientsCount")
    List<Shampoo> findByIngredientsCount(@Param("ingredientsCount")int ingredientsCount);

    List<Shampoo> findBySizeOrderById(Size size);
    List<Shampoo> findByBrand(String brand);
    List<Shampoo> findByBrandAndSize(String brand, Size size);

}
