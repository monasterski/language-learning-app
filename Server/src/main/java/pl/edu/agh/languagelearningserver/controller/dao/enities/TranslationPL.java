package pl.edu.agh.languagelearningserver.controller.dao.enities;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class TranslationPL {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long translationPLId;

    @ManyToOne
    private EnglishWord englishWord;

    private String translationPL;
}
