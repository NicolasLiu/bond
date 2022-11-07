package com.lkq.bond.controller;

import com.lkq.bond.entity.Account;
import com.lkq.bond.mapper.AccountMapper;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
@RequestMapping("/account")
public class AccountController {


  @Autowired
  private AccountMapper accountMapper;
  @GetMapping("/all")
  public List<Account> getAllAccount() {
    List<Account> r = accountMapper.getAllAccount();
    return r;
  }

}
