package com.gdt.spacefactory;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.Octree;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Disposable;

public abstract class GameObject<ColliderType extends Octree.Collider> implements Disposable {
    public Vector2 position;
    public float rotation;
    public Vector2 size;
    public Vector2 scale;
    public Vector2 originPoint;
    public TextureRegion texture;
    public Animation<Texture> animation;
    boolean flipX;
    boolean flipY;
    boolean showColliders = false;
    //    public Rectangle collider;
    public ColliderType collider;
    public String id;
//    public float velocity;

    //Physics lol
    public Vector2 velocity;
    public float angularVelocity;
    public float mass;
    public boolean usePhysics;
    public float gravityScale;

    Vector2 oldPos;
    public boolean isDestroyed;
//    public float glowSize = 0;
//    public ShaderProgram glowShader = Shaders.glowShader;
//    public Texture textureFromRegion;
//    public Color glowColor=new Color(1, 1, 1, 1);


    public GameObject(
            Vector2 position,
            float rotation,
            Vector2 scale,
            TextureRegion texture,
            boolean flipX,
            boolean flipY,

            boolean usePhysics,
            float mass //TODO: добавить физику в конструкторы
    ) {
        this.position = position;
        this.rotation = rotation;
        this.scale = scale;
        this.texture = texture;
        this.flipX = flipX;
        this.flipY = flipY;

        this.usePhysics = usePhysics;
        this.mass = mass;
        this.gravityScale = usePhysics ? 1 : 0;
        this.velocity=Vector2.Zero;
        this.angularVelocity = 0;

        this.id = this.getClass().getName() + Utils.randInt(0, 10000) + "";
        this.isDestroyed = false;
        this.originPoint = new Vector2(size.x / 2, size.y / 2);
        this.size = new Vector2(texture.getRegionWidth(), texture.getRegionHeight());


    }

    public GameObject(Vector2 position, float rotation, Vector2 size, Vector2 scale, Vector2 originPoint, TextureRegion texture, boolean flipX, boolean flipY) {
        this.position = position;
        this.rotation = rotation;
        this.size = size;
        this.scale = scale;
        this.originPoint = originPoint;
        this.texture = texture;
        this.flipX = flipX;
        this.flipY = flipY;
        this.id = this.getClass().getName() + Utils.randInt(0, 10000) + "";
        this.isDestroyed = false;
//        this.textureFromRegion=Utils.getTextureFromTextureRegion(this.texture);
    }

    public GameObject(Vector2 position, float rotation, Vector2 size, Vector2 scale, TextureRegion texture, boolean flipX, boolean flipY) {
        this.position = position;
        this.rotation = rotation;
        this.size = size;
        this.scale = scale;
        this.originPoint = new Vector2(size.x / 2, size.y / 2);
        this.texture = texture;
        this.flipX = flipX;
        this.flipY = flipY;
        this.id = this.getClass().getName() + Utils.randInt(0, 10000) + "";
        this.isDestroyed = false;
//        this.textureFromRegion=Utils.getTextureFromTextureRegion(this.texture);
    }

    public GameObject(Vector2 position, float rotation, Vector2 scale, TextureRegion texture, boolean flipX, boolean flipY) {
        this.position = position;
        this.rotation = rotation;
        this.size = new Vector2(texture.getRegionWidth(), texture.getRegionHeight());
        this.scale = scale;
        this.originPoint = new Vector2(size.x / 2, size.y / 2);
        this.texture = texture;
        this.flipX = flipX;
        this.flipY = flipY;
        this.id = this.getClass().getName() + Utils.randInt(0, 10000) + "";
        this.isDestroyed = false;
//        this.textureFromRegion=Utils.getTextureFromTextureRegion(this.texture);
    }

    public GameObject(Vector2 position, float rotation, TextureRegion texture, Vector2 scale, Vector2 originPoint, boolean flipX, boolean flipY) {
        this.position = position;
        this.rotation = rotation;
        this.size = new Vector2(texture.getRegionWidth(), texture.getRegionHeight());
        this.scale = scale;
        this.originPoint = originPoint;
        this.texture = texture;
        this.flipX = flipX;
        this.flipY = flipY;
        this.id = this.getClass().getName() + Utils.randInt(0, 10000) + "";
        this.isDestroyed = false;
//        this.textureFromRegion=Utils.getTextureFromTextureRegion(this.texture);
    }

    public abstract void update(float delta);

    public void draw(Batch batch) {
        //TODO:демона в виде нижних двух строчек оставить но не выпускать. Это нам ещё пригодится.
//        this.originPoint.x=this.position.x*this.size.x*this.scale.x/2;
//        this.originPoint.y=this.position.y*this.size.y*this.scale.y/2;

        if (animation != null) {
            batch.draw(animation.getKeyFrame(0f), position.x, position.y, size.x, size.y);
        } else {
            if (!batch.isDrawing())
                batch.begin();
            if (this.flipX & this.scale.x > 0)
                this.scale.x *= -1;

            if (this.flipY & this.scale.y > 0)
                this.scale.y *= -1;
//            if (!Shaders.glowShader.isCompiled()) {
//                Gdx.app.error("Shader", Shaders.glowShader.getLog());
//                System.out.println("Shader error!");
//                // Обработка ошибок компиляции, если необходимо
//            } else if (glowSize > 0) {
//
//                glowShader.begin();
//                textureFromRegion.bind(0);
//                glowShader.setUniformf("u_glowStrength", glowSize);
//                glowShader.setUniformi("u_texture", 0);
//                glowShader.setUniformf("u_glowColor", glowColor);
//                glowShader.end();
//                batch.setShader(glowShader);
//            }
            batch.draw(texture, this.position.x, this.position.y, this.originPoint.x, this.originPoint.y, this.size.x, this.size.y, this.scale.x, this.scale.y, this.rotation);
//            batch.setShader(null);

            if (this.showColliders) {
                onShowColliders();
            }
        }
        //PHYSICS!!!
        if (usePhysics){
            rotation+=angularVelocity;
            position.add(velocity);



        }



//        if (oldPos != null) {
//            velocity = this.position.dst(oldPos);
//        }
        oldPos = position;
    }

    public abstract void onShowColliders();

    public void onDestroy() {
        this.isDestroyed = true;
    }

    public void destroy() {
        onDestroy();
        this.dispose();
    }

    @Override
    public void dispose() {
    }

    public void moveForward(float dist) {

        float angleRad = (float) Math.toRadians(this.rotation);


        float x = this.position.x + dist * (float) Math.cos(angleRad);
        float y = this.position.y + dist * (float) Math.sin(angleRad);

        this.position.set(x, y);
    }
}