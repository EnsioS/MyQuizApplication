package wad;

import java.util.List;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.runner.RunWith;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import wad.controller.QuestionController;
import wad.controller.QuizController;
import wad.domain.Answer;
import wad.domain.Question;
import wad.domain.Quiz;
import wad.repository.AnswerRepository;
import wad.repository.QuestionRepository;
import wad.repository.QuizRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
public class QuizControllerTest {

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private QuizRepository quizRepository;

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private AnswerRepository answerRepository;

    private MockMvc mockMvc;

    @Before
    public void setUp() throws Exception {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(context).build();

    }

    @Test
    public void modelHasAttributeQuizzes() throws Exception {
        mockMvc.perform(get("/quizzes"))
                .andExpect(model().attributeExists("quizzes"));
    }

    @Test
    public void modelHasCorrectQuizzes() throws Exception {
        quizRepository.deleteAll();
        Quiz eka = new Quiz();
        eka.setName("eka");
        Quiz toka = new Quiz();
        toka.setName("toka");

        quizRepository.save(eka);
        quizRepository.save(toka);

        MvcResult res = mockMvc.perform(get("/quizzes"))
                .andReturn();

        List<Quiz> quizzes = (List) res.getModelAndView().getModel().get("quizzes");

        assertEquals("quizzes has wrong size", 2, quizzes.size());

        assertEquals("Question has wrong name", "eka", quizzes.get(0).getName());
        assertEquals("Question has wrong name", "toka", quizzes.get(1).getName());
    }

    @Test
    public void createQuizCorrect() throws Exception {
        quizRepository.deleteAll();

        mockMvc.perform(post("/quizzes?name=eka"));

        MvcResult res = mockMvc.perform(get("/quizzes"))
                .andReturn();

        List<Quiz> quizzes = (List) res.getModelAndView().getModel().get("quizzes");

        assertEquals("quizzes has wrong size", 1, quizzes.size());

        assertEquals("Quiz has wrong name", "eka", quizzes.get(0).getName());
    }

    @Test
    public void doesntCreateTwoQuizWithSameName() throws Exception {
        quizRepository.deleteAll();

        mockMvc.perform(post("/quizzes?name=eka"));
        try {
            mockMvc.perform(post("/quizzes?name=eka"));
        } catch (Exception e) {
            System.out.println("Some error while trying make another quiz with same name");
        }

        MvcResult res = mockMvc.perform(get("/quizzes"))
                .andReturn();

        List<Quiz> quizzes = (List) res.getModelAndView().getModel().get("quizzes");

        assertEquals("quizzes has wrong size", 1, quizzes.size());
    }

    @Test
    public void getSingleQuizCorrect() throws Exception {
        init();

        mockMvc.perform(get("/quiz/2"))
                .andExpect(model().attributeExists("quiz"))
                .andExpect(model().attributeExists("questions"))
                .andExpect(model().attributeExists("allQuestions"));

        MvcResult res = mockMvc.perform(get("/quiz/2"))
                .andReturn();

        Quiz quiz = (Quiz) res.getModelAndView().getModel().get("quiz");
        
        assertEquals("quiz has wrong name", "määriä", quiz.getName());
        
        List<Question> questions = (List) res.getModelAndView().getModel().get("questions");

        assertEquals("questions has wrong size", 2, questions.size());
    }

    public void init() {
        quizRepository.deleteAll();
        questionRepository.deleteAll();
        answerRepository.deleteAll();

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
