package edu.ufp.inf.sd.project.server;

import java.io.Serializable;

public class Task implements Serializable {

    private String path;

    public Task(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

}
