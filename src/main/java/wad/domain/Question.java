
package wad.domain;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.validation.constraints.Size;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.data.jpa.domain.AbstractPersistable;

@Entity
public class Question extends AbstractPersistable<Long> {
    
//    @NotBlank
    private String question;
    
//    @NotBlank
//    @Size(min = 2, max = 4)
    @OneToMany
    private List<Answer> options;

    @ManyToMany(mappedBy = "questions")
    private List<Quiz> quizzes;

    private int answeringTime;
    
    public Question() {
        this.options = new ArrayList<>();
        this.answeringTime = 8;
    }
        
    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public void addOption(Answer answer) {
        this.options.add(answer);
    }
    
    public List<Answer> getOptions() {
        return options;
    }
    
    public void setOptions(List<Answer> options) {
        this.options = options;
    }

    public int getAnsweringTime() {
        return answeringTime;
    }

    public void setAnsweringTime(int answeringTime) {
        this.answeringTime = answeringTime;
    }
}
