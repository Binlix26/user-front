package com.binlix26.userFront.controller;

import com.binlix26.userFront.model.PrimaryAccount;
import com.binlix26.userFront.model.SavingAccount;
import com.binlix26.userFront.model.User;
import com.binlix26.userFront.model.security.UserRole;
import com.binlix26.userFront.repository.RoleRepository;
import com.binlix26.userFront.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.security.Principal;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by binlix26 on 16/07/17.
 */
@Controller
public class HomeController {

    @Autowired
    private UserService userService;

    @Autowired
    private RoleRepository roleRepository;

    @RequestMapping(value = "")
    public String home() {
        return "redirect:/index";
    }

    @RequestMapping(value = "/index")
    public String index() {
        return "index";
    }

    @RequestMapping(value = "/signup", method = RequestMethod.GET)
    public String getSignupForm(Model model) {
        User user = new User();
        model.addAttribute("user", user);
        return "signup";
    }

    @RequestMapping(value = "/signup", method = RequestMethod.POST)
    public String processSignupForm(@ModelAttribute("user") User user, Model model) {
        boolean usernameExist = userService.checkUsernameExists(user.getUsername());
        boolean emailExist = userService.checkEmailExists(user.getEmail());

        if (usernameExist || emailExist) {
            model.addAttribute("usernameExists", usernameExist);
            model.addAttribute("emailExists", emailExist);
            return "signup";
        } else {
            Set<UserRole> userRoles = new HashSet<>();
            userRoles.add(new UserRole(user, roleRepository.findByName("ROLE_USER")));
            userService.createUser(user, userRoles);
            return "redirect:/";
        }
    }

    @RequestMapping(value = "/userFront")
    public String userFront(Principal principal, Model model) {
        // Principal represents the user currently logged in
        User user = userService.findByUsername(principal.getName());

        PrimaryAccount primaryAccount = user.getPrimaryAccount();
        SavingAccount savingAccount = user.getSavingAccount();

        model.addAttribute("primaryAccount", primaryAccount);
        model.addAttribute("savingAccount", savingAccount);

        return "userFront";
    }
}
