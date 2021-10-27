package com.solidarychain.citizencardreaderapi.controllers;

import com.solidarychain.citizencardreaderapi.config.ConfigProperties;
import com.solidarychain.citizencardreaderapi.utils.HttpUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
  private ConfigProperties config;

  @Autowired
  public HomeController(final ConfigProperties config) {
    this.config = config;
  }

  // @RequestMapping(value = "/")
  // public String index() {
  //   return "index";
  // }

  /**
   * TODO: test signIn mutation
   * 
   * @return
   */
  @GetMapping("/sign-in")
  public String testSignIn() {
    HttpUtils.graphql(this.config.getGraphqlFqdn(), this.config.getGraphqlUri());
    return "no repply";
  }
}