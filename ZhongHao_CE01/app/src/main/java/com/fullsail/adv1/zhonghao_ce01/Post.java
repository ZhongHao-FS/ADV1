// Hao Zhong
// AD1 - 202111
// Post.java
package com.fullsail.adv1.zhonghao_ce01;

import androidx.annotation.NonNull;

import java.io.Serializable;

public class Post implements Serializable {
    private final String title;
    private final String numComments;

    // Constructor
    public Post(String _title, String _numComments) {
        this.title = _title;
        this.numComments = _numComments;
    }

    @NonNull
    @Override
    public String toString() {
        return "{" + numComments + "} " + title;
    }
}
