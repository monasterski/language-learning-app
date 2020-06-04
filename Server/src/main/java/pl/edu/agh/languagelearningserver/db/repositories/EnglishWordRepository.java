package pl.edu.agh.languagelearningserver.db.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.edu.agh.languagelearningserver.db.enities.EnglishWord;

@Repository
public interface EnglishWordRepository extends JpaRepository<EnglishWord, Long> {
    EnglishWord findEnglishWordByWord(String word);
    boolean existsByWord(String word);

}
