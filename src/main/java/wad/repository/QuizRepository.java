
package wad.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import wad.domain.Quiz;

public interface QuizRepository extends JpaRepository<Quiz, Long> {
    
}
