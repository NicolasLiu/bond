package com.lkq.bond.controller;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.lkq.bond.entity.Apply;
import com.lkq.bond.entity.Order;
import com.lkq.bond.entity.Position;
import com.lkq.bond.mapper.ApplyMapper;
import com.lkq.bond.mapper.OrderMapper;
import com.lkq.bond.mapper.PositionMapper;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
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
@RequestMapping("/order")
public class OrderController {

  @Autowired
  private PositionMapper positionMapper;
  @Autowired
  private OrderMapper orderMapper;
  @Autowired
  private ApplyMapper applyMapper;

  @PostMapping("/create")
  public int createOrder(@RequestBody JsonObject object) {
    JsonObject apply = object.get("apply").getAsJsonObject();
    JsonArray allocation = object.get("allocation").getAsJsonArray();
    int apply_id = apply.get("id").getAsInt();
    for (JsonElement element : allocation) {
      JsonObject position = ((JsonObject)element).get("position").getAsJsonObject();
      int position_id = position.get("id").getAsInt();
      double value = ((JsonObject)element).get("value").getAsDouble();
      Order order = new Order();
      order.apply = apply_id;
      order.position = position_id;
      order.value = value;
      orderMapper.create(order);
      Position position_object = positionMapper.getPositionById(position_id);
      positionMapper.updatePositionValue(position_id, position_object.value - order.value);
    }
    Apply apply_object = applyMapper.getApplyById(apply_id);
    apply_object.status = "已排券";
    applyMapper.updateApply(apply_object);

    return 1;
  }

  @PostMapping("/cancel")
  public int cancelOrder(@RequestBody JsonObject object) {
    JsonObject apply = object.get("apply").getAsJsonObject();
    int apply_id = apply.get("id").getAsInt();
    Apply apply_object = applyMapper.getApplyById(apply_id);
    apply_object.status = "待排券";
    applyMapper.updateApply(apply_object);
    List<Order> orders = orderMapper.getOrdersByApplyId(apply_id);
    for (Order order : orders) {
      double value = order.value;
      Position position_object = positionMapper.getPositionById(order.position);
      if (position_object != null) {
        positionMapper.updatePositionValue(position_object.id, position_object.value + value);
        orderMapper.deleteOrderById(order.id);
      }
    }
    return 1;
  }

  @GetMapping("/get")
  public List<Order> getOrder(@RequestParam int apply) {
    List<Order> orders = orderMapper.getOrdersByApplyId(apply);
    return orders;
  }

  @GetMapping("/auto")
  public List<Order> autoCreateOrder() {
    //查找所有待排券的融资申请,紧急的
    List<Apply> todo1 = applyMapper.getApplyOrderEmergency("待排券");
    //根据交易对手的优先级排序
    todo1.sort((o1, o2) -> o2.opponent.priority - o1.opponent.priority);
    //查找所有待排券的融资申请,不紧急的
    List<Apply> todo2 = applyMapper.getApplyOrderNotEmergency("待排券");
    //根据交易对手的优先级排序
    todo2.sort((o1, o2) -> o2.opponent.priority - o1.opponent.priority);
    todo1.addAll(todo2);
    List<Position> all_positions = positionMapper.getAllPosition();
    //依次分配
    List<Order> rtn = new ArrayList<>();
    for (Apply apply : todo1) {
      rtn.addAll(createOrderForApply(apply, all_positions));
    }
    return rtn;
  }

  private List<Order> createOrderForApply(Apply apply, List<Position> all_positions) {
    List<Order> rtn = new ArrayList<>();
    double sum_value = apply.value;
    double discount_rate = apply.discount_rate;
    List<String> institution_credit_limit = apply.opponent.institution_credit_limit == null ? new ArrayList<>() : Arrays.asList(apply.opponent.institution_credit_limit.split(","));
    List<String> bond_credit_limit = apply.opponent.bond_credit_limit == null ? new ArrayList<>() : Arrays.asList(apply.opponent.bond_credit_limit.split(","));
    List<String> issuer_exclude = apply.opponent.issuer_exclude == null ? new ArrayList<>() : Arrays.asList(apply.opponent.issuer_exclude.split(","));
    List<String> issuer_prefer = apply.opponent.issuer_prefer == null ? new ArrayList<>() : Arrays.asList(apply.opponent.issuer_prefer.split(","));
    for (Position position : all_positions) {
      //跳过不符合交易对手的债券评级限制的持仓
      if (!bond_credit_limit.contains(position.bond_info.bond_credit) || !institution_credit_limit.contains(position.bond_info.institution_credit)) {
        continue;
      }
      //先试图用交易对手的债券偏好进行排券
      if (!issuer_prefer.contains(position.bond_info.issuer_name)) {
        continue;
      }
      if (sum_value <= 0) {
        break;
      }
      if (position.value <= 0) {
        continue;
      }
      Order o = new Order();
      o.apply = apply.id;
      o.position = position.id;
      o.discount_rate = discount_rate;
      if (position.value*discount_rate >= sum_value) {
        o.value = new BigDecimal(sum_value / discount_rate).setScale(0, BigDecimal.ROUND_UP).doubleValue();
      } else {
        o.value = position.value;
      }
      o.discount_value = new BigDecimal(o.value * discount_rate).setScale(0, BigDecimal.ROUND_DOWN).doubleValue();
      position.value = position.value - o.value;
      sum_value = sum_value - o.discount_value;
      rtn.add(o);
    }
    for (Position position : all_positions) {
      //跳过不符合交易对手的债券评级限制的持仓
      if (!bond_credit_limit.contains(position.bond_info.bond_credit) || !institution_credit_limit.contains(position.bond_info.institution_credit)) {
        continue;
      }
      //跳过交易对手的债券黑名单
      if (issuer_exclude.contains(position.bond_info.issuer_name)) {
        continue;
      }
      if (sum_value <= 0) {
        break;
      }
      if (position.value <= 0) {
        continue;
      }
      Order o = new Order();
      o.apply = apply.id;
      o.position = position.id;
      o.discount_rate = discount_rate;
      if (position.value*discount_rate >= sum_value) {
        o.value = new BigDecimal(sum_value / discount_rate).setScale(0, BigDecimal.ROUND_UP).doubleValue();
      } else {
        o.value = position.value;
      }
      o.discount_value = new BigDecimal(o.value * discount_rate).setScale(0, BigDecimal.ROUND_DOWN).doubleValue();
      position.value = position.value - o.value;
      sum_value = sum_value - o.discount_value;
      rtn.add(o);
    }
    return rtn;
  }

}
