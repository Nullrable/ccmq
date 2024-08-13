package io.cc.mq.store;

/**
 * @author nhsoft.lsd
 */
public class Demo {

    public static void main(String[] args) {

        Store store = new Store("com.lsd.test");

        int pos = 0;
        for (int i = 0; i < 10; i++) {
            System.out.println(store.get(pos));
            pos = store.next_pos(pos);
            System.out.println("======>>>>>" + pos);
        }
    }
}
