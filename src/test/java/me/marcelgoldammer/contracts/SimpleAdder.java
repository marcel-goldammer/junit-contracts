package me.marcelgoldammer.contracts;

class SimpleAdder implements Adder {
    @Override
    public int add(int a, int b) {
        return a + b;
    }
}
