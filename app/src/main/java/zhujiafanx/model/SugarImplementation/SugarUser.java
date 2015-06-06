package zhujiafanx.model.SugarImplementation;

import com.orm.SugarRecord;
import com.orm.dsl.Ignore;

import java.util.Date;

/**
 * Created by Administrator on 2015/6/6.
 */

    public class SugarUser extends SugarRecord<SugarUser>
    {
        //required fields,可以是private的？
        private String name;
        private String hashedPwd;
        private Date registerDate;

        //optional fields
        @Ignore
        Date lastLogin;
        @Ignore
        String email;
        @Ignore
        boolean sex;


        //默认构造方法是必要的
        public SugarUser()
        {

        }

        public SugarUser(String name, String password) {
            this.name = name;
            this.hashedPwd = password;
            this.registerDate = new Date();
        }

    }

