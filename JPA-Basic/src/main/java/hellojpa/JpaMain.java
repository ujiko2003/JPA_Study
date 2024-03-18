package hellojpa;

import hellojpa.entity.Member;
import hellojpa.entity.Team;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class JpaMain {

    /*
     * 일대일 관계
     *  - 일대일 관계는 그 반대도 일대일
     *  - 주 테이블이나 대상 테이블 중에 외래 키 선택 가능
     *   - 주 테이블에 외래 키
     *   - 대상 테이블에 외래 키
     *  - 외래 키에 유니크 제약조건 추가
     */

    /*
     * 일대일 정리
     *  - 주 테이블에 외래 키
     *   - 주 객체가 대상 객체의 참조를 가지는 것 처럼
     *     주 테이블에 외래 키를 두고 대상 테이블을 찾음
     *   - 객체지향 개발자 선호
     *   - JPA 매핑 편리
     *   - 장점: 주 테이블만 조회해도 대상 테이블에 데이터가 있는지 확인 가능
     *   - 단점: 값이 없으면 외래 키에 null 허용
     *
     *  - 대상 테이블에 외래 키
     *   - 대상 테이블에 외래 키가 존재
     *   - 전통적인 데이터베이스 개발자 선호
     *   - 장점: 주 테이블과 대상 테이블을 일대일에서 일대다 관계로 변경할 때 테이블 구조 유지
     *   - 단점: 프록시 기능의 한계로 지연 로딩으로 설정해도 항상 즉시 로딩됨
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
