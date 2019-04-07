package com.homework.lrucache.repository.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;

@Data
@Entity
@NoArgsConstructor
public class CalcEntity implements Serializable {

    private static final long serialVersionUID = 4889758213068170677L;

    @Id
    private String key;
    private String value;

    public CalcEntity(String key, String value) {
        this.key = key;
        this.value = value;
    }
}
