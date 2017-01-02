package wad.controller;

import java.util.Arrays;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import wad.domain.Account;
import wad.domain.Answer;
import wad.domain.Question;
import wad.domain.Quiz;
import wad.repository.AnswerRepository;
import wad.repository.QuestionRepository;
import wad.repository.QuizRepository;
import wad.repository.AccountRepository;

@Controller
public class DefaultController {

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private QuizRepository quizRepository;

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private AnswerRepository answerRepository;

    @RequestMapping("*")
    public String redirect() {
        return "redirect:/quizzes";
    }

    @PostConstruct
    public void init() {
        Account wepaUser = new Account();
        if (accountRepository.findAll().isEmpty()) {

            Account account = new Account();
            account.setUsername("Adminensu");
            account.setPassword(passwordEncoder.encode("ensuSalaWord"));
            account.setAuthorities(Arrays.asList("ADMIN", "USER"));

            accountRepository.save(account);
            
            wepaUser.setUsername("wepaUser");
            wepaUser.setPassword(passwordEncoder.encode("wepaSalaWord"));
            wepaUser.setAuthorities(Arrays.asList("USER"));

            accountRepository.save(wepaUser);
        }

        if (quizRepository.findAll().size() > 0) {
            return;
        }

        Quiz maaria = new Quiz();
        maaria.setName("määriä");
        maaria.setOwner(wepaUser);

        maaria = quizRepository.save(maaria);

        Question eka = new Question();
        eka.setQuestion("Montako?");
        Answer yksi = new Answer();
        yksi.setAnswer("yksi");
        yksi.setPoints(1);
        Answer kaksi = new Answer();
        kaksi.setAnswer("kaksi");
        kaksi.setPoints(2);
        yksi = answerRepository.save(yksi);
        kaksi = answerRepository.save(kaksi);

        eka.addOption(yksi);
        eka.addOption(kaksi);

        eka = questionRepository.save(eka);

        maaria.addQuestion(eka);

        Question toka = new Question();
        toka.setQuestion("Montako lisää?");
        Answer kolme = new Answer();
        kolme.setAnswer("kolme lisää");
        kolme.setPoints(3);
        Answer nelja = new Answer();
        nelja.setAnswer("neljä lisää");
        nelja.setPoints(4);
        kolme = answerRepository.save(kolme);
        nelja = answerRepository.save(nelja);

        toka.addOption(kolme);
        toka.addOption(nelja);

        toka = questionRepository.save(toka);

        maaria.addQuestion(toka);
        quizRepository.saveAndFlush(maaria);
    }
}
