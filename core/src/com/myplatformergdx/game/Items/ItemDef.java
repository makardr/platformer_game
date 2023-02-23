package com.myplatformergdx.game.Items;

import com.badlogic.gdx.math.Vector2;

public class ItemDef implements Comparable{
    public Vector2 position;
    //    Class that we don't know
    public Class<?> type;

    public ItemDef(Vector2 position, Class<?> type) {
        this.position = position;
        this.type = type;
    }

    @Override
    public int compareTo(Object o) {
        return 0;
    }
}
