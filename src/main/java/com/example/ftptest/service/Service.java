package com.example.ftptest.service;

import com.example.ftptest.model.RespondEntity;
import org.apache.commons.net.ftp.FTPFile;
import org.springframework.context.annotation.Bean;
import org.springframework.integration.ftp.session.DefaultFtpSessionFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@org.springframework.stereotype.Service
public class Service {

    private List<String> ftpPath = null;

    public List<RespondEntity> searchFile(DefaultFtpSessionFactory sessionFactory, String searchingDir) throws IOException {

        ftpPath = new ArrayList<>();
        findDir("", sessionFactory, searchingDir);

        List<RespondEntity> response = new ArrayList<>();

        for (String path : ftpPath) {
            FTPFile[] files = sessionFactory.getSession().getClientInstance().mlistDir(path);
            if (files.length != 0) {
                for (FTPFile f : files) {
                    if (f.isFile() & f.getName().startsWith("GRP327_")) {
                        response.add(new RespondEntity(f.getName(), f.getTimestamp().getTime().toString(), f.getSize(), path));
                    }
                }
            }
        }
        return response;
    }

    private void findDir(String path, DefaultFtpSessionFactory sessionFactory, String searchingDir) throws IOException {
        if (path.contains(searchingDir)) {
            ftpPath.add(path);
        } else {
            FTPFile[] files = sessionFactory.getSession().getClientInstance().mlistDir(path);
            for (FTPFile f : files) {
                if (f.isDirectory() & !f.getName().startsWith(".")) {
                    String newPath = path + "/" + f.getName();
                    findDir(newPath, sessionFactory, searchingDir);
                }
            }
        }
    }


    @Bean
    public DefaultFtpSessionFactory getSessionFactory() {
        DefaultFtpSessionFactory sessionFactory = new DefaultFtpSessionFactory();
        sessionFactory.setUsername("epiz_33891104");
        sessionFactory.setPassword("CLc195rPV8h3cv");
        sessionFactory.setPort(21);
        sessionFactory.setHost("185.27.134.11");
        sessionFactory.setControlEncoding("UTF8");
        return sessionFactory;
    }

}
