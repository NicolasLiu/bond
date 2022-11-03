package com.lkq.bond.mapper;

import com.lkq.bond.entity.BondInfo;
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
public interface BondInfoMapper {
  @Select("select * from bond_info")
  public List<BondInfo> getAllBondInfo();



  @Select("select * from bond_info where bond_code like '${code}%'")
  public BondInfo getBondInfoByCode(String code);

  @Insert("insert into bond_info(bond_code,bond_trading_market,short_name) values(#{bond_code},#{bond_trading_market},#{short_name})")
  public int addBondInfo(BondInfo bondInfo);


}
