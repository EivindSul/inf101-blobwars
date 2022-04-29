package inf101.grid;

public class BlobLocation {
    private Location fromLoc;
    private Location toLoc;

    public BlobLocation(Location fromLoc, Location toLoc){
        this.fromLoc = fromLoc;
        this.toLoc = toLoc;
    }
    
    public Location getLocFrom() {
        return this.fromLoc;
    }
    
    public Location getLocTo() {
        return this.toLoc;
    }
}
