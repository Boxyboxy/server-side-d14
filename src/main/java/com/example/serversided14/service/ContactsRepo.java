package com.example.serversided14.service;

import com.example.serversided14.model.ContactModel;

// This Repo is an interface to give your code more mobility. In this workshop's context, ContactsRedis uses Jedis(A connection factory creating jedis based connections) as a connection driver to link up with Redis database. 
public interface ContactsRepo {
  public void save(final ContactModel ctc);

  public ContactModel findById(final String contactId);
}
