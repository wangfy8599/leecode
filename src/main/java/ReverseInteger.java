import java.util.ArrayList;
import java.util.List;

public class ReverseInteger {
    public int reverse_1(int x) {
        if(x == 0x80000000) {
            // -0
            return 0;
        }
        boolean sign = x < 0;
        if(sign) {
            x = -x;
        }
        List<Integer> list = new ArrayList<>();
        while(true) {
            if(x < 10) {
                list.add(x);
                break;
            }
            list.add(x % 10);
            x /= 10;
        }

        long xl = 0;
        for(int i=0; i<list.size(); ++i) {
            int j = list.size() - 1 - i;
            xl += list.get(j) * Math.pow(10, i);
        }
        if(xl > 0x7FFFFFFF) {
            xl = 0L;
        }
        x = (int)xl;
        return sign ? -x : x;
    }

    public int reverse_2(int x) {
        if(x == 0x80000000) {
            // -0
            return 0;
        }
        boolean sign = x < 0;
        if(sign) {
            x = -x;
        }
        char[] array = new char[10];
        int i = 0;
        for(; i<10; ++i) {
            if(x < 10) {
                array[i] = (char)x;
                ++i;
                break;
            }
            array[i] = (char)(x % 10);
            x /= 10;
        }

        long xl = 0;
        for(int j=0; j<i; ++j) {
            int m = i - 1 - j ;
            xl += ((int)array[m]) * Math.pow(10, j);
        }
        if(xl > 0x7FFFFFFF) {
            xl = 0L;
        }
        x = (int)xl;
        return sign ? -x : x;
    }

    public int reverse_3(int x) {
        if(x == 0x80000000) {
            // -0
            return 0;
        }
        boolean sign = x < 0;
        if(sign) {
            x = -x;
        }

        long xl = 0;
        while(x != 0) {
            xl = xl * 10 + (x % 10);
            x /= 10;
        }

        if(xl > 0x7FFFFFFF) {
            xl = 0L;
        }
        x = (int)xl;
        return sign ? -x : x;
    }

    public int reverse(int x) {
        if(x == 0x80000000) {
            // -0
            return 0;
        }
        boolean sign = x < 0;
        if(sign) {
            x = -x;
        }

        long xr = 0L;
        while(x != 0) {
            xr = xr * 10 + (x % 10);
            if((xr & 0xFFFFFFFF80000000L) != 0) {
                //overflow
                return 0;
            }
            x /= 10;
        }

        x = (int)xr;
        return sign ? -x : x;
    }

    public static void main(String[] args) {
        ReverseInteger ri = new ReverseInteger();
        System.out.println(ri.reverse(1534236469));
        System.out.println(ri.reverse(-2147483412));
        System.out.println(ri.reverse(-2147483648));
        System.out.println(ri.reverse(123));
        System.out.println(ri.reverse(-123));
    }
}
