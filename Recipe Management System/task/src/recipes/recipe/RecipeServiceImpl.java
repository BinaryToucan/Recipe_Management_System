package recipe;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import user.User;

import java.util.List;

@Service
public class RecipeServiceImpl implements RecipeService {

    @Autowired
    private RecipeRepository recipeRepository;

    @Override
    public Recipe saveRecipe(Recipe recipe, User author) {
        recipe.setAuthor(author);
        return recipeRepository.save(recipe);
    }

    @Override
    public Recipe findRecipeById(Long id) {
        return recipeRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Recipe not found with id: " + id));
    }

    @Override
    public List<Recipe> fetchRecipeListByCategory(String category) {

        return recipeRepository.findByCategoryIgnoreCase(
                category,
                Sort.by(Sort.Direction.DESC, "date")
        );
    }

    @Override
    public List<Recipe> fetchRecipeListByName(String name) {
        return recipeRepository.findByNameContainingIgnoreCase(
                name,
                Sort.by(Sort.Direction.DESC, "date")
        );
    }

    @Override
    public void updateRecipe(Recipe recipe, Long id, User author) {
        recipe.setId(id);
        recipe.setAuthor(author);
        recipeRepository.save(recipe);
    }

    @Override
    public void deleteRecipeById(Long id) {
        recipeRepository.deleteById(id);
    }
}
