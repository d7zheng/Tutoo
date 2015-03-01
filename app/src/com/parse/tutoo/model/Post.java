package com.parse.tutoo.model;

import com.parse.ParseClassName;
import com.parse.ParseObject;

/**
 * Created by hilary on 25/02/2015.
 */
@ParseClassName("Post")
public class Post extends ParseObject {
        private String title;
        private String description;
        private int postId;
        private Category category;

        public Post(String title, String description, int postId, Category category) {
            this.title = title;
            this.description = description;
            this.postId = postId;
            this.category = category;
        }
        public Post() {

        }
        public void setTitleAndDescription(String title, String description) {
            this.title = title;
            this.description = description;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public void setPostId(int postId) { this.postId = postId;}

        public void setCategory(Category category) {this.category = category; }

        public String getTitle() {
            return this.title;
        }

        public String getDescription() {
            return this.description;
        }

        public int getPostId () { return this.postId;}

        public Category getCategory () {return this.category;}

        public void setAttributes(String title, String description, int postId, Category category) {
            this.title = title;
            this.description = description;
            this.postId = postId;
            this.category = category;
        }
}
