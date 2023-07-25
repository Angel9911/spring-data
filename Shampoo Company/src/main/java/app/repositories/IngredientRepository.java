package app.repositories;

import app.entities.Ingredient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface IngredientRepository extends JpaRepository<Ingredient,Long> {

    List<Ingredient> findByNameStartingWith(String name);
    List<Ingredient> findByNameInOrderByPriceAsc(List<String> name);
    int deleteByName(String name);

    @Modifying// when use update query, in advance we must set this annotation to know spring that we change records.
    @Query("UPDATE ingredients i set i.price = i.price + i.price * :percentageIncrease")
    int increasePriceByPercentage(@Param("percentageIncrease") BigDecimal percent);
}
