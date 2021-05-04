package com.sananda.inventory.common;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import lombok.Data;

@Data
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class Audit implements Serializable {

	private static final long serialVersionUID = 1L;

	@Temporal(TemporalType.TIMESTAMP)
    @Column(name = "CREATED_AT", nullable = false, updatable = false)
    @CreatedDate
    private Date createdAt;
	
	@Column(name = "CREATED_BY", updatable = false)
	private String createdBy;
	
	//@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss") private LocalDateTime createdAt;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "UPDATED_AT", nullable = false)
    @LastModifiedDate
    private Date updatedAt;
    
	@Column(name = "UPDATED_BY")
	private String updatedBy;
}
