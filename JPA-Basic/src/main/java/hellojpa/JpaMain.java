package hellojpa;

import hellojpa.entity.Member;
import hellojpa.value.RoleType;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class JpaMain {

    /**
     * hbm2ddl.auto 어노테이션
     * - @Column : 컬럼매핑
     * - @Temporal : 날짜 타입 매핑
     * - @Enumerated : enum 타입 매핑
     * - @Lob : BLOB, CLOB 매핑
     * - @Transient : 특정 필드를 컬럼에 매핑하지 않음
     */

    /**
     * Column 어노테이션
     * - name : 필드와 매핑할 테이블의 컬럼 이름 (default : 객체 필드 이름)
     * - insertable, updatable : 등록, 변경 가능 여부 설정 (default : true)
     * - nullable : null 값의 허용 여부 설정 (default : true)
     * - unique : 유니크 제약 조건 설정 (default : false)
     * - columnDefinition : 데이터베이스 컬럼 정보를 직접 설정
     * - length : 문자 길이 제약 조건 설정 (String 타입에만 사용) (default : 255)
     */

    /**
     * Enumerated 어노테이션
     * - EnumType.ORDINAL : enum 순서를 DB에 저장 (default)
     * - EnumType.STRING : enum 이름을 DB에 저장
     * - 주로 EnumType.STRING을 사용
     * - ORDINAL은 enum 순서가 바뀌면 데이터가 꼬일 수 있음
     * - EnumType.STRING은 enum 이름이 바뀌어도 데이터가 꼬이지 않음
     */

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        try {

            Member member = new Member();
            member.setId(3L);
            member.setUsername("C");
            member.setRoleType(RoleType.GUEST);

            em.persist(member);

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }

        emf.close();
    }

}
