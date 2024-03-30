package com.assambra.gameboxmasterserverunity.testing.manager;

import com.assambra.gameboxmasterserverunity.entity.Player;
import com.assambra.gameboxmasterserverunity.manager.DefaultPlayerManager;
import com.tvd12.test.assertion.Asserts;
import org.testng.annotations.Test;

import java.util.Arrays;

public class DefaultPlayerManagerTest {

    @SuppressWarnings("unchecked")
    @Test
    public void test() {
        // given
        DefaultPlayerManager<Player> sut =
            (DefaultPlayerManager<Player>) DefaultPlayerManager.builder()
                .maxPlayer(100)
                .build();

        Player player1 = Player.builder()
            .name("player1")
            .build();

        Player player2 = Player.builder()
            .name("player2")
            .build();

        // when
        sut.addPlayers(Arrays.asList(player1, player2));

        // then
        Asserts.assertEquals(sut.getMaxPlayer(), 100);
        Asserts.assertEquals(sut.getPlayerCount(), 2);
    }

    @Test
    public void defaultConstructorTest() {
        // given
        DefaultPlayerManager<Player> sut = new DefaultPlayerManager<>();

        // when
        // then
        Asserts.assertEquals(sut.getMaxPlayer(), 999999999);
    }

    @Test
    public void cConstructorWithMaxPlayerTest() {
        // given
        DefaultPlayerManager<Player> sut = new DefaultPlayerManager<>(200);

        // when
        // then
        Asserts.assertEquals(sut.getMaxPlayer(), 200);
    }
}
