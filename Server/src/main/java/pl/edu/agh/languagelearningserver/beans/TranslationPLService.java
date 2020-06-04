package pl.edu.agh.languagelearningserver.beans;

import org.springframework.beans.factory.annotation.Autowired;
import pl.edu.agh.languagelearningserver.db.enities.EnglishWord;
import pl.edu.agh.languagelearningserver.db.enities.TranslationPL;
import pl.edu.agh.languagelearningserver.db.repositories.TranslationPLRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class TranslationPLService {

    @Autowired
    TranslationPLRepository repository;

    public Map<EnglishWord,String> checkQuizResult(Map<EnglishWord, String> result){
        return result.entrySet().stream().filter(e -> repository.existsByEnglishWordAndTranslationPL(e.getKey(),e.getValue()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }


    public Map<EnglishWord,List<String>> getAllTranslationsOfEnglishWords(List<EnglishWord> words){
        Map<EnglishWord, List<String>> result = new HashMap<>();
        for(EnglishWord word: words){
            if(repository.existsByEnglishWord(word)){
                result.put(word,
                        repository.findTranslationPLSByEnglishWord(word).stream().map(TranslationPL::getTranslationPL).collect(Collectors.toList()));
            }

        }
        return result;
    }


    public List<String> getAllTranslationsOfSingleWord(EnglishWord word){
        return repository.findTranslationPLSByEnglishWord(word).stream().map(TranslationPL::getTranslationPL).collect(Collectors.toList());
    }

    public void addTranslationPL(String translation, EnglishWord englishWord){
        TranslationPL translationPL = new TranslationPL();
        translationPL.setTranslationPL(translation);
        translationPL.setEnglishWord(englishWord);
        repository.save(translationPL);
    }
}
