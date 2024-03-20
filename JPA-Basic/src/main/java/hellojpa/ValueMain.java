package hellojpa;

import hellojpa.entity.Address;

public class ValueMain {

    /*
     * 값 타입 비교
     * - 동일성(identity) 비교: 인스턴스의 참조 값을 비교, == 사용
     * - 동등성(equivalence) 비교: 인스턴스의 값을 비교, equals() 사용
     * - 값 타입은 a.equals(b)를 사용해서 동등성 비교를 해야 한다.
     * - 값 타입의 equals 메서드를 적절하게 재정의(구현)해야 한다.
     */

    public static void main(String[] args) {
        int a = 10;
        int b = 10;

        System.out.println("a == b: " + (a == b));

        Address address1 = new Address("city", "street", "10000");
        Address address2 = new Address("city", "street", "10000");

        System.out.println("address1 == address2: " + (address1 == address2));
        System.out.println("address1 equals address2: " + (address1.equals(address2)));
    }
}
