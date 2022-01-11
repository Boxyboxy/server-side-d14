package com.example.serversided14.controller;

import com.example.serversided14.model.ContactModel;
import com.example.serversided14.service.ContactsRedis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AddressBookController {
  private static final Logger logger = LoggerFactory.getLogger(AddressBookController.class);
  // Annotation is applied on fields, setter methods and constructors. The
  // @Autowired annotation injects object dependency implicitly.
  @Autowired
  ContactsRedis service;

  @GetMapping("/")
  public String contactForm(Model model) {
    model.addAttribute("contact", new ContactModel());
    // returns name of html page
    return "contactform";
  }

  @PostMapping("/contactSubmit")
  public String contactSubmit(@ModelAttribute ContactModel contactModel, Model model) {
    long startTime = System.currentTimeMillis();

    logger.info("Id: " + contactModel.getId());
    logger.info("Name: " + contactModel.getName());
    logger.info("Email: " + contactModel.getEmail());
    logger.info("Phone number: " + contactModel.getPhoneNumber());

    ContactModel persistToRedisCtc = new ContactModel(
        contactModel.getName(),
        contactModel.getEmail(),
        contactModel.getPhoneNumber());
    service.save(persistToRedisCtc);
    model.addAttribute("contact", persistToRedisCtc);
    long endTime = System.currentTimeMillis();
    logger.info("Elapsed timing -> contactSubmit() " + (endTime - startTime));
    // returns name of html page
    return "displaycontact";
  }

  @GetMapping("/getContact/{contactId}")
  public String getContact(Model model, @PathVariable(value = "contactId") String contactId) {
    logger.info("contactId : " + contactId);
    ContactModel ctc = service.findById(contactId);
    logger.info("getId > " + ctc.getId());
    logger.info("getEmail > " + ctc.getEmail());

    model.addAttribute("contact", ctc);
    // returns name of html page
    return "displaycontact";
  }
}
