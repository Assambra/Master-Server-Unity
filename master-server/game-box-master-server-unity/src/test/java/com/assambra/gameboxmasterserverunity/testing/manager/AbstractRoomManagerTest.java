package com.assambra.gameboxmasterserverunity.testing.manager;

import com.assambra.gameboxmasterserverunity.entity.Room;
import com.assambra.gameboxmasterserverunity.manager.AbstractRoomManager;
import com.tvd12.test.assertion.Asserts;
import org.testng.annotations.Test;

public class AbstractRoomManagerTest {

    @Test
    public void test() {
        // given
        InternalRoomManager sut = new InternalRoomManager();

        // when
        // then
        Asserts.assertEquals(sut.getMaxRoom(), 10000);
    }

    private static class InternalRoomManager extends AbstractRoomManager<Room> {}
}
