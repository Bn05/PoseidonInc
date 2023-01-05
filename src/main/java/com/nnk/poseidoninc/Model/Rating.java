package com.nnk.poseidoninc.Model;

import jakarta.persistence.*;
import org.hibernate.annotations.DynamicUpdate;

@Entity
@DynamicUpdate
@Table(name = "Rating")
public class Rating {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int ratingId;
    private String moodysRating;
    private String sandPRating;
    private String fitchRating;
    private int orderNumber;

}
