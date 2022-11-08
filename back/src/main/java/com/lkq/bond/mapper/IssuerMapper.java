package com.lkq.bond.mapper;

import com.lkq.bond.entity.Issuer;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface IssuerMapper {
  @Select("select * from issuer")
  public List<Issuer> getAllIssuer();


}
