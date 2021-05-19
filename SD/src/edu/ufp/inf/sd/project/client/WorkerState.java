package edu.ufp.inf.sd.project.client;

import java.io.Serializable;

public class WorkerState implements Serializable {
    private String msg;
    private String id;
    private Boolean isAvailable;

    public WorkerState(String msg, String id, Boolean isAvailable) {
        this.msg = msg;
        this.id = id;
        this.isAvailable = isAvailable;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Boolean getAvailable() {
        return isAvailable;
    }

    public void setAvailable(Boolean available) {
        isAvailable = available;
    }
}
