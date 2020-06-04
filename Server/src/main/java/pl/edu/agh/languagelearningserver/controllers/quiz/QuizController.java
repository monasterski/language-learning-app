package pl.edu.agh.languagelearningserver.controllers.quiz;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import pl.edu.agh.languagelearningserver.beans.TranslationPLService;
import pl.edu.agh.languagelearningserver.beans.UserVocabularyService;
import pl.edu.agh.languagelearningserver.controllers.authorization.ApplicationContext;
import pl.edu.agh.languagelearningserver.db.enities.EnglishWord;
import pl.edu.agh.languagelearningserver.db.enities.User;
import pl.edu.agh.languagelearningserver.db.enities.UserVocabulary;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Map;

@RestController
@RequestMapping("/quiz")
public class QuizController {

    @Autowired
    UserVocabularyService userVocabularyService;

    @Autowired
    TranslationPLService translationPLService;

    @Autowired
    ApplicationContext applicationContext;

    @Transactional
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<String> addQuizResult(@RequestBody Map<String,String> result){
        User user = applicationContext.getUser();
        int allAnswers = result.size();
        Map<EnglishWord,String> filteredResult = translationPLService.checkQuizResult(result, user);
        int goodAnswers = filteredResult.size();

        ArrayList<UserVocabulary> userVocabularies =
                userVocabularyService.getAllUserVocabulariesByEnglishWords(user, filteredResult.keySet());

        userVocabularyService.updateRepetitionInUserVocabularies(userVocabularies);


        return new ResponseEntity<>(
                "Successfull added result, Your score: " + goodAnswers + "/" + allAnswers,
                HttpStatus.OK);
    }

}
