import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
@Table (name = "courses")
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    private Integer duration;

    @Enumerated (EnumType.STRING)
    @Column(columnDefinition = "enum")
    private Type type;

    private String description;

    @ManyToOne (optional=false, cascade=CascadeType.ALL)
    @JoinColumn (name="teacher_id")
    private Teacher teacher;

    @Column (name = "students_count", nullable = true)
    private Integer studentCount;

    private Integer price;

    @Column (name = "price_per_hour", nullable = true)
    private Float pricePerHour;

    @ManyToMany (cascade = CascadeType.ALL)
    @JoinTable (name = "subscriptions",
    joinColumns = {@JoinColumn (name = "course_id")},
    inverseJoinColumns = {@JoinColumn (name = "student_id")})
    private List<Student> studentList;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "subscriptions",
            joinColumns = {@JoinColumn(name = "student_id")},
            inverseJoinColumns = {@JoinColumn(name = "course_id")})
    private List<Course> courses;
}
