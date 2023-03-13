package com.index325.message.consumer.chat.io.models;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "user_messages")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class UserMessages {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String messageBody;

    @ManyToOne
    @JoinColumn(name="from_id", nullable=false)
    private User from;

    @ManyToOne
    @JoinColumn(name="to_id", nullable=false)
    private User to;
}
