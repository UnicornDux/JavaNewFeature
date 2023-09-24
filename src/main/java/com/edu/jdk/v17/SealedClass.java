package com.edu.jdk.v17;

// JDK 17 中正式引入了 密封类,
// 限制了密封类的继承关系和抽象类的实现类(避免抽像类被滥用)
public class SealedClass {

}

// 同一个密封类可以有多个允许的继承子类,需要在类被定义的时候就列出
// 每个继承的子类都必须要申明自己是否继承密封的特性 (
//  > final<失去继承性>,
//  > no-sealed<失去密封性>
//  > sealed<继续保留密封性> 需要指定密封子类型
sealed class Human  permits Person, Woman, Man {}
// 密封类的实现类可以是非密封类
non-sealed class Person extends Human {}
sealed class Woman extends Human permits Belle {}
final class Belle extends Woman {}
final class Man extends Human {}

// 不在密封类允许的实现类中不能继承该类
// class Barbarian extends Human {}

// 继承密封类丢失密封性之后可以被任意其他类继承
class HandSome extends Person {}