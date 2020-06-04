package pl.edu.agh.languagelearningserver.db.enities.ids;



import lombok.Getter;
import lombok.Setter;
import pl.edu.agh.languagelearningserver.db.enities.EnglishWord;
import pl.edu.agh.languagelearningserver.db.enities.User;


import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
@Getter
@Setter
public class UserVocabularyId implements Serializable {

    private Long userId;
    private Long wordId;

    public UserVocabularyId(User user, EnglishWord englishWord) {
        this.userId = user.getUserId();
        this.wordId = englishWord.getWordId();
    }

    public UserVocabularyId() {
    }

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
