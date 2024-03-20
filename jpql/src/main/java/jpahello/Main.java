package jpahello;

import javax.persistence.*;
import java.util.Collection;
import java.util.List;

public class Main {

    /*
     * 경로 표현식
     *  - 상태 필드(state field): 단순히 값을 저장하기 위한 필드
     *  - 연관 필드(association field): 연관관계를 위한 필드
     *  - 단일 값 연관 필드: @ManyToOne, @OneToOne, 대상이 엔티티
     */

    /*
     * 경로 표현식 특징
     *  - 상태 필드(state field): 경로 탐색의 끝, 더 이상 탐색 X
     *  - 단일 값 연관 경로: 묵시적 내부 조인 발생, 탐색 O
     *  - 컬렉션 값 연관 경로: 묵시적 내부 조인 발생, 탐색 X
     *   - FROM 절에서 명시적 조인을 통해 별칭을 얻으면 별칭을 통해 탐색 가능
     */

    /*
     * 경로 탐색을 사용한 묵시적 조인 시 주의사항
     *  - 항상 내부 조인
     *  - 컬렉션은 경로 탐색의 끝, 명시적 조인을 통해 별칭을 얻어야 함
     *  - 경로 탐색은 주로 SELECT, WHERE 절에서 사용하지만 묵시적 조인으로 인해 SQL의 FROM (JOIN) 절에 영향을 줌
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

            Member member = new Member();
            member.setUsername("관리자");
            member.setAge(10);
            member.setTeam(team);
            member.setType(MemberType.ADMIN);
            em.persist(member);

            em.flush();
            em.clear();

            String query = "select m.username FROM Team t join t.members m";
            List<String> result = em.createQuery(query, String.class)
                    .getResultList();

            System.out.println("result = " + result);


            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }
        emf.close();

    }

}