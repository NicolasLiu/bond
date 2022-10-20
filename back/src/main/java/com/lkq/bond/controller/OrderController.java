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
import java.util.ArrayList;
import java.util.List;
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
    //查找所有待排券的融资申请，紧急优先
    List<Apply> todo = applyMapper.getApplyOrderByEmergency("待排券");
    List<Position> all_positions = positionMapper.getAllPosition();
    //依次分配
    List<Order> rtn = new ArrayList<>();
    for (Apply apply : todo) {
      rtn.addAll(createOrderForApply(apply, all_positions));
    }
    return rtn;
  }

  private List<Order> createOrderForApply(Apply apply, List<Position> all_positions) {
    List<Order> rtn = new ArrayList<>();
    double sum_value = apply.value;
    for (Position position : all_positions) {
      if (sum_value <= 0) {
        break;
      }
      if (position.value <= 0) {
        continue;
      }
      Order o = new Order();
      o.apply = apply.id;
      o.position = position.id;
      if (position.value >= sum_value) {
        o.value = sum_value;
      } else {
        o.value = position.value;
      }
      position.value = position.value - o.value;
      sum_value = sum_value - o.value;
      rtn.add(o);
    }
    return rtn;
  }

}
