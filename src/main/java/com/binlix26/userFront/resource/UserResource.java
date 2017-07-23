package com.binlix26.userFront.resource;

import com.binlix26.userFront.model.PrimaryTransaction;
import com.binlix26.userFront.model.SavingTransaction;
import com.binlix26.userFront.model.User;
import com.binlix26.userFront.service.TransactionService;
import com.binlix26.userFront.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@PreAuthorize("hasRole('ADMIN')")
public class UserResource {

    @Autowired
    private UserService userService;

    @Autowired
    private TransactionService transactionService;

    @RequestMapping(value = "/user/all", method = RequestMethod.GET)
    public List<User> userList() {
        return userService.findUserList();
    }

    @RequestMapping(value = "/user/primary/transaction", method = RequestMethod.GET)
    public List<PrimaryTransaction> getPrimaryTransactionList(@RequestParam("username") String username) {
        return transactionService.findPrimaryTransactionList(username);
    }

    @RequestMapping(value = "user/savings/transaction", method = RequestMethod.GET)
    public List<SavingTransaction> getSavingTransactionList(@RequestParam("username") String username) {
        return transactionService.findSavingsTransactionList(username);
    }

    @RequestMapping(value = "user/{username}/enable", method = RequestMethod.GET)
    public void enableUser(@PathVariable("username") String username) {
        userService.enableUser(username);
    }

    @RequestMapping(value = "user/{username}/disable", method = RequestMethod.GET)
    public void disableUser(@PathVariable("username") String username) {
        userService.disableUser(username);
    }
}
