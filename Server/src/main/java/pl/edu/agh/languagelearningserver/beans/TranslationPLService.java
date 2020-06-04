package pl.edu.agh.languagelearningserver.beans;

import org.springframework.beans.factory.annotation.Autowired;
import pl.edu.agh.languagelearningserver.db.enities.EnglishWord;
import pl.edu.agh.languagelearningserver.db.enities.TranslationPL;
import pl.edu.agh.languagelearningserver.db.enities.User;
import pl.edu.agh.languagelearningserver.db.repositories.EnglishWordRepository;
import pl.edu.agh.languagelearningserver.db.repositories.TranslationPLRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class TranslationPLService {

    @Autowired
    EnglishWordRepository englishWordRepository;

    @Autowired
    TranslationPLRepository repository;

    public Map<EnglishWord,String> checkQuizResult(Map<String, String> result, User user){

        Map<EnglishWord, String> convertMap = createConvertedMap(result, user);

        return convertMap.entrySet().stream().filter(e -> repository.existsByEnglishWordAndTranslationPL(e.getKey(),e.getValue()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }


    public Map<String,List<String>> getAllTranslationsOfEnglishWords(List<EnglishWord> words){
        Map<String, List<String>> result = new HashMap<>();
        for(EnglishWord word: words){
            if(repository.existsByEnglishWord(word)){
                result.put(word.getWord(),
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


    private Map<EnglishWord, String> createConvertedMap(Map<String, String> result, User user){
        return result.entrySet().stream().filter( x -> englishWordRepository.existsByWord(x.getKey())).collect(Collectors.toMap(x -> englishWordRepository.findEnglishWordByWord(x.getKey()), Map.Entry::getValue));
    }
}
