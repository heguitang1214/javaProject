package utilsTest;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import entry.Job;
import entry.Role;
import entry.User;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * JSON工具上测试
 */
public class JsonTest {

    @Test
    public void test(){
        ObjectMapper objectMapper = new ObjectMapper();
        String param = "[{\"name\":\"张三\",\"password\":\"123456\", \"roles\":[{\"name\":\"角色1\",\"jobs\":[{\"name\":\"职位1\"}," +
                "{\"name\":\"职位11\"}]},{\"name\":\"角色2\",\"jobs\":[{\"name\":\"职位1\"},{\"name\":\"职位11\"}]}]},{\"name\":\"李四\"," +
                "\"password\":\"123456\", \"roles\":[{\"name\":\"角色3\",\"jobs\":[{\"name\":\"职位3\"},{\"name\":\"职位4\"}]},{\"name\":\"角色4\"}]}]";
        List<User> users = new ArrayList<>();
        try {
            users = objectMapper.readValue(param, new TypeReference<List<User>>() {

            });
        } catch (IOException e) {
            e.printStackTrace();
        }
        for (User user : users){
            System.out.println("=============================================================");
            for (Role role : user.getRoles()){
                for (Job job : role.getJobs()){
                    System.out.println("姓名:" + user.getName() + ",角色:" + role.getName() + ",职位:" + job.getName());
                }
            }
            System.out.println("=============================================================");
        }
    }





}
