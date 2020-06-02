package pl.edu.agh.languagelearningserver.db.enities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class VocabularyGroup {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long vocabularyGroupId;

    @ManyToOne
    private User user;

    private String name;
}
