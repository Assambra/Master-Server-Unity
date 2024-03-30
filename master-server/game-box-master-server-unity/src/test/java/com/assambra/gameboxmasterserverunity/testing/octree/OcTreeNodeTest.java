package com.assambra.gameboxmasterserverunity.testing.octree;

import com.assambra.gameboxmasterserverunity.entity.PositionAware;
import com.assambra.gameboxmasterserverunity.entity.MMOPlayer;
import com.assambra.gameboxmasterserverunity.math.Vec3;
import com.assambra.gameboxmasterserverunity.octree.OcTreeNode;
import com.assambra.gameboxmasterserverunity.math.Bounds;
import com.tvd12.test.assertion.Asserts;
import com.tvd12.test.reflect.MethodUtil;
import org.testng.annotations.Test;

import java.lang.reflect.Method;

public class OcTreeNodeTest {
    
    @Test
    public void insertItemToChildrenWithNullOutputTest() throws NoSuchMethodException {
        // given
        Vec3 leftBottomBack = new Vec3(0, 0, 0);
        Vec3 rightTopFront = new Vec3(2, 2, 2);
        Vec3 outsidePosition = new Vec3(3, 3, 3);
        Bounds bounds = new Bounds(leftBottomBack, rightTopFront);
        OcTreeNode<MMOPlayer> node = new OcTreeNode<>(bounds, 1, 0.01f);
        
        MMOPlayer player1 = MMOPlayer.builder()
            .name("player1")
            .build();
        player1.setPosition(new Vec3(0.5f, 0.5f, 0.5f));
        
        MMOPlayer player2 = MMOPlayer.builder()
            .name("player2")
            .build();
        player2.setPosition(new Vec3(1.5f, 1.5f, 1.5f));
        
        MMOPlayer player3 = MMOPlayer.builder()
            .name("player3")
            .build();
        player3.setPosition(outsidePosition);
        
        node.insert(player1);
        node.insert(player2);
        
        // when
        Method method = OcTreeNode.class.getDeclaredMethod(
            "insertItemToChildren",
            PositionAware.class
        );
        method.setAccessible(true);
        Object nodeContainingInsertedItem = MethodUtil.invokeMethod(
            method,
            node,
            player3
        );
        
        // then
        Asserts.assertNull(nodeContainingInsertedItem);
    }
    
    @Test
    public void findNodeContainingPositionFromChildrenWithNullOutputTest()
        throws NoSuchMethodException {
        // given
        Vec3 leftBottomBack = new Vec3(0, 0, 0);
        Vec3 rightTopFront = new Vec3(2, 2, 2);
        Vec3 outsidePosition = new Vec3(3, 3, 3);
        Bounds bounds = new Bounds(leftBottomBack, rightTopFront);
        OcTreeNode<MMOPlayer> node = new OcTreeNode<>(bounds, 1, 0.01f);
        
        MMOPlayer player1 = MMOPlayer.builder()
            .name("player1")
            .build();
        player1.setPosition(new Vec3(0.5f, 0.5f, 0.5f));
        
        MMOPlayer player2 = MMOPlayer.builder()
            .name("player2")
            .build();
        player2.setPosition(new Vec3(1.5f, 1.5f, 1.5f));
        
        node.insert(player1);
        node.insert(player2);
        
        // when
        Method method = OcTreeNode.class.getDeclaredMethod(
            "findNodeContainingPositionFromChildren",
            Vec3.class
        );
        method.setAccessible(true);
        Object nodeContainingPosition = MethodUtil.invokeMethod(
            method,
            node,
            outsidePosition
        );
        
        // then
        Asserts.assertNull(nodeContainingPosition);
    }
}
