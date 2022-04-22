package com.stas.parceldelivery.admin.domain;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;

import org.hibernate.annotations.GenericGenerator;

import com.stas.parceldelivery.commons.enums.DeliveryStatus;

import lombok.*;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class DeliveryTaskTrace {
	
	@Id
	private String id;
	
	@ManyToOne
	@JoinColumn(name = "task_id")
	private DeliveryTask task;
	
	private Date created;
	
	@OneToOne
	@JoinColumn(name="assignee_id")
	private Courier assignee;
	
	@Enumerated(EnumType.STRING)
	private DeliveryStatus status;
	private String location;
	
	@PrePersist
    public void prePersist() {
		created = new Date();
    }
}
