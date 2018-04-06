package com.teamtreehouse.blog.dao;

import com.teamtreehouse.blog.model.BlogEntry;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class BlogDaoImpl implements BlogDao {

    List<BlogEntry> blogEntries;

    public BlogDaoImpl() {
        this.blogEntries = new ArrayList<>(initializeEntries());
    }

    @Override
    public boolean addEntry(BlogEntry blogEntry) {
        return blogEntries.add(blogEntry);
    }
    @Override
    public boolean removeEntry(BlogEntry blogEntry) {
        return blogEntries.remove(blogEntry);
    }

    private List<BlogEntry> initializeEntries () {
        blogEntries = new ArrayList<>();
        BlogEntry blogEntry1 = new BlogEntry("First Entry", new Date(), "Initial Content");
        BlogEntry blogEntry2 = new BlogEntry("Second Entry", new Date(), "Initial Content");
        BlogEntry blogEntry3 = new BlogEntry("Third Entry", new Date(), "Initial Content");
        blogEntries.add(blogEntry1);
        blogEntries.add(blogEntry2);
        blogEntries.add(blogEntry3);
        return blogEntries;
    }

    @Override
    public void editEntry (BlogEntry blogEntry, String title, String entry) {
        blogEntry.setTitle(title);
        blogEntry.setEntry(entry);
        blogEntry.setDate(new Date());
    }


    @Override
    public List<BlogEntry> findAllEntries() {
        return new ArrayList<>(blogEntries);
    }

    @Override
    public BlogEntry findEntryBySlug(String slug) {
       return blogEntries.stream()
                   .filter(bg -> bg.getSlug().equalsIgnoreCase(slug))
                   .findFirst()
                   .orElse(null);
                    //.orElseThrow(NotFoundException::new);
    }

    @Override
    public BlogEntry findEntryByTitle(String title) {
        return blogEntries.stream()
                .filter(bg -> bg.getTitle().equalsIgnoreCase(title))
                .findFirst()
                .orElse(null);
        //.orElseThrow(NotFoundException::new);
    }

}
