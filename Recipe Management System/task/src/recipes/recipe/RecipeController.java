package recipe;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import user.User;
import user.UserService;

import java.util.List;

@RestController
@RequestMapping("/api/recipe")
public class RecipeController {

    @Autowired
    private RecipeService recipeService;

    @Autowired
    private UserService userService;

    @PostMapping("/new")
    ResponseEntity<RecipeCreateResponce> createRecipe(@Valid @RequestBody Recipe request)
    {
        try{
            String email = SecurityContextHolder
                    .getContext()
                    .getAuthentication()
                    .getName();

            User author = userService.findByEmail(email);

            Recipe newRecipe = recipeService.saveRecipe(request, author);
            return new ResponseEntity<RecipeCreateResponce>(new RecipeCreateResponce(newRecipe.getId()), HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/{id}")
    ResponseEntity<Recipe> getRecipe(@PathVariable Long id)
    {
        try{
            Recipe recipe = recipeService.findRecipeById(id);
            return new ResponseEntity<>(recipe, HttpStatus.OK);
        } catch (Exception e){
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping({"/search", "/search/"})
    ResponseEntity<List<Recipe>> fetchRecipe(@RequestParam(required = false) String name, @RequestParam(required = false) String category)
    {
        try{
            if((name == null && category == null) || (name != null && category != null)){
                return ResponseEntity.badRequest().build();
            }

            List<Recipe> recipes;
            if(name != null){
                recipes = recipeService.fetchRecipeListByName(name);
            } else{
                recipes = recipeService.fetchRecipeListByCategory(category);
            }
            return new ResponseEntity<>(recipes, HttpStatus.OK);
        } catch (Exception e){
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{id}")
    ResponseEntity<Recipe> updateRecipe(@PathVariable Long id, @Valid @RequestBody Recipe request)
    {
        try{
            Recipe recipe = recipeService.findRecipeById(id);

            String email = SecurityContextHolder
                    .getContext()
                    .getAuthentication()
                    .getName();

            if (!recipe.getAuthor().getEmail().equals(email)) {
                return ResponseEntity.status(403).build();
            }

            User author = userService.findByEmail(email);

            recipeService.updateRecipe(request, id, author);
            return ResponseEntity.noContent().build();

        } catch (EntityNotFoundException e){
            return ResponseEntity.notFound().build();
        } catch (Exception e){
            return ResponseEntity.badRequest().build();
        }
    }


    @DeleteMapping("/{id}")
    ResponseEntity<Recipe> deleteRecipe(@PathVariable Long id)
    {
        try{
            Recipe recipe = recipeService.findRecipeById(id);

            String email = SecurityContextHolder
                    .getContext()
                    .getAuthentication()
                    .getName();

            if (!recipe.getAuthor().getEmail().equals(email)) {
                return ResponseEntity.status(403).build();
            }

            recipeService.deleteRecipeById(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e){
            return ResponseEntity.notFound().build();
        }
    }
}
