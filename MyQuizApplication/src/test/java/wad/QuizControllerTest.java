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
import wad.domain.Quiz;
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

        assertEquals("Quiz has wrong name", "eka", quizzes.get(0).getName());
        assertEquals("Quiz has wrong name", "toka", quizzes.get(1).getName());
    }

    // cobertura(testikattavuus) -- travis automatisaatio
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

}
