package pl.edu.agh.languagelearningserver.controller.dao.enities.ids;



import lombok.Getter;
import lombok.Setter;


import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
@Getter
@Setter
public class UserVocabularyId implements Serializable {

    private Long userId;
    private Long wordId;

    @Override
    public int hashCode() {
        return Objects.hash(userId, wordId);
    }

    @Override
    public boolean equals(Object obj) {
        if(this == obj) return true;
        if(obj == null || getClass() != obj.getClass()) return false;
        UserVocabularyId that = (UserVocabularyId) obj;
        return Objects.equals(userId, that.userId) && Objects.equals(wordId, that.wordId);
    }
}
