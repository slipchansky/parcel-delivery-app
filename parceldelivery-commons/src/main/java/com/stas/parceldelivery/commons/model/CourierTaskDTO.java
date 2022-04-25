package com.stas.parceldelivery.commons.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import com.stas.parceldelivery.commons.enums.DeliveryStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CourierTaskDTO {
	private String id;
	private String courierId;
	private String client;
	private String addressFrom;
	private String addressTo;
	private String clientPhone;
	private String clientEmail;
	private String location;
	private DeliveryStatus status;
	private Date created;
	private Date modified;

}
