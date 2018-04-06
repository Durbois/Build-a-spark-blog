package com.teamtreehouse.blog.model;

import com.github.slugify.Slugify;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class BlogEntry {
    private String slug;
    private String title;
    private Date date;
    private String entry;
    private List<String> tags;
    private List<Comment> comments;

    public BlogEntry(String title, Date date, String entry) {
        this.title = title;
        this.date = date;
        this.entry = entry;
        comments = new ArrayList<>();
        tags = new ArrayList<>();
        try {
            Slugify slugify = new Slugify();
            slug = slugify.slugify(title);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public BlogEntry() {
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public boolean addTag(String tag){
        return tags.add(tag);
    }

    public String getEntry() {
        return entry;
    }

    public String getSlug() {
        return slug;
    }

    public String getTitle() {
        return title;
    }

    public Date getDate() {
        return date;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public boolean addComment(Comment comment) {
        // Store these comments!
        return comments.add(comment);
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setEntry(String entry) {
        this.entry = entry;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    @Override
    public String toString() {
        return "BlogEntry{" +
                "slug='" + slug + '\'' +
                ", title='" + title + '\'' +
                ", date=" + date +
                //", comments=" + comments +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BlogEntry)) return false;

        BlogEntry blogEntry = (BlogEntry) o;

        if (getSlug() != null ? !getSlug().equals(blogEntry.getSlug()) : blogEntry.getSlug() != null) return false;
        if (!getTitle().equals(blogEntry.getTitle())) return false;
        if (!getDate().equals(blogEntry.getDate())) return false;
        return getComments() != null ? getComments().equals(blogEntry.getComments()) : blogEntry.getComments() == null;
    }

    @Override
    public int hashCode() {
        int result = getSlug() != null ? getSlug().hashCode() : 0;
        result = 31 * result + getTitle().hashCode();
        result = 31 * result + getDate().hashCode();
        result = 31 * result + (getComments() != null ? getComments().hashCode() : 0);
        return result;
    }
}
