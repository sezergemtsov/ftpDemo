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

    private String ftpPath = null;

    public List<RespondEntity> searchFile(DefaultFtpSessionFactory sessionFactory) throws IOException {

        findFolder("", sessionFactory);
        List<RespondEntity> respond = new ArrayList<>();
        if (ftpPath != null) {
            FTPFile[] str = sessionFactory.getSession().getClientInstance().mlistDir(ftpPath);
            if (str.length != 0) {
                for (FTPFile ftpFile : str) {
                    if (ftpFile.getName().startsWith("GRP327_")) {
                        respond.add(new RespondEntity(ftpFile.getName(), ftpFile.getTimestamp().getTime().toString(), ftpFile.getSize(), ftpPath));
                    }
                }
            }
        }
        ftpPath = null;
        return respond;
    }

    private void findFolder(String folderName,
                            DefaultFtpSessionFactory sessionFactory) throws IOException {
        if (folderName.contains("Ñ\u0084Ð¾Ñ\u0082Ð¾Ð³Ñ\u0080Ð°Ñ\u0084Ð¸Ð¸")) {
            ftpPath = folderName;
        } else {
            String[] str = sessionFactory.getSession().listNames(folderName);
            if (str.length != 0) {
                for (String s : str) {
                    if (!s.startsWith(".") & !s.contains(".jpg")) {
                        String builder = folderName +
                                "/" +
                                s;
                        findFolder(builder, sessionFactory);
                    }
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
        return sessionFactory;
    }

}
