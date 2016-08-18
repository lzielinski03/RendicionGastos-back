package ar.com.besysoft.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lzielinski on 12/07/2016.
 */
public class Process implements Serializable{

    public String id;
    public String name;
    public List<Activity> activities = new ArrayList<>();

    public Process(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public Process(String id, String name, Activity activity) {
        this.id = id;
        this.name = name;
        this.activities.add(activity);
    }
}
