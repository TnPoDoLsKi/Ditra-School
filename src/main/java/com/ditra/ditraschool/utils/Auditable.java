package com.ditra.ditraschool.utils;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.util.Date;


@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
@NoArgsConstructor
public class Auditable<U> {

    @CreatedBy
    @JsonIgnore
    private U createdBy;

    @CreatedDate
    private Date createdDate;

    @LastModifiedBy
    @JsonIgnore
    private U lastModifiedBy;

    @LastModifiedDate
    private Date lastModifiedDate;
}

