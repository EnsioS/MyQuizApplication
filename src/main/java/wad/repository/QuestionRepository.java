
package wad.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import wad.domain.Question;
import wad.domain.Quiz;

public interface QuestionRepository extends JpaRepository<Question, Long> {
    
}
