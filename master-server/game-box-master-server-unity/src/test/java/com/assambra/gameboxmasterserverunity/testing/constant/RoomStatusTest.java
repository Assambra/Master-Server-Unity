package com.assambra.gameboxmasterserverunity.testing.constant;

import com.assambra.gameboxmasterserverunity.constant.RoomStatus;
import com.tvd12.test.assertion.Asserts;
import org.testng.annotations.Test;

public class RoomStatusTest {

    @Test
    public void test() {
        Asserts.assertEquals(RoomStatus.WAITING.getId(), 1);
        Asserts.assertEquals(RoomStatus.WAITING.getName(), "WAITING");
        Asserts.assertEquals(RoomStatus.valueOf(1), RoomStatus.WAITING);
    }
}
