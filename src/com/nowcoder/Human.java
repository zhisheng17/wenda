package com.nowcoder;

/**
 * Created by 10412 on 2016/7/17.
 */
public class Human extends Animal
{
    private String country;

    public Human(String name, int age, String country)
    {
        super(name, age);
        this.country = country;
    }

    @Override
    public void say()
    {
        System.out.println(" Human say from "+country);
    }
}
