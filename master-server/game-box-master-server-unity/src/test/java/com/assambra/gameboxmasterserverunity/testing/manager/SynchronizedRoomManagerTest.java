package com.assambra.gameboxmasterserverunity.testing.manager;

import com.assambra.gameboxmasterserverunity.entity.LocatedRoom;
import com.assambra.gameboxmasterserverunity.manager.SynchronizedRoomManager;
import com.assambra.gameboxmasterserverunity.entity.Room;
import com.assambra.gameboxmasterserverunity.exception.MaxRoomException;
import com.assambra.gameboxmasterserverunity.exception.RoomExistsException;
import com.tvd12.test.assertion.Asserts;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.Collections;

public class SynchronizedRoomManagerTest {

    @SuppressWarnings("unchecked")
    @Test
    public void test() {
        // given
        SynchronizedRoomManager<LocatedRoom> sut =
            (SynchronizedRoomManager<LocatedRoom>) SynchronizedRoomManager.builder()
                .maxRoom(2)
                .build();

        LocatedRoom room1 = LocatedRoom.builder()
            .name("room1")
            .build();

        LocatedRoom room2 = LocatedRoom.builder()
            .name("room2")
            .build();

        LocatedRoom roomx = LocatedRoom.builder()
            .name("roomx")
            .build();

        // when
        // then
        sut.addRoom(room1, false);
        sut.addRoom(room1, false);
        Throwable e1 = Asserts.assertThrows(() -> sut.addRoom(room1, true));
        Asserts.assertEqualsType(e1, RoomExistsException.class);
        sut.addRoom(room2, true);
        Throwable e2 = Asserts.assertThrows(() -> sut.addRoom(roomx, true));
        Asserts.assertEqualsType(e2, MaxRoomException.class);
        sut.clear();

        sut.addRooms(Collections.singletonList(room1), false);
        Throwable e3 = Asserts.assertThrows(
            () -> sut.addRooms(Collections.singletonList(room1), true)
        );
        Asserts.assertEqualsType(e3, RoomExistsException.class);
        sut.addRooms(Collections.singletonList(room2), true);
        Throwable e4 = Asserts.assertThrows(
            () -> sut.addRooms(Collections.singletonList(roomx), true)
        );
        Asserts.assertEqualsType(e4, MaxRoomException.class);

        Asserts.assertEquals(sut.getRoom("room1"), room1);
        Asserts.assertEquals(sut.getRoomList(), Arrays.asList(room1, room2), false);

        sut.removeRoom(room1.getId());
        Asserts.assertEquals(sut.getRoomCount(), 1);

        sut.removeRoom(room2.getName());
        Asserts.assertEquals(sut.getRoomCount(), 0);

        sut.addRoom(room1);

        Asserts.assertTrue(sut.available());

        sut.addRoom(room2);
        sut.removeRooms(Arrays.asList(room1, room2));
        Asserts.assertEquals(sut.getRoomCount(), 0);

        sut.addRoom(room1);
        sut.addRoom(room2);

        Asserts.assertEquals(sut.getRoom(it -> it.getName().equals("room1")), room1);
        Asserts.assertNull(sut.getRoom(it -> it.getName().equals("unknown")));
        Asserts.assertEquals(
            sut.getRoomList(it -> it.getName().equals("room1")),
            Collections.singletonList(room1),
            false
        );

        sut.clear();
        sut.addRooms(new LocatedRoom[]{room1}, false);
        Throwable e5 = Asserts.assertThrows(() -> sut.addRooms(new LocatedRoom[]{room1}, true));
        Asserts.assertEqualsType(e5, RoomExistsException.class);
        sut.addRooms(new LocatedRoom[]{room2}, true);
        Throwable e6 = Asserts.assertThrows(() -> sut.addRooms(new LocatedRoom[]{roomx}, true));
        Asserts.assertEqualsType(e6, MaxRoomException.class);
        Asserts.assertFalse(sut.available());

        Asserts.assertTrue(sut.containsRoom(room1.getId()));
        Asserts.assertTrue(sut.containsRoom("room1"));

        sut.clear();
        sut.removeRoom((LocatedRoom) null);
    }

    @Test
    public void toStringTest() {
        // given
        SynchronizedRoomManager<Room> sut = new SynchronizedRoomManager<>();

        // when
        String actual = sut.toString();

        // then
        Asserts.assertEquals(
            actual,
            "roomByName.size = 0, roomById.size = 0"
        );
    }
}
