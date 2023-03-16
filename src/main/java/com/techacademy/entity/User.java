package com.techacademy.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.PreRemove;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.springframework.transaction.annotation.Transactional;

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
    @NotEmpty // 追加
    @Length(max=20) // 追加
    private String name;

    /** 性別。2桁。列挙型（文字列） */
    @Column(length = 2)
    @Enumerated(EnumType.STRING)
    @NotNull // 追加
    private Gender gender;

    /** 年齢 */
    @Min(0) // 追加
    @Max(120) // 追加
    private Integer age;

    /** メールアドレス。50桁。null許可 */
    @Column(length = 50)
    @Email // 追加
    @Length(max=50) // 追加
    private String email;

    @OneToOne(mappedBy="user")
    private Authentication authentication;

    /** レコードが削除される前に行なう処理 */
    @PreRemove
    @Transactional
    private void preRemove() {
        // 認証エンティティからuserを切り離す
        if (authentication!=null) {
            authentication.setUser(null);
        }
    }
}

//@Column はSpring JPAのアノテーション
//フィールドをマッピングするテーブルのカラム（項目）を指定,name="カラム名"でマッピングするカラム名を指定できます。name属性を省略した場合、フィールド名がマッピングするカラム名
//length : フィールドの桁数を指定します。桁数指定ができるフィールドは文字列型か配列型のみです。数値型は指定できません
//nullable フィールドのNOT NULL制約を指定します。値が「true」のときはnull値を許可し、「false」のときはnull値を許可しません。デフォルト値は「true」です。
//@Enumerated はSpring JPAのアノテーション
//このフィールドの型が列挙型であることを指定, EnumType.STRING 属性でテーブルのカラムを文字列型に指定します。これにより、カラムには「男性」などの列挙子の名前が格納されます

//@NotEmpty：値が空ではないことをチェックする。
//@Length(max=20)：値の長さをチェックする。max属性は許可する最大長を指定する。
//@NotNull：値がnullではないことをチェックする。
//@Min(value=0)：数値の最小値をチェックする。value属性は最小値を指定する。
//@Max(value=120)：数値の最大値をチェックする。value属性は最大値を指定する。
//@Email：値がeメールアドレスかどうかをチェックする。

//認証エンティティと同様、@OneToOneでリレーションを指定しています。
//mappedBy="user" は認証エンティティ側でのユーザエンティティを参照するプロパティを指定します
//@PreRemove アノテーションは、レコードが削除される前に行なう処理であることを示します。
//MySQLではエンティティで OneToOne リレーションを定義すると、テーブルに外部キー制約が設定され、Userレコードの削除を行なおうとするとエラーになります
// （java.sql.SQLIntegrityConstraintViolationException 例外）。
//これを回避するために、Userを削除する前に認証エンティティからuserを切り離しています
