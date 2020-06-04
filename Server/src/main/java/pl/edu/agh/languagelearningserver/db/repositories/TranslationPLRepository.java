package pl.edu.agh.languagelearningserver.db.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.edu.agh.languagelearningserver.db.enities.EnglishWord;
import pl.edu.agh.languagelearningserver.db.enities.TranslationPL;

import java.util.List;
import java.util.Map;

@Repository
public interface TranslationPLRepository extends JpaRepository<TranslationPL, Long> {
    boolean existsByEnglishWordAndTranslationPL(EnglishWord englishWord, String translationPL);
    List<TranslationPL> findTranslationPLSByEnglishWord(EnglishWord word);
    boolean existsByEnglishWord(EnglishWord englishWord);
}
