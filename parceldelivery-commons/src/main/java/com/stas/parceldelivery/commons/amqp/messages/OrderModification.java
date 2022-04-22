package com.stas.parceldelivery.commons.amqp.messages;

import java.io.Serializable;

import lombok.Data;

@Data
public abstract class OrderModification implements Serializable, Identifieble {
}
