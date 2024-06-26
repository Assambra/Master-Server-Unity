package com.assambra.common.masterserver.manager;

import com.assambra.common.masterserver.constant.Commands;
import com.assambra.common.masterserver.entity.UnityRoom;
import com.tvd12.ezyfoxserver.entity.EzyUser;
import com.tvd12.ezyfoxserver.support.factory.EzyResponseFactory;
import com.tvd12.gamebox.manager.AbstractRoomManager;
import com.tvd12.gamebox.manager.RoomManager;

import java.util.List;
import java.util.function.Predicate;

public class SynchronizedUnityRoomManager<R extends UnityRoom> extends AbstractRoomManager<R> {
    public SynchronizedUnityRoomManager() {
        this(10000);
    }

    public SynchronizedUnityRoomManager(int maxRoom) {
        super(maxRoom);
    }

    protected SynchronizedUnityRoomManager(SynchronizedUnityRoomManager.Builder<?, ?> builder) {
        super(builder);
    }

    @SuppressWarnings("rawtypes")
    public static SynchronizedUnityRoomManager.Builder builder() {
        return new SynchronizedUnityRoomManager.Builder<>();
    }

    protected EzyResponseFactory responseFactory;

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
            sendServerStop(room.getName());
        }
    }

    @Override
    public void removeRoom(long id) {
        synchronized (this) {
            super.removeRoom(id);
            sendServerStop(getRoom(id).getName());
        }
    }

    @Override
    public void removeRoom(String name) {
        synchronized (this) {
            super.removeRoom(name);
            sendServerStop(getRoom(name).getName());
        }
    }

    @Override
    public void removeRooms(Iterable<R> rooms) {
        synchronized (this) {
            super.removeRooms(rooms);
            for (UnityRoom room : rooms)
            {
                sendServerStop(room.getName());
            }
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

    private void sendServerStop(String username)
    {
        responseFactory.newObjectResponse()
                .command(Commands.SERVER_STOP)
                .username(username)
                .execute();
    }

    public static class Builder<R extends UnityRoom, B extends SynchronizedUnityRoomManager.Builder<R, B>>
            extends AbstractRoomManager.Builder<R, B> {

        @Override
        public RoomManager<R> build() {
            return new SynchronizedUnityRoomManager<>(this);
        }
    }
}
