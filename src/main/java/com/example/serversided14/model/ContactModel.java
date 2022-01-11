package com.example.serversided14.model;

import java.io.Serializable;
import java.security.SecureRandom;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;

public class ContactModel implements Serializable {
  private static final long serialVersionUID = 1L;

  private String id;
  private String name;
  private String email;
  private String phoneNumber;

  @Bean
  @Scope("singleton")
  public SecureRandom secureRandomSingleton() {
    return new SecureRandom();
  }

  public ContactModel() {
    this.id = this.generateId();
  }

  public ContactModel(String name) {
    this.id = this.generateId();
    this.name = name;
  }

  public ContactModel(String name, String email) {
    this.id = this.generateId();
    this.name = name;
    this.email = email;
  }

  public ContactModel(String name, String email, String phoneNumber) {
    this.id = this.generateId();
    this.name = name;
    this.email = email;
    this.phoneNumber = phoneNumber;
  }

  public ContactModel(String id, String name, String email, String phoneNumber) {
    this.id = id;
    this.name = name;
    this.email = email;
    this.phoneNumber = phoneNumber;
  }

  // all synchronized blocks of the same object can have only one thread executing
  // them at the same time.
  // Leaving synchronized, a race condition might occur leading to duplicate id.
  private synchronized String generateId() {
    SecureRandom randHex = new SecureRandom();
    String id = Integer.toHexString(randHex.nextInt());
    return id;
  }

  public String getId() {
    return this.id;
  }

  public String getName() {
    return this.name;
  }

  public String getEmail() {
    return this.email;
  }

  public String getPhoneNumber() {
    return this.phoneNumber;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public void setPhoneNumber(String phoneNumber) {
    this.phoneNumber = phoneNumber;
  }

}
