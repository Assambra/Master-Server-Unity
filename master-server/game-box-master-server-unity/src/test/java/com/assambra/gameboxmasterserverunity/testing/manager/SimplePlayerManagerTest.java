package com.assambra.gameboxmasterserverunity.testing.manager;

import com.assambra.gameboxmasterserverunity.entity.Player;
import com.assambra.gameboxmasterserverunity.manager.SimplePlayerManager;
import com.tvd12.test.assertion.Asserts;
import org.testng.annotations.Test;

public class SimplePlayerManagerTest {

    @SuppressWarnings("unchecked")
    @Test
    public void test() {
        // given
        SimplePlayerManager<Player> sut =
            (SimplePlayerManager<Player>) SimplePlayerManager.builder()
                .maxPlayer(100)
                .build();

        // when
        // then
        Asserts.assertEquals(sut.getMaxPlayer(), 100);
    }

    @Test
    public void defaultConstructorTest() {
        // given
        SimplePlayerManager<Player> sut = new SimplePlayerManager<>();

        // when
        // then
        Asserts.assertEquals(sut.getMaxPlayer(), 999999999);
    }

    @Test
    public void cConstructorWithMaxPlayerTest() {
        // given
        SimplePlayerManager<Player> sut = new SimplePlayerManager<>(200);

        // when
        // then
        Asserts.assertEquals(sut.getMaxPlayer(), 200);
    }
}
