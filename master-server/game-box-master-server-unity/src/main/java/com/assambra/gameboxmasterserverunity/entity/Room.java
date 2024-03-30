package com.assambra.gameboxmasterserverunity.entity;

import com.tvd12.ezyfox.builder.EzyBuilder;
import com.assambra.gameboxmasterserverunity.constant.IRoomStatus;
import com.assambra.gameboxmasterserverunity.constant.RoomStatus;
import lombok.Getter;
import lombok.Setter;

import java.util.concurrent.atomic.AtomicLong;

@Getter
public class Room {

    protected final long id;
    protected final String name;
    @Setter
    protected String password;
    @Setter
    protected IRoomStatus status = RoomStatus.WAITING;

    protected static final String NAME_PREFIX = "Room#";

    public Room(String name) {
        this.name = name;
        this.id = Builder.ID_GENERATOR.incrementAndGet();
    }

    protected Room(Builder<?> builder) {
        this.id = builder.id;
        this.name = builder.name;
    }

    public static Builder<?> builder() {
        return new Builder<>();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        return id == ((Room) obj).id;
    }

    @Override
    public int hashCode() {
        return Long.hashCode(id);
    }

    @Override
    public String toString() {
        return "(" +
            "name = " + name +
            ", id = " + id +
            ")";
    }

    @SuppressWarnings("unchecked")
    public static class Builder<B extends Builder<B>>
        implements EzyBuilder<Room> {

        protected Long id;
        protected String name;
        protected static final AtomicLong ID_GENERATOR
            = new AtomicLong(0);

        public B id(long id) {
            this.id = id;
            return (B) this;
        }

        public B name(String name) {
            this.name = name;
            return (B) this;
        }

        @Override
        public Room build() {
            if (id == null) {
                this.id = ID_GENERATOR.incrementAndGet();
            }
            if (name == null) {
                this.name = NAME_PREFIX + id;
            }
            preBuild();
            return newProduct();
        }

        protected void preBuild() {}

        protected Room newProduct() {
            return new Room(this);
        }
    }
}
