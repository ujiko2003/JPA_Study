package hellojpa;

import hellojpa.entity.Member;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class JpaMain {

    /**
     * hbm2ddl.auto
     * - create: 기존 테이블 삭제 후 다시 생성(DROP + CREATE)
     * - create-drop: create와 같으나 종료시점에 테이블 DROP
     * - update: 변경분만 반영
     * - validate: 엔티티와 테이블이 정상 매핑되었는지만 확인
     * - none: 사용하지 않음
     * <p>
     * 운영에서는 절대 create, create-drop, update 사용하면 안된다.
     * 운영에서는 validate, none 사용이 권장됨
     * 자칫하면 대장애가 발생할 수 있다.
     */

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        try {
            Member member = em.find(Member.class, 150L);
            member.setName("AAAAA");

            //em.detach(member);
            em.clear();

            Member member2 = em.find(Member.class, 150L);

            System.out.println("====================");
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }

        emf.close();
    }

}
