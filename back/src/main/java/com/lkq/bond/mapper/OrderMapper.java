package com.lkq.bond.mapper;


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

@Mapper
public interface OrderMapper {

   @Insert("insert into orders(apply,position,value) values(#{apply},#{position},#{value})")
   public int create(Order order);

   @Select("select * from orders where apply=#{id}")
   public List<Order> getOrdersByApplyId(int id);


   @Delete("delete from orders where id = #{id}")
   public int deleteOrderById(int id);
}
