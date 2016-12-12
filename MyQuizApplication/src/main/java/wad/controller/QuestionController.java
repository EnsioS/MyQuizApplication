package wad.controller;

import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import wad.domain.Answer;
import wad.domain.Question;
import wad.repository.QuestionRepository;
import wad.repository.AnswerRepository;

@Controller
public class QuestionController {

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private AnswerRepository answerRepository;
    
    @RequestMapping(value = "/question", method = RequestMethod.GET)
    public String getQuestion(Model model) {
        model.addAttribute("question", null);
        
        return "question";
    }

    @RequestMapping(value = "/question/{id}", method = RequestMethod.GET)
    public String getQuestion(@PathVariable Long id, Model model) {
        model.addAttribute("question", questionRepository.findOne(id));
        
        return "question";
    }
    
    @RequestMapping(value = "/question", method = RequestMethod.POST)
    public String postQuestion(@RequestParam Map<String, String> params) {
        Question question = new Question();
        question.setQuestion(params.get("questionText"));
        Answer answer1 = new Answer();
        answer1.setAnswer(params.get("answer1"));
        answer1.setPoints(Integer.parseInt(params.get("points1")));
        answerRepository.save(answer1);
        
        question.addOption(answer1);
        questionRepository.saveAndFlush(question);
        
        return "redirect:/quizzes";
    }

}
