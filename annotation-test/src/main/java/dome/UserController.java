package dome;

/**
 * @Auther: wwt
 * @Date: 2020/12/27 18:20
 * @Description:
 */
public class UserController {
    static UserService service = new UserService();

    public static void main(String[] args) throws Exception {
        System.out.println(service.getAllUser());
    }
}
