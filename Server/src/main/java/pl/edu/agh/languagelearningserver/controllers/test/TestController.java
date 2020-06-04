package pl.edu.agh.languagelearningserver.controllers.test;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import pl.edu.agh.languagelearningserver.beans.*;
import pl.edu.agh.languagelearningserver.db.enities.EnglishWord;
import pl.edu.agh.languagelearningserver.db.enities.User;
import pl.edu.agh.languagelearningserver.db.enities.VocabularyGroup;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/test")
public class TestController {

    @Autowired
    AuthorizationService authorizationService;

    @Autowired
    EnglishWordService englishWordService;

    @Autowired
    UserVocabularyService userVocabularyService;

    @Autowired
    TranslationPLService translationPLService;

    @Autowired
    VocabularyGroupService vocabularyGroupService;



    @Transactional
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<String> createTestData(){

        User user1 = new User("login1", "password1");
        User user2 = new User("login2", "password2");

        authorizationService.registerUser(user1);
        authorizationService.registerUser(user2);

        ArrayList<String> words = new ArrayList<>();
        words.add("word1");
        words.add("word2");
        words.add("word3");
        words.add("word4");
        words.add("word5");
        words.add("word6");
        words.add("word7");
        words.add("word8");
        words.add("word9");
        words.add("word10");

        words.forEach(x -> {
            EnglishWord ew = new EnglishWord();
            ew.setWord(x);
            englishWordService.addWord(ew);
        });

        User userDB1 = authorizationService.getUser("login1");
        User userDB2 = authorizationService.getUser("login2");


        String categoryName = "Sport";
        String categoryName2 = "Rozrywka";

        vocabularyGroupService.addCategory(categoryName, user1);
        vocabularyGroupService.addCategory(categoryName2, user2);
        vocabularyGroupService.addCategory(categoryName, user2);

        VocabularyGroup vg1 = vocabularyGroupService.getCategoryByName(categoryName, user1);
        VocabularyGroup vg2 = vocabularyGroupService.getCategoryByName(categoryName2, user2);

        int counter = 0;
        ArrayList<EnglishWord> toFirstCategory = new ArrayList<>();
        ArrayList<EnglishWord> toSecondCategory = new ArrayList<>();
        for(String word : words) {
            EnglishWord ew = englishWordService.getByWord(word);
            userVocabularyService.addUserVocabulary(userDB1,ew);
            userVocabularyService.addUserVocabulary(userDB2,ew);
            translationPLService.addTranslationPL("t1: " + word, ew);
            translationPLService.addTranslationPL("t2: " + word, ew);
            translationPLService.addTranslationPL("t3: " + word, ew);
            if(counter<5){
                toFirstCategory.add(ew);
            }else{
                toSecondCategory.add(ew);
            }
            counter++;
        }

        userVocabularyService.updateCategoryToAllWords(vg1, toFirstCategory,user1);
        userVocabularyService.updateCategoryToAllWords(vg2, toSecondCategory,user2);




        return new ResponseEntity<>(
                "DONE",
                HttpStatus.OK);

    }
}
