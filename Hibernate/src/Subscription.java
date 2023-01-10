import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "subscriptions")
public class Subscription {

    @EmbeddedId
    private SubKey id;

    @Column(name = "student_id", insertable = false, updatable = false,
            columnDefinition = "INT UNSIGNED", nullable = false)
    private Integer studentId;

    @Column(name = "course_id", insertable = false, updatable = false,
            columnDefinition = "INT UNSIGNED", nullable = false)
    private Integer courseId;

    @Column(name = "subscription_date")
    private Date subscriptionDate;

}

