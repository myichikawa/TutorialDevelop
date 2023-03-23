package com.techacademy.controller;

import java.util.Set;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.techacademy.entity.User;
import com.techacademy.service.UserService;

@Controller
@RequestMapping("user")
public class UserController {
    private final UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    /**一覧画面を表示*/
    @GetMapping("/list")
    public String getList(Model model) {
        //全件検索結果をModelに登録
        model.addAttribute("userlist", service.getUserList());
        //user/list.htmlに画面遷移
        return "user/list";
    }

    /** User登録画面を表示 */
    @GetMapping("/register")
    public String getRegister(@ModelAttribute User user) {
        // User登録画面に遷移
        return "user/register";
    }

    //@ModelAttributeアノテーションを付与すると、自動的に（テンプレートにデータを受け渡す）Modelにインスタンスが登録されます。
    //これは@ModelAttributeアノテーションを付与せずに model.addAttribute("user", user); と記述することと同一です。
    //テンプレート側では user を使い <input type="text" th:field="${user.name}> のように記述できます。
    //実際に生成されるHTMLでは th:field="${user.name} の部分が id="name" name="name" のように変換されます。
    //これによりHTMLのFormの項目が、Userエンティティの項目に紐付けられます。


    /** User登録処理 */
    @PostMapping("/register")
    public String postRegister(@Validated User user, BindingResult res, Model model) {
        if(res.hasErrors()) {
            // エラーあり
            return getRegister(user);
        }
        // User登録
        service.saveUser(user);
        // 一覧画面にリダイレクト
        return "redirect:/user/list";
    }

    //POST側では引数にエンティティを指定することで、HTMLのFormの項目値が、引数のuserの属性としてセットされた状態で受け取ることができます。

    //@Validated アノテーションにより User エンティティの設定に基づいた入力チェックが行なわれます。入力チェックの結果は BindingResult res に格納されます。
    //res.hasErrors() でエラーの有無を確認できます。エラーだった場合は getUser() メソッドを呼び出すことで、User更新画面を表示します。



    /** User更新画面を表示 */
    @GetMapping("/update/{id}/")
    public String getUser(@PathVariable("id") Integer id, Model model, User user) {


        if(id != null) {
            //Modelに登録
            model.addAttribute("user", service.getUser(id));
        }else {
            model.addAttribute("user", user);
        }
        //User更新画面に遷移
        return "user/update";

    }

    @PostMapping("/update/{id}/")
    public String postUser(@Validated User user, BindingResult res, Model model) {

        if(res.hasErrors()) {
            // エラーあり
            return getUser(null, model, user);
        }
        //User登録
        service.saveUser(user);
        //一覧画面にリダイレクト
        return "redirect:/user/list";
    }



    //@PathVariable("id") Integer id でパスパラメータからidを取得しています。
    //そのidを使用し、サービスの getUser(id) 関数で更新対象のUserを取得し、Modelに登録しています。
    //User更新処理はUser登録処理と同一の内容です


    /** User削除処理 */
    @PostMapping(path="list", params="deleteRun")
    public String deleteRun(@RequestParam(name="idck") Set<Integer> idck, Model model) {
        // Userを一括削除
        service.deleteUser(idck);
        // 一覧画面にリダイレクト
        return "redirect:/user/list";
    }
    //Userを削除するためにdeleteRunメソッドが追加
    //deleteRunメソッドの引数に@RequestParam(name="idck") Set<Integer> idckの記述があります。
    //@RequestParamアノテーションで、リクエストパラメータからidckの配列値を取得しています。
    //取得したidckの値をサービスに渡して、対象のUserを削除

}
