package com.assambra.gameboxmasterserverunity.testing.manager;

import com.assambra.gameboxmasterserverunity.entity.Room;
import com.assambra.gameboxmasterserverunity.manager.SimpleRoomManager;
import com.tvd12.test.assertion.Asserts;
import org.testng.annotations.Test;

public class SimpleRoomManagerTest {

    @SuppressWarnings("unchecked")
    @Test
    public void test() {
        // given
        SimpleRoomManager<Room> sut =
            (SimpleRoomManager<Room>) SimpleRoomManager.builder()
                .maxRoom(100)
                .build();

        // when
        // then
        Asserts.assertEquals(sut.getMaxRoom(), 100);
    }

    @Test
    public void defaultConstructorTest() {
        // given
        SimpleRoomManager<Room> sut = new SimpleRoomManager<>();

        // when
        // then
        Asserts.assertEquals(sut.getMaxRoom(), 10000);
    }

    @Test
    public void cConstructorWithMaxRoomTest() {
        // given
        SimpleRoomManager<Room> sut = new SimpleRoomManager<>(200);

        // when
        // then
        Asserts.assertEquals(sut.getMaxRoom(), 200);
    }
}
