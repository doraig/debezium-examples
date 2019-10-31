/*
 * Copyright Debezium Authors.
 *
 * Licensed under the Apache Software License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package io.debezium.examples.outbox.order.event;

import java.util.Date;

import org.apache.avro.generic.GenericContainer;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.debezium.examples.outbox.order.model.OrderLineStatus;
import io.debezium.examples.outbox.order.outbox.ExportedEvent;

public class OrderLineUpdatedEvent implements ExportedEvent {

    private static ObjectMapper mapper = new ObjectMapper();

    private final long orderId;
    private final long orderLineId;
    private final OrderLineStatus newStatus;
    private final OrderLineStatus oldStatus;
    private final Long timestamp;

    public OrderLineUpdatedEvent(long orderId, long orderLineId, OrderLineStatus newStatus, OrderLineStatus oldStatus) {
        this.orderId = orderId;
        this.orderLineId = orderLineId;
        this.newStatus = newStatus;
        this.oldStatus = oldStatus;
        this.timestamp = (new Date()).getTime();
    }

    public static OrderLineUpdatedEvent of(long orderId, long orderLineId, OrderLineStatus newStatus,
            OrderLineStatus oldStatus) {
        return new OrderLineUpdatedEvent(orderId, orderLineId, newStatus, oldStatus);
    }

    public long getOrderId() {
        return orderId;
    }

    public long getOrderLineId() {
        return orderLineId;
    }

    public OrderLineStatus getNewStatus() {
        return newStatus;
    }

    public OrderLineStatus getOldStatus() {
        return oldStatus;
    }

    @Override
    public String getAggregateId() {
        return String.valueOf(orderId);
    }

    @Override
    public String getAggregateType() {
        return "Order";
    }

    @Override
    public Long getTimestamp() {
        return timestamp;
    }

    @Override
    public GenericContainer getPayload() {
        throw new UnsupportedOperationException();
    }

    @Override
    public String getType() {
        return "OrderLineUpdated";
    }
}
