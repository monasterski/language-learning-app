package pl.edu.agh.languagelearningserver.controllers.category;

import org.springframework.beans.factory.annotation.Autowired;


import org.springframework.data.util.Pair;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.edu.agh.languagelearningserver.beans.EnglishWordService;
import pl.edu.agh.languagelearningserver.beans.UserVocabularyService;
import pl.edu.agh.languagelearningserver.beans.VocabularyGroupService;
import pl.edu.agh.languagelearningserver.controllers.authorization.ApplicationContext;
import pl.edu.agh.languagelearningserver.db.enities.EnglishWord;
import pl.edu.agh.languagelearningserver.db.enities.User;
import pl.edu.agh.languagelearningserver.db.enities.VocabularyGroup;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("category")
public class CategoryController {

    @Autowired
    EnglishWordService englishWordService;

    @Autowired
    ApplicationContext applicationContext;

    @Autowired
    VocabularyGroupService vocabularyGroupService;

    @Autowired
    UserVocabularyService userVocabularyService;

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<String> addCategory(@RequestBody String categoryName){
        User user = applicationContext.getUser();
        if(vocabularyGroupService.isCreated(categoryName,user)){
            return new ResponseEntity<>(
                    "This Category exist",
                    HttpStatus.BAD_REQUEST);
        }

        vocabularyGroupService.addCategory(categoryName, user);
        return new ResponseEntity<String>(
                "Successfull added Category.",
                HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<String>> getCategories(){
        User user = applicationContext.getUser();

        return new ResponseEntity<>(
                vocabularyGroupService.getAllVocabularyGroups(user).stream().map(VocabularyGroup::getName).collect(Collectors.toList()),
                HttpStatus.OK);
    }

    @RequestMapping(value = "/word", method = RequestMethod.POST)
    @Transactional
    public ResponseEntity<String> addWordsToCategory(@RequestBody Map<String, List<String>> wordsToAdd){
        if(wordsToAdd.size() != 1){
            return new ResponseEntity<>(
                    "Too many keys on map",
                    HttpStatus.BAD_REQUEST);
        }
        // map size = 1
        User user = applicationContext.getUser();
        String categoryName ="";
        for (String k: wordsToAdd.keySet()) {
            categoryName = k;
        }
        ArrayList<EnglishWord> englishWords = wordsToAdd.get(categoryName).stream().filter(x ->englishWordService.isCreated(x))
                .map(x-> englishWordService.getByWord(x)).collect(Collectors.toCollection(ArrayList::new));
        VocabularyGroup vg = vocabularyGroupService.getCategoryByName(categoryName, user);
        userVocabularyService.updateCategoryToAllWords(vg, englishWords, user);

        return new ResponseEntity<>(
                "Successfull added words to category.",
                HttpStatus.OK);
    }
}
