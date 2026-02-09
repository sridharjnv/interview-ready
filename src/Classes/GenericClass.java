package Classes;

public class GenericClass<T> { // T can be replaced with any other data type except primitive types (int, double, etc.)
    T value;
    public T getValue(){
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }

    public static void main(String[] args) {
        GenericClass<Integer> obj = new GenericClass<Integer>();
        obj.setValue(10);
        Integer value = obj.getValue();

        System.out.println("Value: " + value);
    }
}
