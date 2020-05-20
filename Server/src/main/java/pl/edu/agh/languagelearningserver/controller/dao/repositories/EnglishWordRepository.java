package pl.edu.agh.languagelearningserver.controller.dao.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.edu.agh.languagelearningserver.controller.dao.enities.EnglishWord;

@Repository
public interface EnglishWordRepository extends JpaRepository<EnglishWord, Long> {
}
