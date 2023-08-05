package com.example.finalproject;

    public class NewsItem {
        private String title;
        private String description;
        private String date;
        private String link;


        public NewsItem(String title, String description, String date, String link) {
            this.title = title;
            this.description = description;
            this.date = date;
            this.link = link;
        }
        public String getTitle() {
            return title;
        }

        public String getDescription() {
            return description;
        }

        public String getDate() {
            return date;
        }

        public String getLink() {
            return link;
        }

    }
