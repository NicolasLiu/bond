package com.lkq.bond.mapper;

import com.lkq.bond.entity.Account;
import com.lkq.bond.entity.Apply;
import com.lkq.bond.entity.Position;
import java.util.Date;
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
public interface PositionMapper {
  @Select("select * from position")
  @Results({
      @Result(property = "account", column = "account", one = @One(select = "com.lkq.bond.mapper.AccountMapper.getAccountById")),
      @Result(property = "bond_info", column = "code", one = @One(select = "com.lkq.bond.mapper.BondInfoMapper.getBondInfoByCode"))
  })
  public List<Position> getAllPosition();

  @Update("update position set value=#{value} where id=#{id}")
  public int updatePositionValue(int id, double value);

  @Select("select * from position where id=#{id}")
  @Results({
      @Result(property = "account", column = "account", one = @One(select = "com.lkq.bond.mapper.AccountMapper.getAccountById")),
      @Result(property = "bond_info", column = "code", one = @One(select = "com.lkq.bond.mapper.BondInfoMapper.getBondInfoByCode"))
  })
  public Position getPositionById(int id);

  @Delete("delete from position")
  public int deleteAllPosition();

  @Insert("insert into `position`(code,name,account,value,opponent,market,initial_settlement_day," +
      "expiry_settlement_day,interest_rate,pledge_type,status) values(#{code},#{name},#{account.id}," +
      "#{value},#{opponent},#{market},#{initial_settlement_day},#{expiry_settlement_day}," +
      "#{interest_rate},#{pledge_type},#{status})")
  @Options(useGeneratedKeys=true, keyProperty="id")
  public int createPosition(Position position);


}
