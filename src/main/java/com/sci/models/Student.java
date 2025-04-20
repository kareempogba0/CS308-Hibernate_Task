package com.sci.models;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Entity
@Table(name = "students", schema = "system")
@Data
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE) // 2nd level cashing
@NoArgsConstructor
@AllArgsConstructor
@SequenceGenerator(name="students_gen", sequenceName="students_seq", allocationSize = 1)
public class Student implements Serializable {

    private static final long serialVersionUID = -915428707036605461L;

    @Id
//    @GeneratedValue(strategy= GenerationType.SEQUENCE, generator="students_gen")
    @Column(name = "id")
    private Integer studentId;

//    @Column(name = "dept_id")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dept_id")
    private Dept dept_id;

    @Column(name = "name")
    private String studentName;
    @Column(name = "address")
    private String studentAddress;
    @Column(name = "age")
    private Integer studentAge;
    @Column(name = "joined_date")
//    private Date joinedDate;
    private LocalDate joinedDate;

//    @ManyToMany(fetch = FetchType.LAZY)
//    @JoinTable(
//            name = "student_course",
//            joinColumns = @JoinColumn(name = "student_id"),
//            inverseJoinColumns = @JoinColumn(name = "course_id")
//    )
//    private List<Course> courses;

}