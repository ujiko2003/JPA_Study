package jpahello;

import javax.persistence.*;
import java.util.Collection;
import java.util.List;

public class Main {

    /*
     * 페이 조인의 특징과 한계
     *  - 페치 조인 대상에는 별칭을 줄 수 없다.
     *   - 하이버네이트는 가능하지만, 가급적 사용하지 않는 것이 좋다.
     *  - 둘 이상의 컬렉션은 페치 조인 할 수 없다.
     *  - 컬렉션을 페치 조인하면 페이징 API를 사용할 수 없다.
     *   - 일대일, 다대일 같은 단일 값 연관 필드들은 페치 조인해도 페이징이 가능하다.
     *   - 하이버네이트는 경고 로그를 남기고 메모리에서 페이징을 시도한다.
     */
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {

            Team teamA = new Team();
            teamA.setName("teamA");
            em.persist(teamA);

            Team teamB = new Team();
            teamB.setName("teamB");
            em.persist(teamB);

            Member member1 = new Member();
            member1.setUsername("회원1");
            member1.setTeam(teamA);
            em.persist(member1);

            Member member2 = new Member();
            member2.setUsername("회원2");
            member2.setTeam(teamA);
            em.persist(member2);

            Member member3 = new Member();
            member3.setUsername("회원3");
            member3.setTeam(teamB);
            em.persist(member3);

            em.flush();
            em.clear();

            String query = "select distinct t FROM Team t join fetch t.members ";
            List<Team> result = em.createQuery(query, Team.class)
                    .getResultList();

            System.out.println("result = " + result.size());

            for (Team team : result) {
                System.out.println("member = " + team.getName());
                System.out.println("-> member.team = " + team.getName() + "|members = " + team.getMembers().size());
                for (Member member : team.getMembers()) {
                    System.out.println("-> member = " + member);
                }

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