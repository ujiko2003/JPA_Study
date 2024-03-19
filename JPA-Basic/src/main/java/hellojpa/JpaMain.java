package hellojpa;

import hellojpa.entity.Member;
import hellojpa.entity.Team;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;

public class JpaMain {

    /*
     * 프록시와 즉시로딩 주의
     * - 가급적 지연 로딩만 사용 (특히 실무에서)
     * - 즉시 로딩을 적용하면 예상하지 못한 SQL이 발생
     * - 즉시 로딩은 JPQL에서 N+1 문제를 일으킨다.
     * - @ManyToOne, @OneToOne은 기본이 즉시 로딩 -> LAZY로 설정
     * - @OneToMany, @ManyToMany는 기본이 지연 로딩
     */

    /*
     * 지연 로딩 활용
     * - Member와 Team은 자주 함께 사용 -> 즉시 로딩
     * - Member와 Order는 가끔 사용 -> 지연 로딩
     * - Order와 Product는 자주 함께 사용 -> 즉시 로딩
     */

    /*
     * 지연 로딩 활용 - 실무
     * - 모든 연관관계에 지연 로딩을 사용해라!
     * - 실무에서 즉시 로딩을 사용하지 마라!
     * - JPQL fetch 조인이나, 엔티티 그래프 기능을 사용해라!
     * - 즉시 로딩을 적용하면 예상하지 못한 SQL이 발생한다.
     */

    public static void main(String[] args) {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {
            Team team = new Team();
            team.setName("teamA");
            em.persist(team);

            Team team2 = new Team();
            team2.setName("teamB");
            em.persist(team2);

            Member member1 = new Member();
            member1.setUserName("member1");
            member1.setTeam(team);
            em.persist(member1);

            Member member2 = new Member();
            member2.setUserName("member2");
            member2.setTeam(team2);
            em.persist(member2);

            em.flush();
            em.clear();

//            Member member = em.find(Member.class, member1.getId());
            List<Member> members = em.createQuery("select m from Member m join fetch m.team", Member.class).getResultList();
            members.get(0).getTeam();


            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }
        emf.close();
    }
}
