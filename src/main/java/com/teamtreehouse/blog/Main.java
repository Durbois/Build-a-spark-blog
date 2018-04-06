package com.teamtreehouse.blog;

import com.teamtreehouse.blog.config.ThymeleafTemplateEngine;
import com.teamtreehouse.blog.dao.BlogDao;
import com.teamtreehouse.blog.dao.BlogDaoImpl;
import com.teamtreehouse.blog.model.BlogEntry;
import com.teamtreehouse.blog.model.Comment;
import org.thymeleaf.context.WebContext;
import spark.ModelAndView;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static spark.Spark.*;

public class Main {

    public static final String PASSWORD_MSG = "Please provide admin as username";

    public static void main(String[] args) {

        staticFileLocation("/static");
        BlogDao blogDao = new BlogDaoImpl();

        Map map = new HashMap();

        get("/edit/:slug", (rq, rs) -> {
            BlogEntry blogEntry = blogDao.findEntryBySlug(rq.params(":slug"));

            map.put("slug", blogEntry.getSlug());
            map.put("title", blogEntry.getTitle());
            map.put("entry", blogEntry.getEntry());

            return new ModelAndView(map, "edit");
        }, new ThymeleafTemplateEngine());

        post("/edit/:slug", (rq, rs) -> {

            BlogEntry blogEntry = blogDao.findEntryBySlug(rq.params(":slug"));

            String title = rq.queryParams("title");
            String entry = rq.queryParams("entry");

            blogDao.editEntry(blogEntry, title, entry);

            rs.redirect("/");
            return null;
        });

        before("/edit/:slug", (req, res) -> {
            String slug = req.params(":slug");
            req.session().attribute("action", String.format("/edit/%s", slug));

            if(req.cookie("admin") == null){
                req.session().attribute("flash", PASSWORD_MSG );
                res.redirect("/password");
                halt();
            }
        });

        get("/detail/:slug", (rq, rs) ->{
            blogDao.findAllEntries().stream().forEach(System.out::println);

            BlogEntry blogEntry = blogDao.findEntryBySlug(rq.params(":slug"));
            map.put("entry", blogEntry);

            return new ModelAndView(map, "detail");
        }, new ThymeleafTemplateEngine());

        post("/detail/:slug", (req, res) -> {

            BlogEntry blogEntry = blogDao.findEntryBySlug(req.params(":slug"));

            String name = req.queryParams("name");
            String commentEntry = req.queryParams("comment");
            Comment comment = new Comment(name, commentEntry, new Date());
            blogEntry.addComment(comment);

            res.redirect("/detail/"+req.params(":slug"));
            return null;
        });

        get("/", (rq, rs) -> {

            map.put("entries", blogDao.findAllEntries());
            return new ModelAndView(map, "index");
        } , new ThymeleafTemplateEngine());

        post("/", (req, res) -> {

            String title = req.queryParams("title");
            String entry = req.queryParams("entry");

            BlogEntry blogEntry = blogDao.findEntryByTitle(title);

            if(blogEntry != null){
                res.redirect("/new");
            }else{
                blogEntry = new BlogEntry(title, new Date(), entry);

                int tagsNumber = req.queryParamsValues("tags").length;

                if(tagsNumber != 0){
                    for(String tag: req.queryParamsValues("tags")) {
                        blogEntry.addTag(tag);
                    }
                }

                BlogEntry existedBlogEntry = blogDao.findEntryBySlug(blogEntry.getSlug());

                if(existedBlogEntry != null){
                    res.redirect("/new");
                }else{
                    blogDao.addEntry(blogEntry);
                    res.redirect("/");
                }
            }
            return null;
        });

        before("/new", (req, res) -> {
            req.session().attribute("action", "/new");
            if(req.cookie("admin") == null){
                req.session().attribute("flash", PASSWORD_MSG);
                res.redirect("/password");
                halt();
            }
        });

        get("/new", (req, res) -> new ModelAndView(map, "new"), new ThymeleafTemplateEngine());

        post("/redirect", (req, res) -> {
            String username = req.queryParams("user_name");
            if(username.equals("admin")) {
                res.cookie("admin", "admin");
                res.redirect(req.session().attribute("action"));
            }else{
                res.redirect("/password");
            }
            return null;
        });

        post("/delete/:slug", (req, res) -> {
            BlogEntry blogEntry = blogDao.findEntryBySlug(req.params(":slug"));
            blogDao.removeEntry(blogEntry);
            res.redirect("/");
            return null;
        });

        get("/password", (req, res) -> {
            map.put("flash", req.session().attribute("flash"));
           return new ModelAndView(map, "password");
        }, new ThymeleafTemplateEngine());
    }
}
