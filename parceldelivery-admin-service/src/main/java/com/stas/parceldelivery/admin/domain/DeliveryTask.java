package com.stas.parceldelivery.admin.domain;

import static com.stas.parceldelivery.commons.util.BeanConverter.from;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.UUID;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.stas.parceldelivery.commons.enums.DeliveryStatus;
import com.stas.parceldelivery.commons.model.DeliveryOrderResponseDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Table(name="delivery_task")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DeliveryTask implements Serializable {

	@Id
	private String id; // from DeliveryOrder
	
	private String addressFrom;
	private String addressTo;
	private String clientPhone;
	private String clientEmail;
	private String location;
	private Date created;
	private Date modified;
	 
	@Enumerated(EnumType.ORDINAL)
	private DeliveryStatus status;
	@Enumerated(EnumType.ORDINAL)
	private TaskState state;
	
	@ManyToOne
	@JoinColumn(name="assignee_id")
	private Courier assignee;

	@PrePersist
    public void prePersist() {
		if(id==null) {
			// for simplifying test cases
			id = UUID.randomUUID().toString();
		}
		modified = created = new Date();
    }
 
    @PreUpdate
    public void preUpdate() {
        modified = new Date();
    }
}
