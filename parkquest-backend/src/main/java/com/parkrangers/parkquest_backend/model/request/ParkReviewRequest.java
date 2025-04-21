package com.parkrangers.parkquest_backend.model.request;

public class ParkReviewRequest {
        private Long userId;
        private String parkCode;
        private String content;
        private int rating;



        public Long getUserId() {
            return userId;
        }

        public void setUserId(Long userId) {
            this.userId = userId;
        }

        public String getParkCode() {
            return parkCode;
        }

        public void setParkCode(String parkCode) {
            this.parkCode = parkCode;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public int getRating() {
            return rating;
        }

        public void setRating(int rating) {
            this.rating = rating;
        }
    }