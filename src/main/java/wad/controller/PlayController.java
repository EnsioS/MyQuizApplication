package wad.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import wad.domain.Question;
import wad.service.PlayService;

@Controller
@RequestMapping("/play")
public class PlayController {

    @Autowired
    private PlayService playService;

    @RequestMapping(value = "/{id}/start", method = RequestMethod.GET)
    public String play(@PathVariable Long id, Model model) {

        model.addAttribute("players", playService.getPlayers().keySet());
        model.addAttribute("id", id);
        
        return "startPlay";
    }

    @RequestMapping(value = "/{id}/play", method = RequestMethod.POST)
    public String addPlayer(@PathVariable Long id, @RequestParam String nickname) {
        playService.setNickname(nickname);
        
        return "redirect:/play/" + id + "/start";
    }

    @RequestMapping(value = "/{id}/playQuestion", method = RequestMethod.GET)
    public String playQuestion(@PathVariable Long id, Model model) {
        Question question = playService.getNextQuestion(id);
        
        if (question == null) {
            playService.end();
            return "redirect:/quizzes";
        }
        
        model.addAttribute("id", id);
        model.addAttribute("question", question);
        model.addAttribute("time", question.getAnsweringTime());
        
        return "play";
    }

    @RequestMapping(value = "/{id}/waiting", method = RequestMethod.POST)
    public String waitingPage(@RequestParam String remainingTime, @RequestParam String points,
            @PathVariable Long id, Model model) {
        
        playService.answer(Integer.parseInt(points));
        
        model.addAttribute("id", id);
        model.addAttribute("time", remainingTime);
        
        return "waiting";
    }
    
    @RequestMapping(value = "/{id}/points", method = RequestMethod.GET)
    public String points(@PathVariable Long id, Model model) {
        
        model.addAttribute("id", id);
        model.addAttribute("players", playService.getPlayers().keySet());
        model.addAttribute("points", playService.getPlayers());
        
        return "points";
    }
}
