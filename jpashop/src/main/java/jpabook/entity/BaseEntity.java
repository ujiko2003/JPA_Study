package jpabook.entity;

import javax.persistence.MappedSuperclass;

@MappedSuperclass
public class BaseEntity {

        private String createdBy;

        private String lastModifiedBy;

        private String createdDate;

        private String lastModifiedDate;

}
