package com.example.serversided14.service;

import com.example.serversided14.model.ContactModel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

// Annotation used on a class. @Service marks java class that performs some service, such as execute business logic, perform calculations and call external APIs. It is a specialised form of the @Component annotation intended to be used in the service layer.
@Service
public class ContactsRedis implements ContactsRepo {
  private static final Logger logger = LoggerFactory.getLogger(ContactsRedis.class);

  @Autowired
  RedisTemplate<String, Object> redisTemplate;

  @Override
  public void save(final ContactModel ctc) {
    redisTemplate.opsForValue().set(ctc.getId(), ctc);

  }

  @Override
  public ContactModel findById(final String contactId) {
    ContactModel result = (ContactModel) redisTemplate.opsForValue().get(contactId);

    logger.info("email >>> " + result.getEmail());
    return result;
  }

}
