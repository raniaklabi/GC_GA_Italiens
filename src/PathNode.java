class PathNode implements Comparable<PathNode> {
	  String name;
	  String parent;
	  // distance to the root
	  double distance2root;

	  public PathNode(String name, String parent, double distance2root) {
	    this.name = name;
	    this.parent = parent;
	    this.distance2root = distance2root;
	  }

	  public int compareTo(PathNode another) {
	    return (int) (distance2root - another.distance2root);
	  }

	 
	  public String toString() {
	    return "(" + this.name + "," + this.parent + "," + this.distance2root + ")";
	  }
	}