/*
 * The MIT License (MIT)
 *
 * FXGL - JavaFX Game Library
 *
 * Copyright (c) 2015-2016 AlmasB (almaslvl@gmail.com)
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.almasb.mario;

import com.almasb.fxgl.entity.Entities;
import com.almasb.fxgl.entity.GameEntity;
import com.almasb.fxgl.entity.component.CollidableComponent;
import com.almasb.fxgl.entity.control.LiftControl;
import com.almasb.fxgl.physics.BoundingShape;
import com.almasb.fxgl.physics.HitBox;
import com.almasb.fxgl.physics.PhysicsComponent;
import com.almasb.mario.control.PlayerControl;
import com.almasb.mario.types.PickupType;
import com.almasb.mario.types.PlatformType;
import com.almasb.mario.types.SubTypeComponent;
import javafx.geometry.BoundingBox;
import org.jbox2d.dynamics.BodyType;

import static com.almasb.mario.MarioApp.BLOCK_SIZE;

/**
 * @author Almas Baimagambetov (almaslvl@gmail.com)
 */
public class EntityFactory {

    private static GameEntity makeGenericPlatform(double x, double y) {
        return Entities.builder()
                .at(x * BLOCK_SIZE, y * BLOCK_SIZE)
                .type(EntityType.PLATFORM)
                .with(new SubTypeComponent(PlatformType.NORMAL))
                .build();
    }

    public static GameEntity makeCheckpoint(double x, double y) {
        return Entities.builder()
                .at(x * BLOCK_SIZE, y * BLOCK_SIZE)
                .type(EntityType.CHECKPOINT)
                .viewFromTextureWithBBox("checkpoint.png")
                .with(new CollidableComponent(true))
                .build();
    }

    public static GameEntity makeFinish(double x, double y) {
        return Entities.builder()
                .at(x * BLOCK_SIZE, y * BLOCK_SIZE)
                .type(EntityType.FINISH)
                .viewFromTextureWithBBox("finish.png")
                .with(new CollidableComponent(true))
                .build();
    }

    public static GameEntity makePickupCoin(double x, double y) {
        return Entities.builder()
                .at(x * BLOCK_SIZE, y * BLOCK_SIZE)
                .type(EntityType.PICKUP)
                .viewFromTextureWithBBox("coin.png")
                .with(new CollidableComponent(true))
                .with(new SubTypeComponent(PickupType.COIN))
                .build();
    }

    public static GameEntity makePlatformBegin(double x, double y) {
        GameEntity e = makeGenericPlatform(x, y);
        e.getMainViewComponent().setTexture("platform_begin.png", true);

        PhysicsComponent physics = new PhysicsComponent();
        e.addComponent(physics);

        return e;
    }

    public static GameEntity makePlatformEnd(double x, double y) {
        GameEntity e = makeGenericPlatform(x, y);
        e.getMainViewComponent().setTexture("platform_end.png", true);

        PhysicsComponent physics = new PhysicsComponent();
        e.addComponent(physics);

        return e;
    }

    public static GameEntity makePlatform(double x, double y) {
        GameEntity e = makeGenericPlatform(x, y);
        e.getMainViewComponent().setTexture("platform.png", true);

        PhysicsComponent physics = new PhysicsComponent();
        e.addComponent(physics);

        return e;
    }

    public static GameEntity makePlatformInvisible(double x, double y) {
        GameEntity e = Entities.builder()
                .at(x * BLOCK_SIZE, y * BLOCK_SIZE)
                .type(EntityType.PLATFORM)
                .viewFromTextureWithBBox("platform.png")
                .with(new CollidableComponent(true))
                .with(new SubTypeComponent(PlatformType.INVISIBLE))
                .build();

        e.getMainViewComponent().getView().setVisible(false);
        return e;
    }

    public static GameEntity makePlatformLift(double x, double y) {
        return Entities.builder()
                .at(x * BLOCK_SIZE, y * BLOCK_SIZE)
                .type(EntityType.PLATFORM)
                .viewFromTextureWithBBox("platform.png")
                //.with(new CollidableComponent(true))
                .with(new SubTypeComponent(PlatformType.NORMAL))
                //.with(new LiftControl())
                .build();
    }

    public static GameEntity makePlatformCarry(double x, double y) {
        return Entities.builder()
                .at(x * BLOCK_SIZE, y * BLOCK_SIZE)
                .type(EntityType.PLATFORM)
                .viewFromTextureWithBBox("platform.png")
                //.with(new CollidableComponent(true))
                .with(new SubTypeComponent(PlatformType.NORMAL))
                .build();
    }

    public static GameEntity makeBlock(double x, double y) {
        GameEntity e = makeGenericPlatform(x, y);
        e.getMainViewComponent().setTexture("block.png", true);

        PhysicsComponent physics = new PhysicsComponent();
        e.addComponent(physics);

        return e;
    }

    public static GameEntity makePlayer(double x, double y) {
        PhysicsComponent physics = new PhysicsComponent();
        physics.setBodyType(BodyType.DYNAMIC);

        GameEntity p = Entities.builder()
                .at(x * BLOCK_SIZE, y * BLOCK_SIZE)
                .type(EntityType.PLAYER)
                .viewFromTexture("player.png")
                .with(new CollidableComponent(true))
                .with(physics)
                .with(new PlayerControl())
                .build();

        p.getBoundingBoxComponent().addHitBox(new HitBox("BODY", new BoundingBox(0, 0, 30, 30), BoundingShape.CIRCLE));

        return p;
    }
}
