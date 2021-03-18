package org.newton.webshop.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;


@Getter
@Setter
@NoArgsConstructor
@Table(name = "orders")
@Entity
public class Order {
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "order_id", length = 50, nullable = false)
    private String id;

    @Column(name = "order_on", nullable = false)
    private LocalDateTime orderOn;

    @OneToOne
    @JoinColumn(name = "cart_id", nullable = false)
    private Cart cart;

}

