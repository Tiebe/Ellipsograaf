package nl.tiebe.ellipsograaf

import com.badlogic.gdx.ApplicationAdapter
import com.badlogic.gdx.graphics.Camera
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.*
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType
import com.badlogic.gdx.utils.ScreenUtils


class Main : ApplicationAdapter() {
    private lateinit var batch: SpriteBatch
    private lateinit var img: Texture
    private lateinit var world: World
    private lateinit var debugRenderer: Box2DDebugRenderer
    private lateinit var camera: OrthographicCamera

    override fun create() {
        Box2D.init()

        camera = OrthographicCamera()
        camera.setToOrtho(false, 800f, 480f)

        world = World(Vector2(0f, 0f), true)
        debugRenderer = Box2DDebugRenderer()

        batch = SpriteBatch()
        img = Texture("badlogic.jpg")

        val bodyDef = BodyDef()

        bodyDef.type = BodyType.DynamicBody

        bodyDef.position.set(100f, 300f)

        val body: Body = world.createBody(bodyDef)

        val circle = CircleShape()
        circle.radius = 100f

        val fixtureDef = FixtureDef()
        fixtureDef.shape = circle
        fixtureDef.density = 0.5f
        fixtureDef.friction = 0.4f
        fixtureDef.restitution = 0.6f // Make it bounce a little bit

        val fixture: Fixture = body.createFixture(fixtureDef)

        circle.dispose()

    }

    override fun render() {
        ScreenUtils.clear(0f, 0f, 0f, 1f)
        camera.update()

        world.step(1/60f, 6, 2)

        debugRenderer.render(world, camera.combined)
    }

    override fun dispose() {
        batch.dispose()
        img.dispose()
        debugRenderer.dispose()
        world.dispose()
    }
}
