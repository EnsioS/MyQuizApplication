
package wad.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import wad.domain.Answer;

public interface AnswerRepository extends JpaRepository<Answer, Long> {
    
}
