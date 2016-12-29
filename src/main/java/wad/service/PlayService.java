
package wad.service;

import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import wad.domain.Question;
import wad.domain.Quiz;
import wad.repository.QuizRepository;

@Service
public class PlayService {
    
    private int remainingTime;
    
    @Autowired
    private QuizRepository quizRepository;
    
    @Autowired
    private HttpSession session;
    
    private Map<String, Integer> players;
    
    private int questionIndex;

    public PlayService() {
        this.questionIndex = 0;
        this.players = new HashMap<>();
    }
    
    public Map<String, Integer> getPlayers() {
        return this.players;
    }
    
    public Question getNextQuestion(Long quizId) {
        Quiz quiz = quizRepository.findOne(quizId);
        
        if (questionIndex >= quiz.getQuestions().size()) {
            return null;
        }
        
        Question next = quiz.getQuestions().get(questionIndex);
        
        questionIndex++;
        
        return next;
    }

    public void setNickname(String nickname) {
        
        if (players.get(nickname) == null) {
            
            if (session.getAttribute("nickname") != null) {
                players.remove((String) session.getAttribute("nickname"));
            }
            
            session.setAttribute("nickname", nickname);
            players.put(nickname, 0);
        }
    }
    
    public void answer(int points) {
        
        if (session.getAttribute("nickname") == null) {
            return;
        }
        
        String nickname = (String) session.getAttribute("nickname");
        int newPoints = players.get(nickname) + points;
        players.put(nickname, newPoints);
    }
    
    
    public void end() {
        this.questionIndex = 0;
        this.players.clear();
    }
}
