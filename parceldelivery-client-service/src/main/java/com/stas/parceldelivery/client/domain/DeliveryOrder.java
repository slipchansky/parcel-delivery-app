package com.stas.parceldelivery.client.domain;

import static com.stas.parceldelivery.commons.util.BeanConverter.from;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

import org.hibernate.annotations.GenericGenerator;

import com.stas.parceldelivery.commons.enums.DeliveryStatus;
import com.stas.parceldelivery.commons.model.DeliveryOrderResponseDTO;

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
	private String clientPhone;
	private String clientEmail;
	 
	@Enumerated(EnumType.ORDINAL)
	private DeliveryStatus status;
	
	private Date created;
	
	private Date modified;

	@PrePersist
    public void prePersist() {
		modified = created = new Date();
        
    }
 
    @PreUpdate
    public void preUpdate() {
        modified = new Date();
    }


}
