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
     * 객체와 테이블이 관계를 맺는 차이
     * 객체 연관관계 = 2개
     *  - 회원 -> 팀 연관관계 1개 (단방향)
     *  - 팀 -> 회원 연관관계 1개 (단방향)
     * 테이블 연관관계 = 1개
     *  - 회원 <-> 팀 연관관계 1개 (양방향)
     *
     * 객체의 양방향 관계는 사실 양방향 관계가 아니라 서로 다른 단방향 관계 2개다.
     * 객체를 양방향으로 참조하려면 단방향 연관관계를 2개 만들어야 한다.
     */

    /*
     * 테이블의 양방향 관계
     * - 외래 키 하나로 두 테이블의 연관관계를 관리
     * - MEMBER.TEAM_ID 외래 키 하나로 양방향 연관관계 가짐
     *
     */

    /*
     * 연관관계의 주인(Owner)
     * 양합향 매핑 규칙
     *  - 객체의 두 관계 중 하나를 연관관계의 주인으로 지정
     *  - 연관관계의 주인만이 외래 키를 관리(등록, 수정)
     *  - 주인이 아닌 쪽은 읽기만 가능
     *  - 주인은 mappedBy 속성 사용 X
     *  - 주인이 아니면 mappedBy 속성으로 주인 지정
     */

    /*
     * 누구를 주인으로?
     * - 외래 키가 있는 곳을 주인으로 정해라
     * - 여기서는 Member.team이 주인
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
            member.setTeam(team);
            em.persist(member);

            em.flush();
            em.clear();

            Member findMember = em.find(Member.class, member.getId());
            List<Member> members = findMember.getTeam().getMembers();

            for (Member member1 : members) {
                System.out.println("member1 = " + member1.getName());
            }

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }

        emf.close();
    }

}
