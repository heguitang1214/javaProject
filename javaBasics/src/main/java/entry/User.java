package entry;

import com.fasterxml.jackson.annotation.JsonInclude;
import utils.excel.annotation.ExcelField;
import java.util.ArrayList;
import java.util.List;

public class User {

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private String name;
    private Integer age;
    private String password;
    private List<Role> roles = new ArrayList<>();

    @ExcelField(title="姓名**111", align=2, sort=1)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @ExcelField(title="年龄**222", align=2, sort=2)
    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", password='" + password + '\'' +
                ", roles=" + roles +
                '}';
    }
}
