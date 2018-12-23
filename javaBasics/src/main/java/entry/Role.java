package entry;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author he_guitang
 * @version [1.0 , 2018/8/24]
 */
public class Role implements Serializable{

    private String name;
    private List<Job> jobs = new ArrayList<>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Job> getJobs() {
        return jobs;
    }

    public void setJobs(List<Job> jobs) {
        this.jobs = jobs;
    }
}
