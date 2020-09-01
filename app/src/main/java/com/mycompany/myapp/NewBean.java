package com.mycompany.myapp;

import java.util.List;

public class NewBean {

    private int errorCode;

    private Data data;

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public Data getData() {
        return data;
    }

    public static class Data {

        private Result result;

        public void setResult(Result result) {
            this.result = result;
        }

        public Result getResult() {
            return result;
        }

        public static class Result {

            private A a;

            private List<B> b;

            private String c;

            public void setA(A a) {
                this.a = a;
            }

            public A getA() {
                return a;
            }

            public void setB(List<B> b) {
                this.b = b;
            }

            public List<B> getB() {
                return b;
            }

            public void setC(String c) {
                this.c = c;
            }

            public String getC() {
                return c;
            }

            public static class A {

                private String a;

                private String b;

                private String c;

                private String d;

                private String e;

                public void setA(String a) {
                    this.a = a;
                }

                public String getA() {
                    return a;
                }

                public void setB(String b) {
                    this.b = b;
                }

                public String getB() {
                    return b;
                }

                public void setC(String c) {
                    this.c = c;
                }

                public String getC() {
                    return c;
                }

                public void setD(String d) {
                    this.d = d;
                }

                public String getD() {
                    return d;
                }

                public void setE(String e) {
                    this.e = e;
                }

                public String getE() {
                    return e;
                }

            }

            public static class B {

                private String id;

                private String a;

                private String b;

                private String c;

                private String d;

                private String e;

                private String f;

                private String h;

                private String i;

                private String j;

                private String l;

                private String m;

                private String n;

                private String o;

                private String p;

                public void setId(String id) {
                    this.id = id;
                }

                public String getId() {
                    return id;
                }

                public void setA(String a) {
                    this.a = a;
                }

                public String getA() {
                    return a;
                }

                public void setB(String b) {
                    this.b = b;
                }

                public String getB() {
                    return b;
                }

                public void setC(String c) {
                    this.c = c;
                }

                public String getC() {
                    return c;
                }

                public void setD(String d) {
                    this.d = d;
                }

                public String getD() {
                    return d;
                }

                public void setE(String e) {
                    this.e = e;
                }

                public String getE() {
                    return e;
                }

                public void setF(String f) {
                    this.f = f;
                }

                public String getF() {
                    return f;
                }

                public void setH(String h) {
                    this.h = h;
                }

                public String getH() {
                    return h;
                }

                public void setI(String i) {
                    this.i = i;
                }

                public String getI() {
                    return i;
                }

                public void setJ(String j) {
                    this.j = j;
                }

                public String getJ() {
                    return j;
                }

                public void setL(String l) {
                    this.l = l;
                }

                public String getL() {
                    return l;
                }

                public void setM(String m) {
                    this.m = m;
                }

                public String getM() {
                    return m;
                }

                public void setN(String n) {
                    this.n = n;
                }

                public String getN() {
                    return n;
                }

                public void setO(String o) {
                    this.o = o;
                }

                public String getO() {
                    return o;
                }

                public void setP(String p) {
                    this.p = p;
                }

                public String getP() {
                    return p;
                }

            }

        }

    }

}