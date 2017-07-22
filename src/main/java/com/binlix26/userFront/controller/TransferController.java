package com.binlix26.userFront.controller;

import com.binlix26.userFront.model.Recipient;
import com.binlix26.userFront.model.User;
import com.binlix26.userFront.model.form.BetweenAccountsForm;
import com.binlix26.userFront.model.form.ToSomeoneElseForm;
import com.binlix26.userFront.service.TransactionService;
import com.binlix26.userFront.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.security.Principal;

/**
 * Created by binlix26 on 18/07/17.
 */
@Controller
@RequestMapping(value = "/transfer")
public class TransferController {

    @Autowired
    private UserService userService;

    @Autowired
    private TransactionService transactionService;

    @RequestMapping(value = "/betweenAccounts", method = RequestMethod.GET)
    public String getBetweenAccountsForm(Model model) {
        model.addAttribute("form", new BetweenAccountsForm());
        return "betweenAccounts";
    }

    @RequestMapping(value = "/betweenAccounts", method = RequestMethod.POST)
    public String processBetweenAccountsForm(@ModelAttribute("form") @Valid BetweenAccountsForm form,
                                             BindingResult result, Model model, Principal principal) {
        if (result.hasErrors()) {
            model.addAttribute("form", form);
            return "betweenAccounts";
        }

        transactionService.betweenAccountsTransfer(form, principal.getName());
        return "redirect:/userFront";
    }

    @RequestMapping(value = "/recipient", method = RequestMethod.GET)
    public String getAddRecipientForm(Model model, Principal principal) {
        User user = userService.findByUsername(principal.getName());
        model.addAttribute("recipientList", transactionService.findRecipientList(user.getUsername()));
        model.addAttribute("recipient", new Recipient());
        return "recipient";
    }

    @RequestMapping(value = "/recipient/save", method = RequestMethod.POST)
    public String processRecipientForm(@ModelAttribute("recipient") Recipient recipient, Principal principal) {
        User user = userService.findByUsername(principal.getName());
        recipient.setUser(user);
        transactionService.saveRecipient(recipient);
        return "redirect:/userFront";
    }

    @RequestMapping(value = "/recipient/edit", method = RequestMethod.GET)
    public String editRecipient(@RequestParam(value = "recipientId") Long id, Model model, Principal principal) {
        Recipient recipient = transactionService.findRecipient(id);
        model.addAttribute("recipientList", transactionService.findRecipientList(principal.getName()));
        model.addAttribute("recipient", recipient);
        return "recipient";
    }

    @RequestMapping(value = "/recipient/delete", method = RequestMethod.GET)
    public String removeRecipient(@RequestParam("recipientId") Long id, Model model, Principal principal) {
        transactionService.deleteRecipient(id);
        model.addAttribute("recipientList", transactionService.findRecipientList(principal.getName()));
        model.addAttribute("recipient", new Recipient());
        return "recipient";
    }

    @RequestMapping(value = "/toSomeoneElse", method = RequestMethod.GET)
    public String getToSomeone(Model model, Principal principal) {
        model.addAttribute("form", new ToSomeoneElseForm());
        model.addAttribute("recipientList", transactionService.findRecipientList(principal.getName()));
        return "toSomeoneElse";
    }

    @RequestMapping(value = "/toSomeoneElse", method = RequestMethod.POST)
    public String processToSomeone(@ModelAttribute("form") @Valid ToSomeoneElseForm form,
                                   BindingResult result, Model model, Principal principal) {

        if (result.hasErrors()) {
            model.addAttribute("form", form);
            model.addAttribute("recipientList", transactionService.findRecipientList(principal.getName()));
            return "toSomeoneElse";
        }

//        System.out.println("FORM: " + form.getRecipientId() + "::" + form.getAccountType() + "::" + form.getAmount());
        transactionService.transferToSomeone(form, principal.getName());
        return "redirect:/userFront";
    }
}
