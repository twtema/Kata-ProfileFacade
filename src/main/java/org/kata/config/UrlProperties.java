package org.kata.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("url-properties")
@Getter
@Setter
public class UrlProperties {


    private String profileServiceBaseUrl;
    private String profileServiceGetIndividual;
    private String profileServiceGetAllDocuments;
    private String profileServiceGetContactMedium;
    private String profileServiceGetAvatar;
    private String profileServiceGetAddress;
    private  String profileServiceGetWallets;

    private String CbrBaseUrl;

}
