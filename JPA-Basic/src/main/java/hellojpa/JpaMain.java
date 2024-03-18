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
     * 양방향 연관관계 주의점
     * - 순수 객체 상태를 고려해서 항상 양쪽에 값을 설정하자
     * - 연관관계 편의 메소드를 생성하자
     * - 양방향 매핑시에 무한 루프를 조심하자
     */

    /*
     * 양방향 매핑 정리
     * - 단방향 매핑만으로도 이미 연관관계 매핑은 완료
     * - 양방향 매핑은 반대 방향으로 조회 기능이 추가된 것 뿐
     * - JPQL에서 역방향으로 탐색할 일이 많음
     * - 단방향 매핑을 잘 하고 양방향은 필요할 때 추가해도 됨
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
