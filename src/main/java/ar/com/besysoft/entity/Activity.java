package ar.com.besysoft.entity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lzielinski on 05/08/2016.
 */
public class Activity {

    public String name;
    public List<Instance> instances = new ArrayList<>();

    public Activity(String name) {
        this.name = name;
    }

    public Activity(String name, Instance instance) {
        this.name = name;
        this.instances.add(instance);
    }
}
