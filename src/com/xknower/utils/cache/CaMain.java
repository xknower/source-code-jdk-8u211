package com.xknower.utils.cache;

public class CaMain {

    public static void main(String[] args) {
        DefaultMemory<String, String> defaultMemory = new DefaultMemory();
        defaultMemory.set("a", "v");
        System.out.println(defaultMemory.get("a"));
    }
}
