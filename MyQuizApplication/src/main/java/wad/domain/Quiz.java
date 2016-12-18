
package wad.domain;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.data.jpa.domain.AbstractPersistable;

@Entity
public class Quiz extends AbstractPersistable<Long> {
    
    @NotBlank
    @Column(unique = true)
    private String name;
    
    @ManyToMany
    private List<Question> questions;

    public Quiz() {
        this.questions = new ArrayList<>();
    }    
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public void addQuestion(Question question) {
        this.questions.add(question);
    }
    
    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }
    
}
