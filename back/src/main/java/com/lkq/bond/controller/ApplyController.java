package com.lkq.bond.controller;

import com.google.gson.JsonObject;
import com.lkq.bond.entity.Account;
import com.lkq.bond.entity.Apply;
import com.lkq.bond.mapper.AccountMapper;
import com.lkq.bond.mapper.ApplyMapper;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/apply")
public class ApplyController {

  @Autowired
  private ApplyMapper applyMapper;
  @Autowired
  private AccountMapper accountMapper;

  @GetMapping("/all")
  public List<Apply> getAllApply() {
    List<Apply> r = applyMapper.getAllApply();
    return r;
  }

  @PostMapping("/update")
  public int updateApply(@RequestBody JsonObject object) {
    Apply apply = new Apply();

    apply.id = object.get("id").getAsInt();
    apply.status = object.get("status").getAsString();
    apply.account = accountMapper.getAccountById(object.get("account").getAsInt());
    apply.opponent = object.get("opponent").getAsString();
    apply.temporary_opponent = object.get("temporary_opponent").getAsString();
    apply.trader = object.get("trader").getAsString();
    apply.financing_type = object.get("financing_type").getAsString();
    apply.financing_rate = object.get("financing_rate").getAsDouble();
    apply.clearing_speed = object.get("clearing_speed").getAsString();
    apply.initial_settlement_method = object.get("initial_settlement_method").getAsString();
    apply.expiry_settlement_method = object.get("expiry_settlement_method").getAsString();
    apply.value = object.get("value").getAsDouble();
    apply.duration = object.get("duration").getAsInt();
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
    try {
      apply.recording_day = sdf.parse(object.get("recording_day").getAsString());
      apply.trading_day = sdf.parse(object.get("trading_day").getAsString());
      apply.maturity_day = sdf.parse(object.get("maturity_day").getAsString());
    } catch (ParseException e){
      System.err.println(e);
    }
    apply.aim = object.get("aim").getAsString();
    apply.mark = object.get("mark").getAsString();
    apply.emergency = object.get("emergency").getAsInt();
    return applyMapper.updateApply(apply);
  }

  @PostMapping("/delete")
  public int deleteApply(@RequestParam int id) {
    return applyMapper.deleteApplyById(id);
  }

  @PostMapping("/add")
  public int addApply(@RequestBody JsonObject object) {
    Apply apply = new Apply();
    apply.status = object.get("status").getAsString();
    apply.account = accountMapper.getAccountById(object.get("account").getAsInt());
    apply.opponent = object.get("opponent").getAsString();
    apply.temporary_opponent = object.get("temporary_opponent").getAsString();
    apply.trader = object.get("trader").getAsString();
    apply.financing_type = object.get("financing_type").getAsString();
    apply.financing_rate = object.get("financing_rate").getAsDouble();
    apply.clearing_speed = object.get("clearing_speed").getAsString();
    apply.initial_settlement_method = object.get("initial_settlement_method").getAsString();
    apply.expiry_settlement_method = object.get("expiry_settlement_method").getAsString();
    apply.value = object.get("value").getAsDouble();
    apply.duration = object.get("duration").getAsInt();
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
    try {
      apply.recording_day = sdf.parse(object.get("recording_day").getAsString());
      apply.trading_day = sdf.parse(object.get("trading_day").getAsString());
      apply.maturity_day = sdf.parse(object.get("maturity_day").getAsString());
    } catch (ParseException e){
      System.err.println(e);
    }
    apply.aim = object.get("aim").getAsString();
    apply.mark = object.get("mark").getAsString();
    apply.emergency = object.get("emergency").getAsInt();
    return applyMapper.addApply(apply);
  }

}
