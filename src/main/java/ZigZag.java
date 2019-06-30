
public class ZigZag {

    // Addition Memory
    public String convert_2(String s, int numRows) {
        if(numRows == 1) {
            return s;
        }
        int len = s.length();
        if(len <= numRows) {
            return s;
        }
        int lastRow = numRows - 1;
        int unit = lastRow << 1;
        int remain = len % unit;
        int cols;
        if(remain == 0) {
            cols = len >> 1;
        } else if(remain <= numRows) {
            cols = ((len - remain) >> 1) + 1;
        } else {
            cols = ((len + remain) >> 1) - numRows + 1;
        }

        char[][] metrics = new char[numRows][cols];
        int i = -1;
        int j = 0;
        int n;
        boolean moveDown = true;
        for(n=0; n<len; ++n) {
            if(moveDown) {
                if(++i == lastRow) {
                    moveDown = false;
                }
            } else {
                ++j;
                if(--i == 0) {
                    moveDown = true;
                }
            }
            metrics[i][j] = s.charAt(n);
        }
        char[] output = new char[len];
        n = 0;
        for(i=0; i<numRows; ++i) {
            for(j=0; j<cols; ++j) {
                char c = metrics[i][j];
                if(c != 0) {
                    output[n++] = c;
                }
            }
        }
        return new String(output);
    }

    public String convert_3(String s, int numRows) {
        if(numRows == 1) {
            return s;
        }
        int len = s.length();
        if(len <= numRows) {
            return s;
        }
        int lastRow = numRows - 1;
        int unit = lastRow << 1;
        int remain = len % unit;
        int cols;
        if(remain == 0) {
            cols = len >> 1;
        } else if(remain <= numRows) {
            cols = ((len - remain) >> 1) + 1;
        } else {
            cols = ((len + remain) >> 1) - numRows + 1;
        }

        int n = 0;
        char[] output = new char[len];
        // for i = 0;
        for(int j=0; j<len; j+=unit) {
            output[n++] = s.charAt(j);
        }
        for(int i=1; i<lastRow; ++i) {
            for(int j=0; j<cols; ++j) {
                int m = j*unit + i;
                if(m < len) {
                    output[n++] = s.charAt(m);

                    m += unit - 2 * i;
                    if(m < len) {
                        output[n++] = s.charAt(m);
                    }
                }
            }
        }
        // for i = lastRow;
        for(int j=lastRow; j<len; j+=unit) {
            output[n++] = s.charAt(j);
        }
        return new String(output);
    }

    public String convert(String s, int numRows) {
        if(numRows == 1) {
            return s;
        }
        int len = s.length();
        if(len <= numRows) {
            return s;
        }
        int lastRow = numRows - 1;
        int unit = lastRow << 1;

        int n = 0;
        char[] output = new char[len];
        // for i = 0;
        for(int i=0; i<len; i+=unit) {
            output[n++] = s.charAt(i);
        }
        for(int i=1; i<lastRow; ++i) {
            for(int j=i; j<len; j+=unit) {
                output[n++] = s.charAt(j);
                int m = j + unit - 2 * i;
                if(m < len) {
                    output[n++] = s.charAt(m);
                }
            }
        }
        // for i = lastRow;
        for(int j=lastRow; j<len; j+=unit) {
            output[n++] = s.charAt(j);
        }
        return new String(output);
    }

    public static void main(String[] args) {
        ZigZag zigZag = new ZigZag();
        System.out.println(zigZag.convert("PAYPALISHIRING", 3));
        System.out.println(zigZag.convert("ABCDE", 4));
        System.out.println(zigZag.convert("ABCD", 2));
        System.out.println(zigZag.convert("PAYPALISHIRING", 4));
    }
}