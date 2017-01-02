package wad.controller;

import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import wad.domain.Account;
import wad.repository.QuizRepository;
import wad.repository.QuestionRepository;
import wad.domain.Quiz;
import wad.domain.Question;
import wad.repository.AccountRepository;

@Controller
public class QuizController {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private QuizRepository quizRepository;

    @Autowired
    private QuestionRepository questionRepository;

    @RequestMapping(value = "/quizzes", method = RequestMethod.GET)
    public String getQuizzes(Model model) {
        
        try {
            //if user is logged in wiev users quizzes 
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            Account owner = accountRepository.findByUsername(auth.getName());
            model.addAttribute("quizzes", quizRepository.findByOwner(owner));  
        } catch (Exception e) {
            model.addAttribute("quizzes", quizRepository.findAll());  
        }

        return "quizzes";
    }

    @RequestMapping(value = "/quizzes", method = RequestMethod.POST)
    public String postQuiz(@Valid @ModelAttribute Quiz quiz, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "redirect:/quizzes";
        }

        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            Account owner = accountRepository.findByUsername(auth.getName());
            quiz.setOwner(owner);
        } catch (Exception e) {
        }

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
