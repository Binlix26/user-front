package com.binlix26.userFront.controller;

import com.binlix26.userFront.model.User;
import com.binlix26.userFront.repository.UserRepository;
import com.binlix26.userFront.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.security.Principal;

/**
 * Created by binlix26 on 22/07/17.
 */
@Controller
@RequestMapping(value = "/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @RequestMapping(value = "/profile", method = RequestMethod.GET)
    public String getProfile(Model model, Principal principal) {
        User user = userService.findByUsername(principal.getName());
        model.addAttribute("user", user);
        return "profile";
    }

    @RequestMapping(value = "/profile", method = RequestMethod.POST)
    public String processProfile(@ModelAttribute("user") User theUser) {
        // the reason why I retrieve user again is because @ModelAttribute only includes the files specified in the Template
        User user = userRepository.findOne(theUser.getUserId());
        user.setFirstName(theUser.getFirstName());
        user.setLastName(theUser.getLastName());
        user.setEmail(theUser.getEmail());
        user.setPhone(theUser.getPhone());
        user.setUsername(theUser.getUsername());

        userService.save(user);
        return "redirect:/userFront";
    }
}
