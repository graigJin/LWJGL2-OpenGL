package textures;

public class ModelTexture {
    
    private int textureID;
    
    private float shineDamper = 1;
    private float reflectivity = 0;
    
    private boolean hasTransperancy = false;
    private boolean useFakeLighting = false;

    public ModelTexture(int textureID) {
        this.textureID = textureID;
    }

    public int getTextureID() {
        return textureID;
    }

	public float getShineDamper() {
		return shineDamper;
	}

	public void setShineDamper(float shineDamper) {
		this.shineDamper = shineDamper;
	}

	public float getReflectivity() {
		return reflectivity;
	}

	public void setReflectivity(float reflectivity) {
		this.reflectivity = reflectivity;
	}

	public boolean hasTransperancy() {
		return hasTransperancy;
	}

	public void setHasTransperancy(boolean hasTransperancy) {
		this.hasTransperancy = hasTransperancy;
	}

	public boolean useFakeLighting() {
		return useFakeLighting;
	}

	public void setUseFakeLighting(boolean useFakeLighting) {
		this.useFakeLighting = useFakeLighting;
	}
	

    
}
