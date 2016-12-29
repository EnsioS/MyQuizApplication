package wad.controller;

import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import wad.domain.Answer;
import wad.domain.Question;
import wad.domain.Quiz;
import wad.repository.AnswerRepository;
import wad.repository.QuestionRepository;
import wad.repository.QuizRepository;

@Controller
public class DefaultController {

    @Autowired
    private QuizRepository quizRepository;

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private AnswerRepository answerRepository;

//    @RequestMapping("*")
//    public String redirect() {
//        return "redirect:/quizzes";
//    }

    @PostConstruct
    public void init() {
        if (quizRepository.findAll().size() > 0) {
            return;
        }

        Quiz maaria = new Quiz();
        maaria.setName("määriä");

        quizRepository.save(maaria);

        Question eka = new Question();
        eka.setQuestion("Montako?");
        Answer yksi = new Answer();
        yksi.setAnswer("yksi");
        yksi.setPoints(1);
        Answer kaksi = new Answer();
        kaksi.setAnswer("kaksi");
        kaksi.setPoints(2);
        answerRepository.save(yksi);
        answerRepository.save(kaksi);

        eka.addOption(yksi);
        eka.addOption(kaksi);

        questionRepository.save(eka);

        maaria.addQuestion(eka);

        Question toka = new Question();
        toka.setQuestion("Montako lisää?");
        Answer kolme = new Answer();
        kolme.setAnswer("kolme lisää");
        kolme.setPoints(3);
        Answer nelja = new Answer();
        nelja.setAnswer("neljä lisää");
        nelja.setPoints(4);
        answerRepository.save(kolme);
        answerRepository.save(nelja);

        toka.addOption(kolme);
        toka.addOption(nelja);

        questionRepository.save(toka);

        maaria.addQuestion(toka);
        quizRepository.saveAndFlush(maaria);
    }
}
