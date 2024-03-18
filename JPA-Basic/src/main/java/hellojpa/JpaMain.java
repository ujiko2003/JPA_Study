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
     * 연관관계 매핑시 고려사항 3가지
     * 1. 다중성
     * 2. 단방향, 양방향
     * 3. 연관관계의 주인
     */

    /*
     * 다중성
     * 1. 다대일 : @ManyToOne
     * 2. 일대다 : @OneToMany
     * 3. 일대일 : @OneToOne
     * 4. 다대다 : @ManyToMany
     */

    /*
     * 단방향, 양방향
     * - 테이블
     *   - 외래키 하나로 양쪽 조인 가능
     *   - 사실 방향이라는 개념이 없음
     *
     * - 객체
     *  - 참조용 필드가 있는 쪽으로만 참조 가능
     *  - 한쪽만 참조하면 단방향
     *  - 양쪽이 서로 참조하면 양방향
     */

    /*
     * 연관관계의 주인
     * - 테이블은 외래키 하나로 두 테이블의 연관관계를 관리
     * - 객체 양방향 관계는 A->B, B->A 처럼 참조가 2군데
     * - 객체의 양방향 관계는 참조가 2군데 있는 것 처럼 보이지만 사실 참조가 2군데다.
     * - 연관관계의 주인: 외래 키를 관리하는 참조
     * - 주인의 반대편: 외래 키에 영향을 주지 않음, 단순 조회만 가능
     */

    public static void main(String[] args) {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {

            //저장
            Team team = new Team();
            team.setName("TeamA");
            em.persist(team);

            Member member = new Member();
            member.setName("member1");
            em.persist(member);

            team.addMember(member);

//            em.flush();
//            em.clear();

            Team findTeam = em.find(Team.class, team.getId());
            List<Member> members = findTeam.getMembers();
            System.out.println("============================");
            for (Member m : members) {
                System.out.println("m = " + m.getName());
            }
            System.out.println("============================");

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }

        emf.close();
    }

}
