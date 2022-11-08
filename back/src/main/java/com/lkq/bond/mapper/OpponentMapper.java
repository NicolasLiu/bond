package com.lkq.bond.mapper;

import com.lkq.bond.entity.Apply;
import com.lkq.bond.entity.Opponent;
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
public interface OpponentMapper {
  @Select("select * from opponent")
  public List<Opponent> getAllOpponent();



  @Select("select * from opponent where id=#{id}")
  public Opponent getOpponentById(int id);

  @Update("update opponent set name=#{name},priority=#{priority}," +
      "bond_credit_limit=#{bond_credit_limit},institution_credit_limit=#{institution_credit_limit}," +
      "issuer_prefer=#{issuer_prefer},issuer_exclude=#{issuer_exclude} where id=#{id}")
  public int updateOpponent(Opponent opponent);

   @Delete("delete from opponent where id = #{id}")
   public int deleteOpponentById(int id);

   @Insert("insert into opponent(name,priority,bond_credit_limit,institution_credit_limit,issuer_prefer,issuer_exclude) values(#{name}, " +
       "#{priority},#{bond_credit_limit},#{institution_credit_limit},#{issuer_prefer},#{issuer_exclude})")
   public int addOpponent(Opponent opponent);
}
