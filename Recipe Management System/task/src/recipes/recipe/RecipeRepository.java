package recipe;

import org.springframework.data.domain.Sort;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecipeRepository extends CrudRepository<Recipe, Long> {
    List<Recipe> findByNameContainingIgnoreCase(String name, Sort sort);

    List<Recipe> findByCategoryIgnoreCase(String category, Sort sort);
}
