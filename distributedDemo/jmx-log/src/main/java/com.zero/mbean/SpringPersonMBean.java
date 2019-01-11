package ai.yunxi.demo.mbean;

import org.springframework.jmx.export.annotation.ManagedAttribute;
import org.springframework.jmx.export.annotation.ManagedOperationParameter;
import org.springframework.jmx.export.annotation.ManagedOperationParameters;
import org.springframework.jmx.export.annotation.ManagedResource;

@ManagedResource(objectName = "ai.yunxi.jmxBean:name=SpringPersonMBean", description = "Spring Person MBean")
public class SpringPersonMBean {

	private String name;
	private int age;
	private String address;

	public String getName() {
		return name;
	}

	@ManagedAttribute
	@ManagedOperationParameters({ @ManagedOperationParameter(name = "name", description = "name") })
	public void setName(String name) {
		System.out.println("=============== Name: " + name);
		this.name = name;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

}
