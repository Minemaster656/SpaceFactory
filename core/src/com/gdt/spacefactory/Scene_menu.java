package com.gdt.spacefactory;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ExtendViewport;


import java.util.ArrayList;

//import jdk.tools.jlink.internal.Platform;

public class Scene_menu implements Screen, InputProcessor {
    public Music mainTrack;
    private SpriteBatch batch;
    public float ScrWidth;
    public float ScrHeight;
    public OrthographicCamera camera;
    public Stage stage;
    public ExtendViewport viewport;
    public ShapeRenderer shapeRenderer;
    BitmapFont font_default = new BitmapFont();
    private ArrayList<TextureRegion> starTextures;
    //    ArrayList<Star> stars = new ArrayList<>();
    private ArrayList<TextureRegion> nebulaTextures;
    //    ArrayList<Nebula> nebulas = new ArrayList<>();
    int starsLimit;
    int nebulaLimit;
    long windowSpace;
    float starCountModifier = 1f;
    float nebulaCountModifier = 1f;
    float screenBordersMedian;
    public Color gradientColor1 = new Color(0.01f, 0.9f, 0.8f, 0.7f);
    public Color gradientColor2 = new Color(1, 0, 0.83f, 0.7f);
    Button button_play;
    Skin skin;
    com.badlogic.gdx.scenes.scene2d.ui.Button.ButtonStyle buttonPlayStyle = new Button.ButtonStyle();
    public Texture logo;
    public Image logo_ui;
    public Application.ApplicationType platform = Gdx.app.getType();
    public ArrayList<DummyGameObject> drawables = new ArrayList<>();
    public TextureRegion logo_image;


    @Override
    public void show() {
        System.out.println("Shown scene: " + this.getClass().getName());
        ScrHeight = Gdx.graphics.getHeight();
        ScrWidth = Gdx.graphics.getWidth();
        viewport = new ExtendViewport(ScrWidth, ScrHeight);
        camera = new OrthographicCamera(ScrWidth, ScrHeight);
        camera.position.set(camera.viewportWidth / 2f, camera.viewportHeight / 2f, 0);

        skin = new Skin();
        skin.add("buttonPlay", new Texture("buttonPlay.png"));
        skin.add("buttonPlay_down", new Texture("buttonPlay_down.png"));

        buttonPlayStyle.up = skin.getDrawable("buttonPlay");
        buttonPlayStyle.down = skin.getDrawable("buttonPlay_down");

        button_play = new Button(buttonPlayStyle);
        button_play.setPosition((int) ((ScrWidth / 2) - (button_play.getScaleX() / 2)), (int) ((ScrHeight / 2) - (button_play.getScaleY() / 2)));

        button_play.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
//                Gdx.app.log("Button", "Clicked!");
                System.out.println("Clicked!");
                Main.messages.add("open scene_main");
            }
        });


        load_textures();
//        recalcStarCount();
//        fixStarsArraySize();
//        recalcNebulaCount();
//        fixNebulaArraySize();

        for (int i = 0; i < 10; i++) {
            drawables.add(new DummyGameObject(new Vector2(Utils.randFloat(0, ScrWidth), Utils.randFloat(0, ScrHeight)), 0f, new Vector2(1f, 1f), logo_image, false, false));
        }
        logo_ui = new Image(logo);
