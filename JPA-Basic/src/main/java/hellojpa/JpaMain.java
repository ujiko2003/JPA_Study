package hellojpa;

import hellojpa.entity.Member;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.time.LocalDateTime;

public class JpaMain {

    /*
     * @MappedSuperclass
     *  - 상속관계 매핑X
     *  - 엔티티 X, 테이블과 매핑 X
     *  - 부모 클래스를 상속 받는 자식 클래스에 매핑 정보만 제공
     *  - 조회, 검색 불가(em.find(BaseEntity) 불가)
     *  - 직접 생성해서 사용할 일이 없으므로 추상 클래스 권장
     *
     */

    public static void main(String[] args) {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {

            Member member = new Member();
            member.setUserName("user1");
            member.setCreatedBy("kim");
            member.setCreatedDate(LocalDateTime.now());

            em.persist(member);

            em.flush();
            em.clear();


            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }

        emf.close();
    }
}
