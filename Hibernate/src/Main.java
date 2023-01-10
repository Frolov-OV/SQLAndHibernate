import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.hibernate.QueryException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.query.SelectionQuery;

import java.util.List;


public class Main {
    public static StandardServiceRegistry registry = new StandardServiceRegistryBuilder().configure("hibernate.cfg.xml").build();
    public static Metadata metadata = new MetadataSources(registry).getMetadataBuilder().build();
    public static SessionFactory sessionFactory = metadata.getSessionFactoryBuilder().build();
    public static void main(String[] args) {

        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();

        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<PurchaseList> PurchaseListQuery = criteriaBuilder.createQuery(PurchaseList.class);
        Root<PurchaseList> purchaseRoot = PurchaseListQuery.from(PurchaseList.class);
        List<PurchaseList> purchaseList = session.createQuery(PurchaseListQuery.select(purchaseRoot))
                .getResultList();

        for (PurchaseList p : purchaseList) {
            LinkedPurchaseList linkedPurchaseLists = new LinkedPurchaseList();
            String nameCourse = p.getCourseName();
            String nameStudent = p.getStudentName();

            CriteriaBuilder builder = session.getCriteriaBuilder();

            CriteriaQuery<Course> queryC = builder.createQuery(Course.class);
            CriteriaQuery<Student> queryS = builder.createQuery(Student.class);

            Root<Course> rootC = queryC.from(Course.class);
            Root<Student> rootS = queryS.from(Student.class);

            List<Course> courseP = session.createQuery(queryC.select(rootC)
                    .where(builder.equal(rootC.<String>get("name"), nameCourse))).getResultList();
            List<Student> studentP = session.createQuery(queryS.select(rootS)
                    .where(builder.equal(rootS.<String>get("name"), nameStudent))).getResultList();

            linkedPurchaseLists.setId(new LinkedPurchaseKey(studentP.get(0).getId(), courseP.get(0).getId()));
            session.persist(linkedPurchaseLists);
        }
        transaction.commit();
        sessionFactory.close();
        }
    }
/*

    CriteriaBuilder builderS = session.getCriteriaBuilder();

    CriteriaQuery<Course> queryS = builderS.createQuery(Course.class);
    Root<Course> rootS = queryS.from(Course.class);
    List<Course> courses = session.createQuery(queryS.select(rootS))
            .getResultList();

    CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
    CriteriaQuery<Student> studentQuery = criteriaBuilder.createQuery(Student.class);
    Root<Student> rootSt = studentQuery.from(Student.class);
    List<Student> students = session.createQuery(studentQuery.select(rootSt))
            .getResultList();

            for (Course cr : courses) {
                    System.out.println("Course name: " + cr.getName()
                    + " - Students count: " + cr.getStudentCount() + " pers");
                    }
                    for (Student st : students) {
                    System.out.println("Student name: " + st.getName()
                    + " - Registration date: " + st.getRegistrationDate());
                    }
*/

