package graph;

import java.util.Collections;
import java.util.Map;
import java.util.Set;

public class Empty<L> implements Graph<L> {
    
    public Empty() {
    }
    
    public boolean add(L vertex) {
        return true;
    }
    
    public int set(L source, L target, int weight) {
        return 0;
    }
    
    public boolean remove(L vertex) {
        return false;
    }
    
    public Set<L> vertices() {
        final Set<L> EmptySet = Collections.emptySet();
        return EmptySet;
    }
    
    // Should these maps be final? I am not certain...
    public Map<L, Integer> sources(L target) {
        final Map<L, Integer> EmptyMap = Collections.emptyMap();
        return EmptyMap;
    }
    
    public Map<L, Integer> targets(L source) {
        final Map<L, Integer> EmptyMap = Collections.emptyMap();
        return EmptyMap;
    }
    
}