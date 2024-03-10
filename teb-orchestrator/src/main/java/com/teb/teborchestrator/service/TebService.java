package com.teb.teborchestrator.service;

import com.teb.teborchestrator.model.entity.Order;
import com.teb.teborchestrator.model.request.CreateOrderRequest;
import com.teb.teborchestrator.model.request.GetOffersRequest;

import java.util.List;

public interface TebService {

    List<?> findAll(GetOffersRequest getOffersRequest);

    Order<?> createOrder(CreateOrderRequest createOrderRequest);

}
