package ru.stqa.pft.rest.model;

import java.util.Objects;

public class Issue {
    private int id;
    private String subject;
    private String description;
    private String status;

    public Issue withId(int id) {
        this.id = id;
        return this;
    }

    public Issue withSubject(String subject) {
        this.subject = subject;
        return this;
    }

    public Issue withDescription(String description) {
        this.description = description;
        return this;
    }

    public Issue withStatus(String state) {
        this.status = state;
        return this;

    }

    public int getId() {
        return id;
    }

    public String getSubject() {
        return subject;
    }

    public String getDescription() {
        return description;
    }

    public String getStatus() {
        return status;
    }

    @Override
    public String toString() {
        return "Issue{" +
                "id=" + id +
                ", subject='" + subject + '\'' +
                ", description='" + description + '\'' +
                ", state='" + status + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Issue issue = (Issue) o;
        return id == issue.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}