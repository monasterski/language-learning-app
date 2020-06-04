package pl.edu.agh.languagelearningserver.beans;


import org.springframework.beans.factory.annotation.Autowired;
import pl.edu.agh.languagelearningserver.db.enities.EnglishWord;
import pl.edu.agh.languagelearningserver.db.enities.User;
import pl.edu.agh.languagelearningserver.db.enities.UserVocabulary;
import pl.edu.agh.languagelearningserver.db.enities.VocabularyGroup;
import pl.edu.agh.languagelearningserver.db.enities.ids.UserVocabularyId;
import pl.edu.agh.languagelearningserver.db.repositories.UserVocabularyRepository;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class UserVocabularyService {

    @Autowired
    UserVocabularyRepository userVocabularyRepository;


    public boolean existByUserAndEnglishWord(User user, EnglishWord englishWord){
        return userVocabularyRepository.existsByUserAndEnglishWord(user, englishWord);
    }


    public ArrayList<UserVocabulary> getAllUserVocabulariesByEnglishWords(User user, Set<EnglishWord> englishWords){
        return new ArrayList<>(userVocabularyRepository.findUserVocabulariesByUserAndEnglishWordIn(user, englishWords));
    }

    @Transactional
    public void updateRepetitionInUserVocabularies(List<UserVocabulary> userVocabularies){
        for (UserVocabulary userVocabulary: userVocabularies){
            int newRepetition = userVocabulary.getRepetition() + 1;
            if(newRepetition >=10){
                userVocabularyRepository.delete(userVocabulary);
            }else{
                userVocabulary.setRepetition(newRepetition);
                userVocabularyRepository.save(userVocabulary);
            }
        }
    }

    public void addUserVocabulary(User user, EnglishWord englishWord){
        boolean isAdded = userVocabularyRepository.existsByUserAndEnglishWord(user, englishWord);
        if(!isAdded){
            UserVocabulary userVocabulary = new UserVocabulary(user, englishWord);
            userVocabularyRepository.save(userVocabulary);
        }
    }

    public UserVocabulary getUserVocabulary(User user, EnglishWord englishWord){
        Optional<UserVocabulary> uv = userVocabularyRepository.findById(new UserVocabularyId(user, englishWord));
        return uv.orElse(null);
    }
    

    @Transactional
    public void updateCategoryToAllWords(VocabularyGroup vocabularyGroup, List<EnglishWord> englishWords, User user){
        for (EnglishWord word: englishWords) {
            UserVocabulary userVocabulary = getUserVocabulary(user, word);
            if(userVocabulary!=null){
                userVocabulary.setVocabularyGroup(vocabularyGroup);
                userVocabularyRepository.save(userVocabulary);
            }
        }
    }

    public ArrayList<EnglishWord> getWordsByUserAndCategory(User user, VocabularyGroup vocabularyGroup){
        ArrayList<UserVocabulary> vgs = new ArrayList<>(userVocabularyRepository.findUserVocabulariesByUserAndVocabularyGroup(user, vocabularyGroup));
        return vgs.stream().map(UserVocabulary::getEnglishWord).collect(Collectors.toCollection(ArrayList::new));
    }

    public ArrayList<EnglishWord> getAllWordsByUser(User user){
        ArrayList<UserVocabulary> vgs = new ArrayList<>(userVocabularyRepository.findUserVocabulariesByUser(user));
        return vgs.stream().map(UserVocabulary::getEnglishWord).collect(Collectors.toCollection(ArrayList::new));
    }




}
