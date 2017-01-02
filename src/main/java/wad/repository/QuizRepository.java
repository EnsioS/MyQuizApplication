
package wad.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import wad.domain.Account;
import wad.domain.Quiz;

public interface QuizRepository extends JpaRepository<Quiz, Long> {
 
    List<Quiz> findByOwner(Account owner);
}
