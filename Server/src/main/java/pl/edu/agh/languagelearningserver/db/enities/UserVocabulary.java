package pl.edu.agh.languagelearningserver.db.enities;


import lombok.Getter;
import lombok.Setter;
import pl.edu.agh.languagelearningserver.db.enities.ids.UserVocabularyId;

import javax.persistence.*;


@Entity
@Getter
@Setter
public class UserVocabulary {

    @EmbeddedId
    private UserVocabularyId userVocabularyId;

    @ManyToOne
    @MapsId("userId")
    @JoinColumn
    private User user;

    @ManyToOne
    @MapsId("wordId")
    @JoinColumn
    private EnglishWord englishWord;

    @ManyToOne
    private VocabularyGroup vocabularyGroup;

    private int repetition;
}
