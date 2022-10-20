package com.lkq.bond.mapper;

import com.lkq.bond.entity.Account;
import com.lkq.bond.entity.Apply;
import com.lkq.bond.entity.Order;
import java.util.List;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.One;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface ApplyMapper {
  @Select("select * from apply")
  @Results({
      @Result(property = "account", column = "account", one = @One(select = "com.lkq.bond.mapper.AccountMapper.getAccountById"))
  })
  public List<Apply> getAllApply();



  @Select("select * from apply where id=#{id}")
  @Results({
      @Result(property = "account", column = "account", one = @One(select = "com.lkq.bond.mapper.AccountMapper.getAccountById"))
  })
  public Apply getApplyById(int id);

  @Update("update apply set status=#{status},account=#{account.id}," +
      "opponent=#{opponent},temporary_opponent=#{temporary_opponent}," +
      "trader=#{trader},financing_type=#{financing_type}," +
      "financing_rate=#{financing_rate},clearing_speed=#{clearing_speed}," +
      "initial_settlement_method=#{initial_settlement_method}," +
      "expiry_settlement_method=#{expiry_settlement_method},value=#{value}, " +
      "duration=#{duration},recording_day=#{recording_day},trading_day=#{trading_day}," +
      "maturity_day=#{maturity_day},aim=#{aim},mark=#{mark}, emergency=#{emergency} where id=#{id}")
  public int updateApply(Apply apply);

   @Delete("delete from apply where id = #{id}")
   public int deleteApplyById(int id);

   @Insert("insert into apply(status,account,opponent,temporary_opponent,trader,financing_type," +
       "financing_rate,clearing_speed,initial_settlement_method,expiry_settlement_method,value," +
       "duration,recording_day,trading_day,maturity_day,aim,mark,emergency) values(#{status}, " +
       "#{account.id},#{opponent},#{temporary_opponent},#{trader},#{financing_type},#{financing_rate}," +
       "#{clearing_speed},#{initial_settlement_method},#{expiry_settlement_method},#{value},#{duration}," +
       "#{recording_day, jdbcType=DATE},#{trading_day, jdbcType=DATE},#{maturity_day, jdbcType=DATE}," +
       "#{aim},#{mark},#{emergency})")
   public int addApply(Apply apply);

  @Select("select * from apply where status=#{status} order by emergency DESC")
  @Results({
      @Result(property = "account", column = "account", one = @One(select = "com.lkq.bond.mapper.AccountMapper.getAccountById"))
  })
  public List<Apply> getApplyOrderByEmergency(String status);
}