//        logo_ui.setScale(platform== Application.ApplicationType.Desktop ? 0.4f : 0.6f);
        logo_ui.setScale(0.4f);

        logo_ui.setPosition((ScrWidth / 2) - (102.4f * 1.9f), ScrHeight - 140);

        logo_ui.setAlign(Align.center);
        shapeRenderer = new ShapeRenderer();
        shapeRenderer.setAutoShapeType(true);
        batch = new SpriteBatch();
        batch.setProjectionMatrix(camera.combined);
        stage = new Stage(viewport, batch);
        stage.addActor(logo_ui);
        stage.addActor(button_play);
        camera.update();
        mainTrack.play();
    }

    private void load_textures() {
//        starTextures = Utils.splitRegion(new Texture("stars.png"), 8, 8);
//        nebulaTextures = Utils.splitRegion(new Texture("nebulas_white.png"), 64, 64);
        mainTrack = Gdx.audio.newMusic(Gdx.files.internal("SpaceTD-MenuTheme.mp3"));
        mainTrack.setLooping(true);
        logo = new Texture("SpaceTDLogo0_4_mvp.png");
        logo_image = new TextureRegion(new Texture("SpaceFactoryIcon0.png"));
    }

    @Override
    public void render(float delta) {
        Gdx.input.setInputProcessor(stage);
        batch.begin();
        shapeRenderer.begin();
        ScreenUtils.clear(0, 0, 0, 1);
        batch.setProjectionMatrix(camera.combined);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

//        for (int i = 0; i < nebulas.size(); i++) {
//            nebulas.get(i).draw(batch);
//        }
//        for (int i = 0; i < stars.size(); i++) {
//            if (stars.get(i).isDestroyed) stars.set(i, Star.makeStar(starTextures));
//            stars.get(i).draw(batch);
//            Main.debugData = "Stars count: " + stars.size() + "\n";
//        }

        for (int i = 0; i < drawables.size(); i++) {
            drawables.get(i).draw(batch);
        }

        stage.act(delta);
        if (batch.isDrawing()) batch.end();

        stage.draw();
        if (shapeRenderer.isDrawing()) shapeRenderer.end();
        camera.update();
//        for (int i = 0; i < nebulas.size(); i++) {
//            Vector2 topLeft = new Vector2(camera.position.x - camera.viewportWidth / 2, camera.position.y + camera.viewportHeight / 2);
//            Vector2 bottomRight = new Vector2(camera.position.x + camera.viewportWidth / 2, camera.position.y - camera.viewportHeight / 2);
//
//            nebulas.get(i).updateColor(topLeft, bottomRight, gradientColor1, gradientColor2);
//        }
    }

    @Override
    public void resize(int width, int height) {
        camera.viewportWidth = width;
        camera.viewportHeight = height;
        camera.update();
        viewport.setMinWorldWidth(width);
        viewport.setMinWorldHeight(height);
        stage.setViewport(viewport);
//        recalcStarCount();
//        fixStarsArraySize();
//        recalcNebulaCount();
//        fixNebulaArraySize();
    }

    @Override
    public void pause() {
        mainTrack.stop();

    }

    @Override
    public void resume() {
        mainTrack.play();

    }

    @Override
    public void hide() {
        mainTrack.stop();
    }

    @Override
    public void dispose() {
        batch.dispose();
        shapeRenderer.dispose();
        stage.dispose();
//        for (int i = 0; i < stars.size(); i++) {
//            stars.get(i).dispose();
//        }
        mainTrack.stop();
        mainTrack.dispose();
    }

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchCancelled(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(float amountX, float amountY) {
        return false;
    }

    public void recalcStarCount() {
        windowSpace = (long) Gdx.graphics.getHeight() * Gdx.graphics.getWidth();
        screenBordersMedian = (Gdx.graphics.getHeight() + Gdx.graphics.getWidth()) >> 1;
        starsLimit = (int) ((windowSpace / screenBordersMedian) * starCountModifier);
    }

    public void fixStarsArraySize() {
//        if (stars.size() < starsLimit) {
//            for (int i = 0; i < starsLimit - stars.size(); i++) {
//                stars.add(Star.makeStar(starTextures));
//            }
//        }
//        if (stars.size() > starsLimit) {
//            for (int i = starsLimit - 1; i < stars.size() - 1; i++) {
//                stars.get(i).lifetime = 0;
//            }
//        }
    }

    public void recalcNebulaCount() {
        windowSpace = (long) Gdx.graphics.getHeight() * Gdx.graphics.getWidth();
        screenBordersMedian = (Gdx.graphics.getHeight() + Gdx.graphics.getWidth()) >> 1;
        nebulaLimit = (int) ((windowSpace / screenBordersMedian) * nebulaCountModifier);
    }

    public void fixNebulaArraySize() {
        Vector2 topLeft = new Vector2(camera.position.x - camera.viewportWidth / 2, camera.position.y + camera.viewportHeight / 2);
        Vector2 bottomRight = new Vector2(camera.position.x + camera.viewportWidth / 2, camera.position.y - camera.viewportHeight / 2);
//        if (nebulas.size() < nebulaLimit) {
//            for (int i = 0; i < nebulaLimit - nebulas.size(); i++) {
//                nebulas.add(Nebula.makeNebula(topLeft, bottomRight, gradientColor1, gradientColor2, nebulaTextures.get(Utils.randInt(0, nebulaTextures.size() - 1))));
//            }
//        }
//        if (nebulas.size() > nebulaLimit) {
//            while (nebulas.size() > nebulaLimit) {
//                nebulas.remove(0);
//            }
//        }
    }
}