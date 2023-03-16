package com.techacademy.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "user")
public class User {

    /** 性別用の列挙型 */
    public static enum Gender {
        男性, 女性
    }

    /** 主キー。自動生成 */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /** 名前。20桁。null不許可 */
    @Column(length = 20, nullable = false)
    private String name;

    /** 性別。2桁。列挙型（文字列） */
    @Column(length = 2)
    @Enumerated(EnumType.STRING)
    private Gender gender;

    /** 年齢 */
    private Integer age;

    /** メールアドレス。50桁。null許可 */
    @Column(length = 50)
    private String email;

}

//@Column はSpring JPAのアノテーション
//フィールドをマッピングするテーブルのカラム（項目）を指定,name="カラム名"でマッピングするカラム名を指定できます。name属性を省略した場合、フィールド名がマッピングするカラム名
//length : フィールドの桁数を指定します。桁数指定ができるフィールドは文字列型か配列型のみです。数値型は指定できません
//nullable フィールドのNOT NULL制約を指定します。値が「true」のときはnull値を許可し、「false」のときはnull値を許可しません。デフォルト値は「true」です。
//@Enumerated はSpring JPAのアノテーション
//このフィールドの型が列挙型であることを指定, EnumType.STRING 属性でテーブルのカラムを文字列型に指定します。これにより、カラムには「男性」などの列挙子の名前が格納されます

