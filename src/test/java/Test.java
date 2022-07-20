import com.reji.www.dto.DishDto;
import com.reji.www.entity.Dish;

import java.util.UUID;

public class Test {
    @org.junit.jupiter.api.Test
    public void test(){
        String originalFilename = "djkfkjafjvkaklfvkldfjv.jpg";

        String lastName = originalFilename.substring(originalFilename.lastIndexOf("."));

        String fileName = UUID.randomUUID().toString()+lastName;
        System.out.println(fileName);
    }


    @org.junit.jupiter.api.Test
    public void test1(){
        Dish dish = new Dish();
        dish.setCategoryId(12154L);
        DishDto dishDto = (DishDto) dish;

    }
}
