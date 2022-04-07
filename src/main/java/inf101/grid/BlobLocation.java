package inf101.grid;

public class BlobLocation {
    Location fromLoc;
    Location toLoc;

    public BlobLocation(Location fromLoc, Location toLoc){
        this.fromLoc = fromLoc;
        this.toLoc = toLoc;
    }

    public Location getNeighbor(GridDirection dir) {
		return dir.getNeighbor(this.getLocFrom());
    }
    
    public Location getLocFrom() {
        return this.fromLoc;
    }
    
    public Location getLocTo() {
        return this.toLoc;
    }


}
