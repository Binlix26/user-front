package com.binlix26.userFront.controller;

import com.binlix26.userFront.model.User;
import com.binlix26.userFront.model.form.DepositWithdrawForm;
import com.binlix26.userFront.service.AccountService;
import com.binlix26.userFront.service.TransactionService;
import com.binlix26.userFront.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;
import java.security.Principal;

/**
 * Created by binlix26 on 18/07/17.
 */
@Controller
@RequestMapping(value = "/account")
public class AccountController {

    @Autowired
    private UserService userService;

    @Autowired
    private AccountService accountService;

    @Autowired
    private TransactionService transactionService;

    @RequestMapping(value = "/primaryAccount")
    public String primaryAccount(Principal principal, Model model) {
        User user = userService.findByUsername(principal.getName());
        model.addAttribute("primaryAccount", user.getPrimaryAccount());
        model.addAttribute("primaryTransactionList",
                transactionService.findPrimaryTransactionList(user.getUsername()));
        return "primaryAccount";
    }

    @RequestMapping(value = "/savingAccount")
    public String savingAccount(Principal principal, Model model) {
        User user = userService.findByUsername(principal.getName());
        model.addAttribute("savingAccount", user.getSavingAccount());
        model.addAttribute("savingTransactionList",
                transactionService.findSavingsTransactionList(user.getUsername()));
        return "savingAccount";
    }

    @RequestMapping(value = "/deposit", method = RequestMethod.GET)
    public String getDepositForm(Model model) {
        model.addAttribute("form", new DepositWithdrawForm());
        return "deposit";
    }

    @RequestMapping(value = "/deposit", method = RequestMethod.POST)
    public String processDepositForm(@ModelAttribute("form") @Valid DepositWithdrawForm form,
                                     BindingResult result, Model model, Principal principal) {
        if (result.hasErrors()) {
            model.addAttribute("form", form);
            return "deposit";
        }
//        System.out.println(form.getAccountType() + " :: " + form.getAmount());
        accountService.deposit(form.getAccountType(), form.getAmount(), principal.getName());
        return "redirect:/userFront";
    }

    @RequestMapping(value = "/withdraw", method = RequestMethod.GET)
    public String getWithdrawForm(Model model) {
        model.addAttribute("form", new DepositWithdrawForm());
        return "withdraw";
    }

    @RequestMapping(value = "/withdraw", method = RequestMethod.POST)
    public String processWithdrawForm(@ModelAttribute("form") @Valid DepositWithdrawForm form,
                                      BindingResult result, Model model, Principal principal) {
        if (result.hasErrors()) {
            model.addAttribute("form", form);
            return "withdraw";
        }

        // Check if the balance meets the withdraw
        if (!accountService.isBalanceEnough(
                form.getAccountType(),
                form.getAmount(),
                principal.getName())) {
            model.addAttribute("form", form);
            model.addAttribute("balanceNotEnough", true);
            return "withdraw";
        }

        accountService.withdraw(form.getAccountType(), form.getAmount(), principal.getName());
        return "redirect:/userFront";
    }
}
