package com.stas.parceldelivery.client.domain;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.stas.parceldelivery.commons.enums.DeliveryStatus;
import com.stas.parceldelivery.commons.model.DeliveryOrderDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DeliveryOrder implements Serializable {
	
	@Id
	@GeneratedValue(generator="system-uuid")
	@GenericGenerator(name="system-uuid", strategy = "uuid")
	private String id;
	private String client;
	private String addressFrom;
	private String addressTo;
	 
	@Enumerated(EnumType.ORDINAL)
	private DeliveryStatus status;
	
	LocalDateTime created;
	
	LocalDateTime modified;

	@PrePersist
    public void prePersist() {
		modified = created = LocalDateTime.now();
        
    }
 
    @PreUpdate
    public void preUpdate() {
        modified = LocalDateTime.now();
    }

	public DeliveryOrderDTO toDto() {
		ObjectMapper mapper = new ObjectMapper();
		return mapper.convertValue(this, DeliveryOrderDTO.class);
	}

	public static DeliveryOrder fromDto(DeliveryOrderDTO d) {
		ObjectMapper mapper = new ObjectMapper();
		DeliveryOrder o = new DeliveryOrder();
		return mapper.convertValue(d, DeliveryOrder.class);
	}	

}
