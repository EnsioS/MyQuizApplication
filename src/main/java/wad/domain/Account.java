package wad.domain;

import java.util.List;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.data.jpa.domain.AbstractPersistable;

@Entity
public class Account extends AbstractPersistable<Long> {

    @NotBlank
    @Length(min = 4, max = 20)
    private String username;

    @NotBlank    
    private String password;

    @OneToMany
    private List<Quiz> quizzes;

    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> authorities;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Quiz> getQuizzes() {
        return quizzes;
    }

    public void setQuizzes(List<Quiz> quizzes) {
        this.quizzes = quizzes;
    }

        public List<String> getAuthorities() {
        return authorities;
    }
 
    public void setAuthorities(List<String> authorities) {
        this.authorities = authorities;
    }
}
