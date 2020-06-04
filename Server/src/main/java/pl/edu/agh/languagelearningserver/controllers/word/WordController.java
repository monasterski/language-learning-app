package pl.edu.agh.languagelearningserver.controllers.word;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.edu.agh.languagelearningserver.beans.EnglishWordService;
import pl.edu.agh.languagelearningserver.beans.TranslationPLService;
import pl.edu.agh.languagelearningserver.beans.UserVocabularyService;
import pl.edu.agh.languagelearningserver.beans.VocabularyGroupService;
import pl.edu.agh.languagelearningserver.controllers.authorization.ApplicationContext;
import pl.edu.agh.languagelearningserver.db.enities.*;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/word")
public class WordController {

    @Autowired
    VocabularyGroupService vocabularyGroupService;

    @Autowired
    UserVocabularyService userVocabularyService;

    @Autowired
    TranslationPLService translationPLService;

    @Autowired
    EnglishWordService englishWordService;

    @Autowired
    ApplicationContext applicationContext;

    @Transactional
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<String> addWord(@RequestBody String word){
        User user = applicationContext.getUser();
        if(englishWordService.isCreated(word)){
            EnglishWord englishWord = englishWordService.getByWord(word);
            if(userVocabularyService.existByUserAndEnglishWord(user,englishWord)){
                return new ResponseEntity<>(
                        "You add this word earlier",
                        HttpStatus.BAD_REQUEST);
            }

            userVocabularyService.addUserVocabulary(user, englishWord);

            ArrayList<String> translations = new ArrayList<>(translationPLService.getAllTranslationsOfSingleWord(englishWord));

            String responseTranslation = buildResponseFromTranslation(translations);

            return new ResponseEntity<>(
                    "Successfull added word. Word: " + word + " Translations: " + responseTranslation,
                    HttpStatus.OK);

        }

        EnglishWord newEnglishWord = new EnglishWord();
        newEnglishWord.setWord(word);
        englishWordService.addWord(newEnglishWord);
        EnglishWord englishWordFromDatabase = englishWordService.getByWord(word);
        userVocabularyService.addUserVocabulary(user, englishWordFromDatabase);

        //size < 3
        ArrayList<String> translations = translateWordUsingApi(word);

        for (String translation: translations) {
            translationPLService.addTranslationPL(translation,englishWordFromDatabase);
        }

        String responseTranslation = buildResponseFromTranslation(translations);

        return new ResponseEntity<>(
                "Successfull added word. Word: " + word + " Translations: " + responseTranslation,
                HttpStatus.OK);
    }


    @Transactional
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<Map<EnglishWord, List<String>>> getWordsByCategory(@RequestParam(defaultValue = "all") String category){
        User user = applicationContext.getUser();
        ArrayList<EnglishWord> words;
        if(category.equals("all")){
            words = userVocabularyService.getAllWordsByUser(user);
        }else if(category.equals("none")){
            words = userVocabularyService.getWordsByUserAndCategory(user, null);
        }else{
            VocabularyGroup vg = vocabularyGroupService.getCategoryByName(category,user);
            if(vg == null){
                return new ResponseEntity<>(
                        null,
                        HttpStatus.BAD_REQUEST);
            }
            words = userVocabularyService.getWordsByUserAndCategory(user, vg);
        }


        return new ResponseEntity<>(
                translationPLService.getAllTranslationsOfEnglishWords(words),
                HttpStatus.OK);
    }


    private ArrayList<String> translateWordUsingApi(String word) {
        ArrayList<String> translations =  new ArrayList<>();
        translations.add(word + "1");
        translations.add(word + "2");
        translations.add(word + "3");
        return translations;
    }

    private String buildResponseFromTranslation(List<String> translations){
        String prefix ="";
        StringBuilder responseTranslation = new StringBuilder("[ ");
        for(String translation: translations){
            responseTranslation.append(prefix);
            prefix=", ";
            responseTranslation.append(translation);
        }
        responseTranslation.append(" ]");
        return  responseTranslation.toString();
    }

}
