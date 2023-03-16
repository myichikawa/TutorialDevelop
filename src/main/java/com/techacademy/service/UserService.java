package com.techacademy.service;

import java.util.List;
import java.util.Set;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;
import com.techacademy.entity.User;
import com.techacademy.repository.UserRepository;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository repository) {
        this.userRepository = repository;
    }

    /**全件を検索して返す*/
    public List<User> getUserList(){
        //リポジトリのfindAllメソッドを呼び出す
        return userRepository.findAll();
    }

    /** Userを1件検索して返す */
    public User getUser(Integer id) {
        return userRepository.findById(id).get();
    }

    /** Userの登録を行なう */
    @Transactional
    public User saveUser(User user) {
        return userRepository.save(user);
    }

    /** Userの削除を行なう */
    @Transactional
    public void deleteUser(Set<Integer> idck) {
        for(Integer id : idck) {
            userRepository.deleteById(id);
        }
    }
    //Userを削除するために deleteUser メソッド追加
    //deleteUser メソッドでは、引数として渡された主キーのSet idck（画面で選択した削除対象UserのIDすべて）をfor文で1件ずつ取り出し、
    //JpaRepositoryインターフェイスの定義済みメソッドdeleteByIdで削除を行なっています

}
