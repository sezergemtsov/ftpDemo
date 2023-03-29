package com.example.ftptest.controller;

import com.example.ftptest.model.RespondEntity;
import com.example.ftptest.service.Service;
import org.springframework.integration.ftp.session.DefaultFtpSessionFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
public class Controller {

    private final Service service;
    private final DefaultFtpSessionFactory sessionFactory;

    public Controller(Service service, DefaultFtpSessionFactory sessionFactory) {
        this.service = service;
        this.sessionFactory = sessionFactory;
    }

    @GetMapping("/photos")
    public List<RespondEntity> photos() throws IOException {
        return service.searchFile(sessionFactory);
    }

}
