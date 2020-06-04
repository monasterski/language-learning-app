package pl.edu.agh.languagelearningserver.controllers.category;

import org.springframework.beans.factory.annotation.Autowired;


import org.springframework.data.util.Pair;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.edu.agh.languagelearningserver.beans.UserVocabularyService;
import pl.edu.agh.languagelearningserver.beans.VocabularyGroupService;
import pl.edu.agh.languagelearningserver.controllers.authorization.ApplicationContext;
import pl.edu.agh.languagelearningserver.db.enities.EnglishWord;
import pl.edu.agh.languagelearningserver.db.enities.User;
import pl.edu.agh.languagelearningserver.db.enities.VocabularyGroup;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("category")
public class CategoryController {

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
    public ResponseEntity<List<VocabularyGroup>> getCategories(){
        User user = applicationContext.getUser();

        return new ResponseEntity<>(
                vocabularyGroupService.getAllVocabularyGroups(user),
                HttpStatus.OK);
    }

    @RequestMapping(value = "/word", method = RequestMethod.POST)
    public ResponseEntity<String> addWordsToCategory(@RequestBody Map<String, List<EnglishWord>> wordsToAdd){
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
        VocabularyGroup vg = vocabularyGroupService.getCategoryByName(categoryName, user);
        userVocabularyService.updateCategoryToAllWords(vg, wordsToAdd.get(categoryName), user);

        return new ResponseEntity<>(
                "Successfull added Category.",
                HttpStatus.OK);
    }
}
