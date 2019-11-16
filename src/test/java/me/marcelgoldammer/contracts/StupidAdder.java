package me.marcelgoldammer.contracts;

class StupidAdder implements Adder {
    @Override
    public int add(int a, int b) {
        int sum = a;
        for (int i = 0; i < b; i++) {
            sum++;
        }
        return sum;
    }
}
