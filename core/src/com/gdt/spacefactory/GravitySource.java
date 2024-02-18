package com.gdt.spacefactory;

import com.badlogic.gdx.math.Vector2;

public class GravitySource {
    public Vector2 position;
    public float gravity;
    public float mass;

    public GravitySource(Vector2 position, float gravity, float mass) {
        this.position = position;
        this.gravity = gravity;
        this.mass = mass;
    }
}
