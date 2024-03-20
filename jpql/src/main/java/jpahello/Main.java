package jpahello;

import javax.persistence.*;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {

            Member member = new Member();
            member.setUsername("member1");
            member.setAge(10);
            em.persist(member);

            List<MemberDTO> resultList = em.createQuery("select new jpahello.MemberDTO(m.username, m.age) from Member m", MemberDTO.class)
                    .getResultList();

            for (MemberDTO memberDTO : resultList) {
                System.out.println("memberDTO = " + memberDTO.getUsername());

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