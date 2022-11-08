package com.lkq.bond.controller;

import com.lkq.bond.entity.Issuer;
import com.lkq.bond.mapper.IssuerMapper;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
@RequestMapping("/issuer")
public class IssuerController {

  @Autowired
  private IssuerMapper issuerMapper;

  @GetMapping("/all")
  public List<Issuer> getAllIssuer() {
    List<Issuer> r = issuerMapper.getAllIssuer();
    return r;
  }
}
