package com.almasb.fxglgames.tictactoe;

import com.almasb.fxgl.entity.GameEntity;
import com.almasb.fxglgames.tictactoe.control.TileControl;

/**
 * Instead of using generic GameEntity we add a few convenience methods.
 *
 * @author Almas Baimagambetov (almaslvl@gmail.com)
 */
public class TileEntity extends GameEntity {

    public TileEntity(double x, double y) {
        setX(x);
        setY(y);
        addComponent(new TileValueComponent());

        getViewComponent().setView(new TileView(this), true);
        addControl(new TileControl());
    }

    public TileValue getValue() {
        return getComponentUnsafe(TileValueComponent.class).getValue();
    }

    public TileControl getControl() {
        return getControlUnsafe(TileControl.class);
    }
}
