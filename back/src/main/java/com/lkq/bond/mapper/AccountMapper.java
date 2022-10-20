package com.lkq.bond.mapper;

import com.lkq.bond.entity.Account;
import com.lkq.bond.entity.Order;
import com.lkq.bond.entity.Position;
import java.util.List;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.One;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface AccountMapper {

  @Select("select * from account where id=#{id}")
  public Account getAccountById(int id);

  @Select("select * from account where name=#{name}")
  public Account getAccountByName(String name);

  @Insert("insert into account(name, internal_account_name,internal_account_code,external_account_name, external_account_code) " +
      "values(#{name},#{internal_account_name},#{internal_account_code}, #{external_account_name}, #{external_account_code})")
  @Options(useGeneratedKeys=true, keyProperty="id")
  public int create(Account account);

}
