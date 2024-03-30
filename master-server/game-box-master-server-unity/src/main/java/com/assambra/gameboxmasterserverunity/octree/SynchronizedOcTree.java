package com.assambra.gameboxmasterserverunity.octree;

import com.assambra.gameboxmasterserverunity.entity.PositionAware;
import com.assambra.gameboxmasterserverunity.math.Vec3;

import java.util.List;

public class SynchronizedOcTree<T extends PositionAware> extends OcTree<T> {
    
    public SynchronizedOcTree(
        Vec3 leftBottomBack,
        Vec3 rightTopFront,
        int maxItemsPerNode,
        float minNodeSize
    ) {
        super(leftBottomBack, rightTopFront, maxItemsPerNode, minNodeSize);
    }
    
    public boolean insert(T item) {
        synchronized (this) {
            return super.insert(item);
        }
    }
    
    public boolean remove(T item) {
        synchronized (this) {
            return super.remove(item);
        }
    }

    public List<T> search(T item, float range) {
        synchronized (this) {
            return super.search(item, range);
        }
    }
    
    public boolean contains(T item) {
        synchronized (this) {
            return super.contains(item);
        }
    }
    
    public OcTreeNode<T> findNodeContainingPosition(Vec3 position) {
        synchronized (this) {
            return super.findNodeContainingPosition(position);
        }
    }
    
    public boolean isItemRemainingAtSameNode(T item, Vec3 newPosition) {
        synchronized (this) {
            return super.isItemRemainingAtSameNode(item, newPosition);
        }
    }
}
