package com.uzdz.study;

import com.uzdz.study.jpa.UserRepository;
import com.uzdz.study.module.entity.User;
import mockit.Expectations;
import mockit.Mocked;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Calendar;
import java.util.Date;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

/**
 * @author ：uzdz
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SimpleStudyApplication.class)
public class MyTest extends AbstractJUnit4SpringContextTests {

    @Mocked
    UserRepository userRepository;

    int i = 0;

    @Before
    public void init() {
        i =100;
    }

    @Test
    public void t1() {
        Assert.assertEquals("wowoowow",i, 1010);
    }

    @Test
    public void t2() {
        Assert.assertEquals("wowoowow",i, 100);
    }

    @Ignore
    @Test
    public void t3() {
        Assert.assertEquals("wowoowow",i, 1010);
    }

    @Test(timeout = 2000)
    public void t4() throws InterruptedException {
        TimeUnit.SECONDS.sleep(3);
        Assert.assertEquals("wowoowow",i, 1010);
    }

    @Test
    public void t5() {
//        new Expectations() {
//            {
//                cal.get(Calendar.YEAR);          // 对cal.get方法进行录制，并匹配参数 Calendar.YEAR
//                result = 2016;                   // 年份不再返回当前小时。而是返回2016年
//
//                cal.get(Calendar.HOUR_OF_DAY);   // 对cal.get方法进行录制，并匹配参数 Calendar.HOUR_OF_DAY
//                result = 7;                      // 小时不再返回当前小时。而是返回早上7点钟
//            }
//        };
//        Assert.assertTrue(cal.get(Calendar.YEAR) == 2016);
//        Assert.assertTrue(cal.get(Calendar.HOUR_OF_DAY) == 7);
//        // 因为没有录制过，所以这里月份返回默认值 0
//        Assert.assertTrue(cal.get(Calendar.DAY_OF_MONTH) == 0);

        // 录制，定义被模拟的方法的返回值，与被调用的次数
        new Expectations(){{
            userRepository.findById(1);
            result = User.builder().age(222).build();
            times = 1;
        }};


        //Assert.assertEquals("wowoowow",i, 1010);
        Optional<User> byId = userRepository.findById(1);
        System.out.println(byId.get().getAge());
    }

    // 把对象传入Expectations的构造函数
    @Test
    public void testRecordConstrutctor2() {
        Calendar cal = Calendar.getInstance();
        // 把待Mock的对象传入Expectations的构造函数，可以达到只mock类的部分行为的目的，但只对这个对象影响
        new Expectations(cal) {
            {
                cal.get(Calendar.HOUR_OF_DAY);           // 只对get方法并且参数为Calendar.HOUR_OF_DAY进行录制
                result = 7;                             // 小时永远返回早上7点钟
            }
        };

        // 因为下面的调用mock过了，小时永远返回7点钟了
        Assert.assertTrue(cal.get(Calendar.HOUR_OF_DAY) == 7);

        // 因为下面的调用没有mock过，所以方法的行为不受mock影响，
        Assert.assertTrue(cal.get(Calendar.DAY_OF_MONTH) == (new Date()).getDate());

        // now是另一个对象，上面录制只对cal对象的影响，所以now的方法行为没有任何变化
        Calendar now = Calendar.getInstance();

        // 不受mock影响
        Assert.assertTrue(now.get(Calendar.HOUR_OF_DAY) == (new Date()).getHours());

        // 不受mock影响
        Assert.assertTrue(now.get(Calendar.DAY_OF_MONTH) == (new Date()).getDate());
    }

}
