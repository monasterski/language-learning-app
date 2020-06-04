package pl.edu.agh.languagelearningserver.beans;

import org.springframework.beans.factory.annotation.Autowired;
import pl.edu.agh.languagelearningserver.db.enities.EnglishWord;
import pl.edu.agh.languagelearningserver.db.enities.User;
import pl.edu.agh.languagelearningserver.db.enities.UserVocabulary;
import pl.edu.agh.languagelearningserver.db.enities.VocabularyGroup;
import pl.edu.agh.languagelearningserver.db.repositories.EnglishWordRepository;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

public class EnglishWordService {

    @Autowired
    EnglishWordRepository englishWordRepository;

    public boolean isCreated(String word){
        return englishWordRepository.existsByWord(word);
    }

    public EnglishWord getByWord(String word){
        return englishWordRepository.findEnglishWordByWord(word);
    }

    public void addWord(EnglishWord englishWord){
        englishWordRepository.save(englishWord);
    }


}
