package com.solidarychain.citizencardreaderapi.config;

import lombok.Data;
import lombok.Getter;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Data
@Configuration
@ConfigurationProperties(prefix = "config-properties")
public class ConfigProperties {
  // @Getter
  private String graphqlFqnd;
  private String graphqlPort;
  // @Getter
  private String graphqlUri;
  
  // private List<String> mokeUsers;

  public String getGraphqlFqdn() {
    return this.graphqlFqnd;
  }

  public void setGraphqlFqdn(String graphqlFqnd) {
    this.graphqlFqnd = graphqlFqnd;
  }

  public String getGraphqlUri() {
    return this.graphqlUri;
  }

  public void setGraphqlUri(String graphqlUri) {
    this.graphqlUri = graphqlUri;
  }
}
