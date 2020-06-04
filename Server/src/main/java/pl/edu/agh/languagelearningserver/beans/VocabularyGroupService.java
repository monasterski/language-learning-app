package pl.edu.agh.languagelearningserver.beans;

import org.springframework.beans.factory.annotation.Autowired;
import pl.edu.agh.languagelearningserver.db.enities.EnglishWord;
import pl.edu.agh.languagelearningserver.db.enities.User;
import pl.edu.agh.languagelearningserver.db.enities.VocabularyGroup;
import pl.edu.agh.languagelearningserver.db.repositories.VocabularyGroupRepository;

import java.util.ArrayList;

public class VocabularyGroupService {

    @Autowired
    VocabularyGroupRepository vocabularyGroupRepository;

    public ArrayList<VocabularyGroup> getAllVocabularyGroups(User user){
        return new ArrayList<>(vocabularyGroupRepository.findVocabularyGroupsByUser(user));
    }

    public boolean isCreated(String name, User user){
        return vocabularyGroupRepository.existsVocabularyGroupByNameAndUser(name, user);
    }

    public VocabularyGroup getCategoryByName(String name, User user){
        return vocabularyGroupRepository.findVocabularyGroupByNameAndUser(name, user);
    }

    public void addCategory(String name, User user){
        VocabularyGroup vg = new VocabularyGroup();
        vg.setName(name);
        vg.setUser(user);
        vocabularyGroupRepository.save(vg);
    }



}
