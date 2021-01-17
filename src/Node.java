import java.util.HashMap;
import java.util.Map;



class Node {
        int value;
        double weight;
        String name;
        Map<Node, Double> neighbors = new HashMap<Node, Double>();
        Node(int value,String name, double weight)  {
            this.value = value;
            this.weight = weight;
            this.name=name;
        }
        public Node(String name) {
    	    this.name = name;
    	  }
        public void addNeighbor(Node neighbor, double distance) {
    	    neighbors.put(neighbor, distance);
    	  }
        
        public double getDistance(Node node) {
    		
    		return Math.sqrt(this.weight+node.weight);
    	}
        public Node[] getNeighbors() {
    	    Node[] result = new Node[neighbors.size()];
    	    neighbors.keySet().toArray(result);
    	    return result;
    	  }
        public double getNeighborDistance(Node node) {
    	    return neighbors.get(node);
    	  }
    }