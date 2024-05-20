package com.teb.orderservice.service.impl;

import com.teb.orderservice.mapper.OrderMapper;
import com.teb.orderservice.model.dto.OrderDto;
import com.teb.orderservice.model.dto.hotel.HotelDto;
import com.teb.orderservice.model.entity.CartItem;
import com.teb.orderservice.model.entity.Order;
import com.teb.orderservice.model.entity.OrderedItem;
import com.teb.orderservice.model.enums.OrderStatus;
import com.teb.orderservice.model.request.CreateOrderRequest;
import com.teb.orderservice.repository.OrderRepository;
import com.teb.orderservice.service.OrderService;
import com.teb.orderservice.util.Utils;
import jakarta.ws.rs.NotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;

    @Override
    public OrderDto createOrder(CreateOrderRequest createOrderRequest) {
        log.info("Creating order..");
        List<HotelDto> bookedHotels = createOrderRequest.getBookedHotels();
        List<OrderedItem> orderedItems = getOrderedItems(createOrderRequest, bookedHotels);

        Order order = getOrder(createOrderRequest, createOrderRequest.getCart().getUserId(), orderedItems);

        Order orderToReturn = orderRepository.save(order);
        return OrderMapper.INSTANCE.mapOrderToOrderDto(orderToReturn);
    }

    @Override
    public OrderDto cancelOrder(String id) {
        log.info("Cancelling order with id {}..", id);
        Order orderToReturn = orderRepository.findById(id).orElseThrow(NotFoundException::new);
        orderToReturn.setStatus(OrderStatus.CANCELLED);
        return OrderMapper.INSTANCE.mapOrderToOrderDto(orderRepository.save(orderToReturn));
    }

    @Override
    public List<OrderDto> findUserOrders(String userId) {
        log.info("Finding user orders with id {}", userId);
        return orderRepository.findByUserId(userId).stream()
                .map(OrderMapper.INSTANCE::mapOrderToOrderDto)
                .toList();
    }

    private List<OrderedItem> getOrderedItems(CreateOrderRequest createOrderRequest, List<HotelDto> bookedHotels) {
        List<OrderedItem> orderedItems = new ArrayList<>();

        for (CartItem item : createOrderRequest.getCart().getCartItems()) {
            HotelDto matchedHotel = bookedHotels.stream()
                    .filter(hotel -> hotel.getId().equals(item.getOfferId()) && hotel.getRooms().get(0).getRoomId().equals(item.getRoomId()))
                    .findFirst()
                    .orElse(null);

            OrderedItem orderedItem = OrderedItem.builder()
                    .dateFrom(item.getDateFrom())
                    .dateTo(item.getDateTo())
                    .numberOfRooms(item.getNumberOfRooms())
                    .offer(matchedHotel)
                    .build();
            orderedItems.add(orderedItem);
        }

        return orderedItems;
    }

    private Order getOrder(CreateOrderRequest createOrderRequest, String userId, List<OrderedItem> orderedItems) {
        return Order.builder()
                .status(OrderStatus.BOOKED)
                .userId(userId)
                .totalPrice(
                        orderedItems.stream().map(OrderedItem::getOffer).toList().stream()
                                .flatMapToDouble(hotel -> hotel.getRooms().stream()
                                        .mapToDouble(room -> createOrderRequest.getCart().getCartItems().stream()
                                                .filter(cartItem -> cartItem.getOfferId().equals(hotel.getId()) && cartItem.getRoomId().equals(room.getRoomId()))
                                                .findFirst()
                                                .map(item -> room.getPrice() * Utils.getNumberOfNights(item.getDateFrom(), item.getDateTo()) * item.getNumberOfRooms())
                                                .orElse(0.0))).sum())
                .orderedItems(new ArrayList<>(orderedItems))
                .creationDateTime(LocalDateTime.now())
                .build();
    }

}
