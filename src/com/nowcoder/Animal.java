package com.nowcoder;

/**
 * Created by 10412 on 2016/7/17.
 */
public class Animal implements Talking
{
    private String name;
    private int age;

    public Animal(String name, int age)
    {
        this.name = name;
        this.age = age;
    }

    @Override
    public void say()
    {
        System.out.println(name+" Animal say");
    }


}
