package com.techacademy.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.techacademy.entity.User;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
class UserControllerTest {
    private MockMvc mockMvc;

    //MockMvc はHTTPリクエストを擬似的に再現するクラスです。コントローラーのテストで使用します。
    //@AutoConfigureMockMvc は MockMvc の設定を行なうアノテーションです。

    private final WebApplicationContext webApplicationContext;

    UserControllerTest(WebApplicationContext context){
        this.webApplicationContext = context;
    }

    @BeforeEach
    void beforeEach() {
        // Spring Securityを有効にする
        mockMvc = MockMvcBuilders
                .webAppContextSetup(webApplicationContext)
                .apply(springSecurity()).build();
    }

    //@BeforeEach アノテーションを付けると、各テストの前に、この処理が実行されます。本プロジェクトではSpring Securityを使用しているため、各テストの前に有効化しています


    @Test
    @DisplayName("User更新画面")
                                 //@DisplayName でJUnitビューに表示されるテスト名を設定できます
    @WithMockUser
    void testGetUser()throws Exception {
        //HTTPリクエストに対するレスポンスの検証
        MvcResult result = mockMvc.perform(get("/user/update/1/"))//URLにアクセス
                .andExpect(status().isOk())//ステータスを確認
                .andExpect(model().attributeExists("user")) // Modelの内容を確認 (viewに渡しているModelにuserが登録されていることを確認)
                .andExpect(model().hasNoErrors()) // Modelのエラー有無の確認
                .andExpect(view().name("user/update")) // viewの名称確認
                .andReturn(); // 内容の取得.内容は result 変数に格納

            // userの検証
            // Modelからuserを取り出す
            User user = (User)result.getModelAndView().getModel().get("user");
            assertEquals(user.getId(), 1);
            assertEquals(user.getName(), "キラメキ太郎");
    }

}
