package com.lkq.bond.controller;

import com.google.gson.JsonObject;
import com.lkq.bond.entity.Account;
import com.lkq.bond.entity.Apply;
import com.lkq.bond.entity.BondInfo;
import com.lkq.bond.entity.Position;
import com.lkq.bond.mapper.AccountMapper;
import com.lkq.bond.mapper.ApplyMapper;
import com.lkq.bond.mapper.BondInfoMapper;
import com.lkq.bond.mapper.PositionMapper;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ClassUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@CrossOrigin
@RequestMapping("/position")
public class PositionController {

  @Autowired
  private PositionMapper positionMapper;
  @Autowired
  private AccountMapper accountMapper;
  @Autowired
  private BondInfoMapper bondInfoMapper;

  @GetMapping("/all")
  public List<Position> getAllPosition() {
    List<Position> r = positionMapper.getAllPosition();
    return r;
  }

  @PostMapping("/upload")
  public void uploadPosition(@RequestParam("file") MultipartFile file) {
    String filename = file.getOriginalFilename();
    String filetype = filename.substring(filename.lastIndexOf("."));
    String newfile = UUID.randomUUID()+filetype;
    String classespath = ClassUtils.getDefaultClassLoader().getResource("").getPath();
    String decodeClassespath = null;
    try {
      decodeClassespath = URLDecoder.decode(classespath,"utf-8");
    } catch (UnsupportedEncodingException e) {
      e.printStackTrace();
    }
    /**/
    File file1 = new File(decodeClassespath+"/upload/excel/" + newfile);
    if(!file1.exists()){
      file1.mkdirs();
    }
    try {
      file.transferTo(file1);
    } catch (IOException e) {
      e.printStackTrace();
    }
    positionMapper.deleteAllPosition();
    this.loadPosition(file1);
  }

  private boolean loadPosition(File file) {
    String filetype = file.getName().split("\\.")[1];
    Workbook workbook = null;
    if (filetype.equals("xls")) {
      try {
        workbook = new HSSFWorkbook(new FileInputStream(file));
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
    if (filetype.equals("xlsx")) {
      try {
        workbook = new XSSFWorkbook(new FileInputStream(file));
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
    if (workbook == null) {
      return false;
    }
    Sheet sheet = workbook.getSheetAt(0);
    for (int i = 1; i <= sheet.getLastRowNum(); i++) {
      Row row = sheet.getRow(i);
      String account_name = row.getCell(0).getStringCellValue();
      String bond_code = row.getCell(1).getStringCellValue();
      if (bond_code.isEmpty()) {
        continue;
      }
      String bond_name = row.getCell(2).getStringCellValue();
      double value = row.getCell(3).getNumericCellValue();
      String opponent = row.getCell(5).getStringCellValue();
      String market = row.getCell(6).getStringCellValue();
      String initial_settlement_day = row.getCell(7).getStringCellValue();
      String expiry_settlement_day = row.getCell(8).getStringCellValue();
      double interest_rate = row.getCell(9).getNumericCellValue();
      String pledge_type = row.getCell(10).getStringCellValue();
      String status = row.getCell(11).getStringCellValue();
      Account account = accountMapper.getAccountByName(account_name);
      if (account == null) {
        account = new Account();
        account.name = account_name;
        accountMapper.create(account);
      }
      Position position = new Position();
      position.account = account;
      position.code = bond_code;
      position.name = bond_name;
      position.value = value;
      position.opponent = opponent;
      position.market = market;
      SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
      try {
        if (initial_settlement_day.isEmpty()) {
          position.initial_settlement_day = null;
        } else {
          position.initial_settlement_day = sdf.parse(initial_settlement_day);
        }
        if (expiry_settlement_day.isEmpty()) {
          position.expiry_settlement_day = null;
        } else {
          position.expiry_settlement_day = sdf.parse(expiry_settlement_day);
        }
      } catch (ParseException e) {
        e.printStackTrace();
      }
      position.interest_rate = interest_rate;
      position.pledge_type = pledge_type;
      position.status = status;

      positionMapper.createPosition(position);
//      BondInfo bondInfo = bondInfoMapper.getBondInfoByCode(bond_code);
//      if (bondInfo == null) {
//        bondInfo = new BondInfo();
//        bondInfo.bond_code = bond_code;
//        bondInfo.bond_trading_market = "";
//        bondInfo.short_name = bond_name;
//        bondInfoMapper.addBondInfo(bondInfo);
//      }


    }


    return true;
  }

}
