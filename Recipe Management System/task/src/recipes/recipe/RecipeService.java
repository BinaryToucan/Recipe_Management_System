package recipe;

import user.User;

import java.util.List;

public interface RecipeService {

    // Save operation
    Recipe saveRecipe(Recipe recipe,  User author);

    Recipe findRecipeById(Long id);

    List<Recipe> fetchRecipeListByCategory(String category);

    List<Recipe> fetchRecipeListByName(String name);

    // Update operation
    void updateRecipe(Recipe recipe, Long id, User author);

    // Delete operation
    void deleteRecipeById(Long id);
}
