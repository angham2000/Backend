package tn.esprit.immobilier.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Annonce implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idAnnonce;
    private String titre;
    private String description;
    private LocalDate dateCreation;
    private float prix;
    private String status;
    private String imageUrl;
    @Enumerated(EnumType.STRING)
    private CategoriesEnum categoriesEnum;

    @ManyToOne
    User user;
    @OneToMany(cascade = CascadeType.ALL, mappedBy="annonce")
    private Set<Commentaires> Commentairess;

}
