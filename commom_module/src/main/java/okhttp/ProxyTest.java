package okhttp;

/**
 * Created by ldp.
 * <p>
 * Date: 2021/10/28
 * <p>
 * Summary:
 */
public class ProxyTest {
    public ProxyTest() {
    }

    public interface UserService{
        void select();
        void update();
    }

    public class UserServiceImpl implements UserService{

        @Override
        public void select() {

        }

        @Override
        public void update() {

        }
    }


}
