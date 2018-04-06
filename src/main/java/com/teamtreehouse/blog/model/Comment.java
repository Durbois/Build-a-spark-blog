package com.teamtreehouse.blog.model;

import java.util.Date;

public class Comment {
    private String name;
    private String comment;
    private Date date;

    public Comment(String name, String comment, Date date) {
        this.name = name;
        this.comment = comment;
        this.date = date;
    }

    public Comment() {
    }

    public Date getDate() {
        return date;
    }

    public String getName() {
        return name;
    }

    public String getComment() {
        return comment;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "name='" + name + '\'' +
                ", comment='" + comment + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Comment)) return false;

        Comment comment1 = (Comment) o;

        if (!getName().equals(comment1.getName())) return false;
        return getComment().equals(comment1.getComment());
    }

    @Override
    public int hashCode() {
        int result = getName().hashCode();
        result = 31 * result + getComment().hashCode();
        return result;
    }
}
