package hellojpa;

import hellojpa.entity.inheritance.Item;
import hellojpa.entity.inheritance.Movie;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class JpaMain {

    /*
     * 상속관계 매핑 전략
     *  - 조인 전략
     *  - 단일 테이블 전략
     *  - 구현 클래스마다 테이블 전략
     */

    /*
     * 조인 전략
     *  - 정석적인 전략
     * - 장점
     *  - 테이블이 정규화된다.
     *  - 외래키 참조 무결성 제약조건을 활용할 수 있다.
     *  - 저장공간을 효율적으로 사용한다.
     *
     * - 단점
     *  - 조회시 조인을 많이 사용, 성능 저하
     *  - 조회 쿼리가 복잡하다.
     *  - 데이터 저장시 INSERT SQL이 두번 실행된다.
     */

    /*
     * 단일 테이블 전략
     *  - 장점
     *   - 조인이 필요 없으므로 일반적으로 조회 성능이 빠르다.
     *   - 조회 쿼리가 단순하다.
     *
     *  - 단점
     *   - 자식 엔티티가 매핑한 컬럼은 모두 null을 허용해야 한다.
     *   - 단일 테이블에 모든 것을 저장하므로 테이블이 커질 수 있다. 상황에 따라서 조회 성능이 오히려 느려질 수 있다.
     */

    /*
     * 구현 클래스마다 테이블 전략
     *  - 이 전략은 데이터베이스 설계자와 ORM 전문가 둘 다 추천하지 않는 전략 XXX
     *  - 장점
     *   - 서브타입을 명확하게 구분해서 처리할 때 효과적
     *   - not null 제약조건 사용 가능
     *
     *  - 단점
     *   - 여러 자식 테이블을 함께 조회할 때 성능이 느림(UNION SQL 필요)
     *   - 자식 테이블을 통합해서 쿼리하기 어려움
     *   - 테이블이 여러개여서 상황에 따라서 조인이 많이 필요할 수 있다.
     */

    public static void main(String[] args) {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {

            Movie movie = new Movie();
            movie.setDirector("aaaa");
            movie.setActor("bbbb");
            movie.setName("바람과함께사라지다");
            movie.setPrice(10000);

            em.persist(movie);

            em.flush();
            em.clear();

            Item findItem = em.find(Item.class, movie.getId());
            System.out.println("findItem = " + findItem.getName());

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }

        emf.close();
    }
}
