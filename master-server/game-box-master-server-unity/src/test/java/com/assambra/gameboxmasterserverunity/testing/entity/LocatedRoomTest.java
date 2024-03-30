package com.assambra.gameboxmasterserverunity.testing.entity;

import com.assambra.gameboxmasterserverunity.entity.LocatedRoom;
import com.assambra.gameboxmasterserverunity.exception.NoSlotException;
import com.assambra.gameboxmasterserverunity.entity.LocatedPlayer;
import com.assambra.gameboxmasterserverunity.manager.DefaultLocatedPlayerManager;
import com.tvd12.test.assertion.Asserts;
import org.testng.annotations.Test;

public class LocatedRoomTest {

    @Test
    public void test() {
        // given
        LocatedRoom sut = LocatedRoom.builder()
            .maxSlot(2)
            .playerManager(new DefaultLocatedPlayerManager())
            .build();

        LocatedPlayer player1 = LocatedPlayer.builder()
            .name("player1")
            .build();
        sut.addPlayer(player1);

        LocatedPlayer player2 = LocatedPlayer.builder()
            .name("player2")
            .build();
        sut.addPlayer(player2);

        LocatedPlayer playerx = LocatedPlayer.builder()
            .name("playerx")
            .build();

        // when
        // then
        Asserts.assertEquals(sut.getMaxSlot(), 2);
        Asserts.assertEmpty(sut.getSlots());

        sut.removePlayer(playerx);
        sut.removePlayer(player2);
        Asserts.assertEquals(sut.getPlayerManager().getPlayerCount(), 1);

        sut.addPlayer(player2);
        Throwable e = Asserts.assertThrows(() -> sut.addPlayer(playerx));
        Asserts.assertEqualsType(e, NoSlotException.class);
    }
}
