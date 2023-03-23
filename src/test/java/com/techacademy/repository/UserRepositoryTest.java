package com.techacademy.repository;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.techacademy.entity.User;
@SpringBootTest
@ExtendWith(SpringExtension.class)
class UserRepositoryTest {
    private final UserRepository userRepository;

    @Autowired
    UserRepositoryTest(UserRepository repository){
        this.userRepository = repository;
    }

    @Test
    void testFindById() {
        User user = userRepository.findById(1).get();
        assertEquals(user.getId(),1);
        assertEquals(user.getName(),"キラメキ太郎");
    }

}

//@SpringBootTest アノテーションにより、Spring Bootアプリケーションの初期化が行なわれます。
//これにより、 data.sql などが実行され、テストに必要な環境が準備されます。
//また、テストコードで @Autowired などのSpring Bootのアノテーションを使用できるようになります。

//JUnitには専用のメソッドが用意されています。assertEquals() は2つの値が等しいかどうかを判定するメソッドです