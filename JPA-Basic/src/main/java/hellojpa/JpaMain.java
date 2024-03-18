package hellojpa;

import hellojpa.entity.Member;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class JpaMain {

    public static void main(String[] args) {

        /*
         * 기본 키 매핑 방법
         * - 직접 할당 : @Id만 사용
         * - 자동 생성 : @GeneratedValue
         *   - IDENTITY: 데이터베이스에 위임 (예: MySQL : AUTO_INCREMENT)
         *   - SEQUENCE: 데이터베이스 시퀀스 오브젝트 사용 (예: Oracle)
         *   - TABLE: 키 생성 테이블 사용 (모든 데이터베이스에 사용 가능, 성능이슈로 잘 사용하지 않음)
         *   - AUTO: 방언에 따라 자동 지정, 기본값
         */

        /*
         * IDENTITY 전략
         * - 주로 MySQL, PostgreSQL, SQL Server, DB2에서 사용
         * - JPA는 보통 트랜잭션 커밋 시점에 INSERT SQL 실행
         * - AUTO_INCREMENT는 데이터베이스에 INSERT SQL을 실행한 이후에 ID 값을 알 수 있음
         * - IDENTITY 전략은 em.persist() 시점에 즉시 INSERT SQL을 실행하고 DB에서 식별자를 조회
         */

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {
            Member member = new Member();
            member.setName("D");

            System.out.println("==================");
            em.persist(member);
            System.out.println("member.Id = " + member.getId());
            System.out.println("==================");

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }

        emf.close();
    }

}
