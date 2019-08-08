package com.michaelyu.playlisttracker;

import com.google.api.Http;
import com.michaelyu.playlisttracker.servlets.GetPlaylistServlet;
import com.michaelyu.playlisttracker.servlets.WritePlaylistServlet;
import com.michaelyu.playlisttracker.servlets.RegisterUserServlet;
import com.michaelyu.playlisttracker.servlets.WriteTrackServlet;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.http.HttpServlet;

@Configuration
public class WebConfig {
    @Bean
    public ServletRegistrationBean<HttpServlet> registerUser(){
        ServletRegistrationBean<HttpServlet> bean = new ServletRegistrationBean<>();
        bean.setServlet(new RegisterUserServlet());
        bean.addUrlMappings("/api/register_user");
        return bean;
    }

    @Bean
    public ServletRegistrationBean<HttpServlet> writePlaylist(){
        ServletRegistrationBean<HttpServlet> bean = new ServletRegistrationBean<>();
        bean.setServlet(new WritePlaylistServlet());
        bean.addUrlMappings("/api/write_playlist");
        return bean;
    }

    @Bean
    public ServletRegistrationBean<HttpServlet> writeTrack(){
        ServletRegistrationBean<HttpServlet> bean = new ServletRegistrationBean<>();
        bean.setServlet(new WriteTrackServlet());
        bean.addUrlMappings("/api/write_track");
        return bean;
    }

    @Bean
    public ServletRegistrationBean<HttpServlet> getPlaylist(){
        ServletRegistrationBean<HttpServlet> bean = new ServletRegistrationBean<>();
        bean.setServlet(new GetPlaylistServlet());
        bean.addUrlMappings("/api/get_playlist");
        return bean;
    }
}
