
package wad.controller;

import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import wad.repository.QuizRepository;
import wad.repository.QuestionRepository;
import wad.domain.Quiz;
import wad.domain.Question;
//import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class QuizController {
    
    @Autowired
    private QuizRepository quizRepository;
    
    @Autowired
    private QuestionRepository questionRepository;
    
    
    @RequestMapping(value = "/quizzes", method = RequestMethod.GET)
    public String getQuizzes(Model model) {
        model.addAttribute("quizzes", quizRepository.findAll());
        
        return "quizzes";
    }
    
    @RequestMapping(value = "/quizzes", method = RequestMethod.POST)
    public String postQuiz(@Valid @ModelAttribute Quiz quiz, BindingResult BindingResult) {
        if (BindingResult.hasErrors()) {
            return "redirect:/quizzes";
        }
//        Quiz quiz = new Quiz();
//        quiz.setName(name);
        
        quizRepository.save(quiz);
        
        return "redirect:/quizzes";
    }

    @RequestMapping(value = "/quiz/{id}", method = RequestMethod.GET)
    public String getQuiz(@PathVariable Long id, Model model) {
        Quiz quiz = quizRepository.findOne(id);
        model.addAttribute("quiz", quiz);
        model.addAttribute("questions", quiz.getQuestions());
        model.addAttribute("allQuestions", questionRepository.findAll());
        
        return "quiz";
    }
    
    @RequestMapping(value = "/quiz/{id}", method = RequestMethod.POST)
    public String addExistingQuestion(@PathVariable Long id, @RequestParam Long questionId) {
        Quiz quiz = quizRepository.findOne(id);
        Question question = questionRepository.findOne(questionId);
        
        quiz.addQuestion(question);
        
        quizRepository.saveAndFlush(quiz);
        
        return "redirect:/quiz/" + id;
    }
    
}
