package wad.controller;

import java.util.List;
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
import wad.repository.QuizRepository;

@Controller
public class QuestionController {

    @Autowired
    private QuizRepository quizRepository;
    
    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private AnswerRepository answerRepository;

    @RequestMapping(value = "/{id}/question", method = RequestMethod.GET)
    public String getQuestion(Model model, @PathVariable Long id) {
        model.addAttribute("question", null);
        model.addAttribute("quiz", quizRepository.findOne(id));

        return "question";
    }

    @RequestMapping(value = "/{id}/question/{questionId}", method = RequestMethod.GET)
    public String getQuestion(@PathVariable Long questionId, Model model, @PathVariable Long id) {
        model.addAttribute("question", questionRepository.findOne(questionId));
        model.addAttribute("quiz", quizRepository.findOne(id));
        
        return "question";
    }

    @RequestMapping(value = "/{id}/question", method = RequestMethod.POST)
    public String postQuestion(@RequestParam Map<String, String> params, @PathVariable Long id, Model model) {
        model.addAttribute("quiz", quizRepository.findOne(id));
        
        if (params.containsKey("id")) {
            Question question = questionRepository.findOne(Long.parseLong(params.get("id")));
            List<Answer> options = question.getOptions();
             
            Answer answer = options.get(Integer.parseInt(params.get("orderNumber")));
            answer.setAnswer(params.get("answer"));
            answer.setPoints(Integer.parseInt(params.get("points")));
            answerRepository.saveAndFlush(answer);
            questionRepository.saveAndFlush(question);

            return "redirect:/quiz/" + id;
        }

        Question question = new Question();
        question.setQuestion(params.get("questionText"));


        for (int i = 1; i <= 4; i++) {
            String ans = "answer" + i;
            String points = "points" + i;

            if (params.get(ans) != null && !params.get(points).isEmpty()) {
                Answer answer = new Answer();
                
                answer.setAnswer(params.get(ans));
                answer.setPoints(Integer.parseInt(params.get(points)));
                answer.setOrderNumber(i-1);
                answerRepository.save(answer);

                question.addOption(answer);                
            }
            questionRepository.saveAndFlush(question);
            
        }

        return "redirect:/quiz/" + id;
    }

}
