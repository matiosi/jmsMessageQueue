package com.mateuszosiak.jmsqueue.model.repository;

import com.mateuszosiak.jmsqueue.model.Message;
import org.springframework.data.repository.CrudRepository;

public interface MessageRepository extends CrudRepository<Message, Long> {


}
