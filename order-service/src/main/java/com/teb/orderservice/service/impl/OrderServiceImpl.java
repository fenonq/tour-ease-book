package com.teb.orderservice.service.impl;

import com.teb.orderservice.mapper.OrderMapper;
import com.teb.orderservice.model.dto.OrderDto;
import com.teb.orderservice.model.dto.hotel.HotelDto;
import com.teb.orderservice.model.entity.CartItem;
import com.teb.orderservice.model.entity.Order;
import com.teb.orderservice.model.entity.OrderedItem;
import com.teb.orderservice.model.request.CreateOrderRequest;
import com.teb.orderservice.repository.OrderRepository;
import com.teb.orderservice.service.OrderService;
import com.teb.orderservice.util.Utils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;

    @Override
    public OrderDto createOrder(CreateOrderRequest createOrderRequest) {
        List<HotelDto> bookedHotels = createOrderRequest.getBookedHotels();
        List<OrderedItem> orderedItems = getOrderedItems(createOrderRequest, bookedHotels);

        Order order = getOrder(createOrderRequest, createOrderRequest.getCart().getUserId(), orderedItems);

        Order orderToReturn = orderRepository.save(order);
        return OrderMapper.INSTANCE.mapOrderToOrderDto(orderToReturn);
    }

    private List<OrderedItem> getOrderedItems(CreateOrderRequest createOrderRequest, List<HotelDto> bookedHotels) {
        Map<String, HotelDto> hotelMap = bookedHotels.stream()
                .collect(Collectors.toMap(HotelDto::getId, Function.identity()));
        List<OrderedItem> orderedItems = new ArrayList<>();

        for (CartItem item : createOrderRequest.getCart().getCartItems()) {
            HotelDto matchedHotel = hotelMap.get(item.getOfferId());
            OrderedItem orderedItem = OrderedItem.builder()
                    .dateFrom(item.getDateFrom())
                    .dateTo(item.getDateTo())
                    .offer(matchedHotel)
                    .build();
            orderedItems.add(orderedItem);
        }

        return orderedItems;
    }

    private static Order getOrder(CreateOrderRequest createOrderRequest, String userId, List<OrderedItem> orderedItems) {
        return Order.builder()
                .userId(userId)
                .totalPrice(
                        orderedItems.stream().map(OrderedItem::getOffer).toList().stream()
                                .flatMapToDouble(hotel -> hotel.getRooms().stream()
                                        .mapToDouble(room -> createOrderRequest.getCart().getCartItems().stream()
                                                .filter(cartItem -> cartItem.getOfferId().equals(hotel.getId()) && cartItem.getRoomId().equals(room.getRoomId()))
                                                .findFirst()
                                                .map(item -> room.getPrice() * Utils.getNumberOfNights(item.getDateFrom(), item.getDateTo()))
                                                .orElse(0.0))).sum())
                .orderedItems(new ArrayList<>(orderedItems))
                .creationDateTime(LocalDateTime.now())
                .build();
    }

    @Override
    public List<OrderDto> findUserOrders(String userId) {
        return orderRepository.findByUserId(userId).stream()
                .map(OrderMapper.INSTANCE::mapOrderToOrderDto)
                .toList();
    }

}
