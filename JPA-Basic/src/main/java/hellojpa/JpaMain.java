package hellojpa;

import hellojpa.entity.Address;
import hellojpa.entity.AddressEntity;
import hellojpa.entity.Member;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class JpaMain {

    /*
     * 값 타입컬렉션의 제약사항
     *  - 값 타입은 엔티티와 다르게 식별자 개념이 없다.
     *  - 값은 변경하면 추적이 어렵다.
     *  - 값 타입 컬렉션에 변경사항이 발생하면, 주인 엔티티와 연관된 모든 데이터를 삭제하고, 값 타입 컬렉션에 있는 현재값을 모두 다시 저장한다.
     *  - 따라서 값 타입 컬렉션에 변경사항이 발생하면, 추적이 어렵고, 비용이 많이 든다.
     */

    /*
     * 정리
     * 1. 엔티티 타입의 특징
     *  - 식별자 O
     *  - 생명주기 관리 O
     *  - 공유 O
     *
     * 2. 값 타입의 특징
     *  - 식별자 X
     *  - 생명주기를 엔티티에 의존
     *  - 공유하지 않는 것이 안전
     *  - 불변 객체로 만드는 것이 안전
     *
     */

    public static void main(String[] args) {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {
            Member member = new Member();
            member.setUserName("member1");
            member.setHomeAddress(new Address("HomeCity", "street", "zipcode"));

            member.getFavoriteFoods().add("치킨");
            member.getFavoriteFoods().add("족발");
            member.getFavoriteFoods().add("피자");

            member.getAddressHistory().add(new AddressEntity("old1", "street", "zipcode"));
            member.getAddressHistory().add(new AddressEntity("old2", "street", "zipcode"));

            em.persist(member);

            em.flush();
            em.clear();

            System.out.println("============== START ==============");
            Member findMember = em.find(Member.class, member.getId());
            Address a = findMember.getHomeAddress();
            findMember.setHomeAddress(new Address("newCity", "street", "zipcode"));

            findMember.setHomeAddress(new Address("newCity", a.getStreet(), a.getZipcode()));

            findMember.getFavoriteFoods().remove("치킨");
            findMember.getFavoriteFoods().add("한식");


            System.out.println("============== END ==============");

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }
        emf.close();
    }
}
