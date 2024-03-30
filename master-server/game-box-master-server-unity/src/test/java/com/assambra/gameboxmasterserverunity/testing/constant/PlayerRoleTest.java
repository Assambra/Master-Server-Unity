package com.assambra.gameboxmasterserverunity.testing.constant;

import com.assambra.gameboxmasterserverunity.constant.PlayerRole;
import com.tvd12.test.assertion.Asserts;
import org.testng.annotations.Test;

public class PlayerRoleTest {

    @Test
    public void test() {
        Asserts.assertEquals(PlayerRole.MASTER.getId(), 1);
        Asserts.assertEquals(PlayerRole.MASTER.getName(), "MASTER");
        Asserts.assertEquals(PlayerRole.valueOf(1), PlayerRole.MASTER);
    }
}
