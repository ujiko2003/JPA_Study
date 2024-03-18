package hellojpa;

import hellojpa.entity.Member;
import hellojpa.entity.Team;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class JpaMain {

    /*
     * 일대다 단방향 정리
     *  - 일대다 단방향은 일대다에서 일이 연관관계의 주인
     *  - 테이블 일대다 관계는 항상 다쪽에 외래 키가 있다
     *  - 객체와 테이블의 차이 때문에 반대편 테이블의 외래 키를 관리하는 특이한 구조
     *  - @JoinColumn을 꼭 사용해야 함. 그렇지 않으면 조인 테이블 방식을 사용함
     *  - 단점
     *   - 엔티티가 관리하는 외래 키가 다른 테이블에 있음
     *   - 연관관계 관리를 위한 추가로 UPDATE SQL 실행
     *
     *  - 일대다 단방향 매핑보다는 다대일 양방향 매핑을 사용하자
     */

    public static void main(String[] args) {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {
            Member member = new Member();
            member.setUserName("member1");

            em.persist(member);

            Team team = new Team();
            team.setName("teamA");
            team.getMembers().add(member);
            em.persist(team);

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }

        emf.close();
    }
}
