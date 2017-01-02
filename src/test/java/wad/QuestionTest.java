package wad;

import org.fluentlenium.adapter.FluentTest;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.runner.RunWith;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import wad.domain.Answer;
import wad.domain.Question;
import wad.domain.Quiz;
import wad.repository.AnswerRepository;
import wad.repository.QuestionRepository;
import wad.repository.QuizRepository;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class QuestionTest extends FluentTest {

    @Autowired
    private QuizRepository quizRepository;

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private AnswerRepository answerRepository;

    public WebDriver webDriver = new HtmlUnitDriver();

    @Override
    public WebDriver getDefaultDriver() {
        return webDriver;
    }

    @LocalServerPort
    private Integer port;

    @Test
    public void addExistingQuestion() {
        init();

        goTo("http://localhost:" + port + "/quizzes");

        assertTrue(pageSource().contains("Login"));

        fill(find("input").get(0)).with("wepaUser");
        fill(find("input").get(1)).with("wepaSalaWord");

        submit(find("form").first());

        assertFalse(pageSource().contains("määriä"));
        
        fill(find("input").first()).with("määriä");
        submit(find("form").first());
        
        assertTrue(pageSource().contains("määriä"));
        
        click(find("a").first());

        submit(find("form").first());

        assertTrue(pageSource().contains("Montako?"));
    }

    public void init() {
        quizRepository.deleteAll();
        questionRepository.deleteAll();
        answerRepository.deleteAll();

//        Quiz maaria = new Quiz();
//        maaria.setName("määriä");
//
//        quizRepository.save(maaria);
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

        eka = questionRepository.save(eka);
    }
}
