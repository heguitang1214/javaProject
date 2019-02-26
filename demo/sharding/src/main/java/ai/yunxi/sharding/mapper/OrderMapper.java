package ai.yunxi.sharding.mapper;

import ai.yunxi.sharding.model.Order;
import ai.yunxi.sharding.model.User;

import java.util.List;

public interface OrderMapper {
    public int save(Order order);

    public List<Order> selectHint();
}
