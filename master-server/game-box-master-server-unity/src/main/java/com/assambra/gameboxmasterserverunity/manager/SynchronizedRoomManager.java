package com.assambra.gameboxmasterserverunity.manager;

import com.assambra.gameboxmasterserverunity.entity.Room;

import java.util.List;
import java.util.function.Predicate;

public class SynchronizedRoomManager<R extends Room>
    extends AbstractRoomManager<R> {

    public SynchronizedRoomManager() {
        this(10000);
    }

    public SynchronizedRoomManager(int maxRoom) {
        super(maxRoom);
    }

    protected SynchronizedRoomManager(Builder<?, ?> builder) {
        super(builder);
    }

    @SuppressWarnings("rawtypes")
    public static Builder builder() {
        return new Builder<>();
    }

    @Override
    public void addRoom(R room, boolean failIfAdded) {
        synchronized (this) {
            super.addRoom(room, failIfAdded);
        }
    }

    @Override
    public void addRooms(R[] rooms, boolean failIfAdded) {
        synchronized (this) {
            super.addRooms(rooms, failIfAdded);
        }
    }

    @Override
    public void addRooms(Iterable<R> rooms, boolean failIfAdded) {
        synchronized (this) {
            super.addRooms(rooms, failIfAdded);
        }
    }

    @Override
    public R getRoom(long id) {
        synchronized (this) {
            return super.getRoom(id);
        }
    }

    @Override
    public R getRoom(String name) {
        synchronized (this) {
            return super.getRoom(name);
        }
    }

    @Override
    public R getRoom(Predicate<R> predicate) {
        synchronized (this) {
            return super.getRoom(predicate);
        }
    }

    @Override
    public List<R> getRoomList() {
        synchronized (this) {
            return super.getRoomList();
        }
    }

    @Override
    public void getRoomList(List<R> buffer) {
        synchronized (this) {
            super.getRoomList(buffer);
        }
    }

    @Override
    public List<R> getRoomList(Predicate<R> predicate) {
        synchronized (this) {
            return super.getRoomList(predicate);
        }
    }

    @Override
    public int getRoomCount() {
        synchronized (this) {
            return super.getRoomCount();
        }
    }

    @Override
    public boolean containsRoom(long id) {
        synchronized (this) {
            return super.containsRoom(id);
        }
    }

    @Override
    public boolean containsRoom(String name) {
        synchronized (this) {
            return super.containsRoom(name);
        }
    }

    @Override
    public void removeRoom(R room) {
        synchronized (this) {
            super.removeRoom(room);
        }
    }

    @Override
    public void removeRoom(long id) {
        synchronized (this) {
            super.removeRoom(id);
        }
    }

    @Override
    public void removeRoom(String name) {
        synchronized (this) {
            super.removeRoom(name);
        }
    }

    @Override
    public void removeRooms(Iterable<R> rooms) {
        synchronized (this) {
            super.removeRooms(rooms);
        }
    }

    @Override
    public boolean available() {
        synchronized (this) {
            return super.available();
        }
    }

    @Override
    public void clear() {
        synchronized (this) {
            super.clear();
        }
    }

    public static class Builder<R extends Room, B extends Builder<R, B>>
        extends AbstractRoomManager.Builder<R, B> {

        @Override
        public RoomManager<R> build() {
            return new SynchronizedRoomManager<>(this);
        }
    }
}
