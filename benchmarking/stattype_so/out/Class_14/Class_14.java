import java.lang.reflect.Method;

public class Class_14 {

    public static void main(String[] args) throws Exception {
        String dogClassName = "com.mypackage.bean.Dog";
        // convert string classname to class
        Class<?> dogClass = Class.forName(dogClassName);
        // invoke empty constructor
        Object dog = dogClass.newInstance();
        String methodName = "";
        // with single parameter, return void
        methodName = "setName";
        Method setNameMethod = dog.getClass().getMethod(methodName, String.class);
        // pass arg
        setNameMethod.invoke(dog, "Mishka");
        // without parameters, return string
        methodName = "getName";
        Method getNameMethod = dog.getClass().getMethod(methodName, String.class);
        // explicit cast
        String name = (String) getNameMethod.invoke(dog, "");
        // with multiple parameters
        methodName = "printDog";
        Method printDogMethod = dog.getClass().getMethod(methodName, String.class, Integer.class);
        // pass args
        printDogMethod.invoke(dog, name, 3);
    }
}
