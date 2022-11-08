package edu.alevel.danylenko.diploma.transformer;

public interface Transformer<FROM, TO> {
    TO transform(FROM chat);
}
