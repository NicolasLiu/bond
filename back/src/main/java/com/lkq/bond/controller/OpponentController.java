package com.lkq.bond.controller;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.lkq.bond.entity.Apply;
import com.lkq.bond.entity.Opponent;
import com.lkq.bond.mapper.AccountMapper;
import com.lkq.bond.mapper.ApplyMapper;
import com.lkq.bond.mapper.OpponentMapper;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
@RequestMapping("/opponent")
public class OpponentController {

  @Autowired
  private OpponentMapper opponentMapper;

  @GetMapping("/all")
  public List<Opponent> getAllOpponent() {
    List<Opponent> r = opponentMapper.getAllOpponent();
    return r;
  }

  @PostMapping("/delete")
  public int deleteOpponent(@RequestParam int id) {
    return opponentMapper.deleteOpponentById(id);
  }

  @PostMapping("/add")
  public int addOpponent(@RequestBody JsonObject object) {
    Opponent opponent = new Opponent();
    opponent.name = object.get("name").getAsString();
    opponent.priority = object.get("priority").getAsInt();
    opponent.bond_credit_limit = "";
    opponent.institution_credit_limit = "";
    JsonArray arr1 = object.get("bond_credit_limit").getAsJsonArray();
    StringBuilder bond_credit_limit = new StringBuilder();
    for (JsonElement tmp : arr1) {
      bond_credit_limit.append(tmp.getAsString()).append(",");
    }
    if (bond_credit_limit.length() > 0) {
      opponent.bond_credit_limit = bond_credit_limit.substring(0, bond_credit_limit.lastIndexOf(","));
    }
    JsonArray arr2 = object.get("institution_credit_limit").getAsJsonArray();
    StringBuilder institution_credit_limit = new StringBuilder();
    for (JsonElement tmp : arr2) {
      institution_credit_limit.append(tmp.getAsString()).append(",");
    }
    if (institution_credit_limit.length() > 0) {
      opponent.institution_credit_limit = institution_credit_limit.substring(0, institution_credit_limit.lastIndexOf(","));
    }

    return opponentMapper.addOpponent(opponent);
  }

  @PostMapping("/update")
  public int updateOpponent(@RequestBody JsonObject object) {
    Opponent opponent = new Opponent();

    opponent.id = object.get("id").getAsInt();
    opponent.name = object.get("name").getAsString();
    opponent.priority = object.get("priority").getAsInt();
    JsonArray arr1 = object.get("bond_credit_limit").getAsJsonArray();

    StringBuilder bond_credit_limit = new StringBuilder();
    for (JsonElement tmp : arr1) {
      bond_credit_limit.append(tmp.getAsString()).append(",");
    }
    if (bond_credit_limit.length() > 0) {
      opponent.bond_credit_limit = bond_credit_limit.substring(0, bond_credit_limit.lastIndexOf(","));
    }

    JsonArray arr2 = object.get("institution_credit_limit").getAsJsonArray();
    StringBuilder institution_credit_limit = new StringBuilder();
    for (JsonElement tmp : arr2) {
      institution_credit_limit.append(tmp.getAsString()).append(",");
    }
    if (institution_credit_limit.length() > 0) {
      opponent.institution_credit_limit = institution_credit_limit.substring(0, institution_credit_limit.lastIndexOf(","));
    }

    return opponentMapper.updateOpponent(opponent);
  }


}
