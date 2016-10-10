package engineTester;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector3f;

import entities.Camera;
import entities.Entity;
import entities.Light;
import entities.Player;
import models.RawModel;
import models.TexturedModel;
import objConverter.ModelData;
import objConverter.OBJLoader;
import renderEngine.DisplayManager;
import renderEngine.Loader;
import renderEngine.MasterRenderer;
import terrains.Terrain;
import textures.ModelTexture;
import textures.TerrainTexture;
import textures.TerrainTexturePack;

public class MainGameLoop {
    
    public static void main(String[] args) {
        
        DisplayManager.createDisplay();
        Loader loader = new Loader();
        
        //********* TERRAIN TEXTURES **********
        
        TerrainTexture backgroundTexture = new TerrainTexture(loader.loadTexture("grass2"));
        TerrainTexture rTexture = new TerrainTexture(loader.loadTexture("mud"));
        TerrainTexture gTexture = new TerrainTexture(loader.loadTexture("grassFlowers"));
        TerrainTexture bTexture = new TerrainTexture(loader.loadTexture("path"));
        
        TerrainTexturePack texturePack = new TerrainTexturePack(backgroundTexture, rTexture, gTexture, bTexture);
        TerrainTexture blendMap = new TerrainTexture(loader.loadTexture("blendMap"));
        
        //*************************************
        
        ModelData tree = OBJLoader.loadOBJ("tree");
        ModelData grass = OBJLoader.loadOBJ("grassModel");
        ModelData fern = OBJLoader.loadOBJ("fern");
        
        RawModel treeModel = loader.loadToVAO(tree.getVertices(), tree.getTextureCoords(), tree.getNormals(), tree.getIndices());
        RawModel grassModel  = loader.loadToVAO(grass.getVertices(), grass.getTextureCoords(), grass.getNormals(), grass.getIndices());
        RawModel fernModel  = loader.loadToVAO(fern.getVertices(), fern.getTextureCoords(), fern.getNormals(), fern.getIndices());
        
        TexturedModel treeTex = new TexturedModel(treeModel, new ModelTexture(loader.loadTexture("tree")));
        TexturedModel grassTex = new TexturedModel(grassModel, new ModelTexture(loader.loadTexture("grassTexture")));
        TexturedModel fernTex = new TexturedModel(fernModel, new ModelTexture(loader.loadTexture("fern")));
        
        treeTex.getTexture().setHasTransperancy(true);
        grassTex.getTexture().setUseFakeLighting(true);
        fernTex.getTexture().setHasTransperancy(true);
        
        List<Entity> entities = new ArrayList<>();
        Random random = new Random();
        
        for (int i = 0; i < 1000; i++) {
        	float x = random.nextFloat() * 1600 - 800;
        	float z = random.nextFloat() * -800;
        	entities.add(new Entity(treeTex, new Vector3f(x, 0.6f, z), 0, 0, 0, 2));
        }
        
        for (int i = 0; i < 1000; i++) {
        	float x = random.nextFloat() * 1600 - 800;
        	float z = random.nextFloat() * -800;
        	entities.add(new Entity(grassTex, new Vector3f(x, 0.1f, z), 0, 0, 0, 1));
        }
        
        for (int i = 0; i < 1000; i++) {
        	float x = random.nextFloat() * 1600 - 800;
        	float z = random.nextFloat() * -800;
        	entities.add(new Entity(fernTex, new Vector3f(x, 0.1f, z), 0, 0, 0, 1));
        }
        
        Light light = new Light(new Vector3f(20000, 40000, 20000), new Vector3f(1, 1, 1));
        
        Terrain terrain = new Terrain(-1, -1, loader, texturePack, blendMap);
        Terrain terrain2 = new Terrain(0, -1, loader, texturePack, blendMap);
        
        
        MasterRenderer renderer = new MasterRenderer();
        
        ModelData cube = OBJLoader.loadOBJ("cube");
        RawModel cubeModel = loader.loadToVAO(cube.getVertices(), cube.getTextureCoords(), cube.getNormals(), cube.getIndices());
        TexturedModel cubeTex = new TexturedModel(cubeModel, new ModelTexture(loader.loadTexture("yellow")));
        
        Player player = new Player(cubeTex, new Vector3f(100, 5, -50), 0, 0, 0, 1);
        Camera camera = new Camera(player);
        
        while(!Display.isCloseRequested()) {
            camera.move();
            player.move();
            renderer.processEntity(player);
            
            renderer.processTerrain(terrain);
            renderer.processTerrain(terrain2);
            for (Entity entity : entities)
            	renderer.processEntity(entity);

            renderer.render(light, camera);
            DisplayManager.updateDisplay();
        }
        
        renderer.cleanUp();
        loader.cleanUp();
        DisplayManager.closeDisplay();
        
    }

}
