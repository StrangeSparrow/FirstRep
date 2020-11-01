package com.mycorp.app;


import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.io.UnsupportedEncodingException;
import java.util.List;

@Path("/")
public class NewsController {
    @GET
    @Produces(MediaType.TEXT_HTML)
    public String hello(){
        return "<B>Hello!" +
                " This simple test</B>";
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.TEXT_HTML)
    public String getNews(@PathParam("id") int id) throws UnsupportedEncodingException {
        NewsServiceImpl newsService = new NewsServiceImpl();
        List<News> listNews = newsService.fetchNews(Config.getInstance().getNewsPath());

        if (listNews.size() < id)
            throw new IllegalArgumentException();

        News news = listNews.get(id);

        String result = Constants.HTML_UP + "<h2>" + news.getHead() + "</h2>\n"
                + "<i>" + news.getBriefly() + "</i><p>\n"
                + news.getFull() + Constants.HTML_DOWN;

        return result;
    }
}
