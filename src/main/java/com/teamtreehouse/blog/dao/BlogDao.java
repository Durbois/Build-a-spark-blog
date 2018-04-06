package com.teamtreehouse.blog.dao;

import com.teamtreehouse.blog.model.BlogEntry;

import java.util.List;

public interface BlogDao {
    boolean addEntry(BlogEntry blogEntry);
    void editEntry (BlogEntry blogEntry, String title, String entry);
    boolean removeEntry(BlogEntry blogEntry);
    List<BlogEntry> findAllEntries();
    BlogEntry findEntryBySlug(String slug);
    BlogEntry findEntryByTitle(String title);
}
