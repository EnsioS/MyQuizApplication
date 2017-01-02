package wad.controller;

import java.util.Arrays;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import wad.domain.Account;
import wad.repository.AccountRepository;

@Controller
public class AccountController {

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    private AccountRepository accountRepository;

    @RequestMapping(value = "/account", method = RequestMethod.GET)
    public String creatingPage() {

        return "account";
    }

    @RequestMapping(value = "/account", method = RequestMethod.POST)
    public String createAccount(@RequestParam String username, @RequestParam String password) {

        Account account = new Account();
        account.setUsername(username);
        account.setPassword(passwordEncoder.encode(password));
        account.setAuthorities(Arrays.asList("USER"));
        
        accountRepository.save(account);
        
        return "redirect:/quizzes";
    }

}
