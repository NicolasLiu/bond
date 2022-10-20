package com.lkq.bond.entity;

import java.util.Date;

public class Position {
  public int id;
  public Account account;
  public String code;
  public String name;
  public double value;
  public String opponent;
  public String market;
  public Date initial_settlement_day;
  public Date expiry_settlement_day;
  public double interest_rate;
  public String pledge_type;
  public String status;
  public BondInfo bond_info;

}
