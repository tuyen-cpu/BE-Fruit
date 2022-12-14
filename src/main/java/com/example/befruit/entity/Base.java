package com.example.befruit.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;

@MappedSuperclass
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public abstract class Base<U>  implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	protected long id;

	@LastModifiedBy
	@Column(name = "update_by", nullable = false)
	protected U updatedBy;

	@CreatedBy
	@Column(name = "created_by", nullable = false)
	protected U createdBy;

	@CreationTimestamp
	@Column(name = "created_at", nullable = false)
	protected Date createdAt;

	@UpdateTimestamp
	@Column(name = "update_at", nullable = false)
	protected Date updatedAt;

}