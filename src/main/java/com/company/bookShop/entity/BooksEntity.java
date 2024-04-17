package com.company.bookShop.entity;

import com.company.bookShop.audit.DateAudit;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.io.Serializable;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "book")
@EntityListeners(AuditingEntityListener.class)
public class BooksEntity extends DateAudit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(nullable = false, name = "name")
    private String name;
   @Column
   private String writer;
    @Column
    private Integer quantity;
    @Column
    private Integer price;
}