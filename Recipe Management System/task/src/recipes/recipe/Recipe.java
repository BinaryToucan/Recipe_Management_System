package recipe;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UpdateTimestamp;
import user.User;

import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "recipes")
public class Recipe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private Long id;

    @NotBlank
    private String name;

    @NotBlank
    private String category;

    @UpdateTimestamp
    private LocalDateTime date;

    @NotBlank
    private String description;

    @Size(min = 1)
    @NotNull
    private String[] ingredients;

    @Size(min = 1)
    @NotNull
    private String[] directions;

    @ManyToOne
    @JoinColumn(name = "author_id")
    @JsonIgnore
    private User author;
}
