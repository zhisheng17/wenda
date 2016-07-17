package com.nowcoder;


import java.util.*;

public class Main
{
    public static void print(int index, Object object)
    {
        System.out.println(String.format("{%d}, %s", index, object.toString()));
    }

    public static void demoOperation()
    {
        print(1, 5 + 2);
        print(2, 5 - 2);
        print(3, 5 * 2);
        print(4, 5 / 2);
        print(5, 5 << 2);
        print(6, 5 >> 2);
        print(7, 5 % 2);
        print(8, 5 | 2);    //0x101  0x10  0x111
        print(9, 5  ^ 2);
        print(10, 5 == 2);
        print(11, 5 != 2);
        print(12, 5.0 / 2);
        print(13, 5 / 2.0);

        int a = 11;
        double b = 2.2f;
        a += 2;
        print(14, a);
    }

    public static void demoString()
    {
        String str = "hello world";
        print(1, str.indexOf('e'));
        print(2, str.charAt(1));
        print(3, str.codePointAt(1));
        print(4, str.compareToIgnoreCase("HELLO WORLD"));
        print(5, str.compareTo("hello xorld"));
        print(6, str.compareTo("hello uorld"));
        print(7, str.contains("hell"));
        print(8, str.concat("world"));
        print(9, str.toUpperCase());
        print(10, str.endsWith("d"));
        print(11, str.startsWith("he"));
        print(12, str.replace('l', 'a'));
        print(13, str.replaceAll("hello", "hi"));
        print(14, str.replaceAll("h|l", "a"));
        print(15, str + str);

        StringBuilder sb = new StringBuilder();  //线程不安全
        sb.append("x ");
        sb.append(1.2);
        sb.append('a');
        sb.append(true);
        print(16, sb.toString());
    }


    public static void demoControlFlow()
    {
        int a = 2;
        int x = a==2 ? 1 : 2;
        print(1, x);

        if (a==2)
            print(2, x=1);
        else
            print(3, x=2);

        String Grade = "A";
        switch (Grade)
        {
            case "A":
                print(4, ">80");
                break;
            case "B":
                print(5, "60~80");
                break;
            case "C":
                print(6, "<60");
                break;
            default:
                print(7, "你输入的分数有问题！");
                break;
        }

        for (int i = 0; i < 10; i++)
        {
            if (i%2==0)
                continue;
            print(8, i);
        }

        int y = 5;
        while (y>0)
        {
            print(9, y);
            y--;
        }
    }

    public static void demoList()
    {
        List<String> strList = new ArrayList<String>(10);
        for (int i = 0; i < 4; i++)
        {
            strList.add(String.valueOf(i*i));
        }
        print(1, strList);

        List<String> strListB = new ArrayList<String>(10);
        for (int i = 0; i < 4; i++)
        {
            strListB.add(String.valueOf(i));
        }

        strList.addAll(strListB);
        print(2, strList);
        strList.remove(0);
        print(3, strList);
        strList.remove(String.valueOf(0));
        print(4, strList);
        print(5, strList.get(1));   //1的位置是4

        Collections.reverse(strList);   //逆序
        print(6, strList);

        Collections.sort(strList);      //排序
        print(7, strList);

        Collections.sort(strList, new Comparator<String>()
        {
            @Override
            public int compare(String o1, String o2)
            {
                return o2.compareTo(o1);        //从大到小排序
            }
        });
        print(8, strList);


        for (String obj : strList)      //遍历
        {
            print(9, obj);
        }

        for (int i=0; i<strList.size(); i++)      //遍历
        {
            print(10, strList.get(i));
        }

        int[] array = new int[]{1, 2, 3};
        print(11, array[1]);
    }

    public static void demoMapTable()
    {
        Map<String, String> map = new HashMap<String, String>();
        for (int i=0; i<4; i++)
        {
            map.put(String.valueOf(i), String.valueOf(i*i));

        }

        print(1, map);

        for (Map.Entry<String, String> entry : map.entrySet())
        {
            print(2, entry.getKey()+"|"+entry.getValue());
        }

        print(3, map.values());
        print(4, map.keySet());
        print(5, map.get("3"));
        print(6, map.containsKey("A"));
        map.replace("3", "27");
        print(7, map.get("3"));
    }

    public static void demoSet()
    {
        Set<String> strSet = new HashSet<String>();
        for (int i=0; i<3; i++)
        {
            strSet.add(String.valueOf(i));
            strSet.add(String.valueOf(i));
            strSet.add(String.valueOf(i));
        }
        print(1, strSet);
        strSet.remove(String.valueOf(2));
        print(2, strSet);
        print(3, strSet.contains(String.valueOf(2)));
        print(4, strSet.isEmpty());
        print(5, strSet.size());

        strSet.addAll(Arrays.asList(new String[]{"A", "B", "C"}));
        print(6, strSet );
        for (String value : strSet)
        {
            print(7, value);
        }
    }

    public static void demoException()
    {
            try
            {
                int k = 2;
//                k = k / 0;
                if (k==2)
                {
                    throw new Exception("我故意的");
                }
            }
            catch (Exception e)
            {
                print(2, e.getMessage());
            }
            finally
            {
                print(3, "finally");
            }
    }

    public static void main(String[] args)
    {
//        print(1, "hello TZS!");
//        demoOperation();
//        demoString();
//        demoControlFlow();
//        demoList();
//        demoMapTable();
//        demoSet();
//        demoException();

    }
}
