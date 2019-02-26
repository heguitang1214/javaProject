package ai.yunxi.sharding.mapper;

import ai.yunxi.sharding.model.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

public interface UserMapper {
    public int save(User user);
}
