package hellojpa;

import hellojpa.entity.Member;
import org.hibernate.Hibernate;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class JpaMain {

    /*
     * 프록시 기초
     *  - em.find() vs em.getReference()
     *  - em.find(): 데이터베이스를 통해서 실제 엔티티 객체 조회
     *  - em.getReference(): 데이터베이스 조회를 미루는 가짜(프록시) 엔티티 객체 조회
     */

    /*
     * 프록시 특징 1
     * - 실제 클래스를 상속 받아서 만들어짐
     * - 실제 클래스와 겉 모양이 같다.
     * - 사용하는 입장에서는 진짜 객체인지 프록시 객체인지 구분하지 않고 사용하면 됨(이론상)
     * - 프록시 객체는 실제 객체의 참조(target)를 보관
     * - 프록시 객체를 호출하면 프록시 객체는 실제 객체의 메소드 호출
     */

    /*
     * 프록시 특징 2
     * - 프록시 객체는 처음 사용할 때 한 번만 초기화
     * - 프록시 객체를 초기화 할 때, 프록시 객체가 실제 엔티티로 바뀌는 것은 아님, 초기화되면 프록시 객체를 통해서 실제 엔티티에 접근 가능
     * - 프록시 객체는 원본 엔티티를 상속받음, 따라서 타입 체크시 주의해야함 (== 비교 실패, 대신 instance of 사용)
     * - 영속성 컨텍스트에 찾는 엔티티가 이미 있으면 em.getReference()를 호출해도 실제 엔티티 반환
     * - 영속성 컨텍스트의 도움을 받을 수 없는 준영속 상태일 때, 프록시를 초기화하는 문제 발생 (하이버네이트는 org.hibernate.LazyInitializationException 예외를 터트림)
     */

    public static void main(String[] args) {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {
            Member member1 = new Member();
            member1.setUserName("member1");
            em.persist(member1);

            em.flush();
            em.clear();

            Member refMember = em.getReference(Member.class, member1.getId());
            System.out.println("refMember = " + refMember.getClass());
            Hibernate.initialize(refMember); // 강제 초기화
            System.out.println("isLoaded = " + emf.getPersistenceUnitUtil().isLoaded(refMember));

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            e.printStackTrace();
        } finally {
            em.close();
        }
        emf.close();
    }
}
