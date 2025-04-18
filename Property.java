import java.util.*;

class Property<T> {
    private T value;
    private final Object owner;
    private final List<PropertyListener<T>> listeners = new ArrayList<>();
    
    public Property(Object owner, T value) {
        this.owner = owner;
        this.value = value;
    }
    
    public Object getOwner() {
        return owner;
    }
    
    public T get() {
        return value;
    }
    
    public void set(T newvalue) {
        if(!Objects.equals(value, newvalue)) {
            T oldvalue = this.value;
            this.value = newvalue;
            notifyListeners(oldvalue,newvalue);
        }
    }
    
    public void addListener(PropertyListener<T> listener) {
        listeners.add(listener);
    }
    
    public void removeListener(PropertyListener<T> listener) {
        listeners.remove(listener);
    }
    
    private void notifyListeners(T oldvalue, T newvalue) {
        for(PropertyListener<T> listener : listeners) {
            listener.valueChanged(this,oldvalue,newvalue);
        }
    }
}
