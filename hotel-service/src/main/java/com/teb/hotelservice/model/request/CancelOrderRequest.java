package com.teb.hotelservice.model.request;

import com.teb.hotelservice.model.entity.CancelledItem;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CancelOrderRequest {
    private String orderId;
    private String userId;
    private List<CancelledItem> cancelledItems;
}
