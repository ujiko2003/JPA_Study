package hellojpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class JpaMain {

    /*
     * 다대다 매핑의 한계
     *  - 편리해 보이지만 실무에서 사용 X
     *  - 연결 테이블이 단순히 연결만 하고 끝나지 않음
     *  - 주문시간, 수량 같은 데이터가 들어올 수 있음
     */

    /*
     * 다대다 매핑의 한계 극복
     *  - 연결 엔티티를 만들어서 일대다, 다대일 관계로 풀어내는 방법
     *  - @ManyToMany -> @OneToMany, @ManyToOne
     */

    public static void main(String[] args) {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }

        emf.close();
    }
}
