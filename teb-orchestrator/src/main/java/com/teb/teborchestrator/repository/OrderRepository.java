package com.teb.teborchestrator.repository;

import com.teb.teborchestrator.model.entity.Order;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends MongoRepository<Order<?>, String> {
}
