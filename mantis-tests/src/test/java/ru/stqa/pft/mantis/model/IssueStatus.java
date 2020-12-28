package ru.stqa.pft.mantis.model;

public class IssueStatus {
    private int id;
    private String status;

    public int getId() {
        return id;
    }

    public IssueStatus withId(int id) {
        this.id = id;
        return this;
    }

    public String getStatus() {
        return status;
    }

    public IssueStatus withStatus(String status) {
        this.status = status;
        return this;
    }

}
