package com.sci.models;

import java.io.Serializable;
import java.util.List;
import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Entity
@Table(name = "depts")
@Data
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@NoArgsConstructor
@AllArgsConstructor
public class Dept implements Serializable {

    private static final long serialVersionUID = -915428707036605461L;

    @Id
    private Integer id;
    private String name;

//    @OneToMany(mappedBy = "dept_id", fetch = FetchType.LAZY)
//    private List<Student> students;

}