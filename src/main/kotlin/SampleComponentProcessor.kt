import engine.EngineComponent
import engine.EngineObject
import tiledmap.engineobjects.EngineComponentProcessor
import tiledmap.engineobjects.model.ObjectCustomProperty
import tiledmap.engineobjects.model.ObjectProperties
import tiledmap.tilesets.TileSet

class SampleComponentProcessor : EngineComponentProcessor {
    override fun processEngineComponent(
        engineObject: EngineObject,
        objectProperties: ObjectProperties,
        tileSets: List<TileSet>,
        path: String,
        customProperty: ObjectCustomProperty
    ): EngineComponent {
        return SampleComponent()
    }
}