package pl.edu.agh.languagelearningserver.db.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.edu.agh.languagelearningserver.db.enities.VocabularyGroup;

@Repository
public interface VocabularyGroupRepository extends JpaRepository<VocabularyGroup, Long> {

}
