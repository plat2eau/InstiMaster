package com.instimaster.model.institute;

import com.instimaster.util.IdUtil;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;

@Entity
@Table(name="Institutes")
@Getter
@Setter
@ToString
public class Institute {

    @Id
    @UuidGenerator
    private String id;
    private String name;
    private String location;
    private String contact;
    private String head;
    private String website;
    private String estabYear;

    public Institute() {
        this.id = IdUtil.getInstituteId(this);
    }
}
