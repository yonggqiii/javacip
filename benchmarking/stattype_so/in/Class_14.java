import java.lang.reflect.Method;
public class Class_14
{

    public static void main(String[] args) throws Exception {
        String dogClassName = "com.mypackage.bean.Dog";
        Class<?> dogClass = Class.forName(dogClassName); // convert string classname to class
        Object dog = dogClass.newInstance(); // invoke empty constructor

        String methodName = "";

        // with single parameter, return void
        methodName = "setName";
        Method setNameMethod = dog.getClass().getMethod(methodName, String.class);
        setNameMethod.invoke(dog, "Mishka"); // pass arg

        // without parameters, return string
        methodName = "getName";
        Method getNameMethod = dog.getClass().getMethod(methodName, String.class);
        String name = (String) getNameMethod.invoke(dog, ""); // explicit cast

        // with multiple parameters
        methodName = "printDog";
        Method printDogMethod = dog.getClass().getMethod(methodName, String.class, Integer.class);
        printDogMethod.invoke(dog, name, 3); // pass args
    }
    
}
